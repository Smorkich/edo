package com.education.controller.facsimile;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import model.dto.DepartmentDto;
import model.dto.EmployeeDto;
import model.dto.FacsimileDto;
import model.dto.FilePoolDto;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест, который сохраняет в базу данных объекты Facsimile.
 * Для корректного запуска теста нужны запущенные модули: edo-cloud-server, edo-rest, edo-service, edo-repository
 * Также должна быть заполнена бд как минимум по 1 объекту в каждой табличке(department,employee,file_pool),
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Setter(onMethod_ = @Autowired)
public class FacsimileSaveTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private static FacsimileDto facsimileDto;


    /**
     * Создание facsimile
     */
    @BeforeClass
    public static void init() {
        facsimileDto = FacsimileDto.builder()
                .department(DepartmentDto.builder().id(1l).build())
                .employee(EmployeeDto.builder().id(1l).build())
                .filePool_id(FilePoolDto.builder().id(1l).build())
                .IsArchived(false).build();
    }


    public void postRequestToSaveFacsimile(FacsimileDto facsimileDto) throws Exception {
        this.mockMvc.perform(post("/api/rest/facsimile/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facsimileDto)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Сохранение Facsimile со всеми заполненными полями")
    public void saveFacsimile() throws Exception {
        postRequestToSaveFacsimile(facsimileDto);
    }

}
