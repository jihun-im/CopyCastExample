package com.example.jihun.copycastexample;

/**
 * Created by Jihun on 2017-02-05.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jihun.copycastexample.tools.CopyCastUtils;
import com.example.jihun.copycastexample.tools.GoogleService;
import com.example.jihun.copycastexample.tools.JsonUrl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogActivity2 extends Activity implements
        OnClickListener {

    private Button buttonShare, buttonCancel;
    private String clipDataString;
    private EditText editText;
    private String clipString;
    private String shortUrl;
    private String longUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        longUrl = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_layout);
        setContent();
        clipString = CopyCastUtils.loadClipString(this);
        editText.setText(clipString + "\n\n" + longUrl);

        GoogleService googleService = GoogleService.retrofit.create(GoogleService.class);
        Call<JsonUrl> call = googleService.getShortUrl(new JsonUrl(longUrl));
        call.enqueue(new Callback<JsonUrl>() {
            @Override
            public void onResponse(Call<JsonUrl> call, Response<JsonUrl> response) {
                shortUrl = response.body().getId();
                editText.setText(clipString + "\n\n" + shortUrl);
            }

            @Override
            public void onFailure(Call<JsonUrl> call, Throwable t) {
                Log.d("abc", "onFailure");
                Toast.makeText(getApplicationContext(), "response failed!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onPause() {
        //팝업Activity의 외부화면 클릭시-> 소멸
        this.finish();
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

}