package com.njust.dg.oa.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigUtil {
	private static ConfigUtil instance = null;  
	private static String configFileString = "systemConfig.properties";
	private static Logger logger = Logger.getLogger(ConfigUtil.class.getName());
	public static ConfigUtil getInstance()  
    {  
        if (instance == null)  
        {  
            instance = new ConfigUtil();  
        }  
        return instance;  
    } 
	public Object readConfig(String key){
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
        
		return p.get(key);
	}
}
