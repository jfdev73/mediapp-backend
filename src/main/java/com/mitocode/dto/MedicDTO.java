package com.mitocode.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MedicDTO {

    @EqualsAndHashCode.Include
    private Long idMedic;

    @NotEmpty
    @NotNull
    private String primaryName;

    @NotEmpty
    @NotNull
    private String surname;

    @NotEmpty
    @NotNull
    @Size(max = 12)
    private String cmpMedic;

    @NotEmpty
    @NotNull
    private String photo;
}
