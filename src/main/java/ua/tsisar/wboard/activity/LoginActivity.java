package ua.tsisar.wboard.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import ua.tsisar.wboard.activity.base.LoginActivityBase;
import ua.tsisar.wboard.rest.helper.AccountHelper;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.rest.helper.AuthenticateHelper;
import ua.tsisar.wboard.dto.AuthorizeDTO;
import ua.tsisar.wboard.dto.TokenDTO;
import ua.tsisar.wboard.R;

public class LoginActivity extends LoginActivityBase {

    private static final String TOKEN = "token";

    private static final int REQUEST_CODE_MAIN = 1;
    private static final int RESULT_SIGN_OUT = -2;
    private static final String TAG = "myLogs";

    private EditText userName;
    private EditText password;
    private CheckBox rememberMe;

    private AuthenticateHelper authenticateHelper;
    private AccountHelper accountHelper;

    public Context getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        App.setIdToken(loadIdToken());

        userName = (EditText) findViewById(R.id.userName_editText);
        password = (EditText) findViewById(R.id.password_editText);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe_checkBox);

        authenticateHelper = new AuthenticateHelper(this);
        accountHelper = new AccountHelper(this);
        accountHelper.isAuthenticated();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        authenticateHelper.dispose();
        accountHelper.dispose();
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
    public void onAuthorizeSuccess(TokenDTO tokenDTO) {
        String idToken = tokenDTO.getIdToken();
        App.setIdToken(idToken);
        if (rememberMe.isChecked()){
            saveIdToken(idToken);
        } else {
            saveIdToken("");
        }
        accountHelper.isAuthenticated();
    }

    @Override
    public void onIsAuthenticatedSuccess(String string) {
        if(!string.isEmpty()) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE_MAIN);
        }
    }

    public void register(View view){
        Intent intent = new Intent(getActivity(), RegistrationActivity.class);
        startActivity(intent);
    }

    public void signIn(View view){
        AuthorizeDTO authorizeDTO = new AuthorizeDTO(
                userName.getText().toString(),
                password.getText().toString(),
                rememberMe.isChecked());

        authenticateHelper.authorize(authorizeDTO);
    }

    public void singOut(){
        password.setText("");
        rememberMe.setChecked(false);
        App.getTokenDTO().resetToken();
        saveIdToken("");
    }

    private void saveIdToken(String idToken) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, idToken);
        Log.d(TAG, "saveIdToken: " + idToken);
        editor.apply();
    }

    private String loadIdToken() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String res = sharedPreferences.getString(TOKEN, "");
        Log.d(TAG, "loadIdToken: " + res);
        return res;
    }
}
