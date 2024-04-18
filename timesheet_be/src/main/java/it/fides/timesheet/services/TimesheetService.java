package it.fides.timesheet.services;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import it.fides.timesheet.dtos.CalendarDto;
import it.fides.timesheet.dtos.DayDto;
import it.fides.timesheet.dtos.ErrorDto;
import it.fides.timesheet.dtos.RangeDto;
import it.fides.timesheet.enums.RangeLabelEnum;
import it.fides.timesheet.enums.RoleIdEnum;
import it.fides.timesheet.models.entities.DipendentEntity;
import it.fides.timesheet.models.entities.ResponsableEntity;
import it.fides.timesheet.models.entities.TimesheetEntity;
import it.fides.timesheet.models.entities.UserEntity;
import it.fides.timesheet.models.repositories.TimesheetRepository;
import it.fides.timesheet.utils.AppLogger;

@Service
public class TimesheetService {
	
	@Autowired
	private TimesheetRepository tsRepo;
	
	@Autowired
	private DocumentService docService;
	
	@Autowired
	private AppLogger appLogger;
	
	@Autowired
	private MailService mailService;
	
    public List<TimesheetEntity> getTimesheetsToBeApproved(ResponsableEntity responsable) {
        return tsRepo.findByResponsableAndIsApprovedTimesheet(responsable, false);
    }
    
    public List<TimesheetEntity> getAllMyTimesheets(UserEntity user) {
    	return tsRepo.findByUser(user);
    }
    
	public TimesheetEntity getTimesheet(Long id, boolean saveHtml) throws Exception {
		TimesheetEntity timesheet = tsRepo.findById(id).get();

		if (saveHtml) {
		String title = timesheet.getTitleTimesheet();
	    String filename = title + "_" + timesheet.getIdTimesheet() + ".html";

	    byte[] byteArray = timesheet.getBlobTimesheet();
	    docService.saveHtmlTimesheet(byteArray, filename);
		}
	    return timesheet;
	}
	
	public TimesheetEntity insertTimesheet(CalendarDto calendarDto, UserEntity user, boolean withEmail) throws Exception {
		boolean isResp = user.getRole().getIdRole().equals(RoleIdEnum.RESPONSABLE.getId());
		ResponsableEntity responsable = null;
		DipendentEntity dipendent = null;
		if (!isResp) {
			dipendent = (DipendentEntity) user;
			responsable = dipendent.getGroup().getResponsable();
		} else {
			responsable = (ResponsableEntity) user;
		}
		
		TimesheetEntity newTimesheet = null;
		if (calendarDto != null && user != null) {
			CalendarDto calendar = processCalendar(calendarDto);
			String title = getTimesheetTitle(user, calendarDto);

			TimesheetEntity timesheet = new TimesheetEntity();
			timesheet.setTitleTimesheet(title);
			timesheet.setApprovedTimesheet(isResp);
			timesheet.setWorkHours(calendar.getTotalHours(RangeLabelEnum.WORK));
			timesheet.setPermitHours(calendar.getTotalHours(RangeLabelEnum.PERMIT));
			timesheet.setDiseaseHours(calendar.getTotalHours(RangeLabelEnum.DISEASE));
			timesheet.setVacationHours(calendar.getTotalHours(RangeLabelEnum.VACATION));
			timesheet.setTotalHours();
			timesheet.setBlobTimesheet(docService.generateByteArrayTimesheet(user, calendar, title));
			timesheet.setUser(user);
			timesheet.setResponsable(responsable);
			
			newTimesheet = tsRepo.save(timesheet);
			if (!isResp && withEmail) mailService.sendEmailToGroupResp(dipendent, responsable);
		}
		return newTimesheet;
	}
	
	public TimesheetEntity evaluateTimesheet(Long id, boolean approved, boolean withEmail) throws IOException {
		TimesheetEntity timesheet = tsRepo.findById(id).get();
		DipendentEntity dipendent = (DipendentEntity) timesheet.getUser();
		TimesheetEntity evaluatedTimesheet = null;

		if (approved) {
			appLogger.log.info("APPROVED");
			if (timesheet != null) {
				timesheet.setApprovedTimesheet(true);
				evaluatedTimesheet = tsRepo.save(timesheet);
				if (withEmail) mailService.sendEmailTimesheetApproved(dipendent);
			}
		} else {
			appLogger.log.info("REJECTED");
			if (withEmail) mailService.sendEmailTimesheetRejected(dipendent);
		}
		return evaluatedTimesheet;
	}
	
	private String getTimesheetTitle(UserEntity user, CalendarDto calendarDto) {
		if (user != null) {
			String spacedName = user.getFirstNameUser().toLowerCase() + " " + user.getLastNameUser();
			String fullName = spacedName.replaceAll(" ", "_");
			String date = "_" + calendarDto.getMonth() + "_" + Year.now();
			return fullName + date;
		}
		return null;
	}
	
	private CalendarDto processCalendar(CalendarDto calendarDto) throws Exception {
        List<DayDto> days = new ArrayList<>();
        
        for (DayDto day : calendarDto.getDays()) {
            Optional<String> optionalKeyword = day.getKeyword();
            if (optionalKeyword != null && optionalKeyword.isPresent()) {
                String keyword = optionalKeyword.get();
                List<RangeDto> defaultRanges = createDefaultRanges(keyword);
                day.setRanges(defaultRanges);
            }
            List<ErrorDto> errors = validateDayRanges(day);
            if (!errors.isEmpty()) {
            	throw new Exception(errors.get(0).getMessage());
            }
            days.add(day);
        }
		
		CalendarDto fullCalendar = new CalendarDto();
		fullCalendar.setMonth(calendarDto.getMonth());
		fullCalendar.setDays(days);
        return fullCalendar;
	}
	
	private List<RangeDto> createDefaultRanges(String keyword) {
		List<RangeDto> defaultRanges = new ArrayList<>();
		RangeDto morningRange = new RangeDto(keyword, LocalTime.of(9, 0), LocalTime.of(13, 0));
		RangeDto eveningRange = new RangeDto(keyword, LocalTime.of(14, 0), LocalTime.of(18, 0));
		defaultRanges.add(morningRange);
		defaultRanges.add(eveningRange);
		return defaultRanges;
	}
	
	private List<ErrorDto> validateDayRanges(DayDto day) {
		List<ErrorDto> errors = new ArrayList<>();
		long totalHours = 0;
		
		if (day.getRanges() == null || day.getRanges().size() == 0) {
			errors.add(new ErrorDto("dayError", day.getDay() + " has to be valorized"));
		}
        for (RangeDto range : day.getRanges()) {
            if (range.getStartTime() != null && range.getEndTime() != null) {
                long rangeHours = Duration.between(range.getStartTime(), range.getEndTime()).toHours();
                totalHours += rangeHours;
            }
        }
        if (totalHours < 8) {
        	errors.add(new ErrorDto("rangeError", "You must insert a minimum of 8 hour for " + day.getDay()));
        }
        return errors;
	}
}
