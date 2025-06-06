package com.uisrael.medical_service.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.uisrael.medical_service.entities.Medicine;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestockDTO {
    @EqualsAndHashCode.Include
    private UUID idRestock;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime restockDate = LocalDateTime.now();

    @Size(min = 3, max = 300)
    private String observation;

    private Integer status = 1;
}
