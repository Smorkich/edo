package com.education.controller.appeal;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import lombok.Setter;
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

import java.util.Collection;
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
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Setter(onMethod_ = @Autowired)
public class SaveNewAppealTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private static AppealDto newAppealDto;

    /**
     * Инициализация объекта AppealDto перед каждым тестом
     * со всеми корректно заполненными полями для валидации,
     * с одним автором, одним вопросом, без FilePool
     */
    @BeforeEach
    public void init() {
        Set<EmployeeDto> setSigner = new HashSet<>();
        setSigner.add(EmployeeDto.builder().id(1L).build());
        Set<EmployeeDto> setAddressee = new HashSet<>();
        setAddressee.add(EmployeeDto.builder().id(4L).build());
        Set<AuthorDto> setAuthors = new HashSet<>();
        setAuthors.add(AuthorDto.builder()
                .firstName("Имя")
                .lastName("Фамилия")
                .email("noreply.edo.test@yandex.ru")
                .mobilePhone("89971234567")
                .employment(Employment.UNEMPLOYED)
                .build());
        Set<QuestionDto> setQuestions = new HashSet<>();
        setQuestions.add(QuestionDto.builder()
                .theme(ThemeDto.builder().id(1L).build())
                .summary("Краткое содержание")
                .build());
        Set<FilePoolDto> setFilePool = new HashSet<>();

        newAppealDto = AppealDto.builder()
                .annotation("Заголовок")
                .creator(EmployeeDto.builder().id(3L).build())
                .sendingMethod(ReceiptMethod.VIA_EMAIL)
                .nomenclature(NomenclatureDto.builder().id(1L).build())
                .region(RegionDto.builder().id(1L).build())
                .signer(setSigner)
                .addressee(setAddressee)
                .authors(setAuthors)
                .questions(setQuestions)
                .file(setFilePool)
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
    public void saveAppealWithoutAuthorsTest(){
        newAppealDto.setAuthors(null);
        assertThatThrownBy(() -> postRequestToSaveAppeal(newAppealDto))
                .isInstanceOf(ServletException.class);
    }

    @Test
    @DisplayName("Сохранение стандартной Appeal c двумя авторами")
    public void saveAppealWithTwoAuthorsTest() throws Exception {
        Collection<AuthorDto> newSet = newAppealDto.getAuthors();
        newSet.add(AuthorDto.builder()
                .firstName("Имя")
                .lastName("Фамилия")
                .email("noreply.edo.test@yandex.ru")
                .mobilePhone("89971234567")
                .employment(Employment.UNEMPLOYED)
                .build());
        newAppealDto.setAuthors(newSet);
        postRequestToSaveAppeal(newAppealDto);
    }

    @Test
    @DisplayName("Сохранение стандартной Appeal c двумя вопросами")
    public void saveAppealWithTwoQuestionsTest() throws Exception {
        Collection<QuestionDto> newSet = newAppealDto.getQuestions();
        newSet.add(QuestionDto.builder()
                .theme(ThemeDto.builder().id(1L).build())
                .summary("Краткое содержание")
                .build());
        newAppealDto.setQuestions(newSet);
        postRequestToSaveAppeal(newAppealDto);
    }

    @Test
    @DisplayName("Сохранение стандартной Appeal с FilePool")
    public void saveAppealWithFilePoolTest() throws Exception {
        Collection<FilePoolDto> newSet = new HashSet<>();
        newSet.add(FilePoolDto.builder()
                .id(3L)
                .build());
        newAppealDto.setFile(newSet);
        postRequestToSaveAppeal(newAppealDto);
    }

    @Test
    @DisplayName("Сохранение некорректно заполненной Appeal, ожидает исключение")
    public void saveAppealForValidationTest() {
        Collection<AuthorDto> setAuthors = newAppealDto.getAuthors();
        setAuthors.add(AuthorDto.builder()
                .mobilePhone("телефон")
                .build());
        newAppealDto.setAuthors(setAuthors);

        Collection<QuestionDto> setQuestions = newAppealDto.getQuestions();
        setQuestions.add(QuestionDto.builder().build());
        newAppealDto.setQuestions(setQuestions);

        newAppealDto.setSendingMethod(null);

        ServletException thrown = Assertions.assertThrows(ServletException.class, () ->
                postRequestToSaveAppeal(newAppealDto), "ServletException was expected");

        Assertions.assertNotNull(thrown.getMessage());

        System.out.println("Сообщение исключения ServletException: " + thrown.getMessage());
    }
}
