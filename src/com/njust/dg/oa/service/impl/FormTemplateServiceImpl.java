package com.njust.dg.oa.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.njust.dg.oa.dao.FormTemplateDao;
import com.njust.dg.oa.model.FormTemplate;
import com.njust.dg.oa.service.FormTemplateService;

@Repository("formTemplateService")
public class FormTemplateServiceImpl implements FormTemplateService {
	@Resource
	private FormTemplateDao formTemplateDao;
	@Override
	public FormTemplate load(Long id) {
		// TODO Auto-generated method stub
		return formTemplateDao.load(id);
	}

}
