package com.mitocode.service.impl;


import org.springframework.stereotype.Service;



import com.mitocode.model.Medic;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IMedicRepo;
import com.mitocode.service.IMedicService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicServiceImpl  extends CRUDImpl<Medic, Long> implements IMedicService {
	
	private final IMedicRepo repo;

	@Override
	protected IGenericRepo<Medic, Long> getRepo() {
		return repo;
	}

	
	

}
