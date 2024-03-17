package com.mitocode.repo;

import com.mitocode.model.ConsultExam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IConsultExamRepo extends IGenericRepo<ConsultExam, Long>{


    @Modifying
    @Query(value = "INSERT INTO consult_exam(id_consult, id_exam) VALUES (:idConsult, :idExam) ", nativeQuery = true)
    Integer saveExam(@Param("idConsult") Long idConsult, @Param("idExam") Long idExam);

    @Query("SELECT new ConsultExam(ce.exam) FROM ConsultExam ce WHERE ce.consult.idConsult = :idConsult")
    List<ConsultExam> getExamsByConsultId(@Param("idConsult")Integer id);
}
