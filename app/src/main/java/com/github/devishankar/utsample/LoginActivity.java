package com.github.devishankar.utsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final int CHANGE_PASSWORD_REQUEST = 100;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "uttest@test.com:uttest", "uttest1@test.com:uttest1"
    };
    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private Button mBtnChangePassword;
    private ProgressBar pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEdtEmail = (EditText) findViewById(R.id.edt_login_email);
        mEdtPassword = (EditText) findViewById(R.id.edt_login_password);
        pbLogin = (ProgressBar) findViewById(R.id.pb_login);

        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnChangePassword = (Button) findViewById(R.id.btn_change_password);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mBtnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void attemptLogin() {
        if (validEntries()) {
            pbLogin.setVisibility(View.VISIBLE);
            doLogin();
        }
    }

    private void changePassword() {
        startActivityForResult(new Intent(this, ChangePasswordActivity.class), CHANGE_PASSWORD_REQUEST);
    }

    private boolean validEntries() {
        mEdtEmail.setError(null);
        mEdtPassword.setError(null);

        String errorMessage = getString(R.string.error_field_empty);
        boolean isValid = true;
        if (TextUtils.isEmpty(mEdtPassword.getText().toString())) {
            showErrorMessage(mEdtPassword, errorMessage);
            isValid = false;
        }

        if (TextUtils.isEmpty(mEdtEmail.getText().toString())) {
            showErrorMessage(mEdtEmail, errorMessage);
            isValid = false;
        }

        return isValid;
    }

    private void doLogin() {
        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(mEdtEmail.getText().toString()) && pieces[1].equals(mEdtPassword.getText().toString())) {
                Toast.makeText(this, getString(R.string.toast_welcome), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }

        pbLogin.setVisibility(View.GONE);
        String errorMessage = getString(R.string.error_incorrect_credentials);
        showErrorMessage(mEdtPassword, errorMessage);
        showErrorMessage(mEdtEmail, errorMessage);
    }

    private void showErrorMessage(EditText view, String errorMessage) {
        view.setError(Html.fromHtml("<font color='red'>" + errorMessage + "</font>"));
        view.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, getString(R.string.toast_password_change_success), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.toast_password_change_failure), Toast.LENGTH_SHORT).show();
        }
    }
}
