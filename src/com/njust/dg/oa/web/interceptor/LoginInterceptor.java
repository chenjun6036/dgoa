package com.njust.dg.oa.web.interceptor;

import java.util.Map;

import com.njust.dg.oa.action.LoginAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginInterceptor implements Interceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		if (LoginAction.class == invocation.getAction().getClass()) {
			return invocation.invoke();
		}

		Map<String, Object> session = invocation.getInvocationContext().getSession();

		if (null == session.get("user")) {
			return Action.LOGIN;
		}

		return invocation.invoke();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init() {

	}
}
