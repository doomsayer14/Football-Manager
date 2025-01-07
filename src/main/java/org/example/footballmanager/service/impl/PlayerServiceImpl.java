package org.example.footballmanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.footballmanager.dto.PlayerDto;
import org.example.footballmanager.entity.Player;
import org.example.footballmanager.entity.Team;
import org.example.footballmanager.exception.PlayerNotFoundException;
import org.example.footballmanager.repository.PlayerRepository;
import org.example.footballmanager.service.PlayerService;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {

    @Value("${money.currency}")
    private String initialCurrency;

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player createPlayer(PlayerDto playerDto) {
        Player player = Player.builder()
                .firstName(playerDto.getFirstName())
                .secondName(playerDto.getSecondName())
                .position(playerDto.getPosition())
                .dateOfBirth(playerDto.getDateOfBirth())
                .age(playerDto.getAge())
                .careerStart(playerDto.getCareerStart())
                .team(playerDto.getTeam())
                .transfers(playerDto.getTransfers())
                .build();
        calculateTransferPrice(player);

        log.info("Creating player: {}", player);
        return playerRepository.save(player);
    }

    @Override
    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(
                        "Player cannot be found with id = " + id));
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player updatePlayer(PlayerDto playerDto) {
        Player player = getPlayerById(playerDto.getId());

        if (StringUtils.isNotBlank(playerDto.getFirstName())) {
            player.setFirstName(playerDto.getFirstName());
        }
        if (StringUtils.isNotBlank(playerDto.getSecondName())) {
            player.setSecondName(playerDto.getSecondName());
        }
        if (playerDto.getPosition() != null) {
            player.setPosition(playerDto.getPosition());
        }
        if (playerDto.getDateOfBirth() != null) {
            player.setDateOfBirth(playerDto.getDateOfBirth());
        }
        if (playerDto.getAge() != null) {
            player.setAge(playerDto.getAge());
        }
        if (playerDto.getCareerStart() != null) {
            player.setCareerStart(playerDto.getCareerStart());
        }
        log.info("Updating player: {}", player);
        return playerRepository.save(player);
    }

    @Override
    public void deletePlayer(Long id) {
        log.info("Deleting player with id + {}", id);
        playerRepository.deleteById(id);
    }

    @Override
    public Player updateAge(Player player) {
        if (LocalDate.now().getMonth() != player.getDateOfBirth().getMonth() &&
                LocalDate.now().getDayOfMonth() != player.getDateOfBirth().getDayOfMonth()) {
            return player;
        }
        player.setAge(player.getAge() + 1);
        return playerRepository.save(player);
    }

    /**
     * We may move it to {@link Player} and mark as @PrePersist, but I think due to single
     * responsibility principle it's better to keep all business logic in service layer.
     * Formula:
     * (months of experience * 100000) / player's age
     */
    @Override
    public Player calculateTransferPrice(Player player) {
        player.setTransferPrice(Money.of(
                (ChronoUnit.MONTHS.between(LocalDate.now(), player.getCareerStart())) * 100000
                        / player.getAge(),
                initialCurrency));
        return playerRepository.save(player);
    }

    @Override
    public void changePlayerTeam(Player player, Team newTeam) {
        player.setTeam(newTeam);
        playerRepository.save(player);
    }

}