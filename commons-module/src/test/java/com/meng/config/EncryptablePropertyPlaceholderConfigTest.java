package com.meng.config;

import junit.framework.TestCase;
import org.junit.Test;


public class EncryptablePropertyPlaceholderConfigTest extends TestCase {
    @Test
    public void testGetEncryptString() throws Exception {

        EncryptablePropertyPlaceholderConfig encrypt
                = new EncryptablePropertyPlaceholderConfig();
        String ret = encrypt.getEncryptString("xiaowang@");
        System.out.println(ret);
        ret = encrypt.getDecryptString(ret);
        System.out.println(ret);

    }
}