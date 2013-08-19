package com.njust.dg.oa.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class testMail {
	@Resource
	private JavaMailSender mailSender;

	@Test
	public void mailSender() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("15850546163@126.com");
		msg.setSubject("关于论文");
		msg.setTo("784588072@qq.com");
		msg.setText("关于qq论文");
		mailSender.send(msg);
	}

}
