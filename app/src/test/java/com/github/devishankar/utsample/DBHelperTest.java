package com.github.devishankar.utsample;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Devishankar on 9/10/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DBHelperTest {

    private DBHelper mDBHelper;

    @Before
    public void setup() {
        mDBHelper = new DBHelper(RuntimeEnvironment.application);
        mDBHelper.clearDbAndRecreate();
    }

    @Test
    public void testDbInsertion() {

        ContactModel model1 = new ContactModel(1, "Devishankar", "12312312312");
        ContactModel model2 = new ContactModel(2, "Shankar", "4564564566");

        mDBHelper.insertContact(model1);
        mDBHelper.insertContact(model2);

        ContactModel data1 = mDBHelper.getContact(1);
        ContactModel data2 = mDBHelper.getContact(2);
        assertThat("Check first name ", model1.getName(), is(CoreMatchers.is(data1.getName())));
        assertThat("Check first phone ", model1.getPhone(), is(CoreMatchers.is(data1.getPhone())));
        assertThat("Check second name ", model2.getName(), is(CoreMatchers.is(data2.getName())));
        assertThat("Check second phone ", model2.getPhone(), is(CoreMatchers.is(data2.getPhone())));
    }


    @Test
    public void testDbDeletion() {

        ContactModel model1 = new ContactModel(1, "Devishankar", "12312312312");
        ContactModel model2 = new ContactModel(2, "Shankar", "4564564566");

        mDBHelper.insertContact(model1);
        mDBHelper.insertContact(model2);

        mDBHelper.deleteContact(model1);
        ContactModel data2 = mDBHelper.getContact(2);

        assertThat("Check if deleted ", true, is(CoreMatchers.is(mDBHelper.isColumnExist(model1))));

        assertThat("Check second name ", model2.getName(), is(CoreMatchers.is(data2.getName())));
        assertThat("Check second phone ", model2.getPhone(), is(CoreMatchers.is(data2.getPhone())));
    }

    @Test
    public void testDbUpdate() {

        ContactModel model1 = new ContactModel(1, "Devishankar", "12312312312");

        mDBHelper.insertContact(model1);
        ContactModel data1 = mDBHelper.getContact(1);

        assertThat("Check name before update ", model1.getName(), is(CoreMatchers.is(data1.getName())));
        assertThat("Check phone before update ", model1.getPhone(), is(CoreMatchers.is(data1.getPhone())));

        ContactModel modelToUpdate = new ContactModel(1, "Name Updated", "6786786786");

        mDBHelper.updateContact(modelToUpdate);
        ContactModel dataAfterUpdate = mDBHelper.getContact(1);

        assertThat("Check if deleted ", true, is(CoreMatchers.is(mDBHelper.isColumnExist(model1))));

        assertThat("Check second name ", modelToUpdate.getName(), is(CoreMatchers.is(dataAfterUpdate.getName())));
        assertThat("Check second phone ", modelToUpdate.getPhone(), is(CoreMatchers.is(dataAfterUpdate.getPhone())));
    }

    @Test
    public void testDbTableCount() {
        List<ContactModel> list = new ArrayList<>();
        ContactModel model1 = new ContactModel(1, "Devishankar", "12312312312");
        list.add(model1);
        mDBHelper.insertContact(model1);

        assertThat("Count should be 1 ", list.size(), is(CoreMatchers.is(mDBHelper.getContactsCount())));

        ContactModel model2 = new ContactModel(2, "Shankar", "4564564566");
        list.add(model2);
        mDBHelper.insertContact(model2);
        assertThat("Count should be 2 ", list.size(), is(CoreMatchers.is(mDBHelper.getContactsCount())));
    }
}
