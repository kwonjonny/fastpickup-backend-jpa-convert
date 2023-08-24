package com.project.fastpickup.admin.member.repository.search;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.project.fastpickup.admin.member.dto.MemberListDTO;
import com.project.fastpickup.admin.member.entity.MemberEntity;
import com.project.fastpickup.admin.member.entity.QMemberEntity;
import com.project.fastpickup.admin.member.entity.QMemberRoleEntity;
import com.project.fastpickup.admin.util.PageRequestDTO;
import com.project.fastpickup.admin.util.PageResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

// MemberSearchImpl Class
@Log4j2
public class MemberSearchImpl extends QuerydslRepositorySupport implements MemberSearch {

    public MemberSearchImpl() {
        super(MemberEntity.class);
    }

    // List Member And Search Member
    @Override
    public PageResponseDTO<MemberListDTO> memberList(PageRequestDTO pageRequestDTO) {
        log.info("Is Running MemberSearchImpl MemberList");
        QMemberEntity memberEntity = QMemberEntity.memberEntity;
        QMemberRoleEntity memberRoleEntity = QMemberRoleEntity.memberRoleEntity;

        JPQLQuery<MemberEntity> query = from(memberEntity);

        int pageNum = pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1;

        Pageable pageable = PageRequest.of(pageNum, pageRequestDTO.getSize(), Sort.by("email").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        query.groupBy(memberEntity);

        JPQLQuery<MemberListDTO> dtoQuery = query.select(
                Projections.bean(MemberListDTO.class, memberEntity.email, memberEntity.memberName,
                        memberEntity.memberPw, memberEntity.memberPhone, memberEntity.joinDate));
        List<MemberListDTO> memberListDTO = dtoQuery.fetch();

        Long totalCount = dtoQuery.fetchCount();
        return new PageResponseDTO<>(memberListDTO, totalCount, pageRequestDTO);
    }
}
