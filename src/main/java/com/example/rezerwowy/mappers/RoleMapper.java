package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.RoleDto;
import com.example.rezerwowy.models.Person;
import com.example.rezerwowy.models.Role;
import com.example.rezerwowy.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapper {
    @Lazy
    private final PersonService personService;

    public RoleDto mapRoleToRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .roleOwnersIds(mapRoleOwnersIdsToDto(role.getRoleOwners()))
                .build();
    }

    public Role mapRoleDtoToRole(RoleDto roleDto) {
        return Role.builder()
                .name(roleDto.name())
                .roleOwners(mapRoleOwnersIdsToEntity(roleDto.roleOwnersIds()))
                .build();
    }

    private Set<Long> mapRoleOwnersIdsToDto(Set<Person> roleOwners) {
        return roleOwners != null ? roleOwners.stream()
                .map(Person::getId)
                .collect(Collectors.toSet()) : null;
    }

    private Set<Person> mapRoleOwnersIdsToEntity(Set<Long> roleOwnersIds) {
        return roleOwnersIds != null ? roleOwnersIds.stream()
                .map(personService::getPersonById)
                .collect(Collectors.toSet()) : null;
    }
}
