package com.ordering.base.logic;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import com.ordering.base.http.MXHttpRequest;

public class HTTPApi {
	
	public static InputStream download_image(String image_url) {
	      try {
	        HttpGet httpget = new HttpGet(image_url);
	        HttpResponse response = MXHttpRequest.get_httpclient_instance().execute(httpget);
	        int status_code = response.getStatusLine().getStatusCode();
	        if (HttpStatus.SC_OK == status_code) {
	          return response.getEntity().getContent();
	        } else {
	          return null;
	        }
	      } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	      }
	    }
}
