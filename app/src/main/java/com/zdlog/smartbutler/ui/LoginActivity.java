package com.zdlog.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zdlog.smartbutler.MainActivity;
import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.entity.MyUser;
import com.zdlog.smartbutler.utils.ShareUtils;
import com.zdlog.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.ui
 * 文件名：LoginActivity
 * 创建者：jiushaba
 * 创建时间：2017/4/10 0010下午 5:25
 * 描述： 登录+
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //注册
    private Button btn_registered;
    private EditText et_name;
    private EditText et_password;
    private Button btnLogin;
    private CheckBox keep_password;
    private TextView tv_forget;
    private CustomDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initView();
    }

    private void initView() {

        btn_registered= (Button) findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);
        et_name= (EditText) findViewById(R.id.et_name);
        et_password= (EditText) findViewById(R.id.et_password);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        keep_password= (CheckBox) findViewById(R.id.keep_password);

        //设置选中的状态
        boolean isCheck = ShareUtils.getBoolen(this, "keeppass", false);
        keep_password.setChecked(isCheck);
        if (isCheck){
            //设置用户名和密码
            et_name.setText(ShareUtils.getString(this,"name",""));
            et_password.setText(ShareUtils.getString(this,"password",""));
        }else {
            et_name.setText("");
            et_password.setText("");
        }

        tv_forget= (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);
        dialog=new CustomDialog(this,100,100,R.layout.dialog_loging,R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_forget:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.btn_registered:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
            case R.id.btnLogin:
                //获取输入框的值
                String name=et_name.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(name)&!TextUtils.isEmpty(password)){
                    dialog.show();
                    //登录
                    final MyUser user=new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if (e==null){
                                //判断邮箱是否验证
                                if (user.getEmailVerified()){
                                    //跳转
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this,"请前往邮箱验证后再登录",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this,"登录失败"+e.toString(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //假设我现在输入用户名和密码，但是我不点击登录，而是直接退出

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        ShareUtils.putBoolen(this,"keeppass",keep_password.isChecked());
        //是否记住密码
        if (keep_password.isChecked()){
            //记住用户名和密码
            ShareUtils.putString(this,"name",et_name.getText().toString().trim());
            ShareUtils.putString(this,"password",et_password.getText().toString().trim());
        }
    }
}
