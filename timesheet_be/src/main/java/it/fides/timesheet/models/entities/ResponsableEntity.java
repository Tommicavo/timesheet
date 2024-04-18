package it.fides.timesheet.models.entities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "responsable")
public class ResponsableEntity extends UserEntity{

	private static final long serialVersionUID = 1L;
	
	// Inverse side: 1 responsable - N groups
	@OneToMany(mappedBy = "responsable")
	@JsonIgnore
	private List<GroupEntity> groups;
	
	public ResponsableEntity() {
		super();
	}

	public List<GroupEntity> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupEntity> groups) {
		this.groups = groups;
	}
}
