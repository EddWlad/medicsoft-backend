package com.uisrael.medical_service.dtos;



import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoleDTO {
    @EqualsAndHashCode.Include
    private UUID idRole;

    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3, max = 600)
    private String description;

    private Integer status = 1;

}
