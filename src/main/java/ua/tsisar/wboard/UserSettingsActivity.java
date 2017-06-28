package ua.tsisar.wboard;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSettingsActivity extends AppCompatActivity implements MessageDialog.IMessageDialogListener{

    private UserDTO userDTO;

    private EditText firstName;
    private EditText lastName;
    private EditText email;

    private boolean saved = false;

    public Context getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        firstName = (EditText) findViewById(R.id.firstName_editText);
        lastName = (EditText) findViewById(R.id.lastName_editText);
        email = (EditText) findViewById(R.id.email_editText);

        getAccount();

    }

    private void getAccount(){
        Call<UserDTO> userCall = App.getApi().getAccount(App.getToken().getIdTokenEx());
        userCall.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                switch (response.code()){
                    case 200:
                        setValue(response.body());
                        break;
                    default:
                        Message.makeText(getActivity(), "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable throwable) {
                Message.makeText(getActivity(), "Error", throwable.getMessage()).show();
            }
        });
    }

    public void saveAccount(View view){
        userDTO.setFirstName(firstName.getText().toString());
        userDTO.setLastName(lastName.getText().toString());

        if(isValidEmail(email.getText().toString())){
            userDTO.setEmail(email.getText().toString());
        }else{
            Message.makeText(getActivity(), "Error", "Please enter valid email!").show();
            return;
        }

        Call<String> stringCall = App.getApi().saveAccount(App.getToken().getIdTokenEx(), userDTO);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()){
                    case 200:
                        Message.makeText(getActivity(), "Saved!",
                                "Your settings saved.").show();
                        saved = true;
                        break;
                    default:
                        Message.makeText(getActivity(), "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Message.makeText(getActivity(), "Error", throwable.getMessage()).show();
            }
        });
    }

    private void setValue(UserDTO userDTO){
        if(userDTO != null){
            this.userDTO = userDTO;

            firstName.setText(userDTO.getFirstName());
            lastName.setText(userDTO.getLastName());
            email.setText(userDTO.getEmail());
        }
    }

    private static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public void editPassword(View view){
        PasswordDialog dialog = new PasswordDialog();
        dialog.show(getSupportFragmentManager(), "passwordDialog");
    }

    @Override
    public void onMessageHide() {
        if(saved){
            setResult(RESULT_OK);
            finish();
        }
    }
}
