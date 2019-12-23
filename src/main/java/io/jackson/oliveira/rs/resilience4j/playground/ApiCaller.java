package io.jackson.oliveira.rs.resilience4j.playground;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiCaller {

	public String callExternalAPI(String id) {
		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/todos/" + id );
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) 
				content.append(inputLine);
			
			in.close();
			
			return content.toString();
			
		} catch (Exception e) {
			throw new RuntimeException("Error when calling external  API", e);

		}

	}
	
	public String callForcingExceptin(String id) {
		throw new RuntimeException("Exception being thrown");
	}

}
