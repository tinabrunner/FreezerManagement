package domain;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;

public class CookieManager {

	private CookieStore httpCookieStore;

	public void main(String args[]) throws Exception {

		// init client
		HttpClient client = null;
		httpCookieStore = new BasicCookieStore();
		HttpClientBuilder builder = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore);
		client = builder.build();

		// do stuff
		HttpGet httpRequest = new HttpGet("http://localhost:8080/fridge/app/");
		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpRequest);
		} catch (Throwable error) {
			throw new RuntimeException(error);
		}

	}

	public boolean isUserInCookies(String username) {
		boolean ret = false;
		// check cookies
		List<Cookie> cookies = httpCookieStore.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(username))
				ret = true;
		}
		return ret;
	}

}