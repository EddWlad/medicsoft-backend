package com.uisrael.medical_service.model;



import com.uisrael.medical_service.entities.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
