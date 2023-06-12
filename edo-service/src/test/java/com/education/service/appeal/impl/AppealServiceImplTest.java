package com.education.service.appeal.impl;

import jakarta.ws.rs.core.UriBuilder;
import model.dto.*;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;
import static model.enum_.Employment.UNEMPLOYED;
import static model.enum_.ReceiptMethod.VIA_EMAIL;
import static model.enum_.ResponseType.POST_OFFICE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Проверяет что сервис перессылает сообщение в EDO-Integration
 * по корректным ссылкам
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class AppealServiceImplTest {


    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private AppealServiceImpl appealService;


    private AppealDto appealDto;
    private EmployeeDto employeeDto;
    private ResponseEntity<Object> responseEntity;
    private URIBuilder builder;
    private URIBuilder builderEmployee;


    @BeforeEach
    void setup() {
        appealDto = createAppealTest();
        employeeDto = createEmployDTOTest();
        responseEntity = new ResponseEntity<>(HttpStatus.OK);
        builder = buildURI(EDO_INTEGRATION_NAME, MESSAGE_URL);
        builderEmployee = buildURI(EDO_REPOSITORY_NAME, EMPLOYEE_URL);
    }



    @Test
    void sendMessageTest() throws Exception {

        when(restTemplate.postForEntity(builder.toString(), HttpEntity.class, Object.class))
                .thenReturn((responseEntity));

        when(restTemplate.getForObject(any(String.class), eq(EmployeeDto.class)))
                .thenAnswer(invocation -> {
                    String uri = invocation.getArgument(0, String.class);
                    Assert.assertTrue(uri.contains(builderEmployee.toString()));
                    return employeeDto;

                });


        appealService.sendMessage(appealDto);

        verify(restTemplate).postForEntity(eq(builder.toString()),
                any(HttpEntity.class),
                eq(Object.class));
        verify(restTemplate, times(2))
                .getForObject(any(String.class), eq(EmployeeDto.class));

    }

    // Создание тестового обращения
    private AppealDto createAppealTest() {


        AddressDto addressDto = new AddressDto(10L, "address", "street", "house",
                105050, "housing", "building", "city", "region", "country",
                "flat", "longitude", "latitude");

        DepartmentDto departmentDto = new DepartmentDto(1L, "shortName", "fullName",
                addressDto, "+79811252688", "EXT001", null,
                null, null);

        ThemeDto themeDto = new ThemeDto(1L, "name", null,
                null, "code", null);

        QuestionDto questionDto = new QuestionDto(1L, null, null,
                "summary", themeDto);

        AuthorDto authorDto = new AuthorDto(15L, "FirstName", "LastName", "MiddleName",
                "address", "12312313123", "+79811252688", "test@mail.ru",
                UNEMPLOYED, POST_OFFICE, null, null, null);

        NomenclatureDto nomenclatureDto = new NomenclatureDto(1L, null, null,
                "template", 24L, "12345");

        // Создание сотрудника
        EmployeeDto employee1 = createEmployDTOTest();

        // Создание региона
        RegionDto regionDto = RegionDto.builder()
                .id(10L)
                .externalId("EXT001")
                .nameSubjectRussianFederation("TestRegion")
                .quantity(50)
                .primaryBranches(20)
                .localBranches(30)
                .build();

        // Создание файла
        FilePoolDto filePoolDto = FilePoolDto.builder()
                .id(1L)
                .storageFileId(UUID.randomUUID())
                .name("TestFile")
                .extension("pdf")
                .size(5000L)
                .pageCount(10L)
                .uploadDate(null)
                .archivedDate(null)
                .creator(employee1)
                .build();


        //Создание списков
        List<FilePoolDto> filePoolDtoList = List.of(filePoolDto);
        List<AuthorDto> authorDtoList = List.of(authorDto);
        List<QuestionDto> questionDtoList = List.of(questionDto);
        List<EmployeeDto> employeeDtoList = List.of(employee1);

        // Создание обращения
        AppealDto appealDto = new AppealDto();

        appealDto.setNumber("+79811252688");
        appealDto.setAnnotation("annotation");
        appealDto.setSendingMethod(VIA_EMAIL);
        appealDto.setSigner(employeeDtoList);
        appealDto.setCreator(employee1);
        appealDto.setAddressee(employeeDtoList);
        appealDto.setAuthors(authorDtoList);
        appealDto.setQuestions(questionDtoList);
        appealDto.setNomenclature(nomenclatureDto);
        appealDto.setFile(filePoolDtoList);
        appealDto.setRegion(regionDto);

        return appealDto;
    }

    private EmployeeDto createEmployDTOTest() {


        AddressDto addressDto = new AddressDto(10L, "address", "street", "house",
                105050, "housing", "building", "city", "region", "country",
                "flat", "longitude", "latitude");

        DepartmentDto departmentDto = new DepartmentDto(1L, "shortName", "fullName",
                addressDto, "+79811252688", "EXT001", null,
                null, null);

        ThemeDto themeDto = new ThemeDto(1L, "name", null,
                null, "code", null);


        // Создание сотрудника
        EmployeeDto employee1 = EmployeeDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .middleName("Robert")
                .address(addressDto)
                .fioDative("John Doe")
                .fioNominative("John Doe")
                .fioGenitive("John Doe")
                .externalId("EXT001")
                .phone("+1-123-456-7890")
                .workPhone("+1-098-765-4321")
                .birthDate(LocalDate.now())
                .username("Johndoe")
                .department(departmentDto)
                .email("johndoe@mail.ru")
                .build();


        return employee1;
    }


}

