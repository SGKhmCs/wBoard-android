package ua.tsisar.wboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements AuthenticateService.AuthenticateListener{

    private static final int REQUEST_CODE_MAIN = 1;
    private static final int RESULT_SIGN_OUT = -2;

    private EditText userName;
    private EditText password;
    private CheckBox rememberMe;

    private AuthenticateService authenticateService;

    public Context getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authenticateService = new AuthenticateService(this);
        authenticateService.isAuthenticated();

        userName = (EditText) findViewById(R.id.userName_editText);
        password = (EditText) findViewById(R.id.password_editText);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe_checkBox);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_MAIN:
                if(resultCode == RESULT_SIGN_OUT) {
                    singOut();
                }
                break;
        }

    }

    @Override
    public void authenticated() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivityForResult(intent, REQUEST_CODE_MAIN);
    }

    public void register(View view){
        Intent intent = new Intent(getActivity(), RegistrationActivity.class);
        startActivity(intent);
    }

    public void signIn(View view){
        AuthorizeDTO authorizeDTO = new AuthorizeDTO();
        authorizeDTO.setUsername(userName.getText().toString());
        authorizeDTO.setPassword(password.getText().toString());
        authorizeDTO.setRememberMe(rememberMe.isChecked());

        authenticateService.authorize(authorizeDTO);
    }

    private void singOut(){
        authenticateService.singOut();
        password.setText("");
        rememberMe.setChecked(false);
    }
}
