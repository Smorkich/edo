package com.education.controller.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import model.dto.AddressDto;
import model.dto.DepartmentDto;
import model.dto.EmployeeDto;
import org.json.JSONArray;
import org.junit.Before;
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
@Setter(onMethod_ = @Autowired)
public class FindAllByFullNameTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private static EmployeeDto newEmployeeOne, newEmployeeTwo;

    private static String setValueFirstName = "$[0].firstName",
            setValueLastName = "$[0].lastName";

    private static String employeeOneFirstName = "Антон",
            employeeOneLastName = "Иванов",
            employeeOneMiddleName = "Андреевич";

    private static String employeeTwoFirstName = "Ivan",
            employeeTwoLastName = "Sidorov",
            employeeTwoMiddleName = "Petrovich";


    @BeforeClass
    public static void init() {
        newEmployeeOne = EmployeeDto.builder()
                .firstName(employeeOneFirstName)
                .lastName(employeeOneLastName)
                .middleName(employeeOneMiddleName)
                .fioNominative(String.format("%s %s %s",
                        employeeOneFirstName, employeeOneLastName, employeeOneMiddleName))
                .department(DepartmentDto.builder().id(1L).build())
                .address(AddressDto.builder().id(1L).build())
                .build();
        newEmployeeTwo = EmployeeDto.builder()
                .firstName(employeeTwoFirstName)
                .lastName(employeeTwoLastName)
                .middleName(employeeTwoMiddleName)
                .fioNominative(String.format("%s %s %s",
                        employeeTwoFirstName, employeeTwoLastName, employeeTwoMiddleName))
                .department(DepartmentDto.builder().id(1L).build())
                .address(AddressDto.builder().id(1L).build())
                .build();
    }

    @Before
    @DisplayName("Сохранение одного работника с ФИО на русском " +
            "и двух одинаковых работников с ФИО на английском")
    public void saveNewEmployees() throws Exception {
        if (newEmployeeOne != null) {
            postRequestToSaveEmployee(newEmployeeOne);
            newEmployeeOne = null;
        }
        if (newEmployeeTwo != null) {
            postRequestToSaveEmployee(newEmployeeTwo);
            postRequestToSaveEmployee(newEmployeeTwo);
            newEmployeeTwo = null;
        }
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
    public void englishCharacterTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/rest/employee/search")
                        .param("fullName", "Ivan Sidorov Petrovich"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(setValueFirstName, is(employeeTwoFirstName)))
                .andExpect(jsonPath(setValueLastName, is(employeeTwoLastName)))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertThat(jsonArray.length(), greaterThan(1));
    }

    @Test
    @DisplayName("Поиск работников в базе по ФИО на русском")
    public void russianCharacterTest() throws Exception {
        this.mockMvc.perform(get("/api/rest/employee/search")
                        .param("fullName", "Антон Иванов Андреевич"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(setValueFirstName, equalTo(employeeOneFirstName)))
                .andExpect(jsonPath(setValueLastName, equalTo(employeeOneLastName)));
    }

    @Test
    @DisplayName("Поиск работников в базе при вводе малого количества символов")
    public void smallNumberOfCharacterTest() throws Exception {
        this.mockMvc.perform(get("/api/rest/employee/search")
                        .param("fullName", "Iv"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(setValueFirstName, is(employeeTwoFirstName)))
                .andExpect(jsonPath(setValueLastName, is(employeeTwoLastName)));
    }


    @Test
    @DisplayName("Поиск работников в базе при вводе символов в разном регистре")
    public void differentСharacterСaseTest() throws Exception {
        this.mockMvc.perform(get("/api/rest/employee/search")
                        .param("fullName", "IvAN SiDoRov"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(setValueFirstName, is(employeeTwoFirstName)))
                .andExpect(jsonPath(setValueLastName, is(employeeTwoLastName)));
    }
}
