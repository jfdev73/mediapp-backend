package com.mitocode.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long idPatient;
	
	@Column(length = 70, nullable = false)
	private String firstName;
	
	@Column(length = 70, nullable = false)
	private String lastName;
	
	@Column(length = 8, nullable = false)
	private String dni;
	
	@Column(length = 150, nullable = false)
	private String address;
	
	@Column(length = 9, nullable = false)
	private String phone;
	
	@Column(length = 55, nullable = false)
	private String email;
	
	

}
