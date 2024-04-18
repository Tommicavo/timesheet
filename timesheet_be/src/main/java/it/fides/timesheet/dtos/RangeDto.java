package it.fides.timesheet.dtos;

import java.time.Duration;
import java.time.LocalTime;

public class RangeDto {
	
	private String label;
	private LocalTime startTime;
	private LocalTime endTime;
	
	public RangeDto() {}
	
	public RangeDto(String label, LocalTime startTime, LocalTime endTime) {
		this.label = label;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	public long getRangeHours(String label) {
		if (getLabel().equalsIgnoreCase(label)) {
			return Duration.between(getStartTime(), getEndTime()).toHours();
		}
		return 0L;
	}
}
