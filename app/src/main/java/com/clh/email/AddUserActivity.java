package com.clh.email;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView title;
    private EditText email,password;
    private ImageView showPassword,hidePassword;
    private LinearLayout login;
    private String emailDomainName;
    private Toolbar toolbar;
    //实例化AutoEnglishKeyboard的对象
    AutoEnglishKeyboard autoEnglishKeyboard = new AutoEnglishKeyboard();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduser);

        //沉浸式状态栏和导航栏
        ImmersionBar
                .with(this)  //绑定上下文环境
                .transparentStatusBar()  //设置状态栏为透明
                .statusBarDarkFont(true)  //设置状态栏字体为黑色
                .navigationBarColor(R.color.transparent)  //设置导航栏为透明
                .navigationBarDarkIcon(true)  //设置导航栏字体为黑色
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)  //隐藏导航栏
                .fitsSystemWindows(true)  //不被控件覆盖状态栏
                .init();  //初始化和应用

        //初始化控件
        toolbar = findViewById(R.id.tb_adduser);
        title = findViewById(R.id.tv_adduser_title);
        Spinner emailAddressSpinner = findViewById(R.id.sn_adduser_email_address);
        showPassword = findViewById(R.id.bt_adduser_show_password);
        hidePassword = findViewById(R.id.bt_adduser_hide_password);
        email = findViewById(R.id.et_adduser_email);
        password = findViewById(R.id.et_adduser_password);
        login = findViewById(R.id.bt_adduser_add_email);

        //EditText默认设置为英文输入法
        autoEnglishKeyboard.EnglishKeyboard(this,email);
        autoEnglishKeyboard.EnglishKeyboard(this,password);

        //Toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Spinner
        emailAddressSpinner.setDropDownVerticalOffset(10);
        emailAddressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //获取选择的邮箱地址后缀
                emailDomainName = emailAddressSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //password设置监听事件
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int emailLength = email.length();
                int passwordLength = password.length();
                if(passwordLength == 0){
                    //login设置不可点击，设置不可点击颜色
                    login.setClickable(false);
                    login.setBackgroundColor(AddUserActivity.this.getResources().getColor(R.color.button_blue_unselected));
                }
                else if(emailLength > 0 && passwordLength > 6){
                    login.setClickable(true);
                    login.setBackgroundColor(AddUserActivity.this.getResources().getColor(R.color.button_blue));
                }
            }
        });
    }

    //password格式验证的正则表达式
    public boolean verifyPassword(String Password){
        //首位不能是数字，不能全是数字，不能全是英文，英文和数字混合，字符位数在6位~16位
        String regex = "^(?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Password);
        boolean isMatch = matcher.matches();
        return isMatch;
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        *  控件的显示和隐藏
        *  VISIBLE - 正常显示
        *  INVISIBLE - 不显示，但会占据显示空间
        *  GONE - 不显示，但不会占据显示空间
        */
        //在打开页面时，隐藏showPassword
        showPassword.setVisibility(View.GONE);
        //login设置不可点击，设置不可点击颜色
        login.setClickable(false);
        login.setBackgroundColor(AddUserActivity.this.getResources().getColor(R.color.button_blue_unselected));
        //password为密文
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        //当第一次打开APP时，将Toolbar隐藏，占据显示空间
        //toolbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.bt_adduser_hide_password){
            //点击之后显示明文
            showPassword.setVisibility(View.VISIBLE);
            hidePassword.setVisibility(View.GONE);
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            //设置光标在最后
            password.setSelection(password.length());
        }
        else if(id == R.id.bt_adduser_show_password){
            //点击之后显示密文
            showPassword.setVisibility(View.GONE);
            hidePassword.setVisibility(View.VISIBLE);
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            //设置光标在最后
            password.setSelection(password.length());
        }
        else if(id == R.id.bt_adduser_add_email){
            String emailName = email.getText().toString();
            String Email = emailName + emailDomainName;
            String Password = password.getText().toString();
            //判断当前用户在数据库是否存在

            //判断密码是否符合规则
            boolean checkPassword = verifyPassword(Password);
            if(checkPassword){
                //延时操作
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddUserActivity.this,"成功",Toast.LENGTH_SHORT).show();
                    }
                },1000);
            }else{
                Toast.makeText(this, "请输入正确的密码格式", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "密码格式:\n1.首位不能是数字\n2.不能全是数字\n3.不能全是英文\n4.6~16位英文和数字混合", Toast.LENGTH_LONG).show();
            }
        }
    }
}
