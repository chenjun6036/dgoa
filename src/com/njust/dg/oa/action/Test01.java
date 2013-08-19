package com.njust.dg.oa.action;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class Test01 {
	private SessionFactory sessionFactory;
	private ProcessEngine processEngine;

	@Test
	public void create() {
		new Configuration().configure("jbpm.hibernate.cfg.xml").buildSessionFactory();
	}

	@Test
	public void create1() {
		getProcessEngine().getRepositoryService().createDeployment().addResourceFromClasspath("model/materialPurchaseRequistion.jpdl.xml")
				.addResourceFromClasspath("model/materialPurchaseRequistion.png").deploy();
	}

	// 列出部署的流程定义信息
	@Test
	public void list() {
		List<ProcessDefinition> definitions = processEngine.getRepositoryService().createProcessDefinitionQuery().list();

		for (ProcessDefinition processDefinition : definitions) {
			System.out.println("id=" + processDefinition.getId() + ",name=" + processDefinition.getName() + ",version="
					+ processDefinition.getVersion() + ",key=" + processDefinition.getKey() + ",deployment"
					+ processDefinition.getDeploymentId());
		}
	}

	// 列出所有最高版本的流程定义
	@Test
	public void listLastVersion() {
		// 查询出所有的流程定义，按version升序排序，版本高的在后面
		List<ProcessDefinition> definitions = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION).list();
		// 过滤出最新版本流程定义
		Map<String, ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
		for (ProcessDefinition processDefinition : definitions) {
			map.put(processDefinition.getKey(), processDefinition);
		}

		// 显示
		for (ProcessDefinition processDefinition : map.values()) {
			System.out.println("id=" + processDefinition.getId() + ",name=" + processDefinition.getName() + ",version="
					+ processDefinition.getVersion() + ",key=" + processDefinition.getKey() + ",deployment"
					+ processDefinition.getDeploymentId());
		}
	}

	// 刪除某個部署信息
	@Test
	public void delete() {
		String deploymentId = "1";
		processEngine.getRepositoryService().deleteDeploymentCascade(deploymentId);
		list();
	}

	// 删除指定key的所有版本的流程]
	@Test
	public void deleteByKey() {
		List<ProcessDefinition> definitions = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey("test111").list();
		for (ProcessDefinition processDefinition : definitions) {
			processEngine.getRepositoryService().deleteDeploymentCascade(processDefinition.getDeploymentId());
		}
	}

	@Test
	public void getResourceFormDeployment() throws Exception {
		String deploymentId = "20001";

		Set<String> names = processEngine.getRepositoryService().getResourceNames(deploymentId);
		for (String name : names) {
			System.out.println(name);
		}
		InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId, "model/test.png");
		OutputStream out = new FileOutputStream("D:/test.png");
		for (int b = -1; (b = in.read()) != -1;) {
			out.write(b);
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	@Resource
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

}
