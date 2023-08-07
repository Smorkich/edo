package com.education.service.approval.impl;

import com.education.feign.ApprovalFeignClient;
import com.education.service.approval.ApprovalService;
import com.education.service.approvalBlock.ApprovalBlockService;
import com.education.service.emloyee.EmployeeService;
import com.education.service.member.MemberService;
import com.education.util.URIBuilderUtil;
import com.education.util.Validator;
import lombok.AllArgsConstructor;
import model.dto.ApprovalBlockDto;
import model.dto.ApprovalDto;
import model.dto.EmployeeDto;
import model.dto.MemberDto;
import model.enum_.MemberType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static model.constant.Constant.EDO_REPOSITORY_NAME;

/**
 * Сервис слой для взаимодействия с ApprovalBlockDto
 */
@Service
@AllArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
    private final ApprovalFeignClient approvalFeignClient;
    private final ApprovalBlockService approvalBlockService;
    private final MemberService memberService;
    private final Validator validator;

    // Используется для заглушки
    private final EmployeeService employeeService;

    /**
     * Отправляет post-запрос в edo-repository для сохранения листа согласования
     */
    @Override
    public ApprovalDto save(ApprovalDto approvalDto) {
        // Установка даты создания для нового листа согласования
        if (approvalDto.getCreationDate() == null) {
            approvalDto.setCreationDate(ZonedDateTime.now());
        }

        // Валидация листа согласования
        validator.validateApprovalDto(approvalDto);

        // Список, который хранит новые блоки
        List<ApprovalBlockDto> savedApprovalBlocks = new ArrayList<>();

        try {
            Collection<ApprovalBlockDto> participantApprovalBlocks = approvalDto.getParticipantApprovalBlocks();
            Collection<ApprovalBlockDto> signatoryApprovalBlocks = approvalDto.getSignatoryApprovalBlocks();

            // Сохранение инициатора
            if (approvalDto.getInitiator() == null) {
                approvalDto.setInitiator(memberService.save(MemberDto.builder()
                        // Заменить на нормальный метод получения текущего пользователя, после написания security!!!
                        .employee(getCurrentUser())
                        .creationDate(ZonedDateTime.now())
                        .ordinalNumber(0)
                        .type(MemberType.INITIATOR)
                        .build()));
            }

            // Вызов FeignClient для сохранения ApprovalDto
            approvalDto = approvalFeignClient.save(approvalDto).getBody();

            // Сохранение блоков согласования
            ApprovalDto finalApprovalDto = approvalDto;
            participantApprovalBlocks.forEach(approvalBlockDto -> {
                approvalBlockDto = approvalBlockService.save(approvalBlockDto, finalApprovalDto.getId());
                savedApprovalBlocks.add(approvalBlockDto);
                finalApprovalDto.getParticipantApprovalBlocks().add(approvalBlockDto);
            });
            signatoryApprovalBlocks.forEach(approvalBlockDto -> {
                approvalBlockDto = approvalBlockService.save(approvalBlockDto, finalApprovalDto.getId());
                savedApprovalBlocks.add(approvalBlockDto);
                finalApprovalDto.getSignatoryApprovalBlocks().add(approvalBlockDto);
            });

            // Вызов FeignClient для обновления ApprovalDto
            return approvalFeignClient.save(approvalDto).getBody();
        } catch (Exception e) {
            // Удаление сохранённых сущностей
            savedApprovalBlocks.forEach(approvalBlockDto -> approvalBlockService.delete(approvalBlockDto.getId()));
            if (approvalDto.getId() != null) delete(approvalDto.getId());

            throw e;
        }
    }

    /**
     * Отправляет get-запрос в edo-repository для получения листа согласования по индексу
     */
    @Override
    public ApprovalDto findById(Long id) {
        return approvalFeignClient.findById(id).getBody();
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех листов согласования
     */
    @Override
    public Collection<ApprovalDto> findAll() {
        return approvalFeignClient.findAll().getBody();
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех листов согласования с полученными индексами
     */
    @Override
    public Collection<ApprovalDto> findAllById(Iterable<Long> ids) {
        return approvalFeignClient.findAllById((List<Long>) ids).getBody();
    }

    /**
     * Отправляет delete-запрос в edo-repository для удаления листа согласования по индексу
     */
    @Override
    public void delete(Long id) {
        approvalFeignClient.delete(id);
    }

    /**
     * Отправляет patch-запрос в edo-repository для добавления даты архивации листу согласования
     */
    @Override
    public void moveToArchive(Long id) {
        approvalFeignClient.moveToArchive(id);
    }

    /**
     * Отправляет get-запрос в edo-repository для получения листа согласования без даты архивации по индексу
     */
    @Override
    public ApprovalDto findByIdNotArchived(Long id) {
        return approvalFeignClient.findByIdNotArchived(id).getBody();
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех листов согласования без даты архивации
     */
    @Override
    public Collection<ApprovalDto> findAllNotArchived() {
        return approvalFeignClient.findAllNotArchived().getBody();
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех листов согласования без даты архивации с полученными индексами
     */
    @Override
    public Collection<ApprovalDto> findByIdInAndArchivedDateNull(Iterable<Long> ids) {
        return approvalFeignClient.findByIdInAndArchivedDateNull((List<Long>) ids).getBody();
    }

    /**
     * Заглушка для получения текущего пользователя. Обязательно должен быть employee с id = 1 в БД!
     * После написания Security убрать!!!
     */
    private EmployeeDto getCurrentUser() {
        return employeeService.findById(1L);
    }

    /**
     * Отправляет post-запрос в edo-repository для обновления листа согласования
     */
    @Override
    public ApprovalDto update(ApprovalDto approvalDto) {
        return approvalFeignClient.update(approvalDto).getBody();
    }

    /**
     * Направляет лист согласования
     */
    @Override
    public void sendForApproval(Long id) {
        ApprovalDto approval = findByIdNotArchived(id);

        if (approval.getDateSentForApproval() == null) {
            //Устанавливает дату направления
            approval.setDateSentForApproval(ZonedDateTime.now());

            //устанавливает первого учаcтника первого блока согласующих
            ApprovalBlockDto firstParticipantBlock = approval.getParticipantApprovalBlocks()
                    .stream()
                    .min(Comparator.comparingInt(ApprovalBlockDto::getOrdinalNumber))
                    .get();
            MemberDto currentMember = firstParticipantBlock.getParticipants()
                    .stream()
                    .min(Comparator.comparingInt(MemberDto::getOrdinalNumber))
                    .get();
            approval.setCurrentMember(currentMember);

            update(approval);

            // Вызов FeignClient
            approvalFeignClient.sendForApproval(id);
        }
    }
}