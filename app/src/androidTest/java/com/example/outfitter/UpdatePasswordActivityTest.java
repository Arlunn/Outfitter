package com.example.outfitter;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpdatePasswordActivityTest {
    @Rule
    public ActivityTestRule<UpdatePasswordActivity> mActivityTestRule = new ActivityTestRule<UpdatePasswordActivity>(UpdatePasswordActivity.class);
    private UpdatePasswordActivity mFragment = null;
    @Before
    public void setUp() throws Exception {
        mFragment = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch()throws Exception{
        View v = mFragment.findViewById(R.id.newPasswordText);
        assertNotNull(v);
    }

    @After
    public void tearDown() throws Exception {
        mFragment = null;
    }
}