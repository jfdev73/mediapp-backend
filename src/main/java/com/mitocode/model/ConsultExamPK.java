package com.mitocode.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class ConsultExamPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="id_consult", nullable = false)
	private Consult consult;
	
	@ManyToOne
	@JoinColumn(name = "id_exam", nullable = false)
	private Exam exam;

}
