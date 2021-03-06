package com.jumpower.weixinhhh.util;


import com.jumpower.weixinhhh.bean.*;
import net.sf.json.JSONObject;

import java.io.IOException;


import static com.jumpower.weixinhhh.bean.WeChatContant.doPostStr;

public class MenuUtil {
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";

    public static Menu initMenu() {
        Menu menu = new Menu();
        ClickButton clickButton = new ClickButton();
        clickButton.setKey("clickButton");
        clickButton.setType("click");
        clickButton.setName("输出你好");

        ViewButton viewButton = new ViewButton();
        viewButton.setName("百度一下");
        viewButton.setType("view");
        viewButton.setUrl("https://www.baidu.com/");

        ClickButton clickButton1 = new ClickButton();
        clickButton1.setKey("scanButton");
        clickButton1.setType("scancode_push");
        clickButton1.setName("菜单1");

        ClickButton clickButton2 = new ClickButton();
        clickButton2.setKey("locationButton");
        clickButton2.setType("location_select");
        clickButton2.setName("菜单2");

        ClickButton clickButton3 = new ClickButton();
        clickButton3.setKey("photoButton");
        clickButton3.setType("pic_sysphoto");
        clickButton3.setName("拍照");

        ClickButton clickButton4 = new ClickButton();
        clickButton4.setKey("picButton");
        clickButton4.setType("pic_weixin");
        clickButton4.setName("相册发图");


        Button button = new Button();
        button.setName("你是菜单");
        button.setSub_button(new Button[]{clickButton1, clickButton2,clickButton3,clickButton4});

        menu.setButton(new Button[]{clickButton, viewButton, button});
        return menu;
    }

    //创建菜单的url拼接
    public static int createMenu(String menu, String token) throws IOException {
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        int result = 0;
        JSONObject jsonObject = doPostStr(url, menu);
        if (jsonObject != null) {
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    //查询菜单的url的拼接
    public static JSONObject queryMenu(String token) throws IOException {

        String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = WeChatContant.doGerStr(url);
        return jsonObject;
    }
}
