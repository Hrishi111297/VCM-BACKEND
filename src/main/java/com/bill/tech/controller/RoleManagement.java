package com.bill.tech.controller;

import static com.bill.tech.constants.ApiConstants.GET_ROLES;
import static com.bill.tech.constants.ApiConstants.GET_USERS_BY_ROLE;
import static com.bill.tech.constants.ApiConstants.GET_USER_ROLE;
import static com.bill.tech.constants.ApiConstants.ROLE;
import static com.bill.tech.constants.ApiConstants.UPDATE_USER_ROLE;
import static com.bill.tech.dto_mapper.UserMasterMapper.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill.tech.entity.Role;
import com.bill.tech.entity.UserMaster;
import com.bill.tech.exception.ResourceNotFound;
import com.bill.tech.payload.request.UserMasterDataRequestDto;
import com.bill.tech.repository.RoleRepo;
import com.bill.tech.repository.UserMasterRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ROLE)
@RequiredArgsConstructor
public class RoleManagement {
	private final RoleRepo roleRepo;
	private final UserMasterRepo userMasterRepo;

	@GetMapping(GET_ROLES)
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
	public ResponseEntity<List<Role>> getAllRoles() {
		List<Role> roles = roleRepo.findAll();
		return ResponseEntity.ok(roles);
	}

	@GetMapping(GET_USER_ROLE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
	public ResponseEntity<Set<Role>> getUserRoles(@PathVariable Long id) {
		UserMaster user = userMasterRepo.findById(id).orElseThrow(() -> new ResourceNotFound("User with ID: " + id));
		return ResponseEntity.ok(user.getRoles());
	}

	@GetMapping(GET_USERS_BY_ROLE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
	public ResponseEntity<List<UserMasterDataRequestDto>> getUsersByRole(@PathVariable Long id) {
		List<UserMaster> users = userMasterRepo.findByRolesId(id);
		List<UserMasterDataRequestDto> userDtos = TO_USER_MASTER_DTOS.apply(users);
		return ResponseEntity.ok(userDtos);
	}

	@PutMapping(UPDATE_USER_ROLE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
	public ResponseEntity<String> updateUserRoles(@PathVariable Long id, @RequestBody Set<Long> roleIds) {

		UserMaster user = userMasterRepo.findById(id).orElseThrow(() -> new ResourceNotFound("User with ID: " + id));

		Set<Role> updatedRoles = roleIds.stream().map(
				roleId -> roleRepo.findById(roleId).orElseThrow(() -> new ResourceNotFound("Role with ID: " + roleId)))
				.collect(Collectors.toSet());

		user.setRoles(updatedRoles);
		userMasterRepo.save(user);

		return ResponseEntity.ok("User roles updated successfully");
	}

}
