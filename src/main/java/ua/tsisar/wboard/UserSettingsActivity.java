package ua.tsisar.wboard;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import retrofit2.Response;

public class UserSettingsActivity extends AppCompatActivity
        implements PasswordDialog.PasswordListener, AccountService.AccountListener {

    private static final int RESULT_PSW_CHANGED = 2;

    private UserDTO userDTO;

    private EditText firstName;
    private EditText lastName;
    private EditText email;

    private AccountService accountService;

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

        accountService = new AccountService(this);
        accountService.getAccount();

    }

    @Override
    public void onPasswordChanged(String password) {
        accountService.changePassword(password);
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

        accountService.saveAccount(userDTO);
    }

    private static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public void editPassword(View view){
        PasswordDialog passwordDialog = new PasswordDialog();
        passwordDialog.show(getSupportFragmentManager(), "passwordDialog");
    }

    @Override
    public void onGetAccountResponse(Response<UserDTO> response) {
        userDTO = response.body();

        firstName.setText(userDTO.getFirstName());
        lastName.setText(userDTO.getLastName());
        email.setText(userDTO.getEmail());
    }

    @Override
    public void onIsAuthenticatedResponse(Response<String> response) {

    }

    @Override
    public void onSaveAccountResponse(Response<String> response) {
        switch (response.code()){
            case 200:
                setResult(RESULT_OK);
                finish();
                break;
            default:
                Message.makeText(this, "Error",
                        response.message() + ", status code: " + response.code()).show();
                break;
        }
    }

    public void onChangePasswordResponse(Response<String> response){
        switch (response.code()){
            case 200:
                Message.makeText(getActivity(), "Password saved!",
                        "Your password saved.").show();
                break;
            default:
                Message.makeText(getActivity(), "Error",
                        response.message() + ", status code: " + response.code()).show();
                break;
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        Message.makeText(this, "Error", throwable.getMessage()).show();
    }
}
