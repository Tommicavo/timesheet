package it.fides.timesheet.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.fides.timesheet.models.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	UserEntity findUserByEmailUser(String email);
	boolean existsByEmailUser(String email);
}
