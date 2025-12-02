package com.golfclub.rest.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByMemberNameContainingIgnoreCase(String memberName);

    List<Member> findByMemberPhoneNumber(String phoneNumber);

    List<Member> findByStartDate(LocalDate startDate);

    List<Member> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Member> findByStartDateAfter(LocalDate startDate);

    List<Member> findByStartDateBefore(LocalDate startDate);

    Member findByMemberEmail(String email);

    @Query("SELECT m FROM Member m JOIN m.tournaments t WHERE t.id = :tournamentId")
    List<Member> findMembersByTournamentId(@Param("tournamentId") Long tournamentId);
}
