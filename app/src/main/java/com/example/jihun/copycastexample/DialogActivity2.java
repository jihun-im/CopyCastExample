package com.example.jihun.copycastexample;

/**
 * Created by Jihun on 2017-02-05.
 */

import android.accounts.Account;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.jihun.copycastexample.tools.UrlShortenTask;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

public class DialogActivity2 extends AppCompatActivity implements
        OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    public GoogleApiClient mGoogleApiClient;
    public Account mAuthorizedAccount;

    private Button buttonShare, buttonCancel;
    private String clipDataString;
    private EditText editText;
    private String clipString = "";
    private String shortUrl = "";
    private String longUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authorizeAccess();

        longUrl = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_layout);
        setContent();
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            try {
                clipString = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
            } catch (Exception e) {
                clipString = "";
            }
        } else {
            clipString = "";
        }
        editText.setText(clipString + "\n\n" + longUrl);
    }

    @Override
    protected void onPause() {
        //팝업Activity의 외부화면 클릭시-> 소멸
        if (mAuthorizedAccount != null) {
            //구글로그인 했을시에
            this.finish();
        }
        super.onPause();
    }

    private void setContent() {
        buttonShare = (Button) findViewById(R.id.btn_share);
        buttonCancel = (Button) findViewById(R.id.btn_cancel);
        editText = (EditText) findViewById(R.id.edittext_clip);
        buttonShare.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share:
                startSharing();
                this.finish();
                break;
            case R.id.btn_cancel:
                this.finish();
                break;
            default:
                break;
        }
    }

    public void startSharing() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Log.d("CopyCast", editText.getText().toString());
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
        //sharingIntent.setPackage("com.kakao.talk");
        sharingIntent.setClassName("com.kakao.talk", "com.kakao.talk.activity.SplashConnectActivity");
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    private void authorizeAccess() {
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope(UrlShortenTask.URLSHORTENER_SCOPE_URL))
                        .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, UrlShortenTask.RC_AUTHORIZE_URLSHORTENER);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("abc", "onActivityResult");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == UrlShortenTask.RC_AUTHORIZE_URLSHORTENER) {
            Log.d("abc", "RC_AUTHORIZE_URLSHORTENER");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
                mAuthorizedAccount = googleSignInAccount.getAccount();
                Log.d("abc", "signed in success: " + mAuthorizedAccount.name + ", type: " + mAuthorizedAccount.type);
                if (mAuthorizedAccount != null) {
                    UrlShortenTask task = new UrlShortenTask(DialogActivity2.this, mAuthorizedAccount, editText, clipString);
                    task.execute(longUrl);
                }
            }
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("abc", "onConnectionFailed");
    }

}