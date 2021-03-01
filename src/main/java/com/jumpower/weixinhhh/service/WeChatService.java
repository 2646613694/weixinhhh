package com.jumpower.weixinhhh.service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface WeChatService {

     String processRequest(HttpServletRequest request);

     void  createMenu() throws IOException;

     void  seachCtity(String text) throws IOException;
}
