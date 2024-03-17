package com.mitocode.service;


import com.mitocode.dto.ConsultProcDTO;
import com.mitocode.dto.IConsultProcDTO;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;

import java.time.LocalDateTime;
import java.util.List;


public interface IConsultService extends ICRUD<Consult, Long> {

    Consult saveTransactional(Consult consult, List<Exam> exams);

    List<Consult> search(String dni, String fullname);

    List<Consult> searchByDates(LocalDateTime startDate, LocalDateTime endDate);

    List<ConsultProcDTO> callProcedureOrFunctionNative();

    List<IConsultProcDTO> callProcedureOrFunctionProjection();


}
