package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.PersonDTO;
import com.example.rezerwowy.models.Person;
import com.example.rezerwowy.models.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonMapper {
    @Lazy private final TeamMapper teamMapper;
    public PersonDTO mapPersonToPersonDTO(Person person) {
        Long teamId = person.getTeam() != null
                ? person.getTeam().getId()
                : null;

        return PersonDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .surname(person.getSurname())
                .roles(person.getRoles())
                .teamId(teamId)
                .build();
    }

    public Person mapPersonDTOToPerson(PersonDTO personDTO) {

        Team team = teamMapper
                .mapTeamIdToTeam(personDTO.teamId());

        return Person.builder()
                .id(personDTO.id())
                .name(personDTO.name())
                .surname(personDTO.surname())
                .roles(personDTO.roles())
                .team(team)
                .build();
    }
}
