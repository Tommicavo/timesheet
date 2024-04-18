package it.fides.timesheet.restControllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.fides.timesheet.dtos.ErrorDto;
import it.fides.timesheet.dtos.GroupMembersDto;
import it.fides.timesheet.models.dtos.GroupDto;
import it.fides.timesheet.models.entities.GroupEntity;
import it.fides.timesheet.models.entities.UserEntity;
import it.fides.timesheet.services.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@GetMapping
	public ResponseEntity<?> getAllGroups() {
		try {
			List<GroupEntity> groups = groupService.getAllGroups();
			if (groups == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("groupError", "getAllGroups() method return null"));
			return ResponseEntity.ok().body(groups);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("groupError", "Error calling getAllGroups() in GroupController"));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getGroup(@PathVariable Long id) {
		try {
			GroupEntity group = groupService.getGroup(id);
			if (group == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("groupError", "getGroup() method return null"));
			return ResponseEntity.ok().body(group);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("groupError", "Error calling getGroup() in GroupController"));
		}
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('RESP')")
	public ResponseEntity<?> insertGroup(@RequestBody GroupDto groupDto, @AuthenticationPrincipal UserEntity currentUser) {
		try {
			GroupEntity group = groupService.insertGroup(currentUser.getIdUser(), groupDto.getLabel());
			if (group == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("groupError", "insertGroup() method return null"));
			return ResponseEntity.ok().body(group);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("groupError", "Error calling insertGroup() in GroupController: " + e.getMessage()));
		}
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('RESP')")
	public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody GroupDto groupDto) {
		try {
			GroupEntity group = groupService.updateGroup(id, groupDto.getLabel());
			if (group == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("groupError", "updateGroup() method return null"));
			return ResponseEntity.ok().body(group);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("groupError", "Error calling updateGroup() in GroupController: " + e.getMessage()));
		}
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('RESP')")
	public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
		try {
			groupService.deleteGroup(id);
			return ResponseEntity.ok().body("Group with ID " + id + " successfully deleted");
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("groupError", "Error calling deleteGroup() in GroupController: " + e.getMessage()));
		}
	}
	
	@PostMapping("/add-members")
	@PreAuthorize("hasAuthority('RESP')")
	public ResponseEntity<?> addMembersToGroup(@RequestBody GroupMembersDto groupMembersDto) {
		try {
			GroupEntity group = groupService.addMembersToGroup(groupMembersDto);
			if (group == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("groupError", "addMembersToGroup() method return null"));
			return ResponseEntity.ok().body(group);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("groupError", "Error calling addMembersToGroup() in GroupController: " + e.getMessage()));
		}
	}
	
	@PostMapping("/remove-members")
	@PreAuthorize("hasAuthority('RESP')")
	public ResponseEntity<?> removeMembersToGroup(@RequestBody GroupMembersDto groupMembersDto) {
		try {
			GroupEntity group = groupService.removeMembersToGroup(groupMembersDto);
			if (group == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("groupError", "removeMembersToGroup() method return null"));
			return ResponseEntity.ok().body(group);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("groupError", "Error calling removeMembersToGroup() in GroupController: " + e.getMessage()));
		}
	}
}
