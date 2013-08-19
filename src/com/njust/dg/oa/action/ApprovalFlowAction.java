package com.njust.dg.oa.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.njust.dg.oa.model.ApproveInfo;
import com.njust.dg.oa.model.Form;
import com.njust.dg.oa.model.FormTemplate;
import com.njust.dg.oa.model.TaskView;
import com.njust.dg.oa.model.User;
import com.njust.dg.oa.service.FormService;
import com.njust.dg.oa.service.FormTemplateService;
import com.njust.dg.oa.service.UserService;
import com.njust.dg.oa.util.ConfigUtil;
import com.njust.dg.oa.util.FileIOUtil;
import com.njust.dg.oa.util.RandomStringUtil;
import com.njust.dg.oa.vo.ApprovalFlowFormBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller("approvalFlowAction")
@Scope("prototype")
public class ApprovalFlowAction extends ActionSupport implements ModelDriven<ApprovalFlowFormBean> , SessionAware{
	private ApprovalFlowFormBean approvalFlowFormBean = new ApprovalFlowFormBean();
	private Map<String, Object> session;
	@Resource
	private FormTemplateService formTemplateService;
	@Resource
	private FormService formService;
	@Resource
	private UserService userService;
//	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public ApprovalFlowFormBean getModel() {
		// TODO Auto-generated method stub
		return approvalFlowFormBean;
	}
	
	private User getCurrentUser(){
		return (User)session.get("user");
	}
	
	private static String  getExtension(String fileName){
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}
	
	public String submit() throws Exception{
		Form form = new Form();
		String realpath = (String) ConfigUtil.getInstance().readConfig("saveFilePath");
		User u = getCurrentUser();
		if(u == null){
			approvalFlowFormBean.setSuccess(false);
			return SUCCESS;
		}
		FormTemplate formTemplate = formTemplateService.load(approvalFlowFormBean.getFormTemplateId());
		String fileExtension = getExtension(approvalFlowFormBean.getFileName());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = u.getRealName() + "-" + formTemplate.getName() + "-" + formatter.format(new Date()) + "--" +RandomStringUtil.getRandomString(5) + fileExtension;
		if(approvalFlowFormBean.getFile() != null){
			try {
				File saveFile = new File(new File(realpath),fileName);
				if (!saveFile.getParentFile().exists())
	                saveFile.getParentFile().mkdirs();
				FileUtils.copyFile(approvalFlowFormBean.getFile(), saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			form.setPath(realpath+"/"+fileName);
		}
		try {
			form.setFormTemplate(formTemplateService.load(approvalFlowFormBean.getFormTemplateId()));
//			form.setFormTemplate(formTemplateService.load((long) 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(form.getFormTemplate().getName());
		form.setApplicant(u);
		User audit = userService.load(approvalFlowFormBean.getAudit());
		User executor = userService.load(approvalFlowFormBean.getExecutor());
		
		Map<String,String> shs = new HashMap<String, String>();
		shs.put("audit", audit.getUserName());
		shs.put("executor", executor.getUserName());
		if(approvalFlowFormBean.getAdmin() != 0){
			User admin = userService.load(approvalFlowFormBean.getAdmin());
			shs.put("admin", admin.getUserName());
		}		
		if(approvalFlowFormBean.getSuperAdmin() != 0){
			User superAdmin = userService.load(approvalFlowFormBean.getSuperAdmin());
			shs.put("superAdmin", superAdmin.getUserName());
		}
		formService.submit(form,shs);		
		approvalFlowFormBean.setSuccess(true);
		return SUCCESS;
	}
	
	/** 待我审批（我的任务列表） */
	public String myTaskList() {
		List<TaskView> taskViewList = formService.findMyTaskViewList(getCurrentUser(),approvalFlowFormBean.getFormTemplateId());
		if(approvalFlowFormBean.getFormTemplateId() == 1){
			List<TaskView> taskViewList1 =  formService.findMyTaskViewList(getCurrentUser(),(long) 2);
			taskViewList.addAll(taskViewList1);
		}
		if(approvalFlowFormBean.getFormTemplateId() == 3){
			List<TaskView> taskViewList1 =  formService.findMyTaskViewList(getCurrentUser(),(long) 4);
			taskViewList.addAll(taskViewList1);
		}
		Collections.sort(taskViewList, new Comparator<TaskView>() {
            public int compare(TaskView arg0, TaskView arg1) {
                return arg0.getApplyTime().compareTo(arg1.getApplyTime());
            }
        });
//		approvalFlowFormBean.setTaskViewList(taskViewList);
//		List<Form> forms =new ArrayList<Form>();
//		List<Task> tasks = new ArrayList<Task>();
 		ActionContext.getContext().put("taskViewList", taskViewList);
		return "myTaskList";
	}
	
	/** 审批处理 */
	public String approve() {
		// 生成一个ApproveInfo对象，表示本次的审批信息
		ApproveInfo approveInfo = new ApproveInfo();

		approveInfo.setApproval(approvalFlowFormBean.isApproval()); // 本次是否同意
		approveInfo.setComment(approvalFlowFormBean.getComment());
		approveInfo.setForm(formService.load(approvalFlowFormBean.getFormId()));

		approveInfo.setApprover(getCurrentUser()); // 审批人，当前登录的用户
		approveInfo.setApproveTime(new Date()); // 当前时间

		// 对表单进行审批处理（保存审批信息、完成任务、维护表单状态）
		formService.approve(approveInfo, approvalFlowFormBean.getTaskId());
		approvalFlowFormBean.setSuccess(true);
		return SUCCESS; // 转到待我审批
	}
	
	/**下载申请*/
	public String downloadFile(){
		String path = formService.load(approvalFlowFormBean.getFormId()).getPath();
		HttpServletResponse response = ServletActionContext.getResponse();
		FileIOUtil.getInstance().download(path, response);
		return null;
	}
	
	/** 查看流转记录 */
	public String approvedHistory() {
		Form form = formService.load(approvalFlowFormBean.getFormId());
		ActionContext.getContext().put("approveInfos", form.getApproveInfos());
		return "approvedHistory";
	}
	
	/** 我的申请查询 */
	public String myApplicationList() {
		List<Form> myApplications = formService.getMyApplications(getCurrentUser(),approvalFlowFormBean.getFormTemplateId(),approvalFlowFormBean.getStatus());
		if(approvalFlowFormBean.getFormTemplateId() == 1){
			List<Form> myApplications1 =  formService.getMyApplications(getCurrentUser(),(long)2,approvalFlowFormBean.getStatus());;
			myApplications.addAll(myApplications1);
		}
		if(approvalFlowFormBean.getFormTemplateId() == 3){
			List<Form> myApplications1 =  formService.getMyApplications(getCurrentUser(),(long)4,approvalFlowFormBean.getStatus());;
			myApplications.addAll(myApplications1);
		}
		Collections.sort(myApplications, new Comparator<Form>() {
            public int compare(Form arg0, Form arg1) {
                return arg1.getApplyTime().compareTo(arg0.getApplyTime());
            }
        });
		ActionContext.getContext().put("myApplications", myApplications);
		return "myApplicationList";
	}
	/** 获取form列表*/
	public String formManageList(){
		List<Form> formList = formService.getFormList(approvalFlowFormBean.getFormTemplateId());
		if(approvalFlowFormBean.getFormTemplateId() == 1){
			List<Form> formList1 =  formService.getMyApplications(getCurrentUser(),(long)2,approvalFlowFormBean.getStatus());;
			formList.addAll(formList1);
		}
		if(approvalFlowFormBean.getFormTemplateId() == 3){
			List<Form> formList1 =  formService.getMyApplications(getCurrentUser(),(long)4,approvalFlowFormBean.getStatus());;
			formList.addAll(formList1);
		}
		Collections.sort(formList, new Comparator<Form>() {
            public int compare(Form arg0, Form arg1) {
                return arg1.getApplyTime().compareTo(arg0.getApplyTime());
            }
        });
		ActionContext.getContext().put("formList", formList);
		return "formList";
	}
	/** 获取form列表（未完成）*/
//	public String formList(){
//		List<TaskView> formList = formService.getFormListByTemplate(approvalFlowFormBean.getFormTemplateId());
//		if(approvalFlowFormBean.getFormTemplateId() == 1){
//			List<Form> formList1 =  formService.getMyApplications(getCurrentUser(),(long)2,approvalFlowFormBean.getStatus());;
//			formList.addAll(formList1);
//		}
//		if(approvalFlowFormBean.getFormTemplateId() == 3){
//			List<Form> formList1 =  formService.getMyApplications(getCurrentUser(),(long)4,approvalFlowFormBean.getStatus());;
//			formList.addAll(formList1);
//		}
//		Collections.sort(formList, new Comparator<Form>() {
//			public int compare(Form arg0, Form arg1) {
//				return arg1.getApplyTime().compareTo(arg0.getApplyTime());
//			}
//		});
//		ActionContext.getContext().put("formList", formList);
//		return "formList";
//	}
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.session = arg0;
	}
	
//	public static void main(String[] args) {
//		ApprovalFlowAction aa = new ApprovalFlowAction();
//		
//		System.out.println(getExtension(aa));
//	}
}
