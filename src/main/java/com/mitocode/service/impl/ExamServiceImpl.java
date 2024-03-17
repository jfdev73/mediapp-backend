package com.mitocode.service.impl;


import com.mitocode.model.Exam;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IExamRepo;
import com.mitocode.service.IExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl extends CRUDImpl<Exam, Long> implements IExamService {
	
	private final IExamRepo repo;

	@Override
	protected IGenericRepo<Exam, Long> getRepo() {
		return repo;
	}

	
	

}
