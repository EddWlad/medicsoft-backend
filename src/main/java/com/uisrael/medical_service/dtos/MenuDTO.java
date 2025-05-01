package com.uisrael.medical_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MenuDTO {
    @EqualsAndHashCode.Include
    private UUID idMenu;

    private String icon;
    private String name;
    private String url;
    private Integer status = 1;
}

