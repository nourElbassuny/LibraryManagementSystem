package com.code81.LibraryManagementSystem.entity;

import com.code81.LibraryManagementSystem.entity.Enum.MemberStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
@Entity
@Table(name = "Member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    private String name;

    @Column(unique = true)
    private String email;

    private String phone;
    private String address;

    private LocalDate membershipDate;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

//    @OneToMany(mappedBy = "member")
//    private Set<BorrowTransaction> transactions;

}