package com.mitocode.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Role {
	
	@Id
	@EqualsAndHashCode.Include
	private Long idRole;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(length = 50)
	private String description;
	
	

}
