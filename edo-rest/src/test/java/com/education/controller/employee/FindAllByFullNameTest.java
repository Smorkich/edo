package com.education.controller.employee;

import com.education.ParentTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import model.dto.AddressDto;
import model.dto.DepartmentDto;
import model.dto.EmployeeDto;
import org.json.JSONArray;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест, который сохраняет несколько работников (EmployeeDto) и имитирует их поиск по ФИО
 * Для корректного запуска теста нужны запущенные модули: edo-cloud-server, edo-rest, edo-service, edo-repository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Setter(onMethod_ = @Autowired)
public class FindAllByFullNameTest extends ParentTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Сохранение нового работника с русским ФИО")
    public void TestA_SaveNewRussianEmployee() throws Exception {
        EmployeeDto newEmployee = EmployeeDto.builder()
                .firstName("Антон")
                .lastName("Иванов")
                .middleName("Андреевич")
                .fioNominative("Антон Иванов Андреевич")
                .department(DepartmentDto.builder().id(1L).build())
                .address(AddressDto.builder().id(1L).build())
                .build();

        postRequestToSaveEmployee(newEmployee);
    }

    @Test
    @Repeat(2)
    @DisplayName("Сохранение двух одинаковых работников с ФИО на английском")
    public void TestB_SaveTwoIdenticalEnglishEmployee() throws Exception {
        EmployeeDto newEmployee = EmployeeDto.builder()
                .firstName("Ivan")
                .lastName("Sidorov")
                .middleName("Petrovich")
                .fioNominative("Ivan Sidorov Petrovich")
                .department(DepartmentDto.builder().id(1L).build())
                .address(AddressDto.builder().id(1L).build())
                .build();
        postRequestToSaveEmployee(newEmployee);
    }

    /**
     * Вспомогательный метод для отправки post запроса на сохранение employeeDto в базу
     */
    public void postRequestToSaveEmployee(EmployeeDto employeeDto) throws Exception {
        this.mockMvc.perform(post("/api/rest/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Поиск работников в базе по ФИО на английском " +
            "и проверка, что найдено более одного работника с таким ФИО")
    public void TestC_EnglishCharacterTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/rest/employee/search")
                        .param("fullName", "Ivan Sidorov Petrovich"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Ivan")))
                .andExpect(jsonPath("$[0].lastName", is("Sidorov")))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertThat(jsonArray.length(), greaterThan(1));
    }

    @Test
    @DisplayName("Поиск работников в базе по ФИО на русском")
    public void TestD_RussianCharacterTest() throws Exception {
        this.mockMvc.perform(get("/api/rest/employee/search")
                        .param("fullName", "Антон Иванов Андреевич"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", equalTo("Антон")))
                .andExpect(jsonPath("$[0].lastName", equalTo("Иванов")));
    }

    @Test
    @DisplayName("Поиск работников в базе при вводе малого количества символов")
    public void TestE_SmallNumberOfCharacterTest() throws Exception {
        this.mockMvc.perform(get("/api/rest/employee/search")
                        .param("fullName", "Iv"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Ivan")))
                .andExpect(jsonPath("$[0].lastName", is("Sidorov")));
    }


    @Test
    @DisplayName("Поиск работников в базе при вводе символов в разном регистре")
    public void TestF_DifferentСharacterСaseTest() throws Exception {
        this.mockMvc.perform(get("/api/rest/employee/search")
                        .param("fullName", "IvAN SiDoRov"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Ivan")))
                .andExpect(jsonPath("$[0].lastName", is("Sidorov")));
    }
}
