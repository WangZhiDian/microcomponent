package com.meng.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;

import static com.meng.define.CommonDefine.*;

/**
 * hash utils
 *
 * @author : sunyuecheng
 */
public final class HashUtils {

    private static final String MD5 = "MD5";

    private static final String SHA256 = "SHA-256";

    private static final String PBKDF_SHA256 = "PBKDF2WithHmacSHA256";

    /**
     * make md5
     *
     * @param data :
     * @return String :
     * @throws Exception :
     */
    public static String makeMd5(String data) throws Exception {
        if (data == null) {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance(MD5);
        md.update(data.getBytes(DEFAULT_ENCODING));
        byte[] buffer = md.digest();

        int i;
        StringBuffer strBuf = new StringBuffer();
        for (int offset = 0; offset < buffer.length; offset++) {
            i = buffer[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                strBuf.append("0");
            }
            strBuf.append(Integer.toHexString(i));
        }
        return strBuf.toString();
    }

    /**
     * make md5
     *
     * @param data :
     * @return String :
     * @throws Exception :
     */
    public static String makeMd5(byte[] data) throws Exception {
        if (data == null || data.length == 0) {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance(MD5);
        md.update(data);
        byte[] buffer = md.digest();

        int i;
        StringBuffer strBuf = new StringBuffer();
        for (int offset = 0; offset < buffer.length; offset++) {
            i = buffer[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                strBuf.append("0");
            }
            strBuf.append(Integer.toHexString(i));
        }
        return strBuf.toString();
    }

    /**
     * make sha256
     *
     * @param data :
     * @return String :
     * @throws Exception :
     */
    public static String makeSha256(String data) throws Exception {
        if (data == null) {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance(SHA256);
        md.update(data.getBytes(DEFAULT_ENCODING));
        byte[] buffer = md.digest();

        return Hex.encodeHexString(buffer);
    }

    /**
     * make sha256
     *
     * @param data :
     * @return String :
     * @throws Exception :
     */
    public static String makeSha256(byte[] data) throws Exception {
        if (data == null || data.length == 0) {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance(SHA256);
        md.update(data);
        byte[] buffer = md.digest();

        return Hex.encodeHexString(buffer);
    }

    /**
     * make strong sha256
     *
     * @param data       :
     * @param salt       :
     * @param iterations :
     * @param outLength  :
     * @return String :
     * @throws Exception :
     */
    public static String makeStrongSha256(String data, byte[] salt, int iterations, int outLength) throws Exception {
        if (data == null || ArrayUtils.isEmpty(salt) || iterations < 1 || outLength < 1) {
            return null;
        }

        final int strongHashBits = outLength * Byte.SIZE;
        PBEKeySpec spec = new PBEKeySpec(data.toCharArray(), salt, iterations, strongHashBits);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF_SHA256);

        byte[] bytes = skf.generateSecret(spec).getEncoded();
        return Hex.encodeHexString(bytes);
    }

    private HashUtils() {
    }
}
