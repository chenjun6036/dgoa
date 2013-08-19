package com.njust.dg.oa.dao.impl;

import org.springframework.stereotype.Repository;

import com.njust.dg.oa.dao.FormTemplateDao;
import com.njust.dg.oa.model.FormTemplate;

@Repository("formTemplateDao")
public class FormTemplateDaoImpl extends BaseDaoImpl<FormTemplate> implements
		FormTemplateDao {

}
