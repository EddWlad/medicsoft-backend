package com.uisrael.medical_service.model;

import com.uisrael.medical_service.entities.Dispensary;
import com.uisrael.medical_service.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
