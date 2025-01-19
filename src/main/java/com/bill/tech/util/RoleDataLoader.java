package com.bill.tech.util;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bill.tech.entity.Role;
import com.bill.tech.entity.UserMaster;
import com.bill.tech.exception.ResourceNotFound;
import com.bill.tech.repository.RoleRepo;
import com.bill.tech.repository.UserMasterRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleDataLoader implements CommandLineRunner {

	private final RoleRepo role;
	private final UserMasterRepo userMasterRepo;
	
	@Override
	@org.springframework.transaction.annotation.Transactional
	public void run(String... args) throws Exception {
		if (role.count() == 0) {
			role.save(new Role(1, "SUPER_ADMIN"));
			role.save(new Role(2, "ADMIN"));
			role.save(new Role(3, "STUDENT"));
			role.save(new Role(4, "TEACHER"));
		}

		if(userMasterRepo.count()==0) {
			  Role superAdminRole = role.findByName("SUPER_ADMIN")
			            .orElseThrow(() -> new ResourceNotFound("Role SUPER_ADMIN not found"));
		UserMaster user=new UserMaster( "admin", "admin", "admin", "1234567890", "admin@gmail.com", "admin@123", Set.of(superAdminRole));
		userMasterRepo.save(user);
		}
	}

}
