package com.example.pingkunhuang.dagger2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.annotation_declare.DIBindOnClickEvent;
import com.example.annotation_declare.DIBindView;
import com.example.annotation_inject.inject.ViewInject;

public class MainActivity extends AppCompatActivity {
    @DIBindView(viewId = R.id.userName)
    TextView userName;
    @DIBindView(viewId = R.id.password)
    TextView password;
    @DIBindView(viewId = R.id.login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInject.inject(this);
    }

    @DIBindOnClickEvent(viewId = R.id.login)
    public void onLogin() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }
}
