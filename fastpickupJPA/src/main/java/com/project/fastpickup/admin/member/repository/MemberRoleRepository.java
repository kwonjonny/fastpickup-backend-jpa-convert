package com.project.fastpickup.admin.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fastpickup.admin.member.entity.MemberRoleEntity;

public interface MemberRoleRepository extends JpaRepository<MemberRoleEntity, String>{
    
}
