package com.github.devishankar.utsample;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowToast;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    private Resources mResources;
    private Button mBtnLogin;
    private Button mBtnChangePassword;
    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private ProgressBar mPBLogin;

    /*@Rule
    public PowerMockRule rule = new PowerMockRule();*/
    private LoginActivity mActivity;

    @Before
    public void setUp() {
        mActivity = Robolectric.setupActivity(LoginActivity.class);
        mResources = mActivity.getResources();
        mBtnLogin = (Button) mActivity.findViewById(R.id.btn_login);
        mBtnChangePassword = (Button) mActivity.findViewById(R.id.btn_change_password);
        mEdtEmail = (EditText) mActivity.findViewById(R.id.edt_login_email);
        mEdtPassword = (EditText) mActivity.findViewById(R.id.edt_login_password);
        mPBLogin = (ProgressBar) mActivity.findViewById(R.id.pb_login);
    }

    @Test
    public void loginWithEmptyEmailAndPassword() {
        mBtnLogin.performClick();

        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        assertThat("Next activity not started", application.getNextStartedActivity(), is(nullValue()));
        assertThat("Show empty error for Email field ", mEdtEmail.getError(), is(CoreMatchers.notNullValue()));
        assertThat("Show empty error for Email field ", mEdtEmail.getError(), is(CoreMatchers.anything(mResources.getString(R.string.error_field_empty))));
        assertThat("Show empty error for Password field ", mEdtPassword.getError(), is(CoreMatchers.notNullValue()));
        assertThat("Show empty error for Password field ", mEdtPassword.getError(), is(CoreMatchers.anything(mResources.getString(R.string.error_field_empty))));
    }

    @Test
    public void loginFailure() {
        mEdtEmail.setText("invalid@email");
        mEdtPassword.setText("invalidpassword");
        mBtnLogin.performClick();

        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        assertThat("Next activity not started", application.getNextStartedActivity(), is(nullValue()));
        assertThat("Show incorrect Email ", mEdtEmail.getError(), is(CoreMatchers.notNullValue()));
        assertThat("Show incorrect Email ", mEdtEmail.getError(), is(CoreMatchers.anything(mResources.getString(R.string.error_incorrect_credentials))));
        assertThat("Show incorrect password ", mEdtPassword.getError(), is(CoreMatchers.notNullValue()));
        assertThat("Show incorrect password ", mEdtPassword.getError(), is(CoreMatchers.anything(mResources.getString(R.string.error_incorrect_credentials))));
    }

    @Test
    public void loginSuccess() {
        mEdtEmail.setText("uttest@test.com");
        mEdtPassword.setText("uttest");
        mBtnLogin.performClick();

        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        assertThat("Show success toast", ShadowToast.getTextOfLatestToast(), is(CoreMatchers.anything(mResources.getString(R.string.toast_welcome))));
        assertThat("Next activity has started", application.getNextStartedActivity(), is(notNullValue()));
    }

    @Test
    public void changePasswordFailure() {
        mBtnChangePassword.performClick();
        shadowOf(mActivity).receiveResult(
                new Intent(mActivity, ChangePasswordActivity.class),
                Activity.RESULT_CANCELED,
                new Intent());
        assertThat("Show failure toast", ShadowToast.getTextOfLatestToast(), is(CoreMatchers.anything(mResources.getString(R.string.toast_password_change_failure))));
    }

    @Test
    public void changePasswordSuccess() {
        mBtnChangePassword.performClick();
        shadowOf(mActivity).receiveResult(
                new Intent(mActivity, ChangePasswordActivity.class),
                Activity.RESULT_OK,
                new Intent());
        assertThat("Show success toast", ShadowToast.getTextOfLatestToast(), is(CoreMatchers.anything(mResources.getString(R.string.toast_password_change_success))));
    }

}
