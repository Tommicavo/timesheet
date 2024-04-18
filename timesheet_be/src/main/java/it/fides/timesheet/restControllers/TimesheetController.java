package it.fides.timesheet.restControllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.fides.timesheet.dtos.CalendarDto;
import it.fides.timesheet.dtos.ErrorDto;
import it.fides.timesheet.dtos.EvaluationTimesheetDto;
import it.fides.timesheet.models.entities.ResponsableEntity;
import it.fides.timesheet.models.entities.TimesheetEntity;
import it.fides.timesheet.models.entities.UserEntity;
import it.fides.timesheet.services.TimesheetService;

@RestController
@RequestMapping("/timesheets")
public class TimesheetController {
	
	@Autowired
	private TimesheetService tsService;
	
	@GetMapping("/to-be-approved")
	@PreAuthorize("hasAuthority('RESP')")
	public ResponseEntity<?> getTimesheetsToBeApproved(@AuthenticationPrincipal ResponsableEntity currentUser) {
		try {
			List<TimesheetEntity> timesheets = tsService.getTimesheetsToBeApproved(currentUser);
			if (timesheets == null || timesheets.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("timesheetError", "getTimesheetsToBeApproved() method returned null"));
			return ResponseEntity.ok(timesheets);
		} catch(Exception e) {return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("timesheetError", "Error calling getTimesheetsToBeApproved: " + e.getMessage()));}
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> getAllMyTimesheets(@AuthenticationPrincipal UserEntity currentUser) {
		try {
			List<TimesheetEntity> timesheets = tsService.getAllMyTimesheets(currentUser);
			if (timesheets == null || timesheets.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("timesheetError", "getAllMyTimesheets() method returned null"));
			return ResponseEntity.ok(timesheets);
		} catch(Exception e) {return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("timesheetError", "Error calling getAllMyTimesheets: " + e.getMessage()));}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTimesheet(@PathVariable Long id) {
		try {
			TimesheetEntity timesheet = tsService.getTimesheet(id, true);
			if (timesheet == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("timesheetError", "getTimesheet() method returned null"));
			return ResponseEntity.ok(timesheet);
		} catch(Exception e) {return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("timesheetError", "Error calling getTimesheet() method: " + e.getMessage()));}
	}
		
	@PatchMapping("/evaluate/{id}")
	@PreAuthorize("hasAuthority('RESP')")
	public ResponseEntity<?> evaluateTimesheet(@PathVariable Long id, @RequestBody EvaluationTimesheetDto evaluationTimesheet) {
		try {
			TimesheetEntity timesheet = tsService.evaluateTimesheet(id, evaluationTimesheet.isApproved(), false);
			if (timesheet == null) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("timesheetError", "approveTimesheet() method returned null"));}
			return ResponseEntity.ok(timesheet);
		} catch(Exception e) {return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("timesheetError", "Error calling approveTimesheet() method: " + e.getMessage()));}
	}
	
	@PostMapping
	public ResponseEntity<?> insertTimesheet(@RequestBody CalendarDto calendarDto, @AuthenticationPrincipal UserEntity currentUser) {
		try {
			TimesheetEntity timesheet = tsService.insertTimesheet(calendarDto, currentUser, false);
			if (timesheet == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("timesheetError", "insertTimesheet() method returned null"));
			return ResponseEntity.ok(timesheet);
		} catch (Exception e) {return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("timesheetError", "Error calling insertTimesheet() method: " + e.getMessage()));}
	}
}
