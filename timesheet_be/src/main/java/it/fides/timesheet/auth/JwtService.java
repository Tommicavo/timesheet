package it.fides.timesheet.auth;

import it.fides.timesheet.models.entities.BlackedTokenEntity;
import it.fides.timesheet.models.entities.UserEntity;
import it.fides.timesheet.services.BlackedTokenService;
import it.fides.timesheet.utils.AppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class JwtService {
	
	@Autowired
	private BlackedTokenService blackListService;
	
	@Autowired
	private AppLogger appLogger;
    
	@Value("${jwt.secretKey}")
    private String SECRET_KEY;
		
	private static final int EXPIRATION_MILLIS = 1000 * 60 * 60; // 1 hour 

	public String createToken(UserEntity user) {
		String token = Jwts.builder().setSubject(String.valueOf(user.getIdUser()))
				.claim("role", user.getRole().getLabelRole())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).compact();
		return token;
	}

	public boolean verifyToken(String token, boolean logInfo) throws IOException {
		appLogger.log.info("\n\n\n----------------------TOKEN DATA VERIFICATION----------------------");
	    if (isBlackedToken(token)) {
	        appLogger.log.info("TOKEN IS BLACKLISTED");
	        return false;
	    }
		try {
        	Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parse(token);

        	if (logInfo) {
	        	String idUser = extractIdFromToken(token);
	            Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseClaimsJws(token).getBody();
	            Date expirationDate = claims.getExpiration();
	            String role = (String) claims.get("role");
	            
	            appLogger.log.info("TOKEN: " + token);
	        	appLogger.log.info("ID USER: " + idUser);
	            appLogger.log.info("CURRENT TIME: " + LocalDateTime.now());
	            appLogger.log.info("EXPIRATION DATE: " + expirationDate);
	            appLogger.log.info("ROLE: " + role);
        	}
        	return true;
        } catch (Exception e) {
        	appLogger.log.info("TOKEN VERIFICATION FAILED: " + e);
        	return false;
        }
	}
	
	private boolean isBlackedToken(String token) {
		List<BlackedTokenEntity> blackedTokens = blackListService.getAllBlackedTokens();
		for (BlackedTokenEntity blackedToken : blackedTokens) {
			if (token.equals(blackedToken.getValueBlackedToken())) {
				return true;
			}
		}
		return false;
	}
	    
	public String extractIdFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseClaimsJws(token)
				.getBody().getSubject();
	}
}
