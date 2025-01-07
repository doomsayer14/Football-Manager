package org.example.footballmanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.footballmanager.dto.TeamDto;
import org.example.footballmanager.entity.Player;
import org.example.footballmanager.entity.Team;
import org.example.footballmanager.exception.TeamNotFoundException;
import org.example.footballmanager.repository.TeamRepository;
import org.example.footballmanager.service.TeamService;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {

    @Value("${money.currency}")
    private String initialCurrency;

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team createTeam(TeamDto teamDto) {
        Team team = Team.builder()
                .teamName(teamDto.getTeamName())
                .country(teamDto.getCountry())
                .balance(Money.of(teamDto.getBalance(), initialCurrency))
                .commission(teamDto.getCommission())
                .playerList(teamDto.getPlayerList())
                .transfers(teamDto.getTransfers())
                .build();

        log.info("Creating team: {}", team);
        return teamRepository.save(team);
    }

    @Override
    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(
                        "Team cannot be found with id = " + id));
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team updateTeam(TeamDto teamDto) {
        Team team = getTeamById(teamDto.getId());
        if (StringUtils.isNotBlank(teamDto.getTeamName())) {
            team.setTeamName(teamDto.getTeamName());
        }
        if (StringUtils.isNotBlank(teamDto.getCountry())) {
            team.setCountry(teamDto.getCountry());
        }
        if (teamDto.getBalance() != null) {
            team.setBalance(Money.of(teamDto.getBalance(), initialCurrency));
        }
        if (teamDto.getCommission() != null) {
            team.setCommission(teamDto.getCommission());
        }
        if (teamDto.getPlayerList() != null) {
            team.setPlayerList(teamDto.getPlayerList());
        }
        if (teamDto.getTransfers() != null) {
            team.setTransfers(teamDto.getTransfers());
        }

        log.info("Updating team: {}", team);
        return team;
    }

    @Override
    public void deleteTeam(Long id) {
        log.info("Deleting team with id + {}", id);
        teamRepository.deleteById(id);
    }

    @Override
    public void transferFunds(Money transferPrice, Team oldTeam, Team newTeam) {
        oldTeam.setBalance(oldTeam.getBalance().subtract(transferPrice));
        newTeam.setBalance(newTeam.getBalance().add(transferPrice));
        teamRepository.save(oldTeam);
        teamRepository.save(newTeam);
    }

    @Override
    public void changeTeamSquad(Player player, Team oldTeam, Team newTeam) {
        oldTeam.getPlayerList().remove(player);
        newTeam.getPlayerList().add(player);
        teamRepository.save(oldTeam);
        teamRepository.save(newTeam);
    }
}