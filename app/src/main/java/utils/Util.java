package utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.lebeid.thinkdateapp.adapters.BirthdayItem;
import com.lebeid.thinkdateapp.adapters.ListItem;
import com.lebeid.thinkdateapp.adapters.MonthItem;
import com.lebeid.thinkdateapp.models.Birthday;
import com.lebeid.thinkdateapp.models.User;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final String PREF_FILE = "pref_file";
    private static final String USER = "user";

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat FORMAT_INPUT = new SimpleDateFormat("dd/MM/yyyy");

    public static void setUser(Context context, String json) {
        context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit().putString(USER, json).apply();
    }

    public static User getUser(Context context) throws JSONException, ParseException {
        return new User(context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).getString(USER, ""));
    }

    public static Date initDateFromDB(String str) throws ParseException {
        return FORMAT.parse(str);
    }

    public static String printDate(Date date) {
        return FORMAT.format(date);
    }

    public static long getAge(Date date) {
        long diff = System.currentTimeMillis() - date.getTime();
        return diff / 31622400000l;
    }

    public static boolean isEmailValid(String userName) {
        if (userName == null || TextUtils.isEmpty(userName)) {
            return false;
        }
        return userName.trim().length() > 4;
    }

    public static boolean isPasswordValid(String password) {
        if (password == null || TextUtils.isEmpty(password)) {
            return false;
        }
        return password.trim().length() > 2;
    }

    public static ArrayList<ListItem> createListItems(ArrayList<Birthday> birthdays) {

        ArrayList<ListItem> listItems = new ArrayList<>();

        int monthNumber = 0;
        String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

        for (monthNumber = 0; monthNumber < months.length; monthNumber++) {
            listItems.add(new MonthItem(monthNumber, months[monthNumber]));

            for (int i = 0; i < birthdays.size(); i++) {
                if (compare(birthdays.get(i).date, months[monthNumber])) {
                    listItems.add(new BirthdayItem(birthdays.get(i)));
                }
            }
        }
        // TODO : trier la liste en fonction des mois d'anniversaire

        return listItems;
    }

    public static boolean compare(Date birthdayDate, String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.FRANCE);
        String birthdayMonth = sdf.format(birthdayDate);

        if (birthdayMonth.equalsIgnoreCase(month)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isDateValid(String string) {
        try {
            FORMAT_INPUT.parse(string);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Date initDateFromEditText(String str) throws ParseException {
        return FORMAT_INPUT.parse(str);
    }
}
