package ua.tsisar.wboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity
        implements MessageDialog.IMessageDialogListener, RegisterService.RegisterListener{

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText passwordConfirmation;

    private int responseCode;

    private RegisterService registerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registerService = new RegisterService(this);

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

        registerService.registerAccount(userDTO);
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
    public void onMessageHide() {
        if(responseCode == 201){
            finish();
        }
    }

    @Override
    public void registered(int responseCode) {
        this.responseCode = responseCode;
    }
}
