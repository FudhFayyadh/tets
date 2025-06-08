package test.controller;

import main.controller.ManajemenArtefakController;
import main.model.dto.ArtefakDto;
import main.model.enums.StatusArtefak;
import main.service.interfaces.IArtefakService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

// Unit test untuk ManajemenArtefakController
@DisplayName("Manajemen Artefak Controller Tests")
public class ManajemenArtefakControllerTest {
    
    @Mock
    private IArtefakService artefakService;
    
    @InjectMocks
    private ManajemenArtefakController controller;
    
    private ArtefakDto testArtefakDto;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup test data
        testArtefakDto = new ArtefakDto();
        testArtefakDto.setArtefakId(1L);
        testArtefakDto.setNamaArtefak("Keris Pusaka");
        testArtefakDto.setDeskripsiArtefak("Keris pusaka dari Jawa");
        testArtefakDto.setAsalDaerah("Jawa Tengah");
        testArtefakDto.setPeriode("Abad 15");
        testArtefakDto.setStatus("Tersedia");
        testArtefakDto.setTanggalRegistrasi(LocalDateTime.now());
    }
    
    @Test
    @DisplayName("Should create artefak successfully")
    void testCreateArtefak_Success() {
        // Given
        when(artefakService.createArtefak(any(ArtefakDto.class))).thenReturn(testArtefakDto);
        
        // When
        ArtefakDto result = controller.createArtefak(testArtefakDto);
        
        // Then
        assertNotNull(result);
        assertEquals(testArtefakDto.getNamaArtefak(), result.getNamaArtefak());
        verify(artefakService, times(1)).createArtefak(testArtefakDto);
    }
    
    @Test
    @DisplayName("Should throw exception when create artefak fails")
    void testCreateArtefak_ServiceException() {
        // Given
        when(artefakService.createArtefak(any(ArtefakDto.class)))
            .thenThrow(new RuntimeException("Service error"));
        
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controller.createArtefak(testArtefakDto);
        });
        
        assertTrue(exception.getMessage().contains("Failed to create artefak"));
        verify(artefakService, times(1)).createArtefak(testArtefakDto);
    }
    
    @Test
    @DisplayName("Should update artefak successfully")
    void testUpdateArtefak_Success() {
        // Given
        when(artefakService.updateArtefak(eq(1L), any(ArtefakDto.class))).thenReturn(testArtefakDto);
        
        // When
        ArtefakDto result = controller.updateArtefak(1L, testArtefakDto);
        
        // Then
        assertNotNull(result);
        assertEquals(testArtefakDto.getNamaArtefak(), result.getNamaArtefak());
        verify(artefakService, times(1)).updateArtefak(1L, testArtefakDto);
    }
    
    @Test
    @DisplayName("Should delete artefak successfully")
    void testDeleteArtefak_Success() {
        // Given
        doNothing().when(artefakService).deleteArtefak(1L);
        
        // When & Then
        assertDoesNotThrow(() -> controller.deleteArtefak(1L));
        verify(artefakService, times(1)).deleteArtefak(1L);
    }
    
    @Test
    @DisplayName("Should get artefak by ID successfully")
    void testGetArtefakById_Success() {
        // Given
        when(artefakService.getArtefakById(1L)).thenReturn(testArtefakDto);
        
        // When
        ArtefakDto result = controller.getArtefakById(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(testArtefakDto.getArtefakId(), result.getArtefakId());
        verify(artefakService, times(1)).getArtefakById(1L);
    }
    
    @Test
    @DisplayName("Should get all artefaks successfully")
    void testGetAllArtefaks_Success() {
        // Given
        List<ArtefakDto> artefaks = Arrays.asList(testArtefakDto);
        when(artefakService.getAllArtefaks()).thenReturn(artefaks);
        
        // When
        List<ArtefakDto> result = controller.getAllArtefaks();
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(artefakService, times(1)).getAllArtefaks();
    }
    
    @Test
    @DisplayName("Should search artefaks successfully")
    void testSearchArtefaks_Success() {
        // Given
        List<ArtefakDto> artefaks = Arrays.asList(testArtefakDto);
        when(artefakService.searchArtefakByName("Keris")).thenReturn(artefaks);
        
        // When
        List<ArtefakDto> result = controller.searchArtefaks("Keris");
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(artefakService, times(1)).searchArtefakByName("Keris");
    }
    
    @Test
    @DisplayName("Should get artefaks by asal daerah successfully")
    void testGetArtefaksByAsalDaerah_Success() {
        // Given
        List<ArtefakDto> artefaks = Arrays.asList(testArtefakDto);
        when(artefakService.getArtefaksByAsalDaerah("Jawa Tengah")).thenReturn(artefaks);
        
        // When
        List<ArtefakDto> result = controller.getArtefaksByAsalDaerah("Jawa Tengah");
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(artefakService, times(1)).getArtefaksByAsalDaerah("Jawa Tengah");
    }
    
    @Test
    @DisplayName("Should update artefak status successfully")
    void testUpdateArtefakStatus_Success() {
        // Given
        when(artefakService.updateArtefakStatus(1L, StatusArtefak.DIPELIHARA)).thenReturn(testArtefakDto);
        
        // When
        ArtefakDto result = controller.updateArtefakStatus(1L, StatusArtefak.DIPELIHARA);
        
        // Then
        assertNotNull(result);
        verify(artefakService, times(1)).updateArtefakStatus(1L, StatusArtefak.DIPELIHARA);
    }
    
    @Test
    @DisplayName("Should validate artefak data successfully")
    void testValidateArtefakData_Success() {
        // Given
        when(artefakService.validateArtefakData(testArtefakDto)).thenReturn(true);
        
        // When
        boolean result = controller.validateArtefakData(testArtefakDto);
        
        // Then
        assertTrue(result);
        verify(artefakService, times(1)).validateArtefakData(testArtefakDto);
    }
    
    @Test
    @DisplayName("Should get total artefaks successfully")
    void testGetTotalArtefaks_Success() {
        // Given
        when(artefakService.getTotalArtefaks()).thenReturn(10L);
        
        // When
        long result = controller.getTotalArtefaks();
        
        // Then
        assertEquals(10L, result);
        verify(artefakService, times(1)).getTotalArtefaks();
    }
    
    @Test
    @DisplayName("Should handle service exceptions gracefully")
    void testGetArtefakById_ServiceException() {
        // Given
        when(artefakService.getArtefakById(999L))
            .thenThrow(new RuntimeException("Artefak not found"));
        
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controller.getArtefakById(999L);
        });
        
        assertTrue(exception.getMessage().contains("Failed to get artefak"));
        verify(artefakService, times(1)).getArtefakById(999L);
    }
}