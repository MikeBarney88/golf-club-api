package com.golfclub.rest.tournament;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping
    public ResponseEntity<Tournament> createTournament(@Valid @RequestBody Tournament tournament) {
        Tournament createdTournament = tournamentService.createTournament(tournament);
        return new ResponseEntity<>(createdTournament, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long id) {
        return tournamentService.getTournamentById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tournament> updateTournament(@PathVariable Long id,
                                                       @Valid @RequestBody Tournament tournamentDetails) {
        try {
            Tournament updatedTournament = tournamentService.updateTournament(id, tournamentDetails);
            return ResponseEntity.ok(updatedTournament);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        try {
            tournamentService.deleteTournament(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{tournamentId}/members/{memberId}")
    public ResponseEntity<Tournament> addMemberToTournament(
            @PathVariable Long tournamentId,
            @PathVariable Long memberId) {
        try {
            Tournament tournament = tournamentService.addMemberToTournament(tournamentId, memberId);
            return ResponseEntity.ok(tournament);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{tournamentId}/members/{memberId}")
    public ResponseEntity<Tournament> removeMemberFromTournament(
            @PathVariable Long tournamentId,
            @PathVariable Long memberId) {
        try {
            Tournament tournament = tournamentService.removeMemberFromTournament(tournamentId, memberId);
            return ResponseEntity.ok(tournament);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{tournamentId}/members")
    public ResponseEntity<Tournament> getTournamentWithMembers(@PathVariable Long tournamentId) {
        try {
            Tournament tournament = tournamentService.getTournamentWithMembers(tournamentId);
            return ResponseEntity.ok(tournament);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/start-date")
    public ResponseEntity<List<Tournament>> searchByStartDate(@RequestParam String startDate) {
        LocalDate date = LocalDate.parse(startDate);
        List<Tournament> tournaments = tournamentService.searchByStartDate(date);
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/search/location")
    public ResponseEntity<List<Tournament>> searchByLocation(@RequestParam String location) {
        List<Tournament> tournaments = tournamentService.searchByLocation(location);
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/search/date-range")
    public ResponseEntity<List<Tournament>> searchByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Tournament> tournaments = tournamentService.searchByStartDateRange(start, end);
        return ResponseEntity.ok(tournaments);
    }
}
