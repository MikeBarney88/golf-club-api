package com.golfclub.rest.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Member updateMember(Long id, Member memberDetails) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));

        member.setMemberName(memberDetails.getMemberName());
        member.setMemberAddress(memberDetails.getMemberAddress());
        member.setMemberEmail(memberDetails.getMemberEmail());
        member.setMemberPhoneNumber(memberDetails.getMemberPhoneNumber());
        member.setStartDate(memberDetails.getStartDate());
        member.setDurationMonths(memberDetails.getDurationMonths());

        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        memberRepository.delete(member);
    }

    public List<Member> searchByName(String name) {
        return memberRepository.findByMemberNameContainingIgnoreCase(name);
    }

    public List<Member> searchByPhoneNumber(String phoneNumber) {
        return memberRepository.findByMemberPhoneNumber(phoneNumber);
    }

    public List<Member> searchByStartDate(LocalDate startDate) {
        return memberRepository.findByStartDate(startDate);
    }

    public List<Member> searchByStartDateRange(LocalDate startDate, LocalDate endDate) {
        return memberRepository.findByStartDateBetween(startDate, endDate);
    }

    public List<Member> searchByTournamentId(Long tournamentId) {
        return memberRepository.findMembersByTournamentId(tournamentId);
    }
}
