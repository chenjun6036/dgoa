package com.njust.dg.oa.action;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Controller("testAction")
@Scope("prototype")
public class Test extends ActionSupport{
	@Resource
	private ProcessEngine processEngine;
	@Override
	public String execute() throws Exception {
		try {
			processEngine.getExecutionService().signalExecutionById("materialPurReq.30001","to 审核人员审核");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
}
