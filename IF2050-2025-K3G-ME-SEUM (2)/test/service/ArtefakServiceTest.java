package test.service;

import main.model.dto.ArtefakDto;
import main.model.entities.Artefak;
import main.model.enums.StatusArtefak;
import main.repository.interfaces.IArtefakRepository;
import main.service.impl.ArtefakServiceImpl;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

// Unit test untuk ArtefakService
@DisplayName("Artefak Service Tests")
public class ArtefakServiceTest {
    
    @Mock
    private IArtefakRepository artefakRepository;
    
    @InjectMocks
    private ArtefakServiceImpl artefakService;
    
    private ArtefakDto testArtefakDto;
    private Artefak testArtefak;
    
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
        
        testArtefak = new Artefak("Keris Pusaka", "Keris pusaka dari Jawa", "Jawa Tengah", "Abad 15");
        testArtefak.setArtefakId(1L);
        testArtefak.setStatus(StatusArtefak.TERSEDIA);
    }
    
    @Test
    @DisplayName("Should create artefak successfully")
    void testCreateArtefak_Success() {
        // Given
        when(artefakRepository.save(any(Artefak.class))).thenReturn(testArtefak);
        
        // When
        ArtefakDto result = artefakService.createArtefak(testArtefakDto);
        
        // Then
        assertNotNull(result);
        assertEquals(testArtefakDto.getNamaArtefak(), result.getNamaArtefak());
        assertEquals(testArtefakDto.getDeskripsiArtefak(), result.getDeskripsiArtefak());
        verify(artefakRepository, times(1)).save(any(Artefak.class));
    }
    
    @Test
    @DisplayName("Should throw exception when creating artefak with invalid data")
    void testCreateArtefak_InvalidData() {
        // Given
        ArtefakDto invalidDto = new ArtefakDto();
        invalidDto.setNamaArtefak(""); // Empty name
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            artefakService.createArtefak(invalidDto);
        });
        
        verify(artefakRepository, never()).save(any(Artefak.class));
    }
    
    @Test
    @DisplayName("Should update artefak successfully")
    void testUpdateArtefak_Success() {
        // Given
        when(artefakRepository.existsById(1L)).thenReturn(true);
        when(artefakRepository.save(any(Artefak.class))).thenReturn(testArtefak);
        
        // When
        ArtefakDto result = artefakService.updateArtefak(1L, testArtefakDto);
        
        // Then
        assertNotNull(result);
        assertEquals(testArtefakDto.getNamaArtefak(), result.getNamaArtefak());
        verify(artefakRepository, times(1)).existsById(1L);
        verify(artefakRepository, times(1)).save(any(Artefak.class));
    }
    
    @Test
    @DisplayName("Should throw exception when updating non-existent artefak")
    void testUpdateArtefak_NotFound() {
        // Given
        when(artefakRepository.existsById(999L)).thenReturn(false);
        
        // When & Then
        assertThrows(RuntimeException.class, () -> {
            artefakService.updateArtefak(999L, testArtefakDto);
        });
        
        verify(artefakRepository, times(1)).existsById(999L);
        verify(artefakRepository, never()).save(any(Artefak.class));
    }
    
    @Test
    @DisplayName("Should delete artefak successfully")
    void testDeleteArtefak_Success() {
        // Given
        when(artefakRepository.existsById(1L)).thenReturn(true);
        doNothing().when(artefakRepository).deleteById(1L);
        
        // When
        assertDoesNotThrow(() -> artefakService.deleteArtefak(1L));
        
        // Then
        verify(artefakRepository, times(1)).existsById(1L);
        verify(artefakRepository, times(1)).deleteById(1L);
    }
    
    @Test
    @DisplayName("Should throw exception when deleting non-existent artefak")
    void testDeleteArtefak_NotFound() {
        // Given
        when(artefakRepository.existsById(999L)).thenReturn(false);
        
        // When & Then
        assertThrows(RuntimeException.class, () -> {
            artefakService.deleteArtefak(999L);
        });
        
        verify(artefakRepository, times(1)).existsById(999L);
        verify(artefakRepository, never()).deleteById(anyLong());
    }
    
    @Test
    @DisplayName("Should get artefak by ID successfully")
    void testGetArtefakById_Success() {
        // Given
        when(artefakRepository.findById(1L)).thenReturn(Optional.of(testArtefak));
        
        // When
        ArtefakDto result = artefakService.getArtefakById(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(testArtefak.getArtefakId(), result.getArtefakId());
        assertEquals(testArtefak.getNamaArtefak(), result.getNamaArtefak());
        verify(artefakRepository, times(1)).findById(1L);
    }
    
    @Test
    @DisplayName("Should throw exception when getting non-existent artefak")
    void testGetArtefakById_NotFound() {
        // Given
        when(artefakRepository.findById(999L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(RuntimeException.class, () -> {
            artefakService.getArtefakById(999L);
        });
        
        verify(artefakRepository, times(1)).findById(999L);
    }
    
    @Test
    @DisplayName("Should get all artefaks successfully")
    void testGetAllArtefaks_Success() {
        // Given
        List<Artefak> artefaks = Arrays.asList(testArtefak);
        when(artefakRepository.findAll()).thenReturn(artefaks);
        
        // When
        List<ArtefakDto> result = artefakService.getAllArtefaks();
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testArtefak.getNamaArtefak(), result.get(0).getNamaArtefak());
        verify(artefakRepository, times(1)).findAll();
    }
    
    @Test
    @DisplayName("Should search artefak by name successfully")
    void testSearchArtefakByName_Success() {
        // Given
        List<Artefak> artefaks = Arrays.asList(testArtefak);
        when(artefakRepository.findByNamaContaining("Keris")).thenReturn(artefaks);
        
        // When
        List<ArtefakDto> result = artefakService.searchArtefakByName("Keris");
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testArtefak.getNamaArtefak(), result.get(0).getNamaArtefak());
        verify(artefakRepository, times(1)).findByNamaContaining("Keris");
    }
    
    @Test
    @DisplayName("Should return all artefaks when search term is empty")
    void testSearchArtefakByName_EmptyTerm() {
        // Given
        List<Artefak> artefaks = Arrays.asList(testArtefak);
        when(artefakRepository.findAll()).thenReturn(artefaks);
        
        // When
        List<ArtefakDto> result = artefakService.searchArtefakByName("");
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(artefakRepository, times(1)).findAll();
        verify(artefakRepository, never()).findByNamaContaining(anyString());
    }
    
    @Test
    @DisplayName("Should get artefaks by asal daerah successfully")
    void testGetArtefaksByAsalDaerah_Success() {
        // Given
        List<Artefak> artefaks = Arrays.asList(testArtefak);
        when(artefakRepository.findByAsalDaerah("Jawa Tengah")).thenReturn(artefaks);
        
        // When
        List<ArtefakDto> result = artefakService.getArtefaksByAsalDaerah("Jawa Tengah");
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testArtefak.getAsalDaerah(), result.get(0).getAsalDaerah());
        verify(artefakRepository, times(1)).findByAsalDaerah("Jawa Tengah");
    }
    
    @Test
    @DisplayName("Should update artefak status successfully")
    void testUpdateArtefakStatus_Success() {
        // Given
        when(artefakRepository.findById(1L)).thenReturn(Optional.of(testArtefak));
        when(artefakRepository.save(any(Artefak.class))).thenReturn(testArtefak);
        
        // When
        ArtefakDto result = artefakService.updateArtefakStatus(1L, StatusArtefak.DIPELIHARA);
        
        // Then
        assertNotNull(result);
        verify(artefakRepository, times(1)).findById(1L);
        verify(artefakRepository, times(1)).save(any(Artefak.class));
    }
    
    @Test
    @DisplayName("Should throw exception when updating status of non-existent artefak")
    void testUpdateArtefakStatus_NotFound() {
        // Given
        when(artefakRepository.findById(999L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(RuntimeException.class, () -> {
            artefakService.updateArtefakStatus(999L, StatusArtefak.DIPELIHARA);
        });
        
        verify(artefakRepository, times(1)).findById(999L);
        verify(artefakRepository, never()).save(any(Artefak.class));
    }
    
    @Test
    @DisplayName("Should validate artefak data successfully")
    void testValidateArtefakData_ValidData() {
        // When
        boolean result = artefakService.validateArtefakData(testArtefakDto);
        
        // Then
        assertTrue(result);
    }
    
    @Test
    @DisplayName("Should return false for invalid artefak data")
    void testValidateArtefakData_InvalidData() {
        // Given
        ArtefakDto invalidDto = new ArtefakDto();
        invalidDto.setNamaArtefak(""); // Empty name
        invalidDto.setDeskripsiArtefak("Valid description");
        invalidDto.setAsalDaerah("Valid region");
        invalidDto.setPeriode("Valid period");
        
        // When
        boolean result = artefakService.validateArtefakData(invalidDto);
        
        // Then
        assertFalse(result);
    }
    
    @Test
    @DisplayName("Should get total artefaks count successfully")
    void testGetTotalArtefaks_Success() {
        // Given
        when(artefakRepository.count()).thenReturn(5L);
        
        // When
        long result = artefakService.getTotalArtefaks();
        
        // Then
        assertEquals(5L, result);
        verify(artefakRepository, times(1)).count();
    }
    
    @Test
    @DisplayName("Should handle null values in search")
    void testSearchArtefakByName_NullValue() {
        // Given
        List<Artefak> artefaks = Arrays.asList(testArtefak);
        when(artefakRepository.findAll()).thenReturn(artefaks);
        
        // When
        List<ArtefakDto> result = artefakService.searchArtefakByName(null);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(artefakRepository, times(1)).findAll();
        verify(artefakRepository, never()).findByNamaContaining(anyString());
    }
    
    @Test
    @DisplayName("Should handle repository exceptions gracefully")
    void testCreateArtefak_RepositoryException() {
        // Given
        when(artefakRepository.save(any(Artefak.class)))
            .thenThrow(new RuntimeException("Database error"));
        
        // When & Then
        assertThrows(RuntimeException.class, () -> {
            artefakService.createArtefak(testArtefakDto);
        });
        
        verify(artefakRepository, times(1)).save(any(Artefak.class));
    }
    
    @Test
    @DisplayName("Should return empty list when no artefaks found")
    void testGetAllArtefaks_EmptyResult() {
        // Given
        when(artefakRepository.findAll()).thenReturn(Arrays.asList());
        
        // When
        List<ArtefakDto> result = artefakService.getAllArtefaks();
        
        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(artefakRepository, times(1)).findAll();
    }
}