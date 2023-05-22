package com.education.controller.appeal;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import model.dto.*;
import model.enum_.Employment;
import model.enum_.ReceiptMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Тест, который сохраняет в базу данных объекты Appeal после редактирования, поля которых заполнены по-разному или не заполнены (null).
 * Для корректного запуска теста нужны запущенные модули:
 * edo-cloud-server, edo-rest, edo-service, edo-repository, edo-file-storage, edo-integration
 */

@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Setter(onMethod_ = @Autowired)
public class EditAppealTest {
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private static AppealDto newAppealDto;

    /**
     * Инициализация объекта AppealDto и его сохранение в базу данных перед каждым тестом
     * со всеми корректно заполненными полями для валидации,
     * с одним автором, одним вопросом, без FilePool
     */
    @BeforeEach
    public void init() throws Exception {
        var signers = Set.of(EmployeeDto.builder().id(1L).build());
        var addresses = Set.of(EmployeeDto.builder().id(4L).build());
        var authors = Set.of(AuthorDto.builder()
                .firstName("Имя")
                .lastName("Фамилия")
                .email("noreply.edo.test@yandex.ru")
                .mobilePhone("89971234567")
                .employment(Employment.UNEMPLOYED)
                .build());
        var questions = Set.of(QuestionDto.builder()
                .theme(ThemeDto.builder().id(1L).build())
                .summary("Краткое содержание")
                .build());

        Set<FilePoolDto> filePolls = new HashSet<>();

        newAppealDto = AppealDto.builder()
                .id(1L)
                .annotation("Заголовок")
                .creator(EmployeeDto.builder().id(3L).build())
                .sendingMethod(ReceiptMethod.VIA_EMAIL)
                .nomenclature(NomenclatureDto.builder().id(1L).build())
                .region(RegionDto.builder().id(1L).build())
                .signer(signers)
                .addressee(addresses)
                .authors(authors)
                .questions(questions)
                .file(filePolls)
                .build();

        postRequestToSaveAppeal(newAppealDto);
    }

    /**
     * Вспомогательный метод для отправки POST запроса на сохранение AppealDto в базу данных
     */
    private void postRequestToSaveAppeal(AppealDto appealDto) throws Exception {
        mockMvc.perform(post("/api/edo/appeal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appealDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    /**
     * Вспомогательный метод для отправки GET запроса на получение AppealDto из базы данных
     */
    private AppealDto getRequestToEditAppeal(Long id) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/edo/appeal/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper.readValue(result.getResponse().getContentAsString(), AppealDto.class);
    }

    @Test
    @DisplayName("Сохранение Appeal после редактирования любого поля")
    public void saveAppealTest() throws Exception {
        AppealDto editAppealDto = getRequestToEditAppeal(newAppealDto.getId());
        editAppealDto.setAuthors(Set.of(AuthorDto.builder()
                .firstName("Editname")
                .lastName("Editfamily")
                .email("noreply.edo.test.edit@yandex.ru")
                .mobilePhone("89911234466")
                .employment(Employment.UNEMPLOYED)
                .build()));
        postRequestToSaveAppeal(editAppealDto);

        assertNotEquals(newAppealDto
                        .getAuthors()
                        .stream()
                        .iterator()
                        .next()
                        .getFirstName(),
                getRequestToEditAppeal(newAppealDto.getId())
                        .getAuthors()
                        .stream()
                        .iterator()
                        .next()
                        .getFirstName());
    }

    @Test
    @DisplayName("Сохранение Appeal после редактирования без авторов, ожидает исключение")
    public void saveAppealWithoutAuthorsTest() throws Exception {
        AppealDto editAppealDto = getRequestToEditAppeal(newAppealDto.getId());
        editAppealDto.setAuthors(null);
        assertThatThrownBy(() -> postRequestToSaveAppeal(editAppealDto))
                .isInstanceOf(ServletException.class);
    }

    @Test
    @DisplayName("Сохранение Appeal после добавления второго автора")
    public void saveAppealWithTwoAuthorsTest() throws Exception {
        AppealDto editAppealDto = getRequestToEditAppeal(newAppealDto.getId());
        var authors = Set.of(editAppealDto.getAuthors().iterator().next(),
                AuthorDto.builder()
                        .firstName("Editname")
                        .lastName("Editfamily")
                        .email("noreply.edo.test.edit@yandex.ru")
                        .mobilePhone("89911234466")
                        .employment(Employment.UNEMPLOYED)
                        .build());
        editAppealDto.setAuthors(authors);
        postRequestToSaveAppeal(editAppealDto);
        assertNotEquals(newAppealDto.getAuthors().size(), getRequestToEditAppeal(newAppealDto.getId()).getAuthors().size());
    }

    @Test
    @DisplayName("Сохранение Appeal после добавления второго вопроса")
    public void saveAppealWithTwoQuestionsTest() throws Exception {
        AppealDto editAppealDto = getRequestToEditAppeal(newAppealDto.getId());
        var questions = Set.of(editAppealDto.getQuestions().iterator().next(),
                QuestionDto.builder()
                        .theme(ThemeDto.builder().id(1L).build())
                        .summary("Краткое содержание")
                        .build());
        editAppealDto.setQuestions(questions);
        postRequestToSaveAppeal(editAppealDto);
        assertNotEquals(newAppealDto.getQuestions().size(), getRequestToEditAppeal(newAppealDto.getId()).getQuestions().size());
    }

    @Test
    @DisplayName("Сохранение Appeal после добавления FilePool")
    public void saveAppealWithFilePoolTest() throws Exception {
        AppealDto editAppealDto = getRequestToEditAppeal(newAppealDto.getId());
        var filePolls = Set.of(FilePoolDto.builder()
                .id(1L)
                .build());
        editAppealDto.setFile(filePolls);
        postRequestToSaveAppeal(editAppealDto);
        assertNotEquals(newAppealDto.getFile().size(), getRequestToEditAppeal(newAppealDto.getId()).getFile().size());
    }

    @Test
    @DisplayName("Сохранение некорректно заполненного Appeal после редактирования, ожидает исключение")
    public void saveAppealForValidationTest() throws Exception {
        AppealDto editAppealDto = getRequestToEditAppeal(newAppealDto.getId());
        var authors = Set.of(AuthorDto.builder()
                .mobilePhone("телефон")
                .build());
        editAppealDto.setAuthors(authors);

        var questions = Set.of(QuestionDto.builder().build());
        editAppealDto.setQuestions(questions);

        editAppealDto.setSendingMethod(null);

        var thrown = Assertions.assertThrows(ServletException.class, () ->
                postRequestToSaveAppeal(editAppealDto), "ServletException was expected");

        Assertions.assertNotNull(thrown.getMessage());

        log.info("Сообщение исключения ServletException: " + thrown.getMessage());
    }
}
