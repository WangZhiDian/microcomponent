package com.meng.auth.filter;

import com.meng.auth.bean.UserInfo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;
	//private TokenManager tokenManager;

/*	public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager) {
		this.authenticationManager = authenticationManager;
		this.tokenManager = tokenManager;
		this.setPostOnly(false);
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login")); // 定义登录请求接口
	}*/

	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {

		UserInfo user = new UserInfo(1, "wang", "123");
		return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword(), new ArrayList<>()));
	}

	/**
	* 认证成功
	*/
/*	@Override
	protected void successfulAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		String token = 生成token;
		httpServletResponse.getWriter().write(token);
	}*/

	/**
	* 认证失败
	*/
/*	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest httpServletRequestuest, HttpServletResponse httpServletResponseponse, AuthenticationException e) throws IOException, ServletException {
		httpServletResponse.getWriter().write("认证失败");
	}*/
}
