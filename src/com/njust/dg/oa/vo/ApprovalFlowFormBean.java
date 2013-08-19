package com.njust.dg.oa.vo;

import java.io.File;

public class ApprovalFlowFormBean {
	private String title;
	private int audit;
	private int executor;
	private int admin;
	private int superAdmin;
	private String remark;
	private File file;
	private String fileName;
	private Boolean success;
	private long formTemplateId;
	private boolean approval;
	private String comment;
	private String taskId;
	private Long formId;
	private String status;
	public boolean isApproval() {
		return approval;
	}
	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	//	private List<TaskView> taskViewList;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAudit() {
		return audit;
	}
	public void setAudit(int audit) {
		this.audit = audit;
	}
	public int getExecutor() {
		return executor;
	}
	public void setExecutor(int executor) {
		this.executor = executor;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	public int getSuperAdmin() {
		return superAdmin;
	}
	public void setSuperAdmin(int superAdmin) {
		this.superAdmin = superAdmin;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
//	
//	public List<TaskView> getTaskViewList() {
//		return taskViewList;
//	}
//	public void setTaskViewList(List<TaskView> taskViewList) {
//		this.taskViewList = taskViewList;
//	}
	public long getFormTemplateId() {
		return formTemplateId;
	}
	public void setFormTemplateId(long formTemplateId) {
		this.formTemplateId = formTemplateId;
	}
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
