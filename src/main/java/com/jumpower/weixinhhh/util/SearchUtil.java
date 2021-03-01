package com.jumpower.weixinhhh.util;

import com.jumpower.weixinhhh.bean.Language;
import net.sf.json.JSONObject;

import java.io.IOException;

import static com.jumpower.weixinhhh.bean.WeChatContant.doPostStr;

/**
 * 发送语义理解请求
 * @author  by xka
 * @date  2021.03.01
 */
public class SearchUtil {
    public static final String SEMPROXY_SEACH_URL="https://api.weixin.qq.com/semantic/semproxy/search?access_token=ACCESS_TOKEN";


    public static Language initMenu() {
        Language language = new Language();
        language.setQuery("查一下明天从北京到上海的南航机票");
        language.setCategory("flight,hotel");
        language.setCity("北京");
        return language;
    }

    //创建菜单的url拼接
    public static int seachCtity(String query, String token) throws IOException {
        String url = SEMPROXY_SEACH_URL.replace("ACCESS_TOKEN", token);
        int result = 0;
       /* String post_data = "{"query":"查一下明天从北京到上海的南航机票","city":"北京","category":
        "flight,hotel","appid":"wxaaaaaaaaaaaaaaaa","uid":"123456"}""*/

    JSONObject jsonObject = doPostStr(url, query);
        if (jsonObject != null) {
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

}
