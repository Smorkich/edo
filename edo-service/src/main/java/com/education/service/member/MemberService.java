package com.education.service.member;

import model.dto.MemberDto;

import java.util.Collection;

public interface MemberService {

    /**
     * Сохраняет нового участника
     */
    MemberDto save(MemberDto memberDto);

    /**
     * Поиск участника по индексу
     */
    MemberDto findById(Long id);

    /**
     * Поиск всех участников
     */
    Collection<MemberDto> findAll();

    /**
     * Поиск всех участников по индексам
     */
    Collection<MemberDto> findAllById(Iterable<Long> ids);

    /**
     * Удалить участника по индексу
     */
    void delete(Long id);
}
