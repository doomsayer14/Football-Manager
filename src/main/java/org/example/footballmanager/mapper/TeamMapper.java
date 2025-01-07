package org.example.footballmanager.mapper;

import org.example.footballmanager.dto.TeamDto;
import org.example.footballmanager.entity.Team;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TeamMapper {
    public TeamDto teamToTeamDto(Team team) {
        return TeamDto.builder()
                .id(team.getId())
                .teamName(team.getTeamName())
                .country(team.getCountry())
                .balance(BigDecimal.valueOf(team.getBalance().getNumber().intValue()))
                .commission(team.getCommission())
                .playerList(team.getPlayerList())
                .transfers(team.getTransfers())
                .build();
    }
}
