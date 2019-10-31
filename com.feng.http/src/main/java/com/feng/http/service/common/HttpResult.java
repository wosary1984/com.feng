
package com.feng.http.service.common;

public class HttpResult {

    private int code;
    private String body;
    private String message;
    private String csrfToken;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpResult(int code, String body) {
        this.code = code;
        this.body = body;
    }

    public HttpResult() {
	}

	public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

}
