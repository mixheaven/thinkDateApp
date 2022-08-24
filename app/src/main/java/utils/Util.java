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

public class Util {

    private static final String PREF_FILE = "pref_file";
    private static final String USER = "user";

    public static void setUser(Context context, String json) {

        // TODO : sauvegarder
    }

    public static User getUser(Context context) throws JSONException, ParseException {
        // TODO : restaurer
        return null;
    }

    public static boolean isUserNameValid(String userName) {
        // TODO : écrire votre règle pour un username valide
        return false;
    }

    public static boolean isPasswordValid(String password) {
        // TODO : écrire votre règle pour un password valide
       return false;
    }
}
