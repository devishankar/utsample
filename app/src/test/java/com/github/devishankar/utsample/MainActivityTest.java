package com.github.devishankar.utsample;

import android.app.Activity;
import android.content.res.Resources;
import android.view.Menu;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Devishankar on 9/8/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    @Test
    public void shouldInflateTheMenuOnCreate() {
        Activity activity = Robolectric.setupActivity(MainActivity.class);
        Resources resources = activity.getResources();

        final Menu menu = shadowOf(activity).getOptionsMenu();
        assertThat("Menu settings ", menu.findItem(R.id.item1).getTitle(), is(CoreMatchers.anything(resources.getString(R.string.menu_setting))));
        assertThat("Menu logout ", menu.findItem(R.id.item1).getTitle(), is(CoreMatchers.anything(resources.getString(R.string.menu_logout))));
    }
}
