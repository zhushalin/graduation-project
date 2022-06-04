package com.exam.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.exam.interceptor.JwtIgnore;
import com.google.code.kaptcha.Producer;
import com.exam.DTO.KeyDTO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

//控制器提供生成验证码
@JwtIgnore
@RestController
public class AuthController extends BaseController {
    @Autowired
    Producer producer;

    @Autowired
    KeyDTO keyDTO;
    public final static String CAPTCHA_KEY = "captcha";
    @GetMapping("/captcha")
    public Msg captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        String code = producer.createText();

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        Encoder encoder = Base64.getEncoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encode(outputStream.toByteArray());

        return ResultUtil.success(
                MapUtil.builder()
                        .put("key", key)
                        .put("captchaImg", base64Img)
                        .build()
        );
    }
}
