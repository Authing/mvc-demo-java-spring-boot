package cn.authing.javabasicdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import cn.authing.core.auth.AuthenticationClient;

@SpringBootApplication
public class JavaBasicDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaBasicDemoApplication.class, args);
    }

    @Bean
    @Scope(value = "prototype")
    public AuthenticationClient authenticationClient() {
        AuthenticationClient client = new AuthenticationClient("POOL_ID"); // 改成自己的用户池ID

        client.setAppId("APP_ID"); // 改成自己的 App ID
        client.setSecret("APP_SECRET"); // 改成自己的 App Secret
        client.setRedirectUri("http://localhost:8080/callback"); // 填写之前设置的回调地址

        return client;
    }

}
