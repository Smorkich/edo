package com.education.controller.appeal;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import model.dto.*;
import model.enum_.Employment;
import model.enum_.ReceiptMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Setter(onMethod_ = @Autowired)
public class SaveNewAppealTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private static AppealDto newAppealDto;

    /**
     *      тест на сохранение одного обращения
     *      с корректно заполненными полями для валидации
     *      с автором
     *      с одним вопросом
     *      без FilePool
     * <p>
     *      тест на сохранения двух обращений сразу
     * <p>
     *      тест на сохранение обращения без авторов
     * <p>
     *      с несколькими авторами
     * <p>
     *      с несколькими вопросами
     * <p>
     * с FilePool
     * <p>
     * с некорректно заполненными данными для валидации
     */


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
                .email("mail@mail.ru")
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
    @DisplayName("Сохранение стандартной Appeal без авторов")
    public void saveAppealWithoutAuthorsTest() throws Exception {
        newAppealDto.setAuthors(new HashSet<>());
        postRequestToSaveAppeal(newAppealDto);
    }

    @Test
    @DisplayName("Сохранение стандартной Appeal c двумя авторами")
    public void saveAppealWithTwoAuthorsTest() throws Exception {
        Collection<AuthorDto> newSet = newAppealDto.getAuthors();
        newSet.add(AuthorDto.builder()
                .firstName("Имя")
                .lastName("Фамилия")
                .email("mail@mail.ru")
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
    @DisplayName("Сохранение стандартной Appeal c двумя вопросами")
    public void saveAppealWithFilePoolTest() throws Exception {
        //отправка файла

        var file = new MockMultipartFile(
                "file",
                "FileForSaveNewAppealTest.doc",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "Hello".getBytes()
        );
        this.mockMvc.perform(multipart("/api/rest/minio/upload").file(file))
                .andExpect(status().isOk());


        Collection<FilePoolDto> newSet = new HashSet<>();
        newSet.add(FilePoolDto.builder().id(1L).build());
        newAppealDto.setFile(newSet);
        postRequestToSaveAppeal(newAppealDto);
    }
}
