package com.education.service.member.impl;

import com.education.feign.MemberFeignClient;
import com.education.service.member.MemberService;
import lombok.AllArgsConstructor;
import model.dto.MemberDto;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collection;

/**
 * Сервис слой для взаимодействия с MemberDto
 */
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberFeignClient memberFeignClient;

    /**
     * Отправляет post-запрос в edo-repository для сохранения участника
     */
    @Override
    public MemberDto save(MemberDto memberDto) {

        // Установка даты создания для нового участника
        if (memberDto.getCreationDate() == null) {
            memberDto.setCreationDate(ZonedDateTime.now());
        }

        return  memberFeignClient.save(memberDto).getBody();
    }

    /**
     * Отправляет post-запрос в edo-repository для сохранения участника со ссылкой на блок согласования
     */
    @Override
    public MemberDto save(MemberDto memberDto, Long approvalBlockId) {

        // Установка даты создания для нового участника
        if (memberDto.getCreationDate() == null) {
            memberDto.setCreationDate(ZonedDateTime.now());
        }

        // Установка даты создания для участника
        memberDto.setCreationDate(ZonedDateTime.now());

        return memberFeignClient.save(memberDto).getBody();
    }

    /**
     * Отправляет get-запрос в edo-repository для получения участника по индексу
     */
    @Override
    public MemberDto findById(Long id) {

        return memberFeignClient.findById(id).getBody();
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех участников
     */
    @Override
    public Collection<MemberDto> findAll() {

        return memberFeignClient.findAll().getBody();
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех участников с полученными индексами
     */
    @Override
    public Collection<MemberDto> findAllById(Iterable<Long> ids) {

        return memberFeignClient.findAllById(ids);
    }

    /**
     * Отправляет delete-запрос в edo-repository для удаления участника по индексу
     */
    @Override
    public void delete(Long id) {

        memberFeignClient.delete(id).getBody();
    }
}
