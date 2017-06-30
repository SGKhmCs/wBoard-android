package ua.tsisar.wboard.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;
import ua.tsisar.wboard.Service.AccountService;
import ua.tsisar.wboard.DTO.UserDTO;
import ua.tsisar.wboard.Message;
import ua.tsisar.wboard.R;
import ua.tsisar.wboard.Service.Listener.AccountListener;

public class RegistrationActivity extends AppCompatActivity implements AccountListener {

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText passwordConfirmation;

    private AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        accountService = new AccountService(this);

        username = (EditText) findViewById(R.id.userName_editText);
        email = (EditText) findViewById(R.id.email_editText);
        password = (EditText) findViewById(R.id.password_editText);
        passwordConfirmation = (EditText) findViewById(R.id.password_confirmation_editText);
    }

    public void register(View view) {
        String stringUsername = username.getText().toString();
        String stringEmail = email.getText().toString();
        String stringPassword = password.getText().toString();

        UserDTO userDTO = new UserDTO();

        userDTO.setLogin(stringUsername);

        if(isValidEmail(stringEmail)){
            userDTO.setEmail(stringEmail);
        }else{
            Message.makeText(this, "Error", "Please enter valid email!").show();
            return;
        }

        if (stringPassword.length() > 3) {
            if (stringPassword.equals(passwordConfirmation.getText().toString())) {
                if (isValidPassword(stringPassword)) {
                    userDTO.setPassword(stringPassword);
                } else {
                    Message.makeText(this, "Error", "Your password is unreliable!").show();
                    return;
                }
            } else {
                Message.makeText(this, "Error", "Your passwords do not match!").show();
                return;
            }
        } else {
            Message.makeText(this, "Error", "Your password too short!").show();
            return;
        }

        accountService.registerAccount(userDTO);
    }

    private static boolean isValidPassword(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    @Override
    public void onGetAccountResponse(Response<UserDTO> response) {

    }

    @Override
    public void onIsAuthenticatedResponse(Response<String> response) {

    }

    @Override
    public void onSaveAccountResponse(Response<String> response) {

    }

    @Override
    public void onChangePasswordResponse(Response<String> response) {

    }

    @Override
    public void onRegisterAccountResponse(Response<String> response) {
        switch (response.code()){
            case 201:
                Message.makeText(this, "Registration saved!",
                        "Please check your email for confirmation.", true).show();
                break;
            default:
                Message.makeText(this, "Error",
                        response.message() + ", status code: " + response.code()).show();
                break;
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        Message.makeText(this, "Error", throwable.getMessage()).show();
    }

}
