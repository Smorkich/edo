package com.education.service.resolution.impl;

import com.education.feign.ResolutionFeignClient;
import com.education.service.appeal.AppealService;
import com.education.service.emloyee.EmployeeService;
import model.dto.AppealDto;
import model.dto.QuestionDto;
import model.dto.ResolutionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResolutionServiceImplTest {
    @Mock
    private AppealService appealService;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private ResolutionFeignClient resolutionFeignClient;
    @InjectMocks
    private ResolutionServiceImpl resolutionService;

    @Test
    void save_withDraft_isNull() {

        ResolutionDto resolutionDto = new ResolutionDto();
        resolutionDto.setIsDraft(null);
        when(resolutionFeignClient.saveResolution(resolutionDto)).thenReturn(resolutionDto);

        ResolutionDto result = resolutionService.save(resolutionDto);

        assertNotNull(result);
        assertNotNull(result.getCreationDate());
        assertNotNull(result.getLastActionDate());
        assertTrue(result.getIsDraft());

        verify(resolutionFeignClient, times(1)).saveResolution(resolutionDto);
    }

    @Test
    void save_withDraft_isFalse() {

        ResolutionDto resolutionDto = new ResolutionDto();
        resolutionDto.setIsDraft(false);
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(1L);
            resolutionDto.setQuestion(questionDto);


        AppealDto appealDto = new AppealDto();
        when(appealService.findAppealByQuestionsId(anyLong())).thenReturn(appealDto);
        when(resolutionFeignClient.saveResolution(resolutionDto)).thenReturn(resolutionDto);


        ResolutionDto result = resolutionService.save(resolutionDto);

        assertNotNull(result);
        assertNotNull(result.getCreationDate());
        assertNotNull(result.getLastActionDate());
        assertFalse(result.getIsDraft());

        verify(resolutionFeignClient, times(1)).saveResolution(resolutionDto);
        verify(appealService, times(1)).save(appealDto);
    }
}
