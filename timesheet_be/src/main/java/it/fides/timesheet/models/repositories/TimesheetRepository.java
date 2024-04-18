package it.fides.timesheet.models.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.fides.timesheet.models.entities.ResponsableEntity;
import it.fides.timesheet.models.entities.TimesheetEntity;
import it.fides.timesheet.models.entities.UserEntity;

@Repository
public interface TimesheetRepository extends JpaRepository<TimesheetEntity, Long>{
	List<TimesheetEntity> findByResponsableAndIsApprovedTimesheet(ResponsableEntity responsable, boolean isApprovedTimesheet);
	List<TimesheetEntity> findByUser(UserEntity user);
}
