package com.mitocode.repo;


import com.mitocode.dto.ConsultProcDTO;
import com.mitocode.dto.IConsultProcDTO;
import com.mitocode.model.Consult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;


public interface IConsultRepo extends IGenericRepo<Consult, Long> {

    @Query("FROM Consult c WHERE c.patient.dni = :dni OR LOWER(c.patient.firstName) LIKE %:fullname% OR LOWER(c.patient.lastName) LIKE %:fullname% ") //JPQL
    List<Consult> search(@Param("dni") String dni, String fullname);

    @Query("FROM Consult c WHERE c.consultDate BETWEEN :startDate AND :endDate")
    List<Consult> searchByDates(@Param("startDate")LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "select * from fn_list()", nativeQuery = true)
    List<Object[]> callProcedureOrFunctionNative();

    @Query(value = "select * from fn_list()", nativeQuery = true)
    List<IConsultProcDTO> callProcedureOrFunctionProjection();
}
