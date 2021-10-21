package com.example.jwtt.filter;

import com.example.jwtt.service.security.UserDetailsImpl;
import com.example.jwtt.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtil jwtUtil;

	private String retrieveJwt(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
			ServletException, IOException {
		String token = retrieveJwt(request);
		if (token != null) {
			String userName = jwtUtil.parseUserNameFromToken(token);
			List<SimpleGrantedAuthority> userAuthorities = jwtUtil.parseUserAuthoritiesFromToken(token);
			UserDetails userDetails = new UserDetailsImpl(userName, null, userAuthorities);

			UsernamePasswordAuthenticationToken authAfterSuccessLogin = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			authAfterSuccessLogin.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authAfterSuccessLogin);
		}
		filterChain.doFilter(request, response);
	}
}
