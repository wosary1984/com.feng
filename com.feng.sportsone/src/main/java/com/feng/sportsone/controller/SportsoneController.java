package com.feng.sportsone.controller;

import javax.annotation.Resource;

import com.feng.controller.BaseController;
import com.feng.http.service.common.HttpApiService;
import com.feng.sportsone.service.SportsOneLoginService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/sports")
public class SportsoneController extends BaseController {

    @Resource
    private HttpApiService http;

    @Autowired
    private SportsOneLoginService sports;

    @RequestMapping("checksession")
    public String checkSession() throws Exception {
        String host = "https://s1-dev.bsu.edu.cn";
        return success("checksession", 200, new JSONObject(sports.checkSession(host)));
    }

    @RequestMapping("token")
    public String token() throws Exception {
        String host = "https://s1-dev.bsu.edu.cn";

        JSONObject object = new JSONObject();
        object.accumulate("x-csrf-token", sports.getToken(host));
        return success("token", 200, object);
    }

    @RequestMapping("login")
    public String login() throws Exception {
        String host = "https://s1-dev.bsu.edu.cn";
        return success("login", 200, new JSONObject(sports.login(host)));
    }

    @RequestMapping("logout")
    public String logout() throws Exception {
        String host = "https://s1-dev.bsu.edu.cn";
        return success("login", 200, new JSONObject(sports.logout(host)));
    }
} 