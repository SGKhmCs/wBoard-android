package ua.tsisar.wboard;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class UserSettingsActivity extends AppCompatActivity
        implements MessageDialog.MessageListener, PasswordDialog.PasswordListener{

    private static final int RESULT_SAVED = 1;
    private static final int RESULT_PSW_CHANGED = 2;

    private UserDTO userDTO;

    private EditText firstName;
    private EditText lastName;
    private EditText email;

    private AccountService accountService;
    private PasswordDialog passwordDialog;

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

        accountService = new AccountService(this, new AccountService.AccountListener() {
            @Override
            public void onAccountGetter(UserDTO userDTO) {
                setUserDTO(userDTO);

                firstName.setText(userDTO.getFirstName());
                lastName.setText(userDTO.getLastName());
                email.setText(userDTO.getEmail());
            }
        });
        accountService.getAccount();

    }

    @Override
    public void onMessageHide(int resultCode) {
        switch (resultCode){
            case RESULT_SAVED:
                setResult(RESULT_OK);
                finish();
                break;
            case RESULT_PSW_CHANGED:
                passwordDialog.dismiss();
                break;
        }
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
        passwordDialog = new PasswordDialog();
        passwordDialog.show(getSupportFragmentManager(), "passwordDialog");
    }

    private void setUserDTO(UserDTO userDTO){
        this.userDTO = userDTO;
    }
}
