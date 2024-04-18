package it.fides.timesheet.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.fides.timesheet.models.dtos.UserDto;
import it.fides.timesheet.models.entities.UserEntity;
import it.fides.timesheet.models.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public List<UserEntity> getAllUsers() throws Exception {
		return userRepo.findAll();
	}
	
	public UserEntity getUser(Long id) throws Exception {
		return userRepo.findById(id).get();
	}
	
	public UserEntity getUserByEmail(String email) throws Exception {
		return userRepo.findUserByEmailUser(email);
	}
	
	public boolean isEmailRegistered(String email) {
		return userRepo.existsByEmailUser(email);
	}
	
	public UserEntity updateUser(Long id, UserDto userDto) throws Exception {
		UserEntity user = getUser(id);
		UserEntity updatedUser = null;
		
		if (user != null && userDto != null) {
			user.setFirstNameUser(userDto.getFirstName());
			user.setLastNameUser(userDto.getLastName());
			user.setEmailUser(userDto.getEmail());
			user.setPasswordUser(userDto.getPassword());

			updatedUser = userRepo.save(user);

		}
		return updatedUser;
	}
	
	public void deleteUser(Long id) throws Exception {
		userRepo.deleteById(id);
	}
}
