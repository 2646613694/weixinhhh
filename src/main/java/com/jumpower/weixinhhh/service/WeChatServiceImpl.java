package com.jumpower.weixinhhh.service;

import com.jumpower.weixinhhh.bean.ArticleItem;
import com.jumpower.weixinhhh.bean.WeChatContant;

import com.jumpower.weixinhhh.util.FaceUtil;
import com.jumpower.weixinhhh.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 核心服务类
 */
@Service("weChatService")
public class WeChatServiceImpl implements WeChatService{

    private static final String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";




     @Override
    public String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent;
        try {
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = WeChatUtil.parseXml(request);
            // 消息类型
            String msgType = (String) requestMap.get(WeChatContant.MsgType);
            String mes = null;
            // 文本消息
            if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_TEXT)) {
                mes =requestMap.get(WeChatContant.Content).toString();
                if(mes!=null&&mes.length()<2){
                    List<ArticleItem> items = new ArrayList<>();
                    ArticleItem item = new ArticleItem();
                    item.setTitle("照片墙");
                    item.setDescription("阿狸照片墙");
                    item.setPicUrl("https://uploadfile.bizhizu.cn/2015/0421/20150421080459728.jpg.220.146.jpg");
                    item.setUrl("https://www.bizhizu.cn/pic/5966.html");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("哈哈");
                    item.setDescription("一张照片");
                    item.setPicUrl("http://changhaiwx.pagekite.me/images/me.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/index");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("小游戏2048");
                    item.setDescription("小游戏2048");
                    item.setPicUrl("http://changhaiwx.pagekite.me/images/2048.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/game2048");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("百度");
                    item.setDescription("百度一下");
                    item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505100912368&di=69c2ba796aa2afd9a4608e213bf695fb&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170510%2F0634355517-9.jpg");
                    item.setUrl("http://www.baidu.com");
                    items.add(item);

                    respXml = WeChatUtil.sendArticleMsg(requestMap, items);
                }else if("我的信息".equals(mes)){
                    String s = requestMap.get(WeChatContant.FromUserName);
                    System.out.println(s);
                    /*Map<String, String> userInfo = getUserInfo(requestMap.get(WeChatContant.FromUserName));
                    System.out.println(userInfo.toString());
                    String nickname = userInfo.get("nickname");
                    String city = userInfo.get("city");
                    String province = userInfo.get("province");
                    String country = userInfo.get("country");
                    String headimgurl = userInfo.get("headimgurl");*/
                    List<ArticleItem> items = new ArrayList<>();
                    ArticleItem item = new ArticleItem();
                    item.setTitle("你的信息");
                    item.setDescription("昵称:"+11+"  地址:"+1+" "+1+" "+1);
                    item.setPicUrl("https://img1.doubanio.com/view/group_topic/large/public/p116915028.jpg");
                    item.setUrl("http://www.baidu.com");
                    items.add(item);

                    respXml = WeChatUtil.sendArticleMsg(requestMap, items);
                }
            }
            // 图片消息
            else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_IMAGE)) {

                String picUrl = requestMap.get("PicUrl");
                String xx = FaceUtil.xx(picUrl);
                System.out.println("年龄是"+xx);
                respContent = "年龄是"+xx;
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 语音消息
            else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 视频消息
            else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 地理位置消息
            else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 链接消息
            else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 事件推送
            else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = (String) requestMap.get(WeChatContant.Event);
                // 关注
                if (eventType.equals(WeChatContant.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                    respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
                }
                // 取消关注
                else if (eventType.equals(WeChatContant.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                }
                // 扫描带参数二维码
                else if (eventType.equals(WeChatContant.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equals(WeChatContant.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(WeChatContant.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                }
            }
            mes = mes == null ? "不知道你在干嘛" : mes;
            if(respXml == null) {
               // System.out.println("消息是"+mes);
                respXml = WeChatUtil.sendTextMsg(requestMap, mes);
            }
            System.out.println(respXml);
            return respXml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

}