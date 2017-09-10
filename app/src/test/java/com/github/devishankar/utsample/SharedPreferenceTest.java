package com.github.devishankar.utsample;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by Devishankar on 9/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class SharedPreferenceTest {
    private static final String VALUE_NAME = "UT Sample";
    private static final String VALUE_EMAIL = "utsample@test.com";
    private static final String KEY_NAME = "KEY_NAME";
    private static final String KEY_EMAIL = "KEY_VALUE";

    @Mock
    SharedPreferences mMockSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockEditor;

    @Before
    public void initMocks() {
        createMockSharedPreference();
    }

    @Test
    public void testSaveAndRead() {
        boolean success = saveData();

        assertThat("Checking that SharedPreference entry which returns true",
                success, is(true));

        assertThat("Checking that SharedPreference KEY_NAME has been persisted and read correctly",
                VALUE_NAME,
                is(equalTo(mMockSharedPreferences.getString(KEY_NAME, ""))));
        assertThat("Checking that SharedPreference KEY_EMAIL has been persisted and read correctly",
                VALUE_EMAIL,
                is(equalTo(mMockSharedPreferences.getString(KEY_EMAIL, ""))));
    }


    private void createMockSharedPreference() {
        when(mMockSharedPreferences.getString(eq(KEY_NAME), anyString()))
                .thenReturn(VALUE_NAME);
        when(mMockSharedPreferences.getString(eq(KEY_EMAIL), anyString()))
                .thenReturn(VALUE_EMAIL);

        when(mMockEditor.commit()).thenReturn(true);
    }

    private boolean saveData() {
        mMockEditor.putString(KEY_NAME, VALUE_NAME);
        mMockEditor.putString(KEY_EMAIL, VALUE_EMAIL);

        return mMockEditor.commit();
    }
}
