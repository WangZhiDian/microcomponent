package com.meng.auth.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;

import javax.servlet.http.HttpServletRequest;

public class ExtendBasicAuthentication extends BasicAuthenticationConverter {

        public UsernamePasswordAuthenticationToken convert(HttpServletRequest request) {
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken("token", "bbb");
            return result;
        }
}
