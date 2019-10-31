package com.feng.sportsone.service;

import com.feng.sportsone.service.model.LoginStatus;

public interface SportsOneLoginService {

    final String CHECK_SESSION = "/sap/hana/xs/formLogin/checkSession.xsjs";
    final String LOGIN = "/sap/hana/xs/formLogin/login.xscfunc";
    final String TOKEN = "/sap/hana/xs/formLogin/token.xsjs";
    final String LOGOUT = "/sap/hana/xs/formLogin/logout.xscfunc";

    public LoginStatus checkSession(String host) throws Exception;

    public String getToken(String host) throws Exception;

    public LoginStatus logout(String host) throws Exception;

    public LoginStatus login(String host) throws Exception;
}