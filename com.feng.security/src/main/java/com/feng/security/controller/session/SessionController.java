package com.feng.security.controller.session;

import javax.servlet.http.HttpServletRequest;

import com.feng.http.controller.BaseController;
import com.feng.security.common.SecurityCommon;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SessionController extends BaseController {

	final String PATH_MY_SESSION = "/my/session";
	final String PATH_MY_PRIVILEGE = "/my/privileges";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	HttpServletRequest request;


	/****
	 * Get Login User Session
	 * 
	 * @return
	 * @throws JSONException
	 */

	@GetMapping(value = PATH_MY_SESSION, produces = MEDIA_TYPE)
	public String mySession() throws JSONException {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		logger.debug("requested sessionid={} query auth information", request.getRequestedSessionId());
		return success("Login", 200, SecurityCommon.getJSON(authentication));
	}
}