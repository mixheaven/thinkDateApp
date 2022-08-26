package com.lebeid.thinkdateapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


import com.lebeid.thinkdateapp.R;

import org.json.JSONException;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import utils.ApiCallback;
import utils.Util;
import utils.UtilApi;

public class LoginActivity extends AppCompatActivity implements ApiCallback {

    private EditText mEmailView;
    private EditText mPasswordView;

    private TextView mErrorMessage;

    private View mProgressView;
    private View mLoginFormView;

    public Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handler = new Handler();

        mEmailView = findViewById(R.id.username);
        mErrorMessage = findViewById(R.id.error_message);
        mPasswordView = findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login);
        mProgressView = findViewById(R.id.loading);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();

                mLoginFormView.setEnabled(Util.isEmailValid(email) && Util.isPasswordValid(password));
            }
        };

        mEmailView.addTextChangedListener(textWatcher);
        mPasswordView.addTextChangedListener(textWatcher);

        mPasswordView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
               Util.isPasswordValid(mPasswordView.getText().toString());
            }
            return false;
        });

        mLoginFormView.setOnClickListener(v -> {
            attemptLogin();
          });
    }

    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        Log.d("ATTEMPT LOGIN", email);
        Log.d("ATTEMPT LOGIN", password);

        boolean cancel = false;
        View focusView = null;

        if (!Util.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.invalid_password));
            focusView = mPasswordView;
            cancel = true;
            Log.d("ISPASSWORD VALID", "NO");
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!Util.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.invalid_username));
            focusView = mEmailView;
            cancel = true;
            Log.d("ISEMAIL VALID", "NO");
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);

            Map<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);
            Log.d("SHOW PROGRESS", "LAUNCH REQUEST");
            UtilApi.post("http://10.0.2.2:8080/users/login", map, this);
        }
    }

    private void showProgress(boolean visible) {
        mProgressView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void fail(final String json) {
        mProgressView.setVisibility(View.INVISIBLE);
        handler.post(() -> {
            Log.d("API MESSAGE", "fail: " + json);
            mErrorMessage.setVisibility(TextView.VISIBLE);
            // TODO : Etablisser un comportement lors d'un fail

        });
    }

    @Override
    public void success(final String json) {

        handler.post(() -> {
            Log.d("API MESSAGE", "success: " + json);
            Intent intent = new Intent(this, MainActivity.class);
            Util.setUser(this, json);
            try {
                Log.d("user", Util.getUser(this).lastname);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startActivity(intent);
            mProgressView.setVisibility(View.INVISIBLE);
        });
    }
}