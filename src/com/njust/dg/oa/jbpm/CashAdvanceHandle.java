package com.njust.dg.oa.jbpm;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.njust.dg.oa.util.ConfigUtil;

@SuppressWarnings("serial")
public class CashAdvanceHandle implements DecisionHandler {

	@Override
	public String decide(OpenExecution execution) {
		// TODO Auto-generated method stub
//        Integer pricePoint = Integer.parseInt((String)ConfigUtil.getInstance().readConfig("MaterialPurchaseRequsitionPricePoint")) ;
//        String superAdmin= (String) execution.getVariable("superAdmin");
        if(execution.getVariable("superAdmin") == null)
            return "否";
        else
            return "是";
	}

}
