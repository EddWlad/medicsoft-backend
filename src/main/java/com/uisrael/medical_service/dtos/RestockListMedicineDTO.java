package com.uisrael.medical_service.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestockListMedicineDTO {

    private RestockDTO restock;

    @NotEmpty(message = "Debe incluir al menos una medicina")
    private List<RestockDetailDTO> listMedicine;

}
