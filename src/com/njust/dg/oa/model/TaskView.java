package com.njust.dg.oa.model;

import java.util.Date;

import org.jbpm.api.task.Task;

/**
 * 只是在“待我审批”页面中显示数据时使用，不对应数据表
 * 
 * @author chenjun
 * 
 */
public class TaskView {
//	private Task task; // 当前审批任务
	private String taskId;
//	private Form form; // 当前待审批的表单
	private String title;
	private Date applyTime;
	private String applicant;
	private Long formId;
	private String formTemplateName;

	

	public TaskView(Task task, Form form) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.setTaskId(task.getId());
//		this.form = form;
		this.title = form.getTitle();
		this.applicant = form.getApplicant().getRealName();
		this.setApplyTime(form.getApplyTime());
		this.formId = form.getId();
		this.formTemplateName = form.getFormTemplate().getName();
	}
//
//	public Task getTask() {
//		return task;
//	}
//
//	public void setTask(Task task) {
//		this.task = task;
//	}

//	public Form getForm() {
//		return form;
//	}
//
//	public void setForm(Form form) {
//		this.form = form;
//	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getFormTemplateName() {
		return formTemplateName;
	}

	public void setFormTemplateName(String formTemplateName) {
		this.formTemplateName = formTemplateName;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

}