package ua.tsisar.wboard.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import ua.tsisar.wboard.activity.base.UserSettingsActivityBase;
import ua.tsisar.wboard.rest.helper.AccountService;
import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.dialog.PasswordDialog;
import ua.tsisar.wboard.dialog.PasswordDialog.PasswordListener;
import ua.tsisar.wboard.Message;
import ua.tsisar.wboard.R;

public class UserSettingsActivity extends UserSettingsActivityBase implements PasswordListener {

    private static final int RESPONSE_OK = 200;

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
    protected void onDestroy() {
        super.onDestroy();
        accountService.dispose();
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
    public void onGetAccountSuccess(UserDTO userDTO) {
        this.userDTO = userDTO;

        firstName.setText(userDTO.getFirstName());
        lastName.setText(userDTO.getLastName());
        email.setText(userDTO.getEmail());
    }

    @Override
    public void onSaveAccountSuccess(String string) {
        Message.makeText(this, "Saved!", "Your settings saved.", true).show();
    }

    @Override
    public void onChangePasswordSuccess(String string){
        Message.makeText(this, "Password saved!", "Your password saved.").show();
    }
}
