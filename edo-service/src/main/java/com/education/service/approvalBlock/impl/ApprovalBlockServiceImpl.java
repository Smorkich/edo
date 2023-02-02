package com.education.service.approvalBlock.impl;

import com.education.service.approvalBlock.ApprovalBlockService;
import com.education.service.member.MemberService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.dto.ApprovalBlockDto;
import com.education.util.Validator;
import model.dto.MemberDto;
import model.enum_.ApprovalBlockType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
public class ApprovalBlockServiceImpl implements ApprovalBlockService {
    private final RestTemplate restTemplate;
    private final Validator validator;
    private final MemberService memberService;

    /**
     * Отправляет post-запрос в edo-repository для сохранения блока согласования
     */
    @Override
    public ApprovalBlockDto save(ApprovalBlockDto approvalBlockDto) {

        // Проверка на отсутствие индекса у блока согласования
        if (approvalBlockDto.getId() != null) {
            throw new IllegalArgumentException("The approval block must be without an id");
        }

        // Валидация блока согласования
        validator.validateApprovalBlockDto(approvalBlockDto);

        // Список, который хранит новых участников
        List<MemberDto> savedMembers = new ArrayList<>();

        try {
            Collection<MemberDto> participants = approvalBlockDto.getParticipants();
            Collection<MemberDto> signatories = approvalBlockDto.getSignatories();

            // Сохранение блока согласования без участников
            approvalBlockDto.setSignatories(null);
            approvalBlockDto.setParticipants(null);
            String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approvalBlock").toString();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            approvalBlockDto = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(approvalBlockDto, headers), ApprovalBlockDto.class).getBody();
            Long approvalBlockId = approvalBlockDto.getId();

            // Сохранение участников согласования
            if (approvalBlockDto.getType().equals(ApprovalBlockType.PARTICIPANT_BLOCK)) {
                approvalBlockDto.setParticipants(participants.stream()
                        .map(memberDto -> {
                            memberDto = memberService.save(memberDto, approvalBlockId);
                            savedMembers.add(memberDto);

                            return memberDto;
                        })
                        .collect(Collectors.toList()));
            } else {
                approvalBlockDto.setSignatories(signatories.stream()
                        .map(memberDto -> {
                            memberDto = memberService.save(memberDto, approvalBlockId);
                            savedMembers.add(memberDto);

                            return memberDto;
                        })
                        .collect(Collectors.toList()));
            }

            return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(approvalBlockDto, headers), ApprovalBlockDto.class).getBody();
        } catch (Exception e) {

            // Удаление сохранённых сущностей
            savedMembers.forEach(memberDto -> memberService.delete(memberDto.getId()));
            if (approvalBlockDto.getId() != null) {
                delete(approvalBlockDto.getId());
            }

            throw e;
        }
    }

    /**
     * Отправляет post-запрос в edo-repository для сохранения блока согласования со ссылкой на лист согласования
     */
    @Override
    public ApprovalBlockDto save(ApprovalBlockDto approvalBlockDto, Long approvalId) {

        // Проверка на отсутствие индекса у блока согласования
        if (approvalBlockDto.getId() != null) {
            throw new IllegalArgumentException("The approval block must be without an id");
        }

        // Валидация блока согласования
        validator.validateApprovalBlockDto(approvalBlockDto);

        // Список, который хранит новых участников
        List<MemberDto> savedMembers = new ArrayList<>();

        try {
            Collection<MemberDto> participants = approvalBlockDto.getParticipants();
            Collection<MemberDto> signatories = approvalBlockDto.getSignatories();

            // Сохранение блока согласования без участников
            approvalBlockDto.setSignatories(null);
            approvalBlockDto.setParticipants(null);
            String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approvalBlock/saveWithLinkToApproval/" + approvalId).toString();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            approvalBlockDto = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(approvalBlockDto, headers), ApprovalBlockDto.class).getBody();
            Long approvalBlockId = approvalBlockDto.getId();

            // Сохранение участников согласования
            if (approvalBlockDto.getType().equals(ApprovalBlockType.PARTICIPANT_BLOCK)) {
                approvalBlockDto.setParticipants(participants.stream()
                        .map(memberDto -> {
                            memberDto = memberService.save(memberDto, approvalBlockId);
                            savedMembers.add(memberDto);

                            return memberDto;
                        })
                        .collect(Collectors.toList()));
            } else {
                approvalBlockDto.setSignatories(signatories.stream()
                        .map(memberDto -> {
                            memberDto = memberService.save(memberDto, approvalBlockId);
                            savedMembers.add(memberDto);

                            return memberDto;
                        })
                        .collect(Collectors.toList()));
            }

            return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(approvalBlockDto, headers), ApprovalBlockDto.class).getBody();
        } catch (Exception e) {

            // Удаление сохранённых сущностей
            savedMembers.forEach(memberDto -> memberService.delete(memberDto.getId()));
            if (approvalBlockDto.getId() != null) delete(approvalBlockDto.getId());

            throw e;
        }
    }

    /**
     * Отправляет get-запрос в edo-repository для получения блока согласования по индексу
     */
    @Override
    public ApprovalBlockDto findById(Long id) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approvalBlock/" + id).toString();

        return restTemplate.getForObject(uri, ApprovalBlockDto.class);
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех блоков согласования
     */
    @Override
    public Collection<ApprovalBlockDto> findAll() {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approvalBlock/all").toString();

        return restTemplate.getForObject(uri, List.class);
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех блоков согласования с полученными индексами
     */
    @Override
    public Collection<ApprovalBlockDto> findAllById(Iterable<Long> ids) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approvalBlock/all/" + ids).toString();

        return restTemplate.getForObject(uri, List.class);
    }

    /**
     * Отправляет delete-запрос в edo-repository для удаления блока согласования по индексу
     */
    @Override
    public void delete(Long id) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approvalBlock/" + id).toString();

        restTemplate.delete(uri);
    }
}
