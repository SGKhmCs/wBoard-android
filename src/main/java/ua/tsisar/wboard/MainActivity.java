package ua.tsisar.wboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AuthorizeDialog.ICredentialsDialogListener {

    private final String TAG = "myLogs";
    private final String TOKEN = "token";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        App.setIdToken(loadIdToken());
        isAuthenticated();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_authorize:
                showAuthenticateDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAuthenticateDialog() {
        AuthorizeDialog dialog = new AuthorizeDialog();
        Bundle arguments = new Bundle();
        dialog.setArguments(arguments);

        dialog.show(getSupportFragmentManager(), "authenticateDialog");
    }

    @Override
    public void onDialogPositiveClick(String username, String password, boolean rememberMe) {
        authorize(username, password, rememberMe);
    }

    //TODO перенести в діалог, відпрацювати помилку авторизації, з діалогу повертати токен, перейменувати діалог
    private void authorize(String stringUsername,
                           String stringPassword, final boolean booleanRememberMe){
        Authorize authorize = new Authorize();
        authorize.setUsername(stringUsername);
        authorize.setPassword(stringPassword);
        authorize.setRememberMe(booleanRememberMe);

        Call<Token> tokenCall = App.getApi().authorize(authorize);
        tokenCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                //-----------------------TEST------------------------//
                String string = response.toString();
                Log.d(TAG, "Response: " + response.isSuccessful());
                if(response.isSuccessful()) {
                    String idToken = response.body().getIdToken();
                    App.setIdToken(idToken);

                    Log.d(TAG, "Token: " + idToken);
                }
                //---------------------------------------------------//
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {

            }
        });
        if(booleanRememberMe) {
            saveIdToken(App.getToken().getIdToken());
        }
    }

    private void isAuthenticated(){
        Call<String> stringCall = App.getApi().isAuthenticated(App.getToken().getIdTokenEx());
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //-----------------------TEST------------------------//
                String login = response.body();
                Log.d(TAG, "Login: " + login);
                textView.setText("Hi " + login + "!");
                //---------------------------------------------------//
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });

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