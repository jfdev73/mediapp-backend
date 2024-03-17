package com.mitocode.service.impl;


import com.mitocode.dto.ConsultProcDTO;
import com.mitocode.dto.IConsultProcDTO;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.repo.IConsultExamRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IConsultRepo;
import com.mitocode.service.IConsultService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultServiceImpl extends CRUDImpl<Consult, Long> implements IConsultService {
	
	private final IConsultRepo consultRepo;
	private final IConsultExamRepo ceRepo;

	@Override
	protected IGenericRepo<Consult, Long> getRepo() {
		return consultRepo;
	}

	@Transactional
	@Override
	public Consult saveTransactional(Consult consult, List<Exam> exams) {
		consultRepo.save(consult);
		exams.forEach( ex -> ceRepo.saveExam(consult.getIdConsult(), ex.getIdExam()));

		return consult;
	}

	@Override
	public List<Consult> search(String dni, String fullname) {
		return consultRepo.search(dni, fullname);
	}

	@Override
	public List<Consult> searchByDates(LocalDateTime startDate, LocalDateTime endDate) {
		final int OFFSET_DAYS = 1;
		return consultRepo.searchByDates(startDate, endDate.plusDays(OFFSET_DAYS));
	}

	@Override
	public List<ConsultProcDTO> callProcedureOrFunctionNative() {

		List<ConsultProcDTO> lst = new ArrayList<>();

		/*
		[3, "02/09/2023]
		[2, "23/09/2023]
		 */

		consultRepo.callProcedureOrFunctionNative().forEach(e->{
			ConsultProcDTO dto = new ConsultProcDTO();
			dto.setQuantity((Integer) e[0]);
			dto.setConsultdate( (String) e[1]);
			lst.add(dto);

		});
		return lst;
	}

	@Override
	public List<IConsultProcDTO> callProcedureOrFunctionProjection() {
		return consultRepo.callProcedureOrFunctionProjection();
	}
}
