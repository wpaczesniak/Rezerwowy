package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.PersonDTO;
import com.example.rezerwowy.models.Person;
import com.example.rezerwowy.models.Team;
import com.example.rezerwowy.services.RoleService;
import com.example.rezerwowy.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonMapper {
    @Lazy
    private final TeamService teamService;
    @Lazy
    private final RoleService roleService;

    public PersonDTO mapPersonToPersonDTO(Person person) {
        Long teamId = person.getTeam() != null
                ? person.getTeam().getId()
                : null;

        return PersonDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .surname(person.getSurname())
                .roleId(person.getRole().getId())
                .teamId(teamId)
                .build();
    }

    public Person mapPersonDTOToPerson(PersonDTO personDTO) {

        Team team = teamService.getTeamById(personDTO.teamId());

        return Person.builder()
                .id(personDTO.id())
                .name(personDTO.name())
                .surname(personDTO.surname())
                .role(roleService.getRoleById(personDTO.roleId()))
                .team(team)
                .build();
    }
}
