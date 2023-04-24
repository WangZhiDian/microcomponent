package com.meng.util;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;


/**
 * base 64 utils
 *
 * @author : sunyuecheng
 */
public final class CaptchaUtils {
    /**
     * encode data
     *
     * @return String :
     * @throws Exception :
     */
    public static String BufferImage2Base64(BufferedImage bufferedImage) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", stream);
        byte[] bytes = Base64.encodeBase64(stream.toByteArray());
        String base64 = Base64Utils.encodeData(stream.toByteArray());

        return "data:image/jpeg;base64,"+base64;
    }

    private CaptchaUtils() {
    }
}
