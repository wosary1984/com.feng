package com.feng.sportsone.service.imp;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import com.feng.http.service.common.HttpApiService;
import com.feng.http.service.common.HttpResult;
import com.feng.sportsone.service.SportsOneLoginService;
import com.feng.sportsone.service.model.LoginStatus;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SportsOneLoginServiceImp implements SportsOneLoginService {

    @Resource
    private HttpApiService http;

    private String FullUrl(String host, String relative) {
        return host + relative;
    }

    private LoginStatus composeLoginStatus(HttpResult response) {
        LoginStatus currentStatus = new LoginStatus();
        if (response.getCode() == 200) {
            JSONObject result = new JSONObject(response.getBody());
            currentStatus.setLogin(result.isNull("login") ? false : result.getBoolean("login"));
            currentStatus.setPwdChange(result.isNull("pwdChange") ? false : result.getBoolean("pwdChange"));
            currentStatus.setUsername(result.isNull("username") ? "" : result.getString("username"));
        } else {
            currentStatus.setMessage(response.getBody());
        }
        return currentStatus;

    }

    public LoginStatus checkSession(String host) throws Exception {
        String url = FullUrl(host, CHECK_SESSION);
        HttpResult response = http.doGet(url, null);
        return composeLoginStatus(response);
    }

    public String getToken(String host) throws Exception {
        String url = FullUrl(host, TOKEN);
        Map<String, String> header = new HashMap<>();
        header.put("X-CSRF-Token", "Fetch");
        HttpResult r = http.doGet(url, header);
        return r.getCsrfToken();
    }

    public LoginStatus logout(String host) throws Exception {
        String url = FullUrl(host, LOGOUT);
        LoginStatus cur = checkSession(host);
        if (!cur.isLogin()) {
            return cur;
        } else {
            Map<String, String> header = new HashMap<>();
            header.put("X-CSRF-Token", getToken(host));
            HttpResult r = http.doFormPost(url, null, header);
            return composeLoginStatus(r);
        }
    }

    public LoginStatus login(String host) throws Exception {

        String url = FullUrl(host, LOGIN);
        LoginStatus status = this.checkSession(host);

        if (status.isLogin()) {
            return status;
        } else {
            Map<String, String> header = new HashMap<>();
            header.put("X-CSRF-Token", "unsafe");
            // header.put("Content-Type", "application/x-www-form-urlencoded");
            Map<String, Object> form = new HashMap<>();
            form.put("xs-username", "bruce");
            form.put("xs-password", "Welcome1");
            HttpResult r = http.doFormPost(url, form, header);
            return composeLoginStatus(r);
        }
    }
}