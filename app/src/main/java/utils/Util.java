package utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.lebeid.thinkdateapp.models.User;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final String PREF_FILE = "pref_file";
    private static final String USER = "user";

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[%*$_+=])[A-Za-z\\d%*$-+=]{5,}$";
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    public static void setUser(Context context, String json) {

        // TODO : sauvegarder
    }

    public static User getUser(Context context) throws JSONException, ParseException {
        // TODO : restaurer
        return null;
    }

    public static boolean isUserNameValid(String email) {
        if (email.contains("@")) {
            return true;
        }else{
            return false;
        }
    }

    public static boolean isPasswordValid(String password) {

            Matcher matcher = passwordPattern.matcher(password);
            return matcher.matches();


    }
}
