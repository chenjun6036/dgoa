package com.njust.dg.oa.jbpm;

import java.util.Map;

import javax.annotation.Resource;


import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.njust.dg.oa.util.MailUtils;
@SuppressWarnings("serial")
public class ReviewerVerify implements ExternalActivityBehaviour {
	@Resource(name="mailSender")
	private JavaMailSender mailSender;

	@Override
	public void execute(ActivityExecution arg0) throws Exception {
		JavaMailSenderImpl javaMailSender = MailUtils.getInstance().getMailSender();
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("15850546163@126.com");
		msg.setSubject("关于论文");
		msg.setTo("784588072@qq.com");
		msg.setText("关于qq论文");
		javaMailSender.send(msg);
		arg0.waitForSignal();
	}

	@Override
	public void signal(ActivityExecution arg0, String arg1, Map<String, ?> arg2)
			throws Exception {
		// TODO Auto-generated method stub
		arg0.take(arg1);
	}

}
