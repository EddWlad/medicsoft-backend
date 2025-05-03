package com.uisrael.medical_service.dtos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestockDetailMedicineDTO {

    private RestockDTO restockDTO;

    private List<MedicineRestockDetailDTO> medicines;

}
