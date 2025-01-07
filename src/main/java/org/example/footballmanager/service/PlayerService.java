package org.example.footballmanager.service;

import org.example.footballmanager.dto.PlayerDto;
import org.example.footballmanager.entity.Player;
import org.example.footballmanager.entity.Team;

import java.util.List;

public interface PlayerService {
    Player createPlayer(PlayerDto playerDto);

    Player getPlayerById(Long id);

    List<Player> getAllPlayers();

    Player updatePlayer(PlayerDto playerDto);

    void deletePlayer(Long id);

    Player updateAge(Player player);

    Player calculateTransferPrice(Player player);

    void changePlayerTeam(Player player, Team newTeam);
}