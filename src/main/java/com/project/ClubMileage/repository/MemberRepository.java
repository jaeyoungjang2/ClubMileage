package com.project.ClubMileage.repository;


import com.project.ClubMileage.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
