package com.golfclub.rest.tournament;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    List<Tournament> findByStartDate(LocalDate startDate);

    List<Tournament> findByLocationContainingIgnoreCase(String location);

    List<Tournament> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Tournament> findByStartDateAfter(LocalDate startDate);

    List<Tournament> findByStartDateBefore(LocalDate startDate);

    @Query("SELECT t FROM Tournament t LEFT JOIN FETCH t.participatingMembers WHERE t.id = :tournamentId")
    Tournament findTournamentWithMembers(@Param("tournamentId") Long tournamentId);
}
