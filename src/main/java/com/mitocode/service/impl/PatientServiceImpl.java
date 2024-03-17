package com.mitocode.service.impl;


import org.springframework.stereotype.Service;



import com.mitocode.model.Patient;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IPatientRepo;
import com.mitocode.service.IPatientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl  extends CRUDImpl<Patient, Long> implements IPatientService {
	
	private final IPatientRepo repo;


	@Override
	protected IGenericRepo<Patient, Long> getRepo() {
		
		return repo;
	}
	

}
