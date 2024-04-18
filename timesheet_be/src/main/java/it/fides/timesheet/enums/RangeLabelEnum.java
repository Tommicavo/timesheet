package it.fides.timesheet.enums;

public enum RangeLabelEnum {
    
	WORK("WORK"),
    PERMIT("PERMIT"),
    DISEASE("DISEASE"),
    VACATION("VACATION");

    private final String label;

    RangeLabelEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
