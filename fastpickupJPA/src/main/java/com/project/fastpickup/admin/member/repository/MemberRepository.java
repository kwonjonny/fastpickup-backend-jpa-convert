package com.project.fastpickup.admin.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.fastpickup.admin.member.entity.MemberEntity;
import com.project.fastpickup.admin.member.repository.search.MemberSearch;

public interface MemberRepository extends JpaRepository<MemberEntity, String>, MemberSearch{
    
    @Query("SELECT m FROM MemberEntity m LEFT JOIN FETCH m.roles WHERE m.email = :email")
    Optional<MemberEntity> findByEmailWithRoles(@Param("email") String email);
}
