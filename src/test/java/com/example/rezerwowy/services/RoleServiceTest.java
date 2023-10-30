package com.example.rezerwowy.services;

import com.example.rezerwowy.models.Role;
import com.example.rezerwowy.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService = new RoleService(roleRepository);
    }

    @Test
    void should_callAppropriateMethodInRepository_when_getAllRoles() {
        // given
        List<Role> roleList = Arrays.asList(
                new Role(1L, "Striker"),
                new Role(2L, "Midfielder"),
                new Role(3L, "Defender")
        );
        Mockito.when(roleRepository.findAll()).thenReturn(roleList);

        // when
        roleService.getRoles();

        // then
        Mockito.verify(roleRepository).findAll();
    }

    @Test
    void should_callAppropriateMethodInRepository_when_getRoleById() {
        // given
        Role role = Role.builder()
                .id(100L)
                .name("Striker")
                .build();
        Long id = role.getId();
        Mockito.when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        // when
        roleService.getRoleById(id);

        // then
        Mockito.verify(roleRepository).findById(id);
    }

    @Test
    void should_callAppropriateMethodInRepository_when_addRole() {
        // given
        Role role = Role.builder()
                .id(100L)
                .name("Striker")
                .build();
        Mockito.when(roleRepository.save(role)).thenReturn(role);

        // when
        roleService.addRole(role);

        // then
        Mockito.verify(roleRepository).save(role);
    }

    @Test
    void should_callAppropriateMethodInRepository_when_deleteRole() {
        // given
        Role role = Role.builder()
                .id(100L)
                .name("Striker")
                .build();
        Long id = role.getId();
        Mockito.when(roleRepository.existsById(id)).thenReturn(true);

        // when
        roleService.deleteRole(id);

        // then
        Mockito.verify(roleRepository).deleteById(id);
    }
}
