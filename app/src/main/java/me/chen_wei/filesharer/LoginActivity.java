package me.chen_wei.filesharer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_user_name)
    EditText inputUserName;
    @BindView(R.id.input_phone_name)
    EditText inputPhoneName;
    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.link_signup)
    TextView signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    /**
     * 登陆
     *
     * @param button
     */
    @OnClick(R.id.btn_login)
    public void login(Button button) {
        Intent intent = new Intent(this, WiFiDirectActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * 注册
     *
     * @param view
     */
    @OnClick(R.id.link_signup)
    public void signUp(View view) {
    }


}
