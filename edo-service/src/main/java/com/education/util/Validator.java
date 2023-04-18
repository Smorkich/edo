package com.education.util;

import com.education.exception_handling.AppealAccessDeniedException;
import com.education.exception_handling.AppealCustomException;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import model.dto.ApprovalBlockDto;
import model.dto.ApprovalDto;
import model.dto.EmployeeDto;
import model.dto.MemberDto;
import model.enum_.ApprovalBlockType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singleton;


/**
 * Класс для валидации DTO
 */
@Component
@NoArgsConstructor
@Log4j2
public class Validator {
    private static String emailReg = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static String phoneReg = "/^(\\+?\\d{1,4}[\\s-])?(?!0+\\s+,?$)\\d{10}\\s*,?$";

    /**
     * Проверяет ApprovalBlockDto
     */
    public void validateApprovalBlockDto(ApprovalBlockDto approvalBlockDto) {

        // Проверка на соответствие типу блока согласования
        if (approvalBlockDto.getType().equals(ApprovalBlockType.PARTICIPANT_BLOCK)) {

            // Проверка поля signatories на отсутствие участников
            if (approvalBlockDto.getSignatories().size() != 0) {
                throw new IllegalArgumentException("The participant approval block must be without an signatories");
            }

            // Проверка на отсутствие повторяющихся порядковых номеров у участников
            if (approvalBlockDto.getParticipants().stream()
                    .map(MemberDto::getOrdinalNumber)
                    .collect(Collectors.toSet())
                    .size() != approvalBlockDto.getParticipants().size()) {
                throw new IllegalArgumentException("Members cannot have the same ordinal number in the same block.");
            }

        } else if (approvalBlockDto.getType().equals(ApprovalBlockType.SIGNATORY_BLOCK)) {

            // Проверка поля participants на отсутствие участников
            if (approvalBlockDto.getParticipants().size() != 0) {
                throw new IllegalArgumentException("The signatory approval block must be without an participants");
            }

            // Проверка на отсутствие повторяющихся порядковых номеров у участников
            if (approvalBlockDto.getSignatories().stream()
                    .map(MemberDto::getOrdinalNumber)
                    .collect(Collectors.toSet())
                    .size() != approvalBlockDto.getSignatories().size()) {
                throw new IllegalArgumentException("Members cannot have the same ordinal number in the same block.");
            }

        } else {
            throw new IllegalArgumentException("The approval block must be of type");
        }
    }

    /**
     * Проверяет ApprovalDto
     */
    public void validateApprovalDto(ApprovalDto approvalDto) {

        // Проверка на соответствие типу
        if (approvalDto.getSignatoryApprovalBlocks().stream()
                .map(ApprovalBlockDto::getType)
                .toList().contains(ApprovalBlockType.PARTICIPANT_BLOCK)) {
            throw new IllegalArgumentException("Signatory approval blocks cannot be of type PARTICIPANT_BLOCK.");
        }
        if (approvalDto.getParticipantApprovalBlocks().stream()
                .map(ApprovalBlockDto::getType)
                .toList().contains(ApprovalBlockType.SIGNATORY_BLOCK)) {
            throw new IllegalArgumentException("Participant approval blocks cannot be of type SIGNATORY_BLOCK.");
        }

        // Проверка на отсутствие повторяющихся порядковых номеров у блоков с участниками
        if (approvalDto.getParticipantApprovalBlocks().stream()
                .map(ApprovalBlockDto::getOrdinalNumber)
                .collect(Collectors.toSet())
                .size() != approvalDto.getParticipantApprovalBlocks().size()) {
            throw new IllegalArgumentException("Participant approval blocks cannot have the same ordinal number in the same approval.");
        }

        // Проверка на отсутствие повторяющихся порядковых номеров у блоков с подписантами
        if (approvalDto.getSignatoryApprovalBlocks().stream()
                .map(ApprovalBlockDto::getOrdinalNumber)
                .collect(Collectors.toSet())
                .size() != approvalDto.getSignatoryApprovalBlocks().size()) {
            throw new IllegalArgumentException("Signatory approval blocks cannot have the same ordinal number in the same approval.");
        }
    }
    /**
     * Проверяет Appeal
     */
    public static void getValidateAppeal(AppealDto appeal) throws RuntimeException {
        List<String> err = new ArrayList<>();
        if (appeal.getQuestions().size() > 0) {
            appeal.getQuestions().forEach(question -> {
                if (question.getTheme() == null) {
                    err.add(String.format("Appeal with ID %s has question with ID %s where Theme is null",
                            appeal.getId(), question.getId()));
                }
                if (question.getSummary() == null) {
                    err.add(String.format("Appeal with ID %s has question with ID %s where Summary is null",
                            appeal.getId(), question.getId()));
                } else if (question.getSummary().length() > 200) {
                    err.add(String.format("Appeal with ID %s has question with ID %s where Summary has length more then 200 symbols",
                            appeal.getId(), question.getId()));
                }
            });
        } else {
            err.add(String.format("Appeal with ID %s has not question", appeal.getId()));
        }
        if (appeal.getSendingMethod() == null) {
            err.add(String.format("Appeal with ID %s has null SendingMethod", appeal.getId()));
        }
        if (appeal.getAuthors() != null) {
            appeal.getAuthors().forEach(author -> {
                if (author.getFirstName() == null) {
                    err.add(String.format("Appeal with ID %s has author with ID %s where firstName is null",
                            appeal.getId(), author.getId()));
                } else if (author.getFirstName().length() > 60) {
                    err.add(String.format("Appeal with ID %s has author with ID %s where firstName has length more then 60 symbols",
                            appeal.getId(), author.getId()));
                }
                if (author.getLastName() == null) {
                    err.add(String.format("Appeal with ID %s has author with ID %s where lastName is null",
                            appeal.getId(), author.getId()));
                } else if (author.getLastName().length() > 60) {
                    err.add(String.format("Appeal with ID %s has author with ID %s where lastName has length more then 60 symbols",
                            appeal.getId(), author.getId()));
                }
                if (author.getEmail() == null) {
                    err.add(String.format("Appeal with ID %s has author with ID %s where email is null",
                            appeal.getId(), author.getId()));
                } else {
                    Pattern emailPattern = Pattern.compile(emailReg);
                    Matcher emailMatcher = emailPattern.matcher(author.getEmail());
                    if (!emailMatcher.matches()) {
                        log.info("Email is not valid");
                        err.add(String.format("Appeal with ID %s has author with ID %s with Email is not valid",
                                appeal.getId(), author.getId()));
                    }
                }
                if (author.getMobilePhone() == null) {
                    err.add(String.format("Appeal with ID %s has author with ID %s where mobilePhone is null",
                            appeal.getId(), author.getId()));
                } else {
                    Pattern phonePattern = Pattern.compile(phoneReg);
                    Matcher phoneMatcher = phonePattern.matcher(author.getMobilePhone());
                    if (!phoneMatcher.matches()) {
                        log.info("Phone is not valid");
                        err.add(String.format("Appeal with ID %s has author with ID %s with mobilePhone is not valid",
                                appeal.getId(), author.getId()));
                    }
                }
            });
        }
        if (!err.isEmpty()) {
            throw new AppealCustomException(err.toString());
        }
    }

    public static void validateAccess(EmployeeDto employeeDto, AppealDto appealDto) {

        Stream.of(appealDto.getSigner(), appealDto.getAddressee(), singleton(appealDto.getCreator()))
                .flatMap(Collection::stream)
                .filter(e -> e.getId().equals(employeeDto.getId()))
                .findAny()
                .orElseThrow(() -> new AppealAccessDeniedException("Access denied"));
    }
}
