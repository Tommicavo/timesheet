package it.fides.timesheet.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "timesheet")
public class TimesheetEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_timesheet")
	private Long idTimesheet;
	
	@Column(name = "title")
	private String titleTimesheet;
	
	@Column(name = "is_approved")
	private boolean isApprovedTimesheet;
	
	@Column(name = "work_hours")
	private long workHours;
	
	@Column(name = "permit_hours")
	private long permitHours;
	
	@Column(name = "disease_hours")
	private long diseaseHours;
	
	@Column(name = "vacation_hours")
	private long vacationHours;
	
	@Column(name = "total_hours")
	private long totalHours;

    @Lob
    @Column(name = "blob_timesheet", columnDefinition = "BLOB")
    private byte[] blobTimesheet;
    
    // Owning side: N timesheets - 1 user
    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private UserEntity user;
    
    @ManyToOne
    @JoinColumn(name = "id_responsable")
    @JsonIgnore
    private ResponsableEntity responsable;
	
	public TimesheetEntity() {}

	public Long getIdTimesheet() {
		return idTimesheet;
	}

	public void setIdTimesheet(Long idTimesheet) {
		this.idTimesheet = idTimesheet;
	}

	public String getTitleTimesheet() {
		return titleTimesheet;
	}

	public void setTitleTimesheet(String titleTimesheet) {
		this.titleTimesheet = titleTimesheet;
	}

	public boolean isApprovedTimesheet() {
		return isApprovedTimesheet;
	}

	public void setApprovedTimesheet(boolean isApprovedTimesheet) {
		this.isApprovedTimesheet = isApprovedTimesheet;
	}
	
	public long getWorkHours() {
		return workHours;
	}

	public void setWorkHours(long workHours) {
		this.workHours = workHours;
	}

	public long getPermitHours() {
		return permitHours;
	}

	public void setPermitHours(long permitHours) {
		this.permitHours = permitHours;
	}

	public long getDiseaseHours() {
		return diseaseHours;
	}

	public void setDiseaseHours(long diseaseHours) {
		this.diseaseHours = diseaseHours;
	}

	public long getVacationHours() {
		return vacationHours;
	}

	public void setVacationHours(long vacationHours) {
		this.vacationHours = vacationHours;
	}
	
	public long getTotalHours() {
		return getWorkHours() + getPermitHours() + getDiseaseHours() + getVacationHours();
	}

	public void setTotalHours() {
		this.totalHours = getTotalHours();
	}

	public byte[] getBlobTimesheet() {
		return blobTimesheet;
	}

	public void setBlobTimesheet(byte[] blobTimesheet) {
		this.blobTimesheet = blobTimesheet;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public ResponsableEntity getResponsable() {
		return responsable;
	}

	public void setResponsable(ResponsableEntity responsable) {
		this.responsable = responsable;
	}
}
