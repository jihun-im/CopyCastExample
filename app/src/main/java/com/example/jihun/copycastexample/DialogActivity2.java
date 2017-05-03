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

import com.example.jihun.copycastexample.tools.CopyCastUtils;

public class DialogActivity2 extends Activity implements
        OnClickListener {

    private Button buttonShare, buttonCancel;
    private String clipDataString;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CharSequence text = getIntent()
                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_layout);
        setContent();
        editText.setText(CopyCastUtils.loadClipString(this) + "\n\n" + text);
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