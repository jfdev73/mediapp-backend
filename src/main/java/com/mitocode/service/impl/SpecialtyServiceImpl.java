package com.mitocode.service.impl;


import com.mitocode.model.Specialty;
import com.mitocode.repo.ISpecialtyRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.ISpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl extends CRUDImpl<Specialty, Long> implements ISpecialtyService {
	
	private final ISpecialtyRepo repo;

	@Override
	protected IGenericRepo<Specialty, Long> getRepo() {
		return repo;
	}

	
	

}
