package com.uisrael.medical_service.dtos;



import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {
    private Long id;
    private String name;
    private String description;
    private Integer status;

}
