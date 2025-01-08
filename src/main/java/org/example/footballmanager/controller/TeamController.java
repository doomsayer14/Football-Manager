package org.example.footballmanager.controller;

import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.example.footballmanager.dto.TeamDto;
import org.example.footballmanager.entity.Team;
import org.example.footballmanager.mapper.TeamMapper;
import org.example.footballmanager.service.TeamService;
import org.example.footballmanager.validation.ResponseErrorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/team")
public class TeamController {

    private final TeamService teamService;

    private final TeamMapper teamMapper;

    private final ResponseErrorValidation responseErrorValidation;

    public TeamController(TeamService teamService, TeamMapper teamMapper, ResponseErrorValidation responseErrorValidation) {
        this.teamService = teamService;
        this.teamMapper = teamMapper;
        this.responseErrorValidation = responseErrorValidation;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createTeam(@Valid @RequestBody TeamDto teamDto,
                                             BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Team team = teamService.createTeam(teamDto);
        TeamDto createdTeam = teamMapper.teamToTeamDto(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable String teamId) {
        Team team = teamService.getTeamById(Long.parseLong(teamId));
        TeamDto teamDto = teamMapper.teamToTeamDto(team);
        return ResponseEntity.ok(teamDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        List<TeamDto> teamDtoList = teamService.getAllTeams()
                .stream()
                .map(teamMapper::teamToTeamDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamDtoList);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateTeam(@Valid @RequestBody TeamDto teamDto,
                                               BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Team team = teamService.updateTeam(teamDto);
        TeamDto createdTeam = teamMapper.teamToTeamDto(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable("teamId") String teamId) {
        teamService.deleteTeam(Long.parseLong(teamId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}