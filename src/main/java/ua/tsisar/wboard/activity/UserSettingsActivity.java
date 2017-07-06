package ua.tsisar.wboard.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.github.mrengineer13.snackbar.SnackBar;

import ua.tsisar.wboard.activity.base.UserSettingsActivityBase;
import ua.tsisar.wboard.rest.helper.AccountHelper;
import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.dialog.PasswordDialog;
import ua.tsisar.wboard.dialog.PasswordDialog.PasswordListener;
import ua.tsisar.wboard.R;

public class UserSettingsActivity extends UserSettingsActivityBase implements PasswordListener {

    private UserDTO userDTO;
    private EditText firstName;
    private EditText lastName;
    private EditText email;

    private AccountHelper accountHelper;

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

        accountHelper = new AccountHelper(this);
        accountHelper.getAccount();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accountHelper.dispose();
    }

    @Override
    public void onPasswordChanged(String password) {
        accountHelper.changePassword(password);
    }

    public void saveAccount(View view){
        userDTO.setFirstName(firstName.getText().toString());
        userDTO.setLastName(lastName.getText().toString());

        if(isValidEmail(email.getText().toString())){
            userDTO.setEmail(email.getText().toString());
        }else{
            new SnackBar.Builder(this)
                    .withMessage("Please enter valid email!")
                    .withStyle(SnackBar.Style.ALERT)
                    .show();
            return;
        }

        accountHelper.saveAccount(userDTO);
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
        new SnackBar.Builder(this)
                .withMessage("Your settings saved!")
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

    @Override
    public void onChangePasswordSuccess(String string){
        new SnackBar.Builder(this)
                .withMessage("Your password saved!")
                .withStyle(SnackBar.Style.INFO)
                .show();
    }
}
