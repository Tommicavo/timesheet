package it.fides.timesheet.auth;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.fides.timesheet.dtos.ErrorDto;
import it.fides.timesheet.dtos.LoginDto;
import it.fides.timesheet.dtos.SigninDto;
import it.fides.timesheet.models.dtos.UserDto;
import it.fides.timesheet.models.entities.BlackedTokenEntity;
import it.fides.timesheet.models.entities.UserEntity;
import it.fides.timesheet.services.BlackedTokenService;
import it.fides.timesheet.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder bCrypt;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private BlackedTokenService blackListService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninDto signinDto) {
    	
    	List<ErrorDto> errors = authService.validateSigninData(signinDto);
    	if (!errors.isEmpty()) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    	}
    	
    	try {
        	UserDto user = new UserDto();
            user.setFirstName(signinDto.getFirstName());
            user.setLastName(signinDto.getLastName());
            user.setEmail(signinDto.getEmail());
            user.setPassword(bCrypt.encode(signinDto.getPassword()));
            
            UserEntity newUser = authService.insertUser(user, signinDto.isResponsable());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    	} catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("signinError", "Error calling signin() method on AuthController: \n" + e.getMessage()));
    	}
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
    	List<ErrorDto> errors = authService.validateLoginData(loginDto);
    	if (!errors.isEmpty()) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    	}
    	
    	try {
            UserEntity user = userService.getUserByEmail(loginDto.getEmail());
            String token = null;
    		
            if (bCrypt.matches(loginDto.getPassword(), user.getPassword())) {
                token = jwtService.createToken(user);
                return ResponseEntity.ok().body(token);
            } else {
            	errors.add(new ErrorDto("password", "You inserted wrong password for user with email '" + user.getEmailUser() + "'"));
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
            }
    	} catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("loginError", "Error calling login() method in AuthController: \n" + e.getMessage()));
    	}
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
    	try {
        	String token = jwtFilter.getJwtFromRequest(request);
        	if (token == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("logoutError", "Jwt Token retrieved from request is null"));
        	boolean isStillValidToken = jwtService.verifyToken(token, false);
        	if (isStillValidToken) blackListService.insertBlackedToken(new BlackedTokenEntity(token));
    		return ResponseEntity.ok().body("Logged out successfully");
    	} catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("logoutError", "Error calling logout() method on AuthController: \n" + e.getMessage()));
    	}
    }
}
