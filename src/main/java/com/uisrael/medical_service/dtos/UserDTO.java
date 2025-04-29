package com.uisrael.medical_service.dtos;

import com.uisrael.medical_service.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private Date dateCreate = new Date();
    private String identification;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Role role;
    private Integer status;

}
