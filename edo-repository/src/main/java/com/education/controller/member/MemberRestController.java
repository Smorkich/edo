package com.education.controller.member;

import com.education.entity.Member;
import com.education.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static com.education.mapper.MemberMapper.MEMBER_MAPPER;

/**
 * Rest-контроллер сущности Member для отправки запросов от клиентов к БД
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/repository/member")
@Tag(name = "Rest- контроллер для работы с участниками")
public class MemberRestController {
    private final MemberService memberService;

    @Operation(summary = "Добавляет новую запись в таблицу Member")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDto> save(@RequestBody MemberDto memberDto) {
        log.info("Send a post-request to post new Member to database");
        Member savedMember = memberService.save(MEMBER_MAPPER.toEntity(memberDto));
        log.info("Response: {} was added to database", memberDto);
        return new ResponseEntity<>(MEMBER_MAPPER.toDto(savedMember), HttpStatus.CREATED);
    }

    @Operation(summary = "Возвращает участника листа согласования по id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDto> findById(@PathVariable("id") long id) {
        log.info("Send a get-request to get member of approval with id = {} from database", id);
        var memberDto = MEMBER_MAPPER.toDto(memberService.findById(id));
        log.info("Response from database: {}", memberDto);
        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    @Operation(summary = "Возвращает всех участников листа согласования")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MemberDto>> findAll() {
        log.info("Send a get-request to get all members of approval from database");
        var listMemberDto = MEMBER_MAPPER.toDto(memberService.findAll());
        log.info("Response from database: {}", listMemberDto);
        return new ResponseEntity<>(listMemberDto, HttpStatus.OK);
    }

    @Operation(summary = "Возвращает всех участников листа согласования по списку id")
    @GetMapping("/all/{ids}")
    private ResponseEntity<Collection<MemberDto>> findAllById(@PathVariable List<Long> ids) {
        log.info("Send a get-request to get all members from database by ids");
        var listMemberDto = MEMBER_MAPPER.toDto(memberService.findAllById(ids));
        log.info("Response from database: {}", listMemberDto);
        return new ResponseEntity<>(listMemberDto, HttpStatus.OK);
    }

    @Operation(summary = "Удаляет участника листа согласования")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        log.info("Send a delete-request to delete member with id = {} from database", id);
        memberService.delete(id);
        log.info("Response: member with id = {} was deleted from database", id);
        return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Добавляет новую запись в таблицу Member со ссылкой на ApprovalBlock")
    @PostMapping(value ="saveWithLinkToApprovalBlock/{approvalBlockId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDto> saveWithLinkToApprovalBlock(@RequestBody MemberDto memberDto, @PathVariable Long approvalBlockId) {
        log.info("Send a post-request to post new Member to database with link to ApprovalBlock");
        Member savedMember = memberService.saveWithLinkToApprovalBlock(MEMBER_MAPPER.toEntity(memberDto), approvalBlockId);
        log.info("Response: {} was added to database", memberDto);
        return new ResponseEntity<>(MEMBER_MAPPER.toDto(savedMember), HttpStatus.CREATED);
    }
}
