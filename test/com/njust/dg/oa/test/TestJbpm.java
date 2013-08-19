package com.njust.dg.oa.test;

import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestJbpm {
	@Resource
	private ProcessEngine processEngine;
	
	@Test
	public void deploy(){
		processEngine.getRepositoryService().createDeployment()
		.addResourceFromClasspath("model/equipUse.jpdl.xml")
		.addResourceFromClasspath("model/equipUse.png")
		.deploy();
	}
	
	@Test
	public void findDefinition(){
		List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService()
				.createProcessDefinitionQuery().list();
		for(ProcessDefinition pd : processDefinitions){
			System.out.println("id="+pd.getDeploymentId()
					+"name="+pd.getName()
					+"key="+pd.getKey());
		}
	}
	
	@Test
	public void findDefinition1(){
		List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey("materialPurchaseRequistion")
				.list();
//		System.out.println(processDefinition.getId()+"----"+processDefinition.getVersion());
//		for(ProcessDefinition pd : processDefinitions){
//			System.out.println("id="+pd.getDeploymentId()
//					+"name="+pd.getName()
//					+"key="+pd.getKey()+"version="+pd.getVersion());
//		}
		
		for (ProcessDefinition processDefinition : processDefinitions) {
//			processEngine.getExecutionService().createProcessInstanceQuery().processDefinitionId(arg0)
			List<HistoryProcessInstance> activityInstances = processEngine.getHistoryService().createHistoryProcessInstanceQuery().processDefinitionId(processDefinition.getId()).list();
			for (HistoryProcessInstance historyProcessInstance : activityInstances) {
				System.out.println(historyProcessInstance.getProcessInstanceId());
			}
		}
	}
	
	@Test
	public void createInstance(){
		ProcessInstance processInstance = processEngine.getExecutionService().startProcessInstanceByKey("materialPurReq");
	}
	
	@Test
	public void queryInstance(){
		List<ProcessInstance> processInstances = processEngine.getExecutionService()
				.createProcessInstanceQuery()
//				.processInstanceKey("materialPurReq")
				.list();
		
//		processEngine.getHistoryService().createHistoryProcessInstanceQuery().processInstanceKey(arg0)
		for(ProcessInstance instance : processInstances){
			System.out.println("id=" + instance.getId());
		}
	}
	
	@Test
	public void signalInstance(){
		processEngine.getExecutionService().signalExecutionById("materialPurReq.30001","to 审核人员审核");
	}
}
