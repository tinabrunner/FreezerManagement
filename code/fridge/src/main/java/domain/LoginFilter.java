package domain;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.cookie.SetCookie2;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import db_communication.DB_UserSessionStore;

// @WebService
// @WebFilter("/*") 
/*
public class LoginFilter implements HttpRequestInterceptor { // implements
																// Filter {

	private DB_UserSessionStore db_userSessionStore;

	public LoginFilter() {
		super();
	}

	public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
		if (request == null) {
			throw new IllegalArgumentException("HTTP request may not be null");
		}
		if (context == null) {
			throw new IllegalArgumentException("HTTP context may not be null");
		}

		String method = request.getRequestLine().getMethod();
		if (method.equalsIgnoreCase("CONNECT")) {
			return;
		}

		// Obtain cookie store
		CookieStore cookieStore = (CookieStore) context.getAttribute(ClientContext.COOKIE_STORE);
		if (cookieStore == null) {
			System.out.println("Cookie store not available in HTTP context");
			return;
		}

		// Obtain the registry of cookie specs
		CookieSpecRegistry registry = (CookieSpecRegistry) context.getAttribute(ClientContext.COOKIESPEC_REGISTRY);
		if (registry == null) {
			System.out.println("CookieSpec registry not available in HTTP context");
			return;
		}

		// Obtain the target host (required)
		HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
		if (targetHost == null) {
			throw new IllegalStateException("Target host not specified in HTTP context");
		}

		// Obtain the client connection (required)
		ManagedClientConnection conn = (ManagedClientConnection) context.getAttribute(ExecutionContext.HTTP_CONNECTION);
		if (conn == null) {
			throw new IllegalStateException("Client connection not specified in HTTP context");
		}

		String policy = HttpClientParams.getCookiePolicy(request.getParams());
		System.out.println("CookieSpec selected: " + policy);

		URI requestURI;
		if (request instanceof HttpUriRequest) {
			requestURI = ((HttpUriRequest) request).getURI();
		} else {
			try {
				requestURI = new URI(request.getRequestLine().getUri());
			} catch (URISyntaxException ex) {
				throw new ProtocolException("Invalid request URI: " + request.getRequestLine().getUri(), ex);
			}
		}

		String hostName = targetHost.getHostName();
		int port = targetHost.getPort();
		if (port < 0) {

			// Obtain the scheme registry
			SchemeRegistry sr = (SchemeRegistry) context.getAttribute(ClientContext.SCHEME_REGISTRY);
			if (sr != null) {
				Scheme scheme = sr.get(targetHost.getSchemeName());
				port = scheme.resolvePort(port);
			} else {
				port = conn.getRemotePort();
			}
		}

		CookieOrigin cookieOrigin = new CookieOrigin(hostName, port, requestURI.getPath(), conn.isSecure());

		// Get an instance of the selected cookie policy
		CookieSpec cookieSpec = registry.getCookieSpec(policy, request.getParams());
		// Get all cookies available in the HTTP state
		List<Cookie> cookies = new ArrayList(cookieStore.getCookies());
		// Find cookies matching the given origin
		List<Cookie> matchedCookies = new ArrayList();
		Date now = new Date();
		for (Cookie cookie : cookies) {
			if (!cookie.isExpired(now)) {
				if (cookieSpec.match(cookie, cookieOrigin)) {
					System.out.println("Cookie " + cookie + " match " + cookieOrigin);
					matchedCookies.add(cookie);
				}
			} else {
				System.out.println("Cookie " + cookie + " expired");
			}
		}
		// Generate Cookie request headers
		if (!matchedCookies.isEmpty()) {
			List<Header> headers = cookieSpec.formatCookies(matchedCookies);
			for (Header header : headers) {
				request.addHeader(header);
			}
		}

		int ver = cookieSpec.getVersion();
		if (ver > 0) {
			boolean needVersionHeader = false;
			for (Cookie cookie : matchedCookies) {
				if (ver != cookie.getVersion() || !(cookie instanceof SetCookie2)) {
					needVersionHeader = true;
				}
			}

			if (needVersionHeader) {
				Header header = cookieSpec.getVersionHeader();
				if (header != null) {
					// Advertise cookie version support
					request.addHeader(header);
				}
			}
		}

		// Stick the CookieSpec and CookieOrigin instances to the HTTP context
		// so they could be obtained by the response interceptor
		context.setAttribute(ClientContext.COOKIE_SPEC, cookieSpec);
		context.setAttribute(ClientContext.COOKIE_ORIGIN, cookieOrigin);
	}

	/*
	 * @WebMethod(exclude = true) public void doFilter(ServletRequest req,
	 * ServletResponse res, FilterChain chain) throws ServletException,
	 * IOException { HttpServletRequest request = (HttpServletRequest) req;
	 * HttpServletResponse response = (HttpServletResponse) res; HttpSession
	 * session = request.getSession(false); String loginURI =
	 * request.getContextPath() + "/login";
	 * 
	 * String token = session.getAttribute("username").toString(); boolean
	 * loggedIn = false; if (!db_userSessionStore.tokenExists(token) && (session
	 * != null) && (session.getAttribute("user") != null)) { loggedIn = true; }
	 * 
	 * boolean loginRequest = request.getRequestURI().equals(loginURI);
	 * 
	 * if (loggedIn || loginRequest) { chain.doFilter(request, response); } else
	 * { response.sendRedirect(loginURI); } }
	 * 
	 * @Override public void destroy() { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void init(FilterConfig arg0) throws ServletException {
	 * // TODO Auto-generated method stub
	 * 
	 * }
	 */
// }