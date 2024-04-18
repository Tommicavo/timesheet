package it.fides.timesheet.restControllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.fides.timesheet.dtos.ErrorDto;
import it.fides.timesheet.models.dtos.UserDto;
import it.fides.timesheet.models.entities.UserEntity;
import it.fides.timesheet.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		try {
			List<UserEntity> users = userService.getAllUsers();
			if (users == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("usersError", "getAllUsers() method on UserController returned null"));
			return ResponseEntity.ok().body(users);	
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("userError", "Error calling getAllUsers() method on UserController"));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		try {
			UserEntity user = userService.getUser(id);
			if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("userError", "getUser() method on UserController returned null"));
			return ResponseEntity.ok().body(user);	
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("userError", "Error calling getUser() method on UserController"));
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
		try {
			UserEntity user = userService.updateUser(id, userDto);
			if (user == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("userError", "getUser() method returned null"));
			return ResponseEntity.ok().body(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("userError", e.getMessage()));
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
	    try {
	        userService.deleteUser(id);
	        return ResponseEntity.ok().body("User with ID " + id + " successfully deleted");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("userError", "deleteUser() method threw an exception"));
	    }
	}
}
