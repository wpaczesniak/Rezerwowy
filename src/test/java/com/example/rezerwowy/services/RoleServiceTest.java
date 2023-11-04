package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.RoleAlreadyExistsException;
import com.example.rezerwowy.exceptions.RoleNotFoundException;
import com.example.rezerwowy.models.Role;
import com.example.rezerwowy.repositories.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
                new Role(1L, "Striker", null),
                new Role(2L, "Midfielder", null),
                new Role(3L, "Defender", null)
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
    void should_returnObjectWithCorrectData_when_getRoleByIdAndItExists() {
        //given
        Role role = Role.builder()
                .id(100L)
                .name("Striker")
                .build();
        Long id = role.getId();
        Mockito.when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        //when
        Role foundRole = roleService.getRoleById(id);

        //then
        Assertions.assertAll(
                () -> assertThat(foundRole.getId()).isEqualTo(role.getId()),
                () -> assertThat(foundRole.getName()).isEqualTo(role.getName())
        );
    }

    @Test
    void should_throwException_when_getRoleByIdButItDoesntExist() {
        //given
        Long id = 100L;
        Mockito.when(roleRepository.findById(id)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> roleService.getRoleById(id)).isInstanceOf(RoleNotFoundException.class);
    }

    @Test
    void should_callAppropriateMethodInRepository_when_createRole() {
        // given
        Role role = Role.builder()
                .id(100L)
                .name("Striker")
                .build();
        Mockito.when(roleRepository.save(role)).thenReturn(role);

        // when
        roleService.createRole(role);

        // then
        Mockito.verify(roleRepository).save(role);
    }

    @Test
    void should_returnObjectWithTheSameDate_when_createRole() {
        //given
        Role role = Role.builder()
                .id(100L)
                .name("Striker")
                .build();
        Mockito.when(roleRepository.save(role)).thenReturn(role);

        //when
        Role createdRole = roleService.createRole(role);

        //then
        Assertions.assertAll(
                () -> assertThat(createdRole.getId()).isEqualTo(role.getId()),
                () -> assertThat(createdRole.getName()).isEqualTo(role.getName())
        );

    }

    @Test
    void should_throwException_when_roleWithTheSameNameAlreadyExists() {
        //given
        Role role = Role.builder()
                .id(100L)
                .name("Striker")
                .build();
        Mockito.when(roleRepository.existsByName(role.getName())).thenReturn(true);

        //when then
        assertThatThrownBy(() -> roleService.createRole(role)).isInstanceOf(RoleAlreadyExistsException.class);
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

    @Test
    void should_throwException_when_deleteRoleByIdButItDoesntExist() {
        //given
        Long id = 100L;
        Mockito.when(roleRepository.existsById(id)).thenReturn(false);

        //when then
        assertThatThrownBy(() -> roleService.deleteRole(id)).isInstanceOf(RoleNotFoundException.class);
    }
}
