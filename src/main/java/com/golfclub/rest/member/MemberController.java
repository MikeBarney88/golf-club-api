package com.golfclub.rest.member;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member) {
        Member createdMember = memberService.createMember(member);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, 
                                               @Valid @RequestBody Member memberDetails) {
        try {
            Member updatedMember = memberService.updateMember(id, memberDetails);
            return ResponseEntity.ok(updatedMember);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        try {
            memberService.deleteMember(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Member>> searchByName(@RequestParam String name) {
        List<Member> members = memberService.searchByName(name);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/search/phone")
    public ResponseEntity<List<Member>> searchByPhone(@RequestParam String phoneNumber) {
        List<Member> members = memberService.searchByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/search/start-date")
    public ResponseEntity<List<Member>> searchByStartDate(@RequestParam String startDate) {
        LocalDate date = LocalDate.parse(startDate);
        List<Member> members = memberService.searchByStartDate(date);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/search/date-range")
    public ResponseEntity<List<Member>> searchByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Member> members = memberService.searchByStartDateRange(start, end);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/search/tournament/{tournamentId}")
    public ResponseEntity<List<Member>> getMembersByTournament(@PathVariable Long tournamentId) {
        List<Member> members = memberService.searchByTournamentId(tournamentId);
        return ResponseEntity.ok(members);
    }
}
