package org.example.footballmanager.controller;

import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.example.footballmanager.dto.PlayerDto;
import org.example.footballmanager.entity.Player;
import org.example.footballmanager.mapper.PlayerMapper;
import org.example.footballmanager.service.PlayerService;
import org.example.footballmanager.service.impl.PlayerServiceImpl;
import org.example.footballmanager.validation.ResponseErrorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/player")
public class PlayerController {

    private final PlayerService playerService;

    private final PlayerMapper playerMapper;

    private final ResponseErrorValidation responseErrorValidation;

    public PlayerController(PlayerServiceImpl playerService, PlayerMapper playerMapper, ResponseErrorValidation responseErrorValidation) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
        this.responseErrorValidation = responseErrorValidation;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createPlayer(@Valid @RequestBody PlayerDto playerDto,
                                               BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Player player = playerService.createPlayer(playerDto);
        PlayerDto createdPlayer = playerMapper.playerToPlayerDto(player);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable String playerId) {
        Player player = playerService.getPlayerById(Long.parseLong(playerId));
        PlayerDto playerDto = playerMapper.playerToPlayerDto(player);
        return ResponseEntity.ok(playerDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        List<PlayerDto> playerDtoList = playerService.getAllPlayers()
                .stream()
                .map(playerMapper::playerToPlayerDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerDtoList);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updatePlayer(@Valid @RequestBody PlayerDto playerDto,
                                              BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Player order = playerService.updatePlayer(playerDto);
        PlayerDto createdPlayer = playerMapper.playerToPlayerDto(order);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<HttpStatus> deletePlayer(@PathVariable("playerId") String playerId) {
        playerService.deletePlayer(Long.parseLong(playerId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}