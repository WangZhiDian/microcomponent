package com.meng.controller;

import com.google.code.kaptcha.Producer;
import com.meng.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 图片验证码（支持算术形式）
 * 
 * @author ruoyi
 */
@RestController()
public class CaptchaController
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    /**
     * 验证码生成
     */
    @GetMapping(value = "/captcha/image")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response)
    {
        ServletOutputStream out = null;
        try {
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

            String type = request.getParameter("type");
            String capStr = null;
            String code = null;
            BufferedImage bi = null;
            if ("math".equals(type))
            {
                String capText = captchaProducerMath.createText();
                capStr = capText.substring(0, capText.lastIndexOf("@"));
                code = capText.substring(capText.lastIndexOf("@") + 1);
                bi = captchaProducerMath.createImage(capStr);
            }
            else if ("char".equals(type))
            {
                capStr = code = captchaProducer.createText();
                bi = captchaProducer.createImage(capStr);
            }
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }


    @GetMapping(value = "/captcha/image2")
    public String getKaptchaImage2(HttpServletRequest request) throws Exception {
        String type = request.getParameter("type");
        String capStr = null;
        String code = null;
        BufferedImage bi = null;
        if ("math".equals(type)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            bi = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(type)) {
            capStr = captchaProducer.createText();
            bi = captchaProducer.createImage(capStr);
        }
        //String b = CaptchaUtils.BufferImage2Base64(bi);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", stream);
        //byte[] bytes = Base64.encodeBase64(stream.toByteArray());
        //String base64 = new String(bytes);
        String base64 = Base64Utils.encodeData(stream.toByteArray());
        return  "data:image/jpeg;base64,"+base64;
    }
}