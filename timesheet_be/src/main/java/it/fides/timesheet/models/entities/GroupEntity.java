package it.fides.timesheet.models.entities;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "group_entity")
public class GroupEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_group")
	private Long idGroup;
	
	@Column(name = "label", nullable = false, unique = true)
	private String label;
	
	// Inverse side: 1 group - N dipendents
	@OneToMany(mappedBy = "group")
	List<DipendentEntity> dipendents;

	// Owning side: N groups - 1 responsable
	@ManyToOne
	@JoinColumn(name = "id_responsable")
	private ResponsableEntity responsable;
	
	public GroupEntity() {}

	public Long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<DipendentEntity> getDipendents() {
		return dipendents;
	}

	public void setDipendents(List<DipendentEntity> dipendents) {
		this.dipendents = dipendents;
	}

	public ResponsableEntity getResponsable() {
		return responsable;
	}

	public void setResponsable(ResponsableEntity responsable) {
		this.responsable = responsable;
	}
}
