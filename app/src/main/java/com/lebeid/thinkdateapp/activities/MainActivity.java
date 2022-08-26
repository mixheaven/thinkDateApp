package com.lebeid.thinkdateapp.activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lebeid.thinkdateapp.R;
import com.lebeid.thinkdateapp.adapters.BirthdayAdapter;
import com.lebeid.thinkdateapp.adapters.ListItem;
import com.lebeid.thinkdateapp.databinding.ActivityMainBinding;
import com.lebeid.thinkdateapp.models.Birthday;
import com.lebeid.thinkdateapp.models.User;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

import utils.ApiCallback;
import utils.Util;


public class MainActivity extends AppCompatActivity implements ApiCallback {

    private ActivityMainBinding binding;
    private User user;
    private BirthdayAdapter mBirthdayAdapter;

    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler =  new Handler();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            user = Util.getUser(this);
            Log.d("USERBIRTHDAY", String.valueOf(user.birthdays));
        }
        catch (Exception e) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        ArrayList<ListItem> listItems = Util.createListItems(user.birthdays);
        Log.d("LISTITEMSSSSSS", String.valueOf(listItems));

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mBirthdayAdapter = new BirthdayAdapter(this, listItems);
        recyclerView.setAdapter(mBirthdayAdapter);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*private void showDialogAddNewBirthday() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_add_new_birthdate, null);
        final EditText editTextFirstName = view.findViewById(R.id.edit_text_text_first_name);
        final EditText editTextLastName = view.findViewById(R.id.edit_text_text_last_name);
        final EditText editTextDate = view.findViewById(R.id.edit_text_text_date);

        editTextDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Util.isDateValid(s.toString())) {
                    editTextDate.setError("Date incorrecte");
                }
            }
        });

        builder.setTitle("Nouvel anniversaire ?");
        builder.setView(view);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // TODO : récupérer les valeurs et appeler la méthode addNewBirthday

            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void fail(String json) {
        handler.post(() -> {
            Log.d("HOME MESSAGE", "fail: " + json);
            // TODO : Etablisser un comportement lors d'un fail

        });
    }

    @Override
    public void success(String json) {
        handler.post(() -> {
            Log.d("HOME MESSAGE", "success: " + json);

        });
    }

    public void showList(String jsonResponse) {
        final ObjectMapper objectMapper = new ObjectMapper();

        Birthday birthday = null;

        try {
            birthday = objectMapper.readValue(jsonResponse, Birthday.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}