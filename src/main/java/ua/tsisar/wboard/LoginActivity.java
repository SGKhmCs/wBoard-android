package ua.tsisar.wboard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private final int REQUEST_CODE_MAIN = 1;

    private final String TOKEN = "token";
    private final Activity activity = this;

    private EditText userName;
    private EditText password;
    private CheckBox rememberMe;

    //TODO щось зробити з показом форми авторизації
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        App.setIdToken(loadIdToken());
        isAuthenticated();

        userName = (EditText) findViewById(R.id.userName_editText);
        password = (EditText) findViewById(R.id.password_editText);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe_checkBox);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_MAIN:
                singOut();
                break;
        }

    }

    private void isAuthenticated(){

        Call<String> stringCall = App.getApi().isAuthenticated(App.getToken().getIdTokenEx());
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()){
                    case 200:
                        if(!response.body().isEmpty()) {
                            // Переходимо до актівіті борда
                            Intent intent = new Intent(activity, MainActivity.class);
                            startActivityForResult(intent, REQUEST_CODE_MAIN);
                        }
                        break;
                    default:
                        Message.makeText(activity, "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Message.makeText(activity, "Error", throwable.getMessage()).show();
            }
        });
    }

    public void register(View view){
        Intent intent = new Intent(activity, RegistrationActivity.class);
        startActivity(intent);
    }

    public void signIn(View view){
        Authorize authorize = new Authorize();
        authorize.setUsername(userName.getText().toString());
        authorize.setPassword(password.getText().toString());
        authorize.setRememberMe(rememberMe.isChecked());

        Call<Token> tokenCall = App.getApi().authorize(authorize);
        tokenCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                switch (response.code()){
                    case 200:
                        String idToken = response.body().getIdToken();
                        App.setIdToken(idToken);
                        if (rememberMe.isChecked()){
                            saveIdToken(idToken);
                        } else {
                            saveIdToken("");
                        }
                        isAuthenticated();
                        break;
                    default:
                        Message.makeText(activity, "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {
                Message.makeText(activity, "Error", throwable.getMessage()).show();
            }
        });
    }

    private void singOut(){
        App.getToken().resetToken();
        saveIdToken("");
        password.setText("");
        rememberMe.setChecked(false);
    }

    private void saveIdToken(String idToken) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, idToken);
        editor.apply();
    }

    private String loadIdToken() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN, "");
    }
}
