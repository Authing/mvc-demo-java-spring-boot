package cn.authing.javabasicdemo.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import cn.authing.core.auth.AuthenticationClient;
import cn.authing.core.types.ILogoutParams;
import cn.authing.core.types.IOidcParams;

@Controller
public class DemoController {

    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_ID_TOKEN = "id_token";
    public static final String KEY_REFRESH_TOKEN = "refresh_token";
    
    private final HttpSession session;
    private final ObjectFactory<AuthenticationClient> authClientFactory;

    public DemoController(HttpSession session, ObjectFactory<AuthenticationClient> authClientFactory) {
        this.session = session;
        this.authClientFactory = authClientFactory;
    } 

    @GetMapping("/login")
    public String login() {
        return "redirect:" + authClientFactory.getObject().buildAuthorizeUrl(new IOidcParams());
    }

    @GetMapping("/callback")
    @SuppressWarnings("unchecked")
    public String callback(@RequestParam String code) throws IOException {
        Map<String, String> res = 
            (Map<String, String>) authClientFactory.getObject().getAccessTokenByCode(code).execute();
        
        session.setAttribute(KEY_ACCESS_TOKEN, res.get("access_token"));
        session.setAttribute(KEY_ID_TOKEN, res.get("id_token"));
        session.setAttribute(KEY_REFRESH_TOKEN, res.get("refresh_token"));
        
        return "redirect:/profile";
    }

    @GetMapping("/logout")
    public String logout() {
        String idToken = (String) session.getAttribute(KEY_ID_TOKEN);
        if(idToken == null) {
            return "redirect:/profile"; // 用户当前未登录
        }
        
        session.invalidate();
        return "redirect:" + authClientFactory.getObject().buildLogoutUrl(
            new ILogoutParams(
                null,
                "http://localhost:8080/profile", // 登出完成后的回调地址
                null
            )
        );
    }

    @ResponseBody
    @GetMapping("/profile")
    public String profile() throws IOException {
        String accessToken = (String) session.getAttribute(KEY_ACCESS_TOKEN);
        if(accessToken == null) {
            return "未登录";
        }

        return authClientFactory.getObject().getUserInfoByAccessToken(accessToken).execute().toString();
    }
}
