package com.mitocode.model;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Consult {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idConsult;
	
	@ManyToOne
	@JoinColumn(name = "id_patient", foreignKey = @ForeignKey(name="FK_CONSULT_PATIENT"))
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "id_medic", foreignKey = @ForeignKey(name="FK_CONSULT_MEDIC"))
	private Medic medic;
	
	
	@ManyToOne
	@JoinColumn(name = "id_specialty", foreignKey = @ForeignKey(name="FK_CONSULT_SPECIALTY"))
	private Specialty specialty;
	
	@Column(length = 3, nullable = false)
	private String numConsult;
	
	@Column(nullable = false)
	private LocalDateTime consultDate;
	
	@OneToMany(mappedBy = "consult", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ConsultDetail> details;
	
	
	
	

}
