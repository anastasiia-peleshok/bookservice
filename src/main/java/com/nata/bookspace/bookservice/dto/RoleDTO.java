package com.nata.bookspace.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private long id;
    private String name;
    @JsonBackReference
    private Set<ResponseUserDTO> users;


}