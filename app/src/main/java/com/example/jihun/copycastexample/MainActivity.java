package com.example.jihun.copycastexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //TODO list
    //ShareToKaKao를 카카오톡아이콘모양으로 바꾸기
    //다양한 버튼들을 설정화면에서 추가/삭제 할수 있도록 셋팅페이지 만들기
    //카카오,페이스북,트위터,공유 등등


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(getBaseContext(), CopyCastService.class));
        finish();
    }

}
