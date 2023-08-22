package com.education.controller.resolution;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import model.dto.EmployeeDto;
import model.dto.QuestionDto;
import model.dto.ResolutionDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static model.enum_.ResolutionType.RESOLUTION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест, который сохраняет в базу данных объекты Resolution, поля которых заполнены по-разному или не заполнены (null).
 * Для корректного запуска теста нужны запущенные модули: edo-cloud-server, edo-rest, edo-service, edo-repository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Setter(onMethod_ = @Autowired)
public class SaveResolution {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private static ResolutionDto newResolutionDto;

    @Before
    @DisplayName("Инициализация объекта ResolutionDto со всеми " +
            "заполненными полями перед каждым тестом")
    public void init() {
        List<EmployeeDto> executor = new ArrayList<>();
        executor.add(EmployeeDto.builder().id(1L).build());
        executor.add(EmployeeDto.builder().id(1L).build());

        newResolutionDto = ResolutionDto.builder()
                .type(RESOLUTION)
                .creator(EmployeeDto.builder().id(1L).build())
                .signer(EmployeeDto.builder().id(1L).build())
                .executor(executor)
                .curator(EmployeeDto.builder().id(1L).build())
                .serialNumber("1")
                .isDraft(false)
                .question(QuestionDto.builder().id(1L).build())
                .build();
    }

    /**
     * Вспомогательный метод для отправки post запроса на сохранение ResolutionDto в базу
     */
    public void postRequestToSaveResolution(ResolutionDto resolutionDto) throws Exception {
        this.mockMvc.perform(post("/api/rest/resolution/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resolutionDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("тест на сохранение одной резолюции")
    public void saveTest() throws Exception {
        postRequestToSaveResolution(newResolutionDto);
    }

    @Test
    @DisplayName("тест на сохранение резолюции без исполнителей")
    public void saveWithoutExecutorTest() throws Exception {
        newResolutionDto.setExecutor(null);
        postRequestToSaveResolution(newResolutionDto);
    }

    @Test
    @DisplayName("тест на сохранение резолюции без исполнителей")
    public void seveWithoutSignerTest() throws Exception {
        newResolutionDto.setSigner(null);
        postRequestToSaveResolution(newResolutionDto);
    }

}
