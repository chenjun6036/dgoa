package com.njust.dg.oa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "form")
public class Form {
	/** 表单状态常量：审批中 */
	public static final String STATUS_RUNNING = "审批中";

	/** 表单状态常量：已通过 */
	public static final String STATUS_APPROVED = "已通过";

	/** 表单状态常量：未通过 */
	public static final String STATUS_REJECTED = "未通过";

	private Long id;
	private FormTemplate formTemplate;// 所使用的文档模板
	private Set<ApproveInfo> approveInfos = new HashSet<ApproveInfo>();
	private User applicant;// 申请人

	private String title;// 标题
	private Date applyTime;// 申请时间
	private String path;// 文档的存储路径
	private String status; // 当前的状态
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "formTemplate")
	public FormTemplate getFormTemplate() {
		return formTemplate;
	}
	
	
	public void setFormTemplate(FormTemplate formTemplate) {
		this.formTemplate = formTemplate;
	}
	
	@OneToMany(mappedBy="form")
	@OrderBy("approveTime DESC")
	public Set<ApproveInfo> getApproveInfos() {
		return approveInfos;
	}
	public void setApproveInfos(Set<ApproveInfo> approveInfos) {
		this.approveInfos = approveInfos;
	}
	
	@ManyToOne
	@JoinColumn(name = "applicantId")
	public User getApplicant() {
		return applicant;
	}
	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public static String getStatusRunning() {
		return STATUS_RUNNING;
	}
	public static String getStatusApproved() {
		return STATUS_APPROVED;
	}
	public static String getStatusRejected() {
		return STATUS_REJECTED;
	}
}
