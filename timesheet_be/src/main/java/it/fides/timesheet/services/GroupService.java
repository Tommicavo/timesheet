package it.fides.timesheet.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.fides.timesheet.dtos.GroupMembersDto;
import it.fides.timesheet.models.entities.DipendentEntity;
import it.fides.timesheet.models.entities.GroupEntity;
import it.fides.timesheet.models.entities.ResponsableEntity;
import it.fides.timesheet.models.repositories.GroupRepository;
import it.fides.timesheet.models.repositories.UserRepository;

@Service
public class GroupService {
	
	@Autowired
	private GroupRepository groupRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	public List<GroupEntity> getAllGroups() throws Exception {
		try {
			return groupRepo.findAll();
		} catch(Exception e) {throw new Exception("Error occurred calling getAllGroups() method");}
	}
	
	public GroupEntity getGroup(Long id) throws Exception {
		try {
			return groupRepo.findById(id).get();
		} catch(Exception e) {throw new Exception("Error occurred calling getGroup() method");}
	}
	
	public GroupEntity insertGroup(Long idResponsable, String label) throws Exception {
		GroupEntity group = new GroupEntity();
		GroupEntity newGroup = null;
		
		if (group != null && label != null && !label.isEmpty()) {
			ResponsableEntity responsable = (ResponsableEntity) userService.getUser(idResponsable);
			group.setLabel(label);
			group.setResponsable(responsable);

			newGroup = groupRepo.save(group);
		}
		return newGroup;
	}
	
	public GroupEntity updateGroup(Long id, String label) throws Exception {
		GroupEntity group = getGroup(id);
		GroupEntity updatedGroup = null;
		
		if (group != null && label != null && !label.isBlank()) {
			group.setLabel(label);
			updatedGroup = groupRepo.save(group);
		}
		return updatedGroup;
	}
	
	public void deleteGroup(Long id) throws Exception {
		groupRepo.deleteById(id);
	}
	
	public GroupEntity addMembersToGroup(GroupMembersDto groupMembersDto) throws Exception {
		GroupEntity group = getGroup(groupMembersDto.getIdGroup());
		
		if (group != null && groupMembersDto.getIdDipendentList() != null && !groupMembersDto.getIdDipendentList().isEmpty()) {
			List<DipendentEntity> dipendentsToAdd = new ArrayList<>();
			for (Long idDipendent : groupMembersDto.getIdDipendentList()) {
				DipendentEntity dipendent = (DipendentEntity) userService.getUser(idDipendent);
				if (dipendent != null) {
					dipendentsToAdd.add(dipendent);
				}
			}
			List<DipendentEntity> dipendentsInGroup = group.getDipendents();
			dipendentsInGroup.addAll(dipendentsToAdd);
			
			Set<DipendentEntity> uniqueDipendents = new HashSet<>(dipendentsInGroup);
			List<DipendentEntity> dipendents = new ArrayList<>(uniqueDipendents);
			
			for (DipendentEntity dipendent : dipendents) {
				dipendent.setGroup(group);
				userRepo.save(dipendent);
			}
			return group;
		}
		return null;
	}
	
	public GroupEntity removeMembersToGroup(GroupMembersDto groupMembersDto) throws Exception {
		GroupEntity group = getGroup(groupMembersDto.getIdGroup());
		
		if (group != null && groupMembersDto.getIdDipendentList() != null && !groupMembersDto.getIdDipendentList().isEmpty()) {
			for (Long idDipendent : groupMembersDto.getIdDipendentList()) {
				DipendentEntity dipendent = (DipendentEntity) userService.getUser(idDipendent);
				if (dipendent != null) {
					dipendent.setGroup(null);
					userRepo.save(dipendent);
				}
			}
			return group;
		}
		return null;
	}
}
