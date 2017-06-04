package com.example.jihun.copycastexample.tools;

import android.accounts.Account;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.urlshortener.Urlshortener;
import com.google.api.services.urlshortener.model.Url;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by Jihun on 2017-06-04.
 */

public class UrlShortenTask extends AsyncTask<String, Void, String> {

    public final static int RC_AUTHORIZE_URLSHORTENER = 1931;
    public final static String URLSHORTENER_SCOPE_URL = "https://www.googleapis.com/auth/urlshortener";
    public final static HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    public final static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    Context mContext;
    GoogleApiClient mGoogleApiClient;
    Account mAuthorizedAccount;
    EditText editText;
    String clipString;

    public UrlShortenTask(Context mContext, Account mAuthorizedAccount, EditText editText, String clipString) {
        this.mContext = mContext;
        this.mAuthorizedAccount = mAuthorizedAccount;
        this.editText = editText;
        this.clipString = clipString;
    }

    @Override
    protected String doInBackground(String... params) {

        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        mContext,
                        Collections.singleton(
                                URLSHORTENER_SCOPE_URL)
                );
        credential.setSelectedAccount(mAuthorizedAccount);
        Urlshortener shortener = new Urlshortener.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("CopyCaseExample")
                .build();
        Url longUrl = new Url();
        Url shortUrl;
        longUrl.setLongUrl(params[0]);

        try {
            shortUrl = shortener.url().insert(longUrl).execute();
        } catch (Exception e) {
            Log.e("abc", "error : " + e);
            e.printStackTrace();
            return null;
        }
        return shortUrl.getId();
    }

    @Override
    protected void onPostExecute(String s) {
        editText.setText(clipString + "\n\n" + s);
    }
}