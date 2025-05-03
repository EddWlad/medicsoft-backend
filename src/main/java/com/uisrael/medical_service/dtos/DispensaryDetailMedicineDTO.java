package com.uisrael.medical_service.dtos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DispensaryDetailMedicineDTO {
    private DispensaryDTO dispensaryDTO;

    private List<MedicineDispensaryDetailDTO> medicines;
}
