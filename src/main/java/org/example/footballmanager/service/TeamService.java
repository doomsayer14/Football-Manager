package org.example.footballmanager.service;

import org.example.footballmanager.dto.TeamDto;
import org.example.footballmanager.entity.Player;
import org.example.footballmanager.entity.Team;
import org.javamoney.moneta.Money;

import java.util.List;

public interface TeamService {
    Team createTeam(TeamDto teamDto);

    Team getTeamById(Long id);

    List<Team> getAllTeams();

    Team updateTeam(TeamDto teamDto);

    void deleteTeam(Long id);

    void transferFunds(Money transferPrice, Team oldTeam, Team newTeam);

    void changeTeamSquad(Player player, Team oldTeam, Team newTeam);
}
