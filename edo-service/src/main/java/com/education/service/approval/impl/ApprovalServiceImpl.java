package com.education.service.approval.impl;

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
import java.util.List;
import java.util.stream.Collectors;

import static model.constant.Constant.EDO_REPOSITORY_NAME;

/**
 * Сервис слой для взаимодействия с ApprovalBlockDto
 */
@Service
@AllArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
    private final RestTemplate restTemplate;
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

        // Проверка на отсутствие индекса у листа согласования
        if (approvalDto.getId() != null) {
            throw new IllegalArgumentException("The approval must be without an id");
        }

        // Валидация листа согласования
        validator.validateApprovalDto(approvalDto);

        // Список, который хранит новые блоки
        List<ApprovalBlockDto> savedApprovalBlocks = new ArrayList<>();

        try {
            Collection<ApprovalBlockDto> participantApprovalBlocks = approvalDto.getParticipantApprovalBlocks();
            Collection<ApprovalBlockDto> signatoryApprovalBlocks = approvalDto.getSignatoryApprovalBlocks();

            // Установка даты создания для листа согласования
            approvalDto.setCreationDate(ZonedDateTime.now());

            // Сохранение инициатора
            approvalDto.setInitiator(memberService.save(MemberDto.builder()
                    // Заменить на нормальный метод получения текущего пользователя, после написания security!!!
                    .employee(getCurrentUser())
                    .creationDate(ZonedDateTime.now())
                    .ordinalNumber(0)
                    .type(MemberType.INITIATOR)
                    .build()));

            // Сохранение листа согласования без блоков
            approvalDto.setSignatoryApprovalBlocks(null);
            approvalDto.setParticipantApprovalBlocks(null);
            String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval").toString();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            approvalDto = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(approvalDto, headers), ApprovalDto.class).getBody();
            Long approvalId = approvalDto.getId();

            // Сохранение блоков согласования
            approvalDto.setParticipantApprovalBlocks(participantApprovalBlocks.stream()
                    .map(approvalBlockDto -> {
                        approvalBlockDto = approvalBlockService.save(approvalBlockDto, approvalId);
                        savedApprovalBlocks.add(approvalBlockDto);

                        return approvalBlockDto;
                    })
                    .collect(Collectors.toList()));
            approvalDto.setSignatoryApprovalBlocks(signatoryApprovalBlocks.stream()
                    .map(approvalBlockDto -> {
                        approvalBlockDto = approvalBlockService.save(approvalBlockDto, approvalId);
                        savedApprovalBlocks.add(approvalBlockDto);

                        return approvalBlockDto;
                    })
                    .collect(Collectors.toList()));

            return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(approvalDto, headers), ApprovalDto.class).getBody();
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
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/" + id).toString();

        return restTemplate.getForObject(uri, ApprovalDto.class);
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех листов согласования
     */
    @Override
    public Collection<ApprovalDto> findAll() {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/all").toString();

        return restTemplate.getForObject(uri, List.class);
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех листов согласования с полученными индексами
     */
    @Override
    public Collection<ApprovalDto> findAllById(Iterable<Long> ids) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/all/" + ids).toString();

        return restTemplate.getForObject(uri, List.class);
    }

    /**
     * Отправляет delete-запрос в edo-repository для удаления листа согласования по индексу
     */
    @Override
    public void delete(Long id) {
        ApprovalDto approvalDto = findById(id);
        if (approvalDto != null) {
            approvalDto.getParticipantApprovalBlocks().forEach(approvalBlock -> approvalBlockService.delete(approvalBlock.getId()));
            approvalDto.getSignatoryApprovalBlocks().forEach(approvalBlock -> approvalBlockService.delete(approvalBlock.getId()));
            String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/" + id).toString();
            restTemplate.delete(uri);
            memberService.delete(approvalDto.getInitiator().getId());
        }

    }

    /**
     * Отправляет patch-запрос в edo-repository для добавления даты архивации листу согласования
     */
    @Override
    public void moveToArchive(Long id) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/move/" + id).toString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.exchange(uri, HttpMethod.PATCH, new HttpEntity<>(headers), ApprovalDto.class).getBody();
    }

    /**
     * Отправляет get-запрос в edo-repository для получения листа согласования без даты архивации по индексу
     */
    @Override
    public ApprovalDto findByIdNotArchived(Long id) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/findByIdNotArchived/" + id).toString();

        return restTemplate.getForObject(uri, ApprovalDto.class);
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех листов согласования без даты архивации
     */
    @Override
    public Collection<ApprovalDto> findAllNotArchived() {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/findAllNotArchived").toString();

        return restTemplate.getForObject(uri, List.class);
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех листов согласования без даты архивации с полученными индексами
     */
    @Override
    public Collection<ApprovalDto> findByIdInAndArchivedDateNull(Iterable<Long> ids) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/archive/all/findByIdInAndArchivedDateNull/" + ids).toString();

        return restTemplate.getForObject(uri, List.class);
    }

    /**
     * Заглушка для получения текущего пользователя. Обязательно должен быть employee с id = 1 в БД!
     * После написания Security убрать!!!
     */
    private EmployeeDto getCurrentUser() {
        return employeeService.findById(1L);
    }
}
