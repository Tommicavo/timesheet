package it.fides.timesheet.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DayDto {
	
	private LocalDate day;
	private Optional<String> keyword;
	private List<RangeDto> ranges;
	
	public DayDto() {}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public Optional<String> getKeyword() {
		return keyword;
	}

	public void setKeyword(Optional<String> keyword) {
		this.keyword = keyword;
	}

	public List<RangeDto> getRanges() {
		return ranges;
	}

	public void setRanges(List<RangeDto> ranges) {
		this.ranges = ranges;
	}
	
	public long getDayHours(String label) {
		long dayHours = 0;
		for (RangeDto range : getRanges()) {
			dayHours += range.getRangeHours(label);
		}
		return dayHours;
	}
}
