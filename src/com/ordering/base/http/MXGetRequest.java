package com.ordering.base.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;

public abstract class MXGetRequest<TResult> extends MXHttpRequest<TResult> {
    public MXGetRequest(final String request_path, final NameValuePair... nv_pairs) {
        String request_url = MXHttpRequest.SITE + request_path + build_params_string(nv_pairs);
        this.http_uri_request = new HttpGet(request_url);
        this.http_uri_request.setHeader("User-Agent", "android");
    }
}
