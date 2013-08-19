package com.njust.dg.oa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Repository;

import com.njust.dg.oa.dao.FormDao;
import com.njust.dg.oa.dao.UserDao;
import com.njust.dg.oa.model.ApproveInfo;
import com.njust.dg.oa.model.Form;
import com.njust.dg.oa.model.FormTemplate;
import com.njust.dg.oa.model.TaskView;
import com.njust.dg.oa.model.User;
import com.njust.dg.oa.service.ApproveInfoService;
import com.njust.dg.oa.service.FormService;
import com.njust.dg.oa.service.FormTemplateService;
import com.njust.dg.oa.util.ConfigUtil;
import com.njust.dg.oa.util.MailUtils;
@Repository("formService")
public class FormServiceImpl implements FormService {
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private FormDao formDao;
	@Resource
	private ApproveInfoService approveInfoService;
	@Resource
	private FormTemplateService formTemplateService;
	@Resource
	private UserDao userDao;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String openMailNotice = (String) ConfigUtil.getInstance().readConfig("openMailNotice");
	@Override
	public void submit(Form form,Map<String,String> shs) {
		try {
			// TODO Auto-generated method stub
			form.setApplyTime(new Date());
			// 1，设置属性并保存表单
			form.setTitle(form.getFormTemplate().getName() // 标题的格式为：{模板名称}_{申请人姓名}_{申请时间}
					+ "_" + form.getApplicant().getRealName() //
					+ "_" + sdf.format(form.getApplyTime()));
			form.setStatus(Form.STATUS_RUNNING); // 状态为正在审批中
			formDao.add(form); // 保存
			// 2，开始流转
			// >> a, 启动流程实例，并设置流程变量
			String processKey = form.getFormTemplate().getProcessDefinitionKey();
			Map<String, Object> variables = new HashMap<String, Object>(); // 流程变量
			variables.put("form", form);
			Set<String> key = shs.keySet();
	        for (Iterator<String> it = key.iterator(); it.hasNext();) {
	            String s = (String) it.next();
	            variables.put(s, shs.get(s));
	        }
			ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey(processKey, variables);

			// >> b, 完成第一个任务“提交申请”
			Task task = processEngine.getTaskService()// 
					.createTaskQuery()// 查询出本流程实例中当前仅有一个任务（提交申请的任务）
					.processInstanceId(pi.getId())//
					.uniqueResult();//
			processEngine.getTaskService().completeTask(task.getId()); // 完成任务
			pi = processEngine.getExecutionService().findProcessInstanceById(task.getExecutionId());
			task = processEngine.getTaskService().createTaskQuery().executionId(pi.getId()).uniqueResult();
			String userName=task.getAssignee();
			
			if(openMailNotice.equals("true") && userName != null  ){
				User user = userDao.findUserByUserName(userName);
				if(user.getEmail() != null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String subject = "有" + form.getFormTemplate().getName()+"需要您审批！";
					String to = user.getEmail();
					String text = subject + "    申请人："+user.getRealName()+"    申请时间："+sdf.format(form.getApplyTime());
					MailUtils.getInstance().SendSystemMail(subject, to, text);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public List<TaskView> findMyTaskViewList(User u, Long formTemplateId) {
		// TODO Auto-generated method stub
		// 1，获取任务列表
		List<Task> taskList = processEngine.getTaskService().findPersonalTasks(u.getUserName());
		
		// 2，获取与当前审批任务对应的待审批的表单，并放到要返回的结果集合中
		List<TaskView> taskViewList = new ArrayList<TaskView>();
		for (Task task : taskList) {
			Form form = (Form) processEngine.getTaskService().getVariable(task.getId(), "form");
			if(form.getFormTemplate().getId() == formTemplateId){
				taskViewList.add(new TaskView(task, form));
			}
		}
		

		return taskViewList;
	}
	@Override
	public Form load(Long formId) {
		// TODO Auto-generated method stub
		return formDao.load(formId);
	}
	@Override
	public void approve(ApproveInfo approveInfo, String taskId) {
		// TODO Auto-generated method stub
		// 保存审批信息
		
		try {
			approveInfoService.save(approveInfo);

			// 完成任务
			Task task = processEngine.getTaskService().getTask(taskId);// 这个获取正在执行的任务的代码必须要放在完成任务之前，如果放到后则就查不到了，因为变成历史了
			processEngine.getTaskService().completeTask(taskId);

			// 获取当前正执行的流程实例对象（如果查到的为null，表示已经执行完了）
			ProcessInstance pi = processEngine.getExecutionService().findProcessInstanceById(task.getExecutionId());
			
			// 维护表单状态
			Form form = approveInfo.getForm();
			if (!approveInfo.isApproval()) {
				// 如果本次未同意，流程直接结束，表单状态为：未通过
				if (pi != null) {
					processEngine.getExecutionService().endProcessInstance(task.getExecutionId(), ProcessInstance.STATE_ENDED);
				}
				form.setStatus(Form.STATUS_REJECTED);
			} else {
				// 如果本次同意，且本次是最后一个审批，就表示所有的环节都通过了，则流程正常结束，表单的状态为：已通过
				if (pi == null) { // 如果本流程实例已执行完，表示本次是最后一个审批
					form.setStatus(Form.STATUS_APPROVED);
				}else{
					task = processEngine.getTaskService().createTaskQuery().executionId(pi.getId()).uniqueResult();
					String userName=task.getAssignee();
					if(openMailNotice.equals("true") && userName != null){
						User user = userDao.findUserByUserName(userName);
						if(user.getEmail() != null){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String subject = "有" + form.getFormTemplate().getName()+"需要您审批！";
							String to = user.getEmail();
							String text = subject + "    申请人："+user.getRealName()+"    申请时间："+sdf.format(form.getApplyTime());
							MailUtils.getInstance().SendSystemMail(subject, to, text);
						}
					}
				}
			}
			formDao.update(form);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@Override
	public List<Form> getMyApplications(User currentUser,Long templateId,String status) {
		// TODO Auto-generated method stub
		try {
			FormTemplate formTemplate = formTemplateService.load(templateId);
			if(status.equals("==显示全部==")){
				Object[] o = new Object[]{currentUser,formTemplate};		
				return formDao.list("From Form f where f.applicant = ? and f.formTemplate = ? order by f.applyTime DESC", o);
			}
			Object[] o = new Object[]{currentUser,formTemplate,status};		
			return formDao.list("From Form f where f.applicant = ? and f.formTemplate = ? and f.status = ? order by f.applyTime DESC", o);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Form> getFormList(long formTemplateId) {
		// TODO Auto-generated method stub
		try {
			FormTemplate formTemplate = formTemplateService.load(formTemplateId);
			return formDao.list("From Form f where f.formTemplate = ? order by f.applyTime DESC", formTemplate);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**未完成*/
	@Override
	public List<TaskView> getFormListByTemplate(long formTemplateId) {
		// TODO Auto-generated method stub
		try {
			FormTemplate formTemplate = formTemplateService.load(formTemplateId);
			List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService()
					.createProcessDefinitionQuery()
					.processDefinitionKey(formTemplate.getProcessDefinitionKey())
					.list();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
