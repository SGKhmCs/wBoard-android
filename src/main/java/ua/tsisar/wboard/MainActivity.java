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

public class MainActivity extends AppCompatActivity implements AuthorizeDialog.IAuthorizeDialogListener {

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
                showAuthorizeDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAuthorizeDialog() {
        AuthorizeDialog dialog = new AuthorizeDialog();
        Bundle arguments = new Bundle();
        dialog.setArguments(arguments);

        dialog.show(getSupportFragmentManager(), "authenticateDialog");
    }

    @Override
    public void onAuthorized(boolean rememberMe) {
        if(rememberMe){
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