package it.fides.timesheet.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import it.fides.timesheet.dtos.CalendarDto;
import it.fides.timesheet.enums.RangeLabelEnum;
import it.fides.timesheet.models.entities.UserEntity;

@Service
public class DocumentService {
	
	public void saveHtmlTimesheet(byte[] htmlContent, String filename) {
		String content = new String(htmlContent, StandardCharsets.UTF_8);
		try {
			String dirOutput = "timesheets/htmlDocs";
            File directory = new File(dirOutput);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            File outputFile = new File(directory, filename);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.getMessage();
        }
	}
	
	public byte[] generateByteArrayTimesheet(UserEntity user, CalendarDto calendar, String title) throws IOException {
		Handlebars handlebars = new Handlebars();
        Template template = handlebars.compile("templates/timesheet_template");
        Map<String, Long> hoursData = getTimesheetHoursData(calendar);
        
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("calendar", calendar);
        data.put("year", Year.now().toString());
        data.putAll(hoursData);
        
        String html = template.apply(data);
        return html.getBytes();
	}
	
	private Map<String, Long> getTimesheetHoursData(CalendarDto calendar){
		Map<String, Long> hoursMap = new HashMap<>();
		
		Long workHours = calendar.getTotalHours(RangeLabelEnum.WORK);
		hoursMap.put("workHours", workHours);
		Long permitHours = calendar.getTotalHours(RangeLabelEnum.PERMIT);
		hoursMap.put("permitHours", permitHours);
		Long diseaseHours = calendar.getTotalHours(RangeLabelEnum.DISEASE);
		hoursMap.put("diseaseHours", diseaseHours);
		Long vacationHours = calendar.getTotalHours(RangeLabelEnum.VACATION);
		hoursMap.put("vacationHours", vacationHours);
		Long totalHours = workHours + permitHours + diseaseHours + vacationHours;
		hoursMap.put("totalHours", totalHours);
		
		return hoursMap;
	}
}
