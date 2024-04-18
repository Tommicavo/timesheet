package it.fides.timesheet.dtos;

public class ErrorDto {
    
    private String label;
    private String message;

    public ErrorDto() {}
    
    public ErrorDto(String label, String message) {
    	this.label = label;
    	this.message = message;
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
