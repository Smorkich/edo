package com.education.service.resolution;

import com.education.feign.ResolutionFeignClient;
import com.education.service.appeal.AppealService;
import com.education.service.resolution.impl.ResolutionServiceImpl;
import model.dto.AppealDto;
import model.dto.ResolutionDto;
import model.enum_.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ResolutionServiceImplTest {
    @Mock
    private AppealService appealService;
    @Mock
    private ResolutionFeignClient resolutionFeignClient;
    @InjectMocks
    private ResolutionServiceImpl resolutionService;

    /**
     * Тест метода, для проверки разархивирования резолюции.
     * Проверяет, что резолюция успешно разархивирована и сохранена.
     * Меняет статус обращения на UNDER_CONSIDERATION, если статус резолюции isDraft = false
     */
    @Test
    void testUnarchiveResolution() {
        Long id = 1L;
        ResolutionDto resolutionDto = new ResolutionDto();
        resolutionDto.setId(id);
        resolutionDto.setIsDraft(false);

        AppealDto appealDto = new AppealDto();
        appealDto.setId(id);

        when(resolutionFeignClient.findById(id)).thenReturn(resolutionDto);
        when(appealService.findById(id)).thenReturn(appealDto);
        when(resolutionFeignClient.findAll(id)).thenReturn(Collections.singletonList(resolutionDto));

        resolutionService.unarchiveResolution(id);

        verify(resolutionFeignClient).findById(id);
        verify(resolutionFeignClient).saveResolution(resolutionDto);
        verify(appealService).findById(id);
        verify(appealService).save(appealDto);
        verify(resolutionFeignClient).findAll(id);

        assertNull(resolutionDto.getArchivedDate());
        assertEquals(Status.UNDER_CONSIDERATION, appealDto.getAppealsStatus());
        verify(resolutionFeignClient, times(1)).saveResolution(resolutionDto);
    }

}
