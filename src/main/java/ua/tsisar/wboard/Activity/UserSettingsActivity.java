package ua.tsisar.wboard.Activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import retrofit2.Response;
import ua.tsisar.wboard.Activity.Super.UserSettingsActivitySuper;
import ua.tsisar.wboard.Service.AccountService;
import ua.tsisar.wboard.DTO.UserDTO;
import ua.tsisar.wboard.Dialog.PasswordDialog;
import ua.tsisar.wboard.Dialog.PasswordDialog.PasswordListener;
import ua.tsisar.wboard.Message;
import ua.tsisar.wboard.R;

public class UserSettingsActivity extends UserSettingsActivitySuper implements PasswordListener {

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
    public void onSaveAccountResponse(Response<String> response) {
        if(response.code() == RESPONSE_OK){
            Message.makeText(this, "Saved!", "Your settings saved.", true).show();
            return;
        }
        super.onSaveAccountResponse(response);
    }

    public void onChangePasswordResponse(Response<String> response){
        if(response.code() == RESPONSE_OK) {
            Message.makeText(this, "Password saved!",
                    "Your password saved.").show();
            return;
        }
        super.onChangePasswordResponse(response);
    }
}
