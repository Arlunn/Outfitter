package com.example.outfitter;

import android.os.Looper;
import android.widget.Button;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.logging.Handler;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.*;

public class AccountSettingActivityTest {
    @Rule
    public ActivityTestRule<AccountSettingActivity> mActivityTestRule = new ActivityTestRule<AccountSettingActivity>(AccountSettingActivity.class);
    private AccountSettingActivity mActivity = null;
    private Button signOut = null;
    private Button updatePass = null;
    @Before
    public void setUp() throws Exception {
        mActivity= mActivityTestRule.getActivity();
        signOut = mActivity.findViewById(R.id.signOutButton);
        updatePass = mActivity.findViewById(R.id.updatePasswordButton);

    }
    @Test
    public void testSignOutButton() throws Throwable {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertEquals(true,signOut.performClick());
            }
        });

    }

    @Test
    public void testUpdatePassword() throws Throwable{
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertEquals(true,updatePass.performClick());
            }
        });


    }
    @After
    public void tearDown() throws Exception {
        mActivity = null;
        signOut = null;
        updatePass = null;
    }
}