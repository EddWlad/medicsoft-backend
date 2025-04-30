package com.uisrael.medical_service.dtos;



import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {
    private UUID idRole;
    private String name;
    private String description;
    private Integer status;

}
