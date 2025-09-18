package com.code81.LibraryManagementSystem.repository;

import com.code81.LibraryManagementSystem.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
