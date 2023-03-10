package com.jhzhong.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Scanner;

/**
 * Shiro Hello world!
 * Subject 对象常用 API：
 * subject.login(token) 登录认证
 * subject.logout() 注销
 * subject.getPrincipal() 获取登录凭证（登录名)
 * subject.isAuthenticated() 是否已登录
 */
public class HelloShiro {
    public static void main(String[] args) {
        // 1. 使用 Ini 工厂加载 shiro.ini 配置文件
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 2. 工厂模式创建 securityManager 实例对象
        SecurityManager securityManager = factory.getInstance();
        // 3. 将 securityManager 对象托管到 SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);
        // 4. SecurityUtils.getSubject() 获取 subject 对象
        Subject subject = SecurityUtils.getSubject();
        // 5. 常用 Subject API 演示
        Boolean isAuthenticated = subject.isAuthenticated();
        if (!isAuthenticated) {
            System.out.print("请输入登录名:");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.next();
            System.out.print("请输入密码:");
            String password = scanner.next();
            // 登录需要构造 UsernamePasswordToken
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // ops: 登陆失败会抛出异常，可自行补充异常处理
            subject.login(token);
            isAuthenticated = subject.isAuthenticated();
            System.out.println(isAuthenticated ? "log in sucess" : "log in failed");
            if (isAuthenticated) {
                boolean isAdmin = subject.hasRole("admin");
                boolean isPermitted = subject.isPermitted("user:update");
                System.out.println("isAdmin = " + isAdmin);
                System.out.println("isPermitted = " + isPermitted);
            }
        }

    }
}
