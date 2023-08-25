package com.project.fastpickup.admin.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.fastpickup.admin.member.entity.MemberRoleEntity;

public interface MemberRoleRepository extends JpaRepository<MemberRoleEntity, Long> {

    @Modifying
    @Query("DELETE FROM MemberRoleEntity mr WHERE mr.member.email = :email")
    void deleteByEmail(@Param("email") String email);

    @Query("SELECT mr FROM MemberRoleEntity mr WHERE mr.member.email = :email")
    List<MemberRoleEntity> findByEmail(@Param("email") String email);
}
