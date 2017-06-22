package ua.tsisar.wboard;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSettings extends AppCompatActivity {
    private User user;
    private Activity activity = this;

    private EditText firstName;
    private EditText lastName;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        firstName = (EditText) findViewById(R.id.firstName_editText);
        lastName = (EditText) findViewById(R.id.lastName_editText);
        email = (EditText) findViewById(R.id.email_user_settings_editText);

        getAccount();
    }

    private void getAccount(){
        Call<User> userCall = App.getApi().getAccount(App.getToken().getIdTokenEx());
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch (response.code()){
                    case 200:
                        setValue(response.body());
                        break;
                    default:
                        Message.makeText(activity, "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Message.makeText(activity, "Error", throwable.getMessage()).show();
            }
        });
    }

    public void saveAccount(View view){
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());

        if(isValidEmail(email.getText().toString())){
            user.setEmail(email.getText().toString());
        }else{
            Message.makeText(activity, "Error", "Please enter valid email!").show();
            return;
        }

        Call<String> stringCall = App.getApi().saveAccount(App.getToken().getIdTokenEx(), user);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()){
                    case 200:
                        Message.makeText(activity, "Saved!",
                                "Your settings saved.").show();
                        // Don't finish?
                        finish();
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

    private void setValue(User user){
        if(user != null){
            this.user = user;

            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            email.setText(user.getEmail());
        }
    }

    private static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}
