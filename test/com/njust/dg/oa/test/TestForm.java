package com.njust.dg.oa.test;

import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.njust.dg.oa.model.Form;
import com.njust.dg.oa.model.FormTemplate;
import com.njust.dg.oa.service.FormTemplateService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestForm {
	@Resource
	private FormTemplateService formTemplateService;
	@Resource
	private ProcessEngine processEngine;
	
	
	@Test
	public void testLoad(){
		Long id = (long) 1;
		FormTemplate formTemplate = formTemplateService.load(id);
		System.out.println(formTemplate.getId());
	}
	@Test
	public void testFindPersonalTasks(){
		List<Task> taskList = processEngine.getTaskService().findPersonalTasks("chenjun");
		for (Task task : taskList) {
			Form form = (Form) processEngine.getExecutionService().getVariable(task.getExecutionId(), "form");
			if(form.getFormTemplate().getId() == (long)1){
				System.out.println(task.getId());
			}
		}
	}
}
