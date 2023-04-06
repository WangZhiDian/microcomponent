package com.meng.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * rsa utils
 *
 * @author : sunyuecheng
 */
public final class RsaUtils {

    private static final int RSA_PKCS1_PADDING_SIZE = 11;

    private static final String CIPHER = "RSA/None/PKCS1Padding";

    private static final long DECRYPT_FILE_MAX_PLAIN_SIZE = 50 * 1024 * 1024;

    private static final String RSA = "RSA";

    private static final String CIPHER_PLUGIN = "BC";

    private RsaUtils() {
    }

    /**
     * make private key
     *
     * @param key :
     * @return PrivateKey :
     * @throws Exception :
     */
    public static PrivateKey makePrivateKey(String key) throws Exception {
        byte[] bytes = Base64.decodeBase64(key);

        KeyFactory factory = KeyFactory.getInstance(RSA);;
        KeySpec spec = new PKCS8EncodedKeySpec(bytes);
        return factory.generatePrivate(spec);
    }

    /**
     * make public key
     *
     * @param key :
     * @return PublicKey :
     * @throws Exception :
     */
    public static PublicKey makePublicKey(String key)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key);
        KeyFactory factory = KeyFactory.getInstance(RSA);
        KeySpec spec = new X509EncodedKeySpec(keyBytes);

        return factory.generatePublic(spec);
    }

    /**
     * decrypt
     *
     * @param encryptKey :
     * @param keyBits    :
     * @param encrypted  :
     * @param offset     :
     * @param length     :
     * @return byte[] :
     * @throws Exception :
     */
    public static byte[] decrypt(Key encryptKey, int keyBits, byte[] encrypted, int offset, int length)
            throws Exception {
        int plainLength = (encrypted[offset] & 0xFF) | ((encrypted[offset + 1] << 8) & 0xff00)
                | ((encrypted[offset + 2] << 16) & 0xff0000)
                | ((encrypted[offset + 3] << 24) & 0xff000000);
        int blockLength = keyBits / 8;
        if (length % blockLength != 4) {
            return null;
        }

        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER, CIPHER_PLUGIN);
        cipher.init(Cipher.DECRYPT_MODE, encryptKey);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int decryptedLength = 0;
        for (int begin = offset + 4; begin < length; begin += blockLength, plainLength -= decryptedLength) {
            byte[] plain = cipher.doFinal(encrypted, begin, blockLength);

            decryptedLength = Math.min(plain.length, plainLength);
            baos.write(plain, 0, decryptedLength);
        }

        return baos.toByteArray();
    }

    /**
     * decrypt file
     *
     * @param encryptedFilepath :
     * @param plainFilepath     :
     * @param encryptKey        :
     * @param keyBits           :
     * @return boolean :
     * @throws Exception :
     */
    public static boolean decryptFile(String encryptedFilepath, String plainFilepath, Key encryptKey, int keyBits)
            throws Exception {
        int blockLength = keyBits / 8;

        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER, CIPHER_PLUGIN);
        cipher.init(Cipher.DECRYPT_MODE, encryptKey);

        File inFile = new File(encryptedFilepath);
        File outFile = new File(plainFilepath);
        long fileSize = inFile.length();
        if (fileSize <= 4 || fileSize % blockLength != 4) {
            return false;
        }
        boolean ok = false;
        try (FileInputStream fis = new FileInputStream(inFile); FileOutputStream fos = new FileOutputStream(outFile)) {

            byte[] head = new byte[4];
            int readSize = fis.read(head, 0, 4);
            if (readSize != 4) {
                return false;
            }

            int plainLength = (head[0] & 0xFF) | ((head[1] << 8) & 0xff00)
                    | ((head[2] << 16) & 0xff0000)
                    | ((head[3] << 24) & 0xff000000);

            if (plainLength > DECRYPT_FILE_MAX_PLAIN_SIZE) {
                return false;
            }

            byte[] encrypted = new byte[blockLength];

            int decryptedLength = 0;
            readSize = fis.read(encrypted, 0, blockLength);
            for (; readSize == blockLength; plainLength -= decryptedLength) {
                byte[] plain = cipher.doFinal(encrypted, 0, blockLength);
                decryptedLength = Math.min(plain.length, plainLength);

                fos.write(plain, 0, decryptedLength);

                readSize = fis.read(encrypted, 0, blockLength);
            }

            if (plainLength != 0) {
                return false;
            }

            ok = true;
        } finally {
            if (!ok) {
                boolean ret = outFile.delete();
                if (ret) {
                    throw new Exception("Delete file error.");
                }
            }
        }

        return ok;
    }

    /**
     * encrypt
     *
     * @param encryptKey :
     * @param keyBits    :
     * @param plain      :
     * @param offset     :
     * @param length     :
     * @return byte[] :
     * @throws Exception :
     */
    public static byte[] encrypt(Key encryptKey, int keyBits, byte[] plain, int offset, int length)
            throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] head = new byte[4];
        head[0] = (byte) (length & 0xFF);
        head[1] = (byte) ((length >> 8) & 0xFF);
        head[2] = (byte) ((length >> 16) & 0xFF);
        head[3] = (byte) ((length >> 24) & 0xFF);
        int blockLength = keyBits / 8 - RSA_PKCS1_PADDING_SIZE;

        baos.write(head);

        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER, CIPHER_PLUGIN);
        cipher.init(Cipher.ENCRYPT_MODE, encryptKey);

        int left = length;
        for (int begin = 0; begin < length; begin += blockLength, left -= blockLength) {
            int toEncryptLength = Math.min(blockLength, left);
            byte[] encrypted = cipher.doFinal(plain, begin, toEncryptLength);
            baos.write(encrypted);
        }

        return baos.toByteArray();
    }

}
