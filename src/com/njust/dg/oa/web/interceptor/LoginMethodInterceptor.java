package com.njust.dg.oa.web.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("serial")
public class LoginMethodInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {

		Map<String, Object> session = invocation.getInvocationContext().getSession();

		if (null == session.get("user")) {
			return Action.LOGIN;
		}

		return invocation.invoke();

	}

}
