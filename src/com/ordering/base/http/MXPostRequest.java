package com.ordering.base.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;

public abstract class MXPostRequest<TResult> extends MXHttpRequest<TResult> {
  
    public MXPostRequest(final String request_path, final HttpParam... param) {
        HttpEntity entity = build_entity(param);
        this.http_uri_request = build_http_post(entity, request_path);
    }

    private HttpEntity build_entity(HttpParam... param) {
        MultipartEntity entity = new MultipartEntity();
        for (HttpParam param_file : param) {
            entity.addPart(param_file.get_name(), param_file.get_body());
        }
        return entity;
    }

    private HttpPost build_http_post(HttpEntity entity, String request_path) {
        HttpPost http_post = new HttpPost(MXHttpRequest.SITE + request_path);
        System.out.println("http_post " + MXHttpRequest.SITE + request_path);
        http_post.setHeader("User-Agent", "android");
        http_post.setEntity(entity);
        return http_post;
    }
}