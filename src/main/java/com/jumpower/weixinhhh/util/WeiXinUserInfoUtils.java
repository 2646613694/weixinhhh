package com.jumpower.weixinhhh.util;



import com.jumpower.weixinhhh.bean.AccessToken;
import com.jumpower.weixinhhh.bean.WeChatContant;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;

/**
 * @author scw
 * @create 2018-01-18 16:42
 * @desc 用于获取微信用户的信息
 **/
public class WeiXinUserInfoUtils {
    private static final String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";


    /**
     * 获取微信用户账号的相关信息
     * @param opendID  用户的openId，这个通过当用户进行了消息交互的时候，才有
     * @return
     */
    public static String getUserInfo(String opendID) throws IOException {

        AccessToken accessToken = WeChatContant.getAccessToken();
        //获取access_token
        String token = accessToken.getToken();
        String url = GET_USERINFO_URL.replace("ACCESS_TOKEN" , token);
        url = url.replace("OPENID" ,opendID);
        JSONObject jsonObject = WeChatContant.doGerStr(url);
        return jsonObject.toString();
    }
}

