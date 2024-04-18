package it.fides.timesheet.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dipendent")
public class DipendentEntity extends UserEntity {
	
	private static final long serialVersionUID = 1L;
	
	// Owning side: N dipendents - 1 group
	@ManyToOne
	@JoinColumn(name = "id_group")
	@JsonIgnore
	private GroupEntity group;
	
	public DipendentEntity() {
		super();
	}

	public GroupEntity getGroup() {
		return group;
	}

	public void setGroup(GroupEntity group) {
		this.group = group;
	}
}
