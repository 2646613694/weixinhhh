package com.jumpower.weixinhhh.controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @ClassName: WeiXinController
 * @Description: TODO(对微信请求校验，成为开发者)
 * @author CaoWenCao
 * @date 2018年6月6日 下午2:09:38
 */
@Controller
@RequestMapping(value = "weixin")
public class WeiXinController {

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    @RequestMapping(value = "getWeiXinMethod", method = RequestMethod.GET)
    @ResponseBody
    public void getWeiXinMethod(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean validate = validate(request);
        if (validate) {
            response.getWriter().write(request.getParameter("echostr"));
            response.getWriter().close();
        }

    }

    private boolean validate(HttpServletRequest req) throws IOException {
        String signature = req.getParameter("signature");// 微信加密签名
        String timestamp = req.getParameter("timestamp");// 时间戳
        String nonce = req.getParameter("nonce");// 随机数
        List<String> list = new ArrayList<String>();
        list.add("xiewantao123");
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);// 字典排序
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            str += (String) list.get(i);
        }
        if (encode("SHA1", str).equalsIgnoreCase(signature)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static String encode(String algorithm, String str) {
        if (str == null) {
            return null;
        }
        try {
            // Java自带的加密类
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 转为byte
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
}