# 安装依赖

多数 IDE 会自动利用 Maven 安装依赖。也可以在项目根目录运行以下命令来手动安装依赖：

```shell
./mvnw dependency:resolve
```

# 填写你的应用配置

在 JavaBasicDemoApplication.java 的第 20 行，修改配置为你的应用配置：

```java
AuthenticationClient client = new AuthenticationClient("POOL_ID"); // 改成自己的用户池ID

client.setAppId("APP_ID"); // 改成自己的 App ID
client.setSecret("APP_SECRET"); // 改成自己的 App Secret
client.setRedirectUri("http://localhost:8080/callback"); // 填写之前设置的回调地址
```

# 运行

直接在 IDE 中编译并运行即可。

# 参考文档

待填坑

# License

mvc-demo-java-spring is MIT licensed
