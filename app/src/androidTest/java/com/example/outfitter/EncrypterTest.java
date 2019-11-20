package com.example.outfitter;
import static org.junit.Assert.assertEquals;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EncrypterTest {
    static Encrypter encrypter;
    static  String value;
    static String encrypt;


    @Before
    public  void setUp() throws Exception {
        encrypter = new Encrypter();
        value = "A$AP";
    }

    @Test
    public  void testEncryption() throws Exception{

        encrypt = encrypter.encrypt(value);
        assertEquals(value, encrypter.decrypt(encrypt));

    }

    @After
    public  void tearDown() throws Exception {
    }
}