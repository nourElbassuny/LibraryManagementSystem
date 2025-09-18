package com.code81.LibraryManagementSystem.service;

import com.code81.LibraryManagementSystem.dto.MemberRequest;
import com.code81.LibraryManagementSystem.dto.MemberResponse;
import com.code81.LibraryManagementSystem.entity.Enum.MemberStatus;
import com.code81.LibraryManagementSystem.entity.Member;
import com.code81.LibraryManagementSystem.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberResponse createMember(MemberRequest request) {
        Member member = Member.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .address(request.address())
                .status(MemberStatus.valueOf(request.status()))
                .membershipDate(LocalDate.now())
                .build();

        Member saved = memberRepository.save(member);
        return mapToMemberResponse(saved);
    }


    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(this::mapToMemberResponse)
                .toList();
    }


    public MemberResponse getMemberById(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));
        return mapToMemberResponse(member);
    }


    public MemberResponse updateMember(Integer memberId, MemberRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));

        member.setName(request.name());
        member.setEmail(request.email());
        member.setPhone(request.phone());
        member.setAddress(request.address());
        member.setStatus(MemberStatus.valueOf(request.status()));

        Member updated = memberRepository.save(member);
        return mapToMemberResponse(updated);
    }


    public void deleteMember(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));
        memberRepository.delete(member);
    }


    private MemberResponse mapToMemberResponse(Member member) {
        return new MemberResponse(
                member.getMemberId(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getAddress(),
                member.getStatus().name()
        );
    }
}
