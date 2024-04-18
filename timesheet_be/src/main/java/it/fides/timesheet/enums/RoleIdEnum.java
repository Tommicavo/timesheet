package it.fides.timesheet.enums;

public enum RoleIdEnum {
    
	DIPENDENT(1L),
    RESPONSABLE(2L);

    private final Long id;

    RoleIdEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
