package com.feng.security.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class SecurityCommon {

    public static JSONObject getJSON(Authentication authentication) {
        JSONObject object = new JSONObject();
        try {
            if (authentication != null) {
                String name = authentication.getName();
                boolean isLogged = authentication.isAuthenticated() && !"anonymousUser".equals(name);

                object.put("userName", name);
                object.put("isLogged", isLogged);
                if (isLogged) {
                    // object.put("userPermissions", getUserPermissions(name));
                    object.put("userPermissions", getUserPermissions(authentication));
                    // object.put("userPermissions", toJsonArray(authentication.getAuthorities()));
                }
            } else {
                object.put("userName", "Guest");
                object.put("isLogged", false);
                object.put("userPermissions", new JSONArray());
                // return object;
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return object;
    }

    private static JSONArray getUserPermissions(Authentication authentication) {
        JSONArray userPermissions = new JSONArray();
        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).forEach(userPermissions::put);
        return userPermissions;
    }

}