package ua.tsisar.wboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private Button signIn;
    private Button register;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.signIn);
        register = (Button) findViewById(R.id.register);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAuthenticate(login.getText().toString(),
                        password.getText().toString(), rememberMe.isChecked());
            }
        });
    }

    private void postAuthenticate(String stringUsername,
                                  String stringPassword, boolean booleanRememberMe){
        AuthenticateDTO authenticate = new AuthenticateDTO();
        authenticate.setUsername(stringUsername);
        authenticate.setPassword(stringPassword);
        authenticate.setRememberMe(booleanRememberMe);

        Call<TokenDTO> tokenCall = App.getApi().authorize(authenticate);
        tokenCall.enqueue(new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                String string = response.toString();
                TokenDTO tokenDTO = response.body();
            }

            @Override
            public void onFailure(Call<TokenDTO> call, Throwable throwable) {

            }
        });
    }
}