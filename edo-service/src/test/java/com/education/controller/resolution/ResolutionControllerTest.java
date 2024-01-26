package com.education.controller.resolution;

import com.education.service.resolution.ResolutionService;
import model.dto.ResolutionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ResolutionControllerTest {

    @Mock
    private ResolutionService resolutionService;

    @InjectMocks
    private ResolutionController resolutionController;

    /**
     * Тест контроллера, для проверки разархивирования резолюции.
     * Проверяет, что метод unarchiveResolution() контроллера ResolutionController возвращает код состояния 200 OK
     */
    @Test
    public void testUnarchiveResolutionSuccess() {
        Long id = 1L;
        ResolutionDto resolutionDto = new ResolutionDto();
        resolutionDto.setId(id);

        ResponseEntity<ResolutionDto> response = resolutionController.unarchiveResolution(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(resolutionService, times(1)).unarchiveResolution(id);
    }

    /**
     * Тест контроллера, для проверки исключения при разархивировании резолюции.
     * Проверяет, что метод unarchiveResolution() контроллера ResolutionController
     * возвращает код состояния 500 Internal Server Error
     */
    @Test
    public void testUnarchiveResolutionException() {
        Long id = 1L;
        ResolutionDto resolutionDto = new ResolutionDto();
        resolutionDto.setId(id);
        doThrow(new RuntimeException("Ошибка разархивации резолюции")).when(resolutionService).unarchiveResolution(id);

        ResponseEntity<ResolutionDto> response = null;
        try {
            response = resolutionController.unarchiveResolution(id);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(resolutionService, times(1)).unarchiveResolution(id);
    }

}