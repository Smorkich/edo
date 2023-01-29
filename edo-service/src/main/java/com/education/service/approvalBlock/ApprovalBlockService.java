package com.education.service.approvalBlock;

import model.dto.ApprovalBlockDto;

import java.util.Collection;

public interface ApprovalBlockService {
    /**
     * Сохраняет новый блок листа согласования
     */
    ApprovalBlockDto save(ApprovalBlockDto approvalBlockDto);

    /**
     * Поиск блока листа согласования по индексу
     */
    ApprovalBlockDto findById(Long id);

    /**
     * Поиск всех блоков листа согласования
     */
    Collection<ApprovalBlockDto> findAll();

    /**
     * Поиск всех блоков листа согласования по индексам
     */
    Collection<ApprovalBlockDto> findAllById(Iterable<Long> ids);

    /**
     * Удалить блок листа согласования по индексу
     */
    void delete(Long id);
}
