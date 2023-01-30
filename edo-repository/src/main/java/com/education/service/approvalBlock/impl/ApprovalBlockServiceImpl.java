package com.education.service.approvalBlock.impl;

import com.education.entity.ApprovalBlock;
import com.education.repository.approvalBlock.ApprovalBlockRepository;
import com.education.service.approvalBlock.ApprovalBlockService;
import com.education.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Сервис-слой для связи контроллера и репозитория сущности ApprovalBlock
 */
@Service
@AllArgsConstructor
public class ApprovalBlockServiceImpl implements ApprovalBlockService {

    private final ApprovalBlockRepository approvalBlockRepository;
    private final MemberService memberService;

    /**
     * Метод принимает сущность ApprovalBlock и сохраняет её в БД
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(ApprovalBlock approvalBlock) {
        approvalBlockRepository.save(approvalBlock);
    }

    /**
     * Метод принимает индекс и ищет сущность ApprovalBlock с таким индексом в БД.
     * Возвращает найденную сущность.
     */
    @Transactional(readOnly = true)
    @Override
    public ApprovalBlock findById(Long id) {
        return approvalBlockRepository.findById(id).orElse(null);
    }

    /**
     * Метод возвращает все сущности ApprovalBlock, которые хранятся в БД
     */
    @Transactional(readOnly = true)
    @Override
    public Collection<ApprovalBlock> findAll() {
        return approvalBlockRepository.findAll();
    }

    /**
     * Метод принимает индексы и ищет сущности ApprovalBlock с такими индексами в БД.
     * Возвращает найденные сущности.
     */
    @Transactional(readOnly = true)
    @Override
    public Collection<ApprovalBlock> findAllById(Iterable<Long> ids) {
        return approvalBlockRepository.findAllById(ids);
    }

    /**
     * Метод принимает индекс и удаляет сущность ApprovalBlock с таким индексом из БД
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {

        // Удаление всех вложенных сущностей
        ApprovalBlock approvalBlock = findById(id);
        if (approvalBlock != null) {
            approvalBlock.getSignatories().forEach(member -> memberService.delete(member.getId()));
            approvalBlock.getParticipants().forEach(member -> memberService.delete(member.getId()));
        }

        approvalBlockRepository.deleteById(id);
    }
}
