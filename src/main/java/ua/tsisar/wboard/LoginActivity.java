package ua.tsisar.wboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity
        implements AuthenticateService.AuthenticateListener, AccountService.AccountListener{

    private static final String TOKEN = "token";

    private static final int REQUEST_CODE_MAIN = 1;
    private static final int RESULT_SIGN_OUT = -2;

    private EditText userName;
    private EditText password;
    private CheckBox rememberMe;

    private AuthenticateService authenticateService;
    private AccountService accountService;

    public Context getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        App.setIdToken(loadIdToken());

        authenticateService = new AuthenticateService(this);
        accountService = new AccountService(this);

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
    public void onAuthorizeResponse(Response<TokenDTO> response) {
        switch (response.code()){
            case 200:
                String idToken = response.body().getIdToken();
                App.setIdToken(idToken);
                if (rememberMe.isChecked()){
                    saveIdToken(idToken);
                } else {
                    saveIdToken("");
                }
                accountService.isAuthenticated();
                break;
            default:
                Message.makeText(this, "Error",
                        response.message() + ", status code: " + response.code()).show();
                break;
        }
    }

    @Override
    public void onGetAccountResponse(Response<UserDTO> response) {

    }

    @Override
    public void onIsAuthenticatedResponse(Response<String> response) {
        switch (response.code()){
            case 200:
                if(!response.body().isEmpty()) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_MAIN);
                }
                break;
            default:
                Message.makeText(this, "Error",
                        response.message() + ", status code: " + response.code()).show();
                break;
        }
    }

    @Override
    public void onSaveAccountResponse(Response<String> response) {

    }

    @Override
    public void onChangePasswordResponse(Response<String> response) {

    }

    @Override
    public void onRegisterAccountResponse(Response<String> response) {

    }

    @Override
    public void onFailure(Throwable throwable) {
        Message.makeText(this, "Error", throwable.getMessage()).show();
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

        authenticateService.authorize(authorizeDTO);
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
        editor.apply();
    }

    private String loadIdToken() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN, "");
    }
}
