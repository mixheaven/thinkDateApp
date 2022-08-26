package com.lebeid.thinkdateapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lebeid.thinkdateapp.R;
import com.lebeid.thinkdateapp.models.Birthday;

import java.util.ArrayList;

import utils.Util;

public class BirthdayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<ListItem> mListItems;

    public BirthdayAdapter(Context mContext, ArrayList<ListItem> listItems) {
        this.mContext = mContext;
        this.mListItems = listItems;
    }

    public void setListItems(ArrayList<ListItem> mListItems) {
        this.mListItems = mListItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mListItems.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case ListItem.TYPE_BIRTHDAY:
                View v1 = LayoutInflater.from(mContext).inflate(R.layout.item_birthday, parent, false);
                return new BirthDayViewHolder(v1);
            case ListItem.TYPE_MONTH:
                View v2 = LayoutInflater.from(mContext).inflate(R.layout.item_month, parent, false);
                return new MonthViewHolder(v2);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case ListItem.TYPE_BIRTHDAY:
                Birthday birthday = ((BirthdayItem) mListItems.get(position)).birthday;
                BirthDayViewHolder birthDayViewHolder = (BirthDayViewHolder) viewHolder;
                birthDayViewHolder.mTextViewFirstname.setText(birthday.firstname);
                birthDayViewHolder.mTextViewLastname.setText(birthday.lastname);
                birthDayViewHolder.mTextViewDate.setText(birthday.date.getDate()+"");
                birthDayViewHolder.mTextViewAge.setText(Util.getAge(birthday.date) + " ans");
                break;
            case ListItem.TYPE_MONTH:
                MonthItem monthItem = (MonthItem) mListItems.get(position);
                MonthViewHolder monthViewHolder = (MonthViewHolder) viewHolder;
                monthViewHolder.mTextViewMonth.setText(monthItem.month);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public class BirthDayViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewDate;
        private TextView mTextViewFirstname;
        private TextView mTextViewLastname;
        private TextView mTextViewAge;

        public BirthDayViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewDate = itemView.findViewById(R.id.item_birthday_date);
            mTextViewFirstname = itemView.findViewById(R.id.item_firstname);
            mTextViewAge = itemView.findViewById(R.id.item_age);
            mTextViewLastname = itemView.findViewById(R.id.item_lastname);
        }
    }

    public class MonthViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewMonth;

        public MonthViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewMonth = itemView.findViewById(R.id.text_view_item_month);
        }
    }
}
