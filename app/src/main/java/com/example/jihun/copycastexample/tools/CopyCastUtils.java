package com.example.jihun.copycastexample.tools;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jihun on 2017-03-02.
 */

public class CopyCastUtils {
    public static final String URL_REGEX = "((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";


    static public boolean isUrl(String string) {
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(string);//replace with string to compare
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
//    static public void saveClipString(Context context, String string) {
//        SharedPreferences pref = context.getSharedPreferences("FormerClipString", Context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = pref.edit();
//        edit.putString("FormerClipString", string);
//        edit.commit();
//    }
//
//    static public String loadClipString(Context context) {
//        SharedPreferences pref = context.getSharedPreferences("FormerClipString", Context.MODE_PRIVATE);
//        String savedString = pref.getString("FormerClipString", null);    // 진동 허용 여부
//        return savedString;
//    }
}
