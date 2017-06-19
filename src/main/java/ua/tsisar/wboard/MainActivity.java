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

    private boolean isAuthenticated = false;
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.menu_not_authorize, !isAuthenticated);
        menu.setGroupVisible(R.id.menu_authorize, isAuthenticated);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sign_in:
                showAuthorizeDialog();
                return true;
            case R.id.menu_register:
                return true;
            case R.id.menu_sign_out:
                singOut();
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
        isAuthenticated();
    }

    private void singOut(){
        isAuthenticated = false;
        App.getToken().resetToken();
        saveIdToken("");
    }

    private void isAuthenticated(){
        Call<String> stringCall = App.getApi().isAuthenticated(App.getToken().getIdTokenEx());
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()){
                    case 200:
                        if(response.body().isEmpty()) {
                            isAuthenticated = false;
                        } else {
                            isAuthenticated = true;
                            textView.setText("Hi " + response.body() + "!");
                            Log.d(TAG, "Login: " + response.body());
                        }
                        break;
                    default:
                        isAuthenticated = false;
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                isAuthenticated = false;
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