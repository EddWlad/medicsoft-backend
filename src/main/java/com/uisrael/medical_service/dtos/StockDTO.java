package com.uisrael.medical_service.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uisrael.medical_service.entities.Medicine;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StockDTO {
    @EqualsAndHashCode.Include
    private UUID idStock;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdate = LocalDateTime.now();

    @NotNull(message = "El estado es obligatorio (1: activo, 2: inactivo, 0: eliminado)")
    private Integer status = 1;

    @NotNull(message = "El ID del medicamento es obligatorio")
    @JsonIgnoreProperties({"stock"})
    private Medicine medicine;

    private Integer quantity;
}
