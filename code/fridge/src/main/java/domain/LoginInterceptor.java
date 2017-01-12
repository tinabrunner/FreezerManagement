package domain;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import db_communication.DB_UserSessionStore;

/**
 * @author Christina Brunner
 */

@Interceptor
@Logged
public class LoginInterceptor {

	private DB_UserSessionStore db_userSessionStore;

	@AroundInvoke
	public Object invokeInterceptorMethod(InvocationContext ctx) throws Exception {

		System.out.println("Test interceptor");
		return ctx.proceed();
	}

}
