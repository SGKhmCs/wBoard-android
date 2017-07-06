package ua.tsisar.wboard.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.github.mrengineer13.snackbar.SnackBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.tsisar.wboard.activity.base.RegistrationActivityBase;
import ua.tsisar.wboard.rest.helper.AccountHelper;
import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.R;

public class RegistrationActivity extends RegistrationActivityBase {

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText passwordConfirmation;

    private AccountHelper accountHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        accountHelper = new AccountHelper(this);

        username = (EditText) findViewById(R.id.userName_editText);
        email = (EditText) findViewById(R.id.email_editText);
        password = (EditText) findViewById(R.id.password_editText);
        passwordConfirmation = (EditText) findViewById(R.id.password_confirmation_editText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accountHelper.dispose();
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
            errorMessage("Please enter valid email!");
            return;
        }

        if (stringPassword.length() > 3) {
            if (stringPassword.equals(passwordConfirmation.getText().toString())) {
                if (isValidPassword(stringPassword)) {
                    userDTO.setPassword(stringPassword);
                } else {
                    errorMessage("Your password is unreliable!");
                    return;
                }
            } else {
                errorMessage("Your passwords do not match!");
                return;
            }
        } else {
            errorMessage("Your password too short!");
            return;
        }

        accountHelper.registerAccount(userDTO);
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
    public void onRegisterAccountSuccess(String string) {
        new SnackBar.Builder(this)
                .withMessage("Please check your email for confirmation.")
                .withActionMessage("OK")
                .withStyle(SnackBar.Style.CONFIRM)
                .withVisibilityChangeListener(new SnackBar.OnVisibilityChangeListener() {
                    @Override
                    public void onShow(int stackSize) {

                    }

                    @Override
                    public void onHide(int stackSize) {
                        finish();
                    }
                })
                .show();
    }

    private SnackBar errorMessage(String message){
        return new SnackBar.Builder(this)
                .withMessage(message)
                .withStyle(SnackBar.Style.ALERT)
                .show();
    }
}
