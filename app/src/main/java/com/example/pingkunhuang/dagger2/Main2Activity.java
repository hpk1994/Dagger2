package com.example.pingkunhuang.dagger2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.annotation_declare.DIBindView;

public class Main2Activity extends AppCompatActivity {
    @DIBindView(viewId = R.id.one)
    TextView one;
    @DIBindView(viewId = R.id.two)
    TextView two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
