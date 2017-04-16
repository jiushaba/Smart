package com.zdlog.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.entity.MyUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.ui
 * 文件名：ForgetPasswordActivity
 * 创建者：jiushaba
 * 创建时间：2017/4/11 0011上午 10:27
 * 描述： 忘记/重置密码
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_forget_password;
    private EditText et_email;
    private EditText et_now;
    private EditText et_new;
    private EditText et_new_password;
    private Button btn_updata_password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    //初始化View
    private void initView() {
        btn_forget_password= (Button) findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);
        et_email= (EditText) findViewById(R.id.et_email);
        et_now= (EditText) findViewById(R.id.et_now);
        et_new= (EditText) findViewById(R.id.et_new);
        et_new_password= (EditText) findViewById(R.id.et_new_password);
        btn_updata_password= (Button) findViewById(R.id.btn_updata_password);
        btn_updata_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_forget_password:
                //1、获取输入框的邮箱
                final String email=et_email.getText().toString().trim();
                //2、判断是否为空
                if (!TextUtils.isEmpty(email)){
                    //3、判断是否为邮箱
                    if (isEmail(email)){
                        //4、发送邮件
                        MyUser.resetPasswordByEmail(email, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Toast.makeText(ForgetPasswordActivity.this,"邮件已经发送至："+email,Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(ForgetPasswordActivity.this,"邮件发送失败",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }else {
                        Toast.makeText(this,"输入的不是邮箱",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_updata_password:
                //1、说去输入框的值
                String now=et_now.getText().toString().trim();
                String news=et_new.getText().toString().trim();
                String new_password=et_new_password.getText().toString().trim();
                //2、判断是否为空
                if (!TextUtils.isEmpty(now)&!TextUtils.isEmpty(news)&!TextUtils.isEmpty(new_password)){
                    //判断两次新密码是否一致
                    if (news.equals(new_password)){
                        //4、重置密码
                        MyUser.updateCurrentUserPassword(now, news, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else {
                        Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }
}
