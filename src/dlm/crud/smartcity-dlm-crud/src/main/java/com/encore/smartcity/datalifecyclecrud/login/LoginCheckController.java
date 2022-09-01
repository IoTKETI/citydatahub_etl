package com.encore.smartcity.datalifecyclecrud.login;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@RestController
@RequestMapping(value = "/api/login")
@PropertySource(value = { "classpath:application-real-db.properties" })
public class LoginCheckController {
	
	@Value("${application.id}")
	String applicationId;
	
	@Value("${application.secret}")
	String applicationSecret;
	
	@Value("${sso.url}")
	String url;
	
	@RequestMapping(value = "/authenticate")
	public Map<String, Object> getLogin( @org.springframework.web.bind.annotation.RequestBody Map<String, String> params) {
		Map<String, Object> result = null;
		OkHttpClient client = new OkHttpClient();
		ObjectMapper mapper = new ObjectMapper();

		System.out.println(params);
		MediaType mediaType = MediaType.parse("application/json");
		Map<String, String> bodyData = new HashMap<String, String>();
		bodyData.put("grant_type", "password");
		bodyData.put("username", params.get("username"));
		bodyData.put("password", params.get("password"));
		
		
		String encodeStr =String.format("%s:%s", applicationId, applicationSecret);
		String applicationCode = Base64.getEncoder().encodeToString(encodeStr.getBytes());
		
		try{
		RequestBody body = RequestBody.create(mediaType, mapper.writeValueAsString(bodyData));
		Request hrequest = new Request.Builder()
		  .url(url)
		  .post(body)
		  .addHeader("authorization", "Basic "+applicationCode)
		  .addHeader("content-type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .build();
			Response response = client.newCall(hrequest).execute();
			
			Map<String, Object> map = mapper.readValue(response.body().string(), Map.class);
			result =  map;
		}
		catch(Exception e) {
			e.printStackTrace();
			result = null;
		}
		finally {
			return result;
		}
	}
	
	
}
