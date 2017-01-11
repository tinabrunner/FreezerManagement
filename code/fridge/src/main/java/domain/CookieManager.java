package domain;

public class CookieManager {
	/*
	 * public static void main(String args[]) throws Exception {
	 * 
	 * // init client HttpClient http = null; CookieStore httpCookieStore = new
	 * BasicCookieStore(); HttpClientBuilder builder =
	 * HttpClientBuilder.create()
	 * .setDefaultCookieStore((org.apache.http.client.CookieStore)
	 * httpCookieStore); http = builder.build();
	 * 
	 * // do stuff HttpGet httpRequest = new
	 * HttpGet("http://stackoverflow.com/"); HttpResponse httpResponse = null;
	 * try { httpResponse = http.execute(httpRequest); } catch (Throwable error)
	 * { throw new RuntimeException(error); }
	 * 
	 * // check cookies httpCookieStore.getCookies();
	 * 
	 * // .---------
	 * 
	 * HttpClient client = new HttpClient();
	 * client.getParams().setParameter("http.useragent", "My Browser");
	 * 
	 * GetMethod method = new GetMethod("http://localhost:8080/"); try {
	 * client.executeMethod(method); Cookie[] cookies =
	 * client.getState().getCookies(); for (int i = 0; i < cookies.length; i++)
	 * { Cookie cookie = cookies[i]; System.err.println("Cookie: " +
	 * cookie.getName() + ", Value: " + cookie.getValue() + ", IsPersistent?: "
	 * + cookie.isPersistent() + ", Expiry Date: " + cookie.getExpiryDate() +
	 * ", Comment: " + cookie.getComment());
	 * 
	 * cookie.setValue("My own value"); } client.executeMethod(method); } catch
	 * (Exception e) { System.err.println(e); } finally {
	 * method.releaseConnection(); } }
	 */
}