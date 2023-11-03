package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.RoleDto;
import com.example.rezerwowy.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    public RoleDto mapRoleToRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public Role mapRoleDtoToRole(RoleDto roleDto) {
        return Role.builder()
                .name(roleDto.name())
                .build();
    }
}
