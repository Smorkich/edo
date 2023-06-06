package com.education.controller.appeal;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import model.dto.*;
import model.enum_.Employment;
import model.enum_.ReceiptMethod;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест, который сохраняет в базу данных объекты Appeal, поля которых заполнены по-разному или не заполнены (null).
 * Для корректного запуска теста нужны запущенные модули:
 * edo-cloud-server, edo-rest, edo-service, edo-repository, edo-file-storage, edo-integration
 */

@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Setter(onMethod_ = @Autowired)
@Testcontainers
public class SaveNewAppealTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private static AppealDto newAppealDto;
    private static PostgreSQLContainer<?> postgresContainer;

    /**
     * Инициализация объекта AppealDto перед каждым тестом
     * со всеми корректно заполненными полями для валидации,
     * с одним автором, одним вопросом, без FilePool
     */

    @BeforeAll
    static void initAll() {
        // Запуск контейнера PostgreSQL
        postgresContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("edo_db")
                .withUsername("postgres")
                .withPassword("oog");
        postgresContainer.start();

        // Настройка свойств соединения с базой данных для тестов
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }


    @BeforeEach
    public void init() {
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
    }

    /**
     * Вспомогательный метод для отправки post запроса на сохранение AppealDto в базу
     */
    private void postRequestToSaveAppeal(AppealDto appealDto) throws Exception {
        this.mockMvc.perform(post("/api/edo/appeal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appealDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Сохранение стандартной Appeal")
    public void saveAppealTest() throws Exception {
        postRequestToSaveAppeal(newAppealDto);
    }

    @Test
    @DisplayName("Сохранение двух стандартных Appeal")
    public void saveTwoAppealTest() throws Exception {
        postRequestToSaveAppeal(newAppealDto);
        postRequestToSaveAppeal(newAppealDto);
    }

    @Test
    @DisplayName("Сохранение стандартной Appeal без авторов, ожидает исключение")
    public void saveAppealWithoutAuthorsTest() {
        newAppealDto.setAuthors(null);
        assertThatThrownBy(() -> postRequestToSaveAppeal(newAppealDto))
                .isInstanceOf(ServletException.class);
    }

    @Test
    @DisplayName("Сохранение стандартной Appeal c двумя авторами")
    public void saveAppealWithTwoAuthorsTest() throws Exception {
        var authors = Set.of(newAppealDto.getAuthors().iterator().next(),
                AuthorDto.builder()
                        .firstName("Имя")
                        .lastName("Фамилия")
                        .email("noreply.edo.test@yandex.ru")
                        .mobilePhone("89971234567")
                        .employment(Employment.UNEMPLOYED)
                        .build());
        newAppealDto.setAuthors(authors);
        postRequestToSaveAppeal(newAppealDto);
    }

    @Test
    @DisplayName("Сохранение стандартной Appeal c двумя вопросами")
    public void saveAppealWithTwoQuestionsTest() throws Exception {
        var questions = Set.of(newAppealDto.getQuestions().iterator().next(),
                QuestionDto.builder()
                        .theme(ThemeDto.builder().id(1L).build())
                        .summary("Краткое содержание")
                        .build());
        newAppealDto.setQuestions(questions);
        postRequestToSaveAppeal(newAppealDto);
    }

    @Test
    @DisplayName("Сохранение стандартной Appeal с FilePool")
    public void saveAppealWithFilePoolTest() throws Exception {
        var filePolls = Set.of(FilePoolDto.builder()
                .id(1L)
                .build());
        newAppealDto.setFile(filePolls);
        postRequestToSaveAppeal(newAppealDto);
    }

    @Test
    @DisplayName("Сохранение некорректно заполненной Appeal, ожидает исключение")
    public void saveAppealForValidationTest() {
        var authors = Set.of(AuthorDto.builder()
                .mobilePhone("телефон")
                .build());
        newAppealDto.setAuthors(authors);

        var questions = Set.of(QuestionDto.builder().build());
        newAppealDto.setQuestions(questions);

        newAppealDto.setSendingMethod(null);

        var thrown = Assertions.assertThrows(ServletException.class, () ->
                postRequestToSaveAppeal(newAppealDto), "ServletException was expected");

        Assertions.assertNotNull(thrown.getMessage());

        log.info("Сообщение исключения ServletException: " + thrown.getMessage());
    }
}
