package com.project.fastpickup.admin.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fastpickup.admin.member.entity.MemberEntity;
import com.project.fastpickup.admin.member.repository.search.MemberSearch;

public interface MemberRepository extends JpaRepository<MemberEntity, String>, MemberSearch{
    
}
