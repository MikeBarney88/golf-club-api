package com.golfclub.rest.tournament;

import com.golfclub.rest.member.Member;
import com.golfclub.rest.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository, 
                            MemberRepository memberRepository) {
        this.tournamentRepository = tournamentRepository;
        this.memberRepository = memberRepository;
    }

    public Tournament createTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Optional<Tournament> getTournamentById(Long id) {
        return tournamentRepository.findById(id);
    }

    public Tournament updateTournament(Long id, Tournament tournamentDetails) {
        Tournament tournament = tournamentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tournament not found with id: " + id));

        tournament.setStartDate(tournamentDetails.getStartDate());
        tournament.setEndDate(tournamentDetails.getEndDate());
        tournament.setLocation(tournamentDetails.getLocation());
        tournament.setEntryFee(tournamentDetails.getEntryFee());
        tournament.setCashPrizeAmount(tournamentDetails.getCashPrizeAmount());

        return tournamentRepository.save(tournament);
    }

    public void deleteTournament(Long id) {
        Tournament tournament = tournamentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tournament not found with id: " + id));
        tournamentRepository.delete(tournament);
    }

    public Tournament addMemberToTournament(Long tournamentId, Long memberId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
            .orElseThrow(() -> new RuntimeException("Tournament not found with id: " + tournamentId));
        
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        tournament.addMember(member);
        return tournamentRepository.save(tournament);
    }

    public Tournament removeMemberFromTournament(Long tournamentId, Long memberId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
            .orElseThrow(() -> new RuntimeException("Tournament not found with id: " + tournamentId));
        
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        tournament.removeMember(member);
        return tournamentRepository.save(tournament);
    }

    public Tournament getTournamentWithMembers(Long tournamentId) {
        Tournament tournament = tournamentRepository.findTournamentWithMembers(tournamentId);
        if (tournament == null) {
            throw new RuntimeException("Tournament not found with id: " + tournamentId);
        }
        return tournament;
    }

    public List<Tournament> searchByStartDate(LocalDate startDate) {
        return tournamentRepository.findByStartDate(startDate);
    }

    public List<Tournament> searchByLocation(String location) {
        return tournamentRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Tournament> searchByStartDateRange(LocalDate startDate, LocalDate endDate) {
        return tournamentRepository.findByStartDateBetween(startDate, endDate);
    }
}
