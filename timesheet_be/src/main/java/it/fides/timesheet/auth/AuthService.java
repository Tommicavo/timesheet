package it.fides.timesheet.auth;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.fides.timesheet.dtos.ErrorDto;
import it.fides.timesheet.dtos.LoginDto;
import it.fides.timesheet.dtos.SigninDto;
import it.fides.timesheet.enums.RoleIdEnum;
import it.fides.timesheet.models.dtos.UserDto;
import it.fides.timesheet.models.entities.DipendentEntity;
import it.fides.timesheet.models.entities.ResponsableEntity;
import it.fides.timesheet.models.entities.RoleEntity;
import it.fides.timesheet.models.entities.UserEntity;
import it.fides.timesheet.models.repositories.UserRepository;
import it.fides.timesheet.services.RoleService;
import it.fides.timesheet.services.UserService;

@Service
public class AuthService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleService roleService;
	
	private String nameRegex = "^[a-zA-Z]+$";
	private String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	private String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_=+]).{8,}$";
	
	public UserEntity insertUser(UserDto userDto, boolean isResponsable) throws Exception {
		UserEntity user = isResponsable ? new ResponsableEntity() : new DipendentEntity();
		
		if (userDto != null) {
			user.setFirstNameUser(userDto.getFirstName());
			user.setLastNameUser(userDto.getLastName());
			user.setEmailUser(userDto.getEmail());
			user.setPasswordUser(userDto.getPassword());
			
			RoleEntity role = isResponsable ? roleService.getRole(RoleIdEnum.RESPONSABLE) : roleService.getRole(RoleIdEnum.DIPENDENT);
			user.setRole(role);
		}
		return userRepo.save(user);
	}
	
    public List<ErrorDto> validateSigninData(SigninDto signinData) {
    	List<ErrorDto> errorList = new ArrayList<>();
    	
    	// already registered email
    	if (userService.isEmailRegistered(signinData.getEmail())) {
    		errorList.add(new ErrorDto("email", "The email '" + signinData.getEmail() + "' is already registered"));
    		return errorList;
    	}
    	
    	// firstName
    	if (signinData.getFirstName() == null || signinData.getFirstName().isBlank()) {
    		errorList.add(new ErrorDto("firstName", "firstName field must not be blank"));
    	} else if (!signinData.getFirstName().matches(nameRegex)) {
    		errorList.add(new ErrorDto("firstName", "Insert a valid firstName"));
    	}
    	
    	// lastName
    	if (signinData.getLastName() == null || signinData.getLastName().isBlank()) {
    		errorList.add(new ErrorDto("lastName", "lastName field must not be blank"));
    	} else if (!signinData.getLastName().matches(nameRegex)) {
    		errorList.add(new ErrorDto("lastName", "Insert a valid lastName"));
    	}
    	
    	// email
    	if (signinData.getEmail() == null || signinData.getEmail().isBlank()) {
    		errorList.add(new ErrorDto("email", "Email field must not be blank"));
    	} else if (!signinData.getEmail().matches(emailRegex)) {
    		errorList.add(new ErrorDto("email", "Insert a valid email"));
    	}
    	
    	// password
    	if (signinData.getPassword() == null || signinData.getPassword().isBlank()) {
    		errorList.add(new ErrorDto("password", "Password field must not be blank"));
    	} else if (!signinData.getPassword().matches(passwordRegex)) {
    		errorList.add(new ErrorDto("password", "Insert a valid password"));
    	}

    	return errorList;
    }
    
    public List<ErrorDto> validateLoginData(LoginDto loginData) {
    	List<ErrorDto> errorList = new ArrayList<>();
    	    	
    	// email
    	if (loginData.getEmail() == null || loginData.getEmail().isBlank()) {
    		errorList.add(new ErrorDto("email", "Email field must not be blank"));
    	} else if (!loginData.getEmail().matches(emailRegex)) {
    		errorList.add(new ErrorDto("email", "Insert a valid email"));
    	} else if (!userService.isEmailRegistered(loginData.getEmail())) {
    		errorList.add(new ErrorDto("email", "The email '" + loginData.getEmail() + "' is not registered yet"));
    		return errorList;
    	}
    	
    	// password
    	if (loginData.getPassword() == null || loginData.getPassword().isBlank()) {
    		errorList.add(new ErrorDto("password", "Password field must not be blank"));
    	} else if (!loginData.getPassword().matches(passwordRegex)) {
    		errorList.add(new ErrorDto("password", "Insert a valid password"));
    	}
    	return errorList;
    }
}
