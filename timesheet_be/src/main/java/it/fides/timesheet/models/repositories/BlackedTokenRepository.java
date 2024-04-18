package it.fides.timesheet.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.fides.timesheet.models.entities.BlackedTokenEntity;

@Repository
public interface BlackedTokenRepository extends JpaRepository<BlackedTokenEntity, Long>{
}
