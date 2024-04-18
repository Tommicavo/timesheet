package it.fides.timesheet.dtos;

public class EvaluationTimesheetDto {
	
	private boolean isApproved;
	
	public EvaluationTimesheetDto() {}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
}
