package com.golfclub.rest.tournament;

import com.golfclub.rest.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Start date is required")
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(nullable = false)
    private LocalDate endDate;

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;

    @NotNull(message = "Entry fee is required")
    @Column(nullable = false)
    private BigDecimal entryFee;

    @NotNull(message = "Cash prize amount is required")
    @Column(nullable = false)
    private BigDecimal cashPrizeAmount;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "tournament_members",
        joinColumns = @JoinColumn(name = "tournament_id"),
        inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    @JsonIgnoreProperties("tournaments")
    private Set<Member> participatingMembers = new HashSet<>();

    public Tournament() {
    }

    public Tournament(LocalDate startDate, LocalDate endDate, String location, 
                     BigDecimal entryFee, BigDecimal cashPrizeAmount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.entryFee = entryFee;
        this.cashPrizeAmount = cashPrizeAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(BigDecimal entryFee) {
        this.entryFee = entryFee;
    }

    public BigDecimal getCashPrizeAmount() {
        return cashPrizeAmount;
    }

    public void setCashPrizeAmount(BigDecimal cashPrizeAmount) {
        this.cashPrizeAmount = cashPrizeAmount;
    }

    public Set<Member> getParticipatingMembers() {
        return participatingMembers;
    }

    public void setParticipatingMembers(Set<Member> participatingMembers) {
        this.participatingMembers = participatingMembers;
    }

    public void addMember(Member member) {
        this.participatingMembers.add(member);
        member.getTournaments().add(this);
    }

    public void removeMember(Member member) {
        this.participatingMembers.remove(member);
        member.getTournaments().remove(this);
    }
}
