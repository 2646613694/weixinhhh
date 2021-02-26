package com.jumpower.weixinhhh.bean;


import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Component
public  class WeChatContant {
    //APPID
    public static final String appID = "wxe7f5fef32ad4afd4";
    //appsecret
    public static final String appsecret = "fb1cb5514cbb481e4475462ecdbf76aa";
    // Token
    public static final String TOKEN = "xiewantao123";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    public static final Object REQ_MESSAGE_TYPE_TEXT = "text";
    public static final Object REQ_MESSAGE_TYPE_IMAGE = "image";
    public static final Object REQ_MESSAGE_TYPE_VOICE = "voice";
    public static final Object REQ_MESSAGE_TYPE_VIDEO = "video";
    public static final Object REQ_MESSAGE_TYPE_LOCATION = "location";
    public static final Object REQ_MESSAGE_TYPE_LINK = "link";
    public static final Object REQ_MESSAGE_TYPE_EVENT = "event";
    public static final Object EVENT_TYPE_SUBSCRIBE = "SUBSCRIBE";
    public static final Object EVENT_TYPE_UNSUBSCRIBE = "UNSUBSCRIBE";
    public static final Object EVENT_TYPE_SCAN = "SCAN";
    public static final Object EVENT_TYPE_LOCATION = "LOCATION";
    public static final Object EVENT_TYPE_CLICK = "CLICK";

    public static final String FromUserName = "FromUserName";
    public static final String ToUserName = "ToUserName";
    public static final String MsgType = "MsgType";
    public static final String Content = "Content";
    public static final String Event = "Event";

    @Autowired
    private RestTemplate restTemplate;

    private static RestTemplate restTemplateemp;

    @PostConstruct
    public void init(){
        restTemplateemp  = restTemplate;
    }

    /**
     * 编写Get请求的方法。但没有参数传递的时候，可以使用Get请求
     *
     * @param url 需要请求的URL
     * @return 将请求URL后返回的数据，转为JSON格式，并return
     */
    public static JSONObject doGerStr(String url) throws IOException {
        ResponseEntity responseEntity = restTemplateemp.getForEntity
                (
                        url,
                        String.class
                );
        Object body = responseEntity.getBody();
        assert body != null;
        JSONObject jsonObject = JSONObject.fromObject(body);
        System.out.println(11);
        return jsonObject;
    }



    /**
     * 编写Post请求的方法。当我们需要参数传递的时候，可以使用Post请求
     *
     * @param url 需要请求的URL
     * @param outStr  需要传递的参数
     * @return 将请求URL后返回的数据，转为JSON格式，并return
     */
    public static JSONObject doPostStr(String url,String outStr) throws ClientProtocolException, IOException {
        String uri = url;
        ResponseEntity<String> yinJiangResponseEntity = restTemplateemp.postForEntity
                (
                        uri,
                        generatePostJson(outStr),
                        String.class
                );

        String body = yinJiangResponseEntity.getBody();
        JSONObject jsonObject = null;
        jsonObject = JSONObject.fromObject(body);//字符串类型转为JSON类型
        return jsonObject;
    }

    /**
     * 获取AccessToken
     * @return 返回拿到的access_token及有效期
     */
    public static AccessToken getAccessToken() throws ClientProtocolException, IOException{
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", appID).replace("APPSECRET", appsecret);//将URL中的两个参数替换掉
        JSONObject jsonObject = doGerStr(url);//使用刚刚写的doGet方法接收结果
        if(jsonObject!=null){ //如果返回不为空，将返回结果封装进AccessToken实体类
            token.setToken(jsonObject.getString("access_token"));//取出access_token
            token.setExpiresIn(jsonObject.getInt("expires_in"));//取出access_token的有效期
        }
        return token;
    }

    /**
     * 生成post请求的JSON请求参数
     *
     * @param yinJiang 需要发送的json数据
     * @return
     */
    public static HttpEntity<String> generatePostJson(String yinJiang) {

        //如果需要其它的请求头信息、都可以在这里追加
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpHeaders.setContentType(type);
        HttpEntity<String> httpEntity = new HttpEntity<>(yinJiang, httpHeaders);
        return httpEntity;
    }

    /**
     * 生成get参数请求url
     * 示例：https://0.0.0.0:80/api?u=u&o=o
     * 示例：https://0.0.0.0:80/api
     *
     * @param protocol 请求协议 示例: http 或者 https
     * @param uri      请求的uri 示例: 0.0.0.0:80
     * @param yinJiang   请求参数
     * @return
     */
    public String generateRequestParameters(String protocol, String uri, Map<String, Object> yinJiang) {
        StringBuilder sb = new StringBuilder(protocol).append("://").append(uri);
        if (yinJiang!=null) {
            sb.append("?");
            for (Map.Entry map : yinJiang.entrySet()) {
                sb.append(map.getKey())
                        .append("=")
                        .append(map.getValue())
                        .append("&");
            }
            uri = sb.substring(0, sb.length() - 1);
            return uri;
        }
        return sb.toString();
    }

    /**
     * 生成get无餐请求url
     * @param protocol 请求协议 示例: http 或者 https
     * @param uri 请求的uri 示例: 0.0.0.0:80
     * @return
     */
    public String generateRequest(String protocol, String uri){
        StringBuilder sb = new StringBuilder(protocol).append("://").append(uri);
        return sb.toString();
    }



    /**
     * get请求、请求参数为?拼接形式的
     * <p>
     * 最终请求的URI如下：
     * <p>
     * http://127.0.0.1:80/?name=张耀烽&sex=男
     *
     * @return
     */
    public String sendGet(String protocol, String uri, Map<String, Object> yinJiang) {


        ResponseEntity responseEntity = restTemplate.getForEntity
                (
                        generateRequestParameters(protocol, uri, yinJiang),
                        String.class
                );
        return (String) responseEntity.getBody();
    }

}