package com.njust.dg.oa.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailUtils {
	private static MailUtils instance = null;  
	
	/** 
     * 日志组件 
     */  
	private static Logger logger = Logger.getLogger(MailUtils.class.getName()); 
	/** 
     * 配置文件名称 
     */  
    private String configFileString = "mail.properties"; 
    
    /** 
     * 创建本类的单例 
     * <p/> 
     * getInstance(单例) 
     * 
     * @return FtpURLUtil对象 
     */  
    public static MailUtils getInstance()  
    {  
        if (instance == null)  
        {  
            instance = new MailUtils();  
        }  
        return instance;  
    } 
	public  JavaMailSenderImpl getMailSender(){
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		//读取配置文件       
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configFileString);  
  
        //读路径出错，换另一种方式读取配置文件  
        if (null == inputStream)  
        {  
            logger.error("read config file failed.");  
            return null;  
        }  
  
        //读取配置文件中的appid和moduleId  
        Properties p = new Properties();  
  
        try  
        {  
            p.load(inputStream);  
        }  
        catch (IOException e1)  
        {  
            logger.error("Load config file failed." + e1);  
            return null;  
        } 
        String host = p.getProperty("mail.host");
        String username = p.getProperty("mail.username");
        String password = p.getProperty("mail.password");
        javaMailSender.setHost(host);
        javaMailSender.setPassword(password);
        javaMailSender.setUsername(username);
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailSender.setJavaMailProperties(javaMailProperties);
		return javaMailSender;
	}
	
	public void SendSystemMail(String subject, String to, String text){
		JavaMailSenderImpl javaMailSender = this.getMailSender();
		SimpleMailMessage msg = new SimpleMailMessage();
		//读取配置文件       
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configFileString);  
  
        //读路径出错，换另一种方式读取配置文件  
        if (null == inputStream)  
        {  
            logger.error("read config file failed.");  
            return;  
        }  
  
        //读取配置文件中的appid和moduleId  
        Properties p = new Properties();  
  
        try  
        {  
            p.load(inputStream);  
        }  
        catch (IOException e1)  
        {  
            logger.error("Load config file failed." + e1);  
            return;  
        } 
		String from = p.getProperty("mail.username");
		msg.setFrom(from);
		msg.setSubject(subject);
		msg.setTo(to);
		msg.setText(text);
		try {
			javaMailSender.send(msg);
			System.out.println("发送成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
//	public static void main(String[] args) {
//		MailUtils mailUtils = new MailUtils();
//		JavaMailSenderImpl mailSenderImpl= mailUtils.getMailSender();
//		System.out.println(mailSenderImpl.getHost()+"------"+mailSenderImpl.getPassword()+"-------"+mailSenderImpl.getUsername());
//	}
	
}
