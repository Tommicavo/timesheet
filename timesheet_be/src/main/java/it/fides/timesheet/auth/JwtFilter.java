package it.fides.timesheet.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.fides.timesheet.dtos.ErrorDto;
import it.fides.timesheet.models.entities.UserEntity;
import it.fides.timesheet.services.UserService;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ObjectMapper objMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = getJwtFromRequest(request);
		String tokenError = null;

		if (token != null) {

			if (jwtService.verifyToken(token, false)) {
				String id = jwtService.extractIdFromToken(token);

				UserEntity currentUser;
				try {
					currentUser = userService.getUser(Long.parseLong(id));
					
					Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
					filterChain.doFilter(request, response);
					return;
				} catch (Exception e) {
					e.getMessage();
				}
			} else {
				tokenError = "EXPIRED TOKEN";
			}
		} else {
			tokenError = "MISSING TOKEN";
		}
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objMapper.writeValueAsString(new ErrorDto("tokenError", tokenError)));
        response.getWriter().flush();
	}

	public String getJwtFromRequest(HttpServletRequest request) {
	    String token = null;
	    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
	    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
	        token = bearerToken.substring(7);
	    }
	    return token;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}
}
