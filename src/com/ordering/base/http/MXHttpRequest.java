package com.ordering.base.http;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.accounts.AccountManager;

public abstract class MXHttpRequest<TResult> {
    public static final String SITE = "http://baidu.com";
    protected HttpUriRequest http_uri_request;
    private DefaultHttpClient http_client;

    // 公共方法，构造一个http_client实例，会自动设置cookie到其中
    final static public DefaultHttpClient get_httpclient_instance() {
        HttpParams params = new BasicHttpParams();
        HttpClientParams.setRedirecting(params, false);
        DefaultHttpClient client = new DefaultHttpClient(params);
//        client.setCookieStore(AccountManager.get_cookie_store());
        return client;
    }

    final public String get_cookies() {
      return CookieHelper.parse_cookie_store_to_string(http_client.getCookieStore());
    }

    // 主方法 GO
    public TResult go() throws Exception {
        http_client = get_httpclient_instance();
        HttpResponse response = http_client.execute(http_uri_request);

        int status_code = response.getStatusLine().getStatusCode();

        InputStream res_content = response.getEntity().getContent();
        String responst_text = IOUtils.toString(res_content);

        res_content.close();

        switch (status_code) {
            case HttpStatus.SC_OK:
                return on_success(responst_text);
            case HttpStatus.SC_UNAUTHORIZED:
                on_authenticate_exception();
                throw new AuthenticateException(); //抛出未登录异常，会被 TeamknRunnable 接到并处理
            case HttpStatus.SC_UNPROCESSABLE_ENTITY:
            	return on_unprocessable_entity(responst_text);
            case HttpStatus.SC_FORBIDDEN:
            	return on_permission_denied(responst_text);
            default:
                throw new ResponseNot200Exception();    //不是 200 也不是 401 只能认为是出错了。会被 TeamknRunnable 接到并处理
        }
    }
    public TResult on_unprocessable_entity(String responst_text) {
		return null;
	}
    public TResult on_permission_denied(String responst_text) {
		return null;
	}

	// 此方法为 status_code = 200 时 的处理方法，由用户自己定义
    public abstract TResult on_success(String response_text) throws Exception;

    public void on_authenticate_exception() {/*nothing..*/};

    protected String build_params_string(NameValuePair... nv_pairs) {
        String params_string = "?";

        for (NameValuePair pair : nv_pairs) {
            String name = pair.getName();
            String value = pair.getValue();
            params_string += (name + "=" + value + "&");
        }

        return params_string;
    }

    // 一般请求，字符串参数
    protected HttpEntity build_entity(NameValuePair... nv_pairs) throws UnsupportedEncodingException {
        List<NameValuePair> nv_pair_list = new ArrayList<NameValuePair>();
        for (NameValuePair pair : nv_pairs) {
            nv_pair_list.add(pair);
        }
        return new UrlEncodedFormEntity(nv_pair_list, HTTP.UTF_8);
    }
    
    public static class ResponseNot200Exception extends Exception{
      private static final long serialVersionUID = -7542262559198093947L;
    }
    
    public static class AuthenticateException extends Exception {
      private static final long serialVersionUID = 8741487079704426464L;
    }
}