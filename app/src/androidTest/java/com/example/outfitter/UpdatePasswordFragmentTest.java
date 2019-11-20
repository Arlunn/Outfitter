package com.example.outfitter;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpdatePasswordFragmentTest {
    @Rule
    public ActivityTestRule<UpdatePasswordFragment> mActivityTestRule = new ActivityTestRule<UpdatePasswordFragment>(UpdatePasswordFragment.class);
    private UpdatePasswordFragment mFragment = null;
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