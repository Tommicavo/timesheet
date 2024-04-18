package it.fides.timesheet.dtos;

import java.util.List;

public class GroupMembersDto {
	
	private Long idGroup;
	private List<Long> idDipendentList;
	
	public GroupMembersDto() {}

	public Long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

	public List<Long> getIdDipendentList() {
		return idDipendentList;
	}

	public void setIdDipendentList(List<Long> idDipendentList) {
		this.idDipendentList = idDipendentList;
	}
}
