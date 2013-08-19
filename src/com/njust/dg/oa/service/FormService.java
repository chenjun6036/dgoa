package com.njust.dg.oa.service;

import java.util.List;
import java.util.Map;

import com.njust.dg.oa.model.ApproveInfo;
import com.njust.dg.oa.model.Form;
import com.njust.dg.oa.model.TaskView;
import com.njust.dg.oa.model.User;

public interface FormService {
	public void submit(Form form,Map<String,String> shs);

	public List<TaskView> findMyTaskViewList(User u, Long formTemplateId);

	public Form load(Long formId);

	public void approve(ApproveInfo approveInfo, String taskId);

	public List<Form> getMyApplications(User currentUser,Long templateId,String status);

	public List<Form> getFormList(long formTemplateId);

	public List<TaskView> getFormListByTemplate(long formTemplateId);
}
