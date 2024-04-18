package it.fides.timesheet.dtos;

import java.util.List;
import it.fides.timesheet.enums.RangeLabelEnum;

public class CalendarDto {
	
	private String month;
	private List<DayDto> days;
	
	public CalendarDto() {}
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<DayDto> getDays() {
		return days;
	}

	public void setDays(List<DayDto> days) {
		this.days = days;
	}
	
    public void addDay(DayDto day) {
        this.days.add(day);
    }
    
    public long getTotalHours(RangeLabelEnum label) {
    	long totalHours = 0;
    	for (DayDto day : getDays()) {
    		totalHours += day.getDayHours(label.toString());
    	}
    	return totalHours;
    }
}
