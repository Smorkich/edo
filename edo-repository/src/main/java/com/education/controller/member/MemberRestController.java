package com.education.controller.member;

import com.education.entity.Member;
import com.education.service.member.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static com.education.mapper.MemberMapper.MEMBER_MAPPER;
import static model.constant.Constant.MEMBER_URL;

/**
 * Rest-контроллер сущности Member для отправки запросов от клиентов к БД
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping(MEMBER_URL)
@ApiOperation("Rest-контроллер для MemberDto, который отправляет запросы от клиентов к сервисам edo-service")
public class MemberRestController {
    private final MemberService memberService;

    @ApiOperation(value = "Добавляет новую запись в таблицу Member")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberDto save (@RequestBody MemberDto memberDto) {
        log.info("Send a post-request to post new Member to database");
        Member savedMember = memberService.save(MEMBER_MAPPER.toEntity(memberDto));
        log.info("Response: {} was added to database", memberDto);
        return MEMBER_MAPPER.toDto(savedMember);
    }

    @ApiOperation(value = "Возвращает участника листа согласования по id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberDto findById (@PathVariable long id) {
        log.info("Send a get-request to get member of approval with id = {} from database", id);
        var memberDto = MEMBER_MAPPER.toDto(memberService.findById(id));
        log.info("Response from database: {}", memberDto);
        return memberDto;
    }

    @ApiOperation(value = "Возвращает всех участников листа согласования")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<MemberDto> findAll() {
        log.info("Send a get-request to get all members of approval from database");
        var listMemberDto = MEMBER_MAPPER.toDto(memberService.findAll());
        log.info("Response from database: {}", listMemberDto);
        return listMemberDto;
    }

    @ApiOperation("Возвращает всех участников листа согласования по списку id")
    @GetMapping("/all/{ids}")
    private Collection<MemberDto> findAllById(@PathVariable List<Long> ids) {
        log.info("Send a get-request to get all members from database by ids");
        var listMemberDto = MEMBER_MAPPER.toDto(memberService.findAllById(ids));
        log.info("Response from database: {}", listMemberDto);
        return listMemberDto;
    }

    @ApiOperation(value = "Удаляет участника листа согласования")
    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        log.info("Send a delete-request to delete member with id = {} from database", id);
        memberService.delete(id);
        log.info("Response: member with id = {} was deleted from database", id);
        return  HttpStatus.ACCEPTED;
    }

    @ApiOperation(value = "Добавляет новую запись в таблицу Member со ссылкой на ApprovalBlock")
    @PostMapping(value ="saveWithLinkToApprovalBlock/{approvalBlockId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberDto saveWithLinkToApprovalBlock(@RequestBody MemberDto memberDto, @PathVariable Long approvalBlockId) {
        log.info("Send a post-request to post new Member to database with link to ApprovalBlock");
        Member savedMember = memberService.saveWithLinkToApprovalBlock(MEMBER_MAPPER.toEntity(memberDto), approvalBlockId);
        log.info("Response: {} was added to database", memberDto);
        return MEMBER_MAPPER.toDto(savedMember);
    }
}
