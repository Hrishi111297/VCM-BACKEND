package com.bill.tech.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bill.tech.util.ListToStringConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserMaster extends Auditable implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7079920786055249340L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;
	@Column(name = "middle_name", nullable = false, length = 100)
	private String middleName;
	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;
	@Column(name = "contact_number", nullable = false, unique = true, length = 10)
	private String contactNumber;
	@Column(name = "email_id", nullable = false, length = 100, unique = true)
	private String emailId;
	@Column(name = "password", nullable = false, length = 100)
	private String password;
	@Column(name = "old_password", columnDefinition = "TEXT")
	@Convert(converter = ListToStringConverter.class)
	private List<String> oldPassword;
	@Column(name = "gender",length = 10)
	    private String gender;
	@Column(name = "birth_date",nullable=false,length=10)
	    private LocalDate birthDate;
	@Column(name = "adhar_number", unique = true, length = 12)
    private String adharNumber;

    @Column(name = "blood_group", length = 5)
    private String bloodGroup;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private GuardianDetail guardianDetails;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Document> documents;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Education> educationDetails;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_master_role", joinColumns = @JoinColumn(name = "user_master_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream()
				.map((role) -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return this.emailId;
	}

	@PrePersist
	// @PreUpdate
	public void encryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		this.password = passwordEncoder.encode(this.password);
	}

	public UserMaster(String firstName, String middleName, String lastName, String contactNumber, String emailId,
			String password, Set<Role> roles,LocalDate date) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
		this.emailId = emailId;
		this.password = password;
		this.roles = roles;
		this.birthDate=date;
	}
}
