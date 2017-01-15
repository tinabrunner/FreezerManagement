package domain;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Christina Brunner
 */

@Interceptor
@Logged
public class LoginInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	// @Inject
	// HttpServletRequest request;

	@AroundInvoke
	public Object invokeInterceptorMethod(InvocationContext ctx) throws Exception {

		HttpServletRequest req = ((RestService) ctx.getTarget()).getHttpRequest();

		System.out.println(
				"!!! interceptor !!! " + ", remAdr: " + req.getRemoteAddr() + ", Auth: " + req.getHeader("token")
						+ ", Param: " + req.getParameter("token") + ", Attr: " + req.getAttribute("token"));
		return ctx.proceed();

	}

}
