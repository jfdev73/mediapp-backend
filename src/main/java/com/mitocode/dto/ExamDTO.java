package com.mitocode.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExamDTO {

    @EqualsAndHashCode.Include
    private Long idExam;

    @NotNull
    @NotEmpty
    private String nameExam;

    @NotNull
    @NotEmpty
    private String descriptionExam;

}
