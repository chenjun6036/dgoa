package com.njust.dg.oa.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.njust.dg.oa.dao.ApproveInfoDao;
import com.njust.dg.oa.model.ApproveInfo;
import com.njust.dg.oa.service.ApproveInfoService;
@Repository("approveInfoService")
public class ApproveInfoServiceImpl implements ApproveInfoService {
	@Resource
	private ApproveInfoDao approveInfoDao;
	@Override
	public void save(ApproveInfo approveInfo) {
		// TODO Auto-generated method stub
		approveInfoDao.add(approveInfo);
	}

}
