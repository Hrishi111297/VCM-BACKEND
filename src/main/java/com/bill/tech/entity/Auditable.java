package com.bill.tech.entity;

import static java.util.Objects.nonNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static com.bill.tech.constants.SchedularConstant.INDIA_ZONE;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@MappedSuperclass
public class Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4003961051501196362L;

	@Column(name = "created_by", nullable = true, updatable = true)
	private Long createdBy;

	@Column(name = "created_date", nullable = true, updatable = true, columnDefinition = "TIMESTAMP")
	private LocalDateTime createdDate;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_date", columnDefinition = "TIMESTAMP")
	private LocalDateTime updatedDate;

	@Column(name = "deleted_by")
	protected Long deletedBy;

	@Column(name = "deleted_date", columnDefinition = "TIMESTAMP")
	private LocalDateTime deletedDate;
	
	

	@PrePersist
	public void beforePersist() {
		
		this.createdBy = getIdofLoggedinUser();

		this.createdDate =now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime();
	}

	@PreUpdate
	public void beforUpdate() {
		
		this.updatedBy = getIdofLoggedinUser();
		this.updatedDate = now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime();
	}

	 @PreRemove
	public void beforDelete() {
	
		this.deletedBy = getIdofLoggedinUser();
		this.deletedDate = now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime();

	}
	 public Long getIdofLoggedinUser() {
		    if (nonNull(getContext()) && nonNull(getContext().getAuthentication())
		            && getContext().getAuthentication().getPrincipal() instanceof UserMaster) {
		        UserMaster details = (UserMaster) getContext().getAuthentication().getPrincipal();
		        return details.getId();
		    }
		    return null;
		}

	

}
