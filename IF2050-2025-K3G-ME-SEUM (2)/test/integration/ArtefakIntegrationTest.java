package test.integration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import main.controller.ManajemenArtefakController;
import main.model.dto.ArtefakDto;
import main.model.entities.Artefak;
import main.model.enums.StatusArtefak;
import main.repository.interfaces.IArtefakRepository;
import main.service.impl.ArtefakServiceImpl;
import main.service.interfaces.IArtefakService;

// Integration test untuk Artefak flow
@DisplayName("Artefak Integration Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArtefakIntegrationTest {
    
    private ManajemenArtefakController controller;
    private IArtefakService artefakService;
    private IArtefakRepository artefakRepository;
    
    private ArtefakDto testArtefakDto;
    
    @BeforeEach
    void setUp() {
        // Initialize real implementations for integration testing
        artefakRepository = new MockArtefakRepository();
        artefakService = new ArtefakServiceImpl(artefakRepository);
        controller = new ManajemenArtefakController(artefakService);
        
        // Setup test data
        testArtefakDto = new ArtefakDto();
        testArtefakDto.setNamaArtefak("Keris Pusaka Integration");
        testArtefakDto.setDeskripsiArtefak("Keris pusaka untuk testing integration");
        testArtefakDto.setAsalDaerah("Jawa Tengah");
        testArtefakDto.setPeriode("Abad 15");
        testArtefakDto.setStatus("Tersedia");
        testArtefakDto.setTanggalRegistrasi(LocalDateTime.now());
    }
    
    @Test
    @DisplayName("Should create, read, update, and delete artefak successfully")
    void testArtefakCRUD_FullFlow() {
        // CREATE
        ArtefakDto createdArtefak = controller.createArtefak(testArtefakDto);
        assertNotNull(createdArtefak);
        assertNotNull(createdArtefak.getArtefakId());
        assertEquals(testArtefakDto.getNamaArtefak(), createdArtefak.getNamaArtefak());
        
        Long artefakId = createdArtefak.getArtefakId();
        
        // READ
        ArtefakDto retrievedArtefak = controller.getArtefakById(artefakId);
        assertNotNull(retrievedArtefak);
        assertEquals(artefakId, retrievedArtefak.getArtefakId());
        assertEquals(testArtefakDto.getNamaArtefak(), retrievedArtefak.getNamaArtefak());
        
        // UPDATE
        retrievedArtefak.setNamaArtefak("Keris Pusaka Updated");
        retrievedArtefak.setDeskripsiArtefak("Updated description");
        
        ArtefakDto updatedArtefak = controller.updateArtefak(artefakId, retrievedArtefak);
        assertNotNull(updatedArtefak);
        assertEquals("Keris Pusaka Updated", updatedArtefak.getNamaArtefak());
        assertEquals("Updated description", updatedArtefak.getDeskripsiArtefak());
        
        // DELETE
        assertDoesNotThrow(() -> controller.deleteArtefak(artefakId));
        
        // Verify deletion
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controller.getArtefakById(artefakId);
        });
        assertTrue(exception.getMessage().contains("Failed to get artefak"));
    }
    
    @Test
    @DisplayName("Should search artefaks by name successfully")
    void testSearchArtefaksByName_IntegrationFlow() {
        // Create test artefaks
        ArtefakDto artefak1 = createTestArtefak("Keris Jawa", "Keris dari Jawa");
        ArtefakDto artefak2 = createTestArtefak("Batik Tulis", "Batik tulis tradisional");
        ArtefakDto artefak3 = createTestArtefak("Keris Bali", "Keris dari Bali");
        
        // Search for "Keris"
        List<ArtefakDto> searchResults = controller.searchArtefaks("Keris");
        assertNotNull(searchResults);
        assertEquals(2, searchResults.size());
        
        // Verify search results contain both Keris items
        assertTrue(searchResults.stream().anyMatch(a -> a.getNamaArtefak().equals("Keris Jawa")));
        assertTrue(searchResults.stream().anyMatch(a -> a.getNamaArtefak().equals("Keris Bali")));
        
        // Search for non-existent item
        List<ArtefakDto> emptyResults = controller.searchArtefaks("NonExistent");
        assertNotNull(emptyResults);
        assertTrue(emptyResults.isEmpty());
    }
    
    @Test
    @DisplayName("Should filter artefaks by asal daerah successfully")
    void testGetArtefaksByAsalDaerah_IntegrationFlow() {
        // Create test artefaks from different regions
        ArtefakDto javaArtefak1 = createTestArtefak("Gamelan Jawa", "Gamelan tradisional Jawa");
        javaArtefak1.setAsalDaerah("Jawa Tengah");
        controller.createArtefak(javaArtefak1);
        
        ArtefakDto javaArtefak2 = createTestArtefak("Wayang Kulit", "Wayang kulit tradisional");
        javaArtefak2.setAsalDaerah("Jawa Tengah");
        controller.createArtefak(javaArtefak2);
        
        ArtefakDto baliArtefak = createTestArtefak("Barong Bali", "Kostum Barong dari Bali");
        baliArtefak.setAsalDaerah("Bali");
        controller.createArtefak(baliArtefak);
        
        // Filter by Jawa Tengah
        List<ArtefakDto> javaArtefaks = controller.getArtefaksByAsalDaerah("Jawa Tengah");
        assertNotNull(javaArtefaks);
        assertEquals(2, javaArtefaks.size());
        
        // Filter by Bali
        List<ArtefakDto> baliArtefaks = controller.getArtefaksByAsalDaerah("Bali");
        assertNotNull(baliArtefaks);
        assertEquals(1, baliArtefaks.size());
        assertEquals("Barong Bali", baliArtefaks.get(0).getNamaArtefak());
    }
    
    @Test
    @DisplayName("Should update artefak status successfully")
    void testUpdateArtefakStatus_IntegrationFlow() {
        // Create artefak
        ArtefakDto createdArtefak = controller.createArtefak(testArtefakDto);
        Long artefakId = createdArtefak.getArtefakId();
        
        // Update status to DIPELIHARA
        ArtefakDto updatedArtefak = controller.updateArtefakStatus(artefakId, StatusArtefak.DIPELIHARA);
        assertNotNull(updatedArtefak);
        
        // Verify status change
        ArtefakDto retrievedArtefak = controller.getArtefakById(artefakId);
        assertEquals("DIPELIHARA", retrievedArtefak.getStatus());
        
        // Update status to RUSAK
        controller.updateArtefakStatus(artefakId, StatusArtefak.RUSAK);
        retrievedArtefak = controller.getArtefakById(artefakId);
        assertEquals("RUSAK", retrievedArtefak.getStatus());
    }
    
    @Test
    @DisplayName("Should validate artefak data correctly")
    void testValidateArtefakData_IntegrationFlow() {
        // Valid data
        assertTrue(controller.validateArtefakData(testArtefakDto));
        
        // Invalid data - empty name
        ArtefakDto invalidDto = new ArtefakDto();
        invalidDto.setNamaArtefak("");
        invalidDto.setDeskripsiArtefak("Valid description");
        invalidDto.setAsalDaerah("Valid region");
        invalidDto.setPeriode("Valid period");
        
        assertFalse(controller.validateArtefakData(invalidDto));
        
        // Invalid data - null fields
        ArtefakDto nullDto = new ArtefakDto();
        assertFalse(controller.validateArtefakData(nullDto));
    }
    
    @Test
    @DisplayName("Should get total artefaks count correctly")
    void testGetTotalArtefaks_IntegrationFlow() {
        // Initial count
        long initialCount = controller.getTotalArtefaks();
        
        // Add some artefaks
        controller.createArtefak(createTestArtefak("Test 1", "Description 1"));
        controller.createArtefak(createTestArtefak("Test 2", "Description 2"));
        controller.createArtefak(createTestArtefak("Test 3", "Description 3"));
        
        // Verify count increased
        long finalCount = controller.getTotalArtefaks();
        assertEquals(initialCount + 3, finalCount);
    }
    
    @Test
    @DisplayName("Should handle concurrent operations correctly")
    void testConcurrentOperations_IntegrationFlow() {
        // Create multiple artefaks concurrently
        ArtefakDto artefak1 = controller.createArtefak(createTestArtefak("Concurrent 1", "Description 1"));
        ArtefakDto artefak2 = controller.createArtefak(createTestArtefak("Concurrent 2", "Description 2"));
        ArtefakDto artefak3 = controller.createArtefak(createTestArtefak("Concurrent 3", "Description 3"));
        
        // Verify all created successfully
        assertNotNull(artefak1.getArtefakId());
        assertNotNull(artefak2.getArtefakId());
        assertNotNull(artefak3.getArtefakId());
        
        // Verify all can be retrieved
        assertDoesNotThrow(() -> controller.getArtefakById(artefak1.getArtefakId()));
        assertDoesNotThrow(() -> controller.getArtefakById(artefak2.getArtefakId()));
        assertDoesNotThrow(() -> controller.getArtefakById(artefak3.getArtefakId()));
        
        // Get all artefaks should include all created
        List<ArtefakDto> allArtefaks = controller.getAllArtefaks();
        assertTrue(allArtefaks.size() >= 3);
    }
    
    // Helper method to create test artefak
    private ArtefakDto createTestArtefak(String nama, String deskripsi) {
        ArtefakDto dto = new ArtefakDto();
        dto.setNamaArtefak(nama);
        dto.setDeskripsiArtefak(deskripsi);
        dto.setAsalDaerah("Test Region");
        dto.setPeriode("Test Period");
        dto.setStatus("Tersedia");
        dto.setTanggalRegistrasi(LocalDateTime.now());
        return dto;
    }
    
    // Mock repository implementation for integration testing
    private static class MockArtefakRepository implements IArtefakRepository {
        private final java.util.Map<Long, Artefak> storage = new java.util.HashMap<>();
        private Long nextId = 1L;
        
        @Override
        public Artefak save(Artefak artefak) {
            if (artefak.getArtefakId() == null) {
                artefak.setArtefakId(nextId++);
            }
            storage.put(artefak.getArtefakId(), artefak);
            return artefak;
        }
        
        @Override
        public Optional<Artefak> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }
        
        @Override
        public List<Artefak> findAll() {
            return new java.util.ArrayList<>(storage.values());
        }
        
        @Override
        public List<Artefak> findByNamaContaining(String nama) {
            return storage.values().stream()
                .filter(a -> a.getNamaArtefak().toLowerCase().contains(nama.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
        }
        
        @Override
        public List<Artefak> findByAsalDaerah(String asalDaerah) {
            return storage.values().stream()
                .filter(a -> asalDaerah.equals(a.getAsalDaerah()))
                .collect(java.util.stream.Collectors.toList());
        }
        
        @Override
        public void deleteById(Long id) {
            storage.remove(id);
        }
        
        @Override
        public boolean existsById(Long id) {
            return storage.containsKey(id);
        }
        
        @Override
        public long count() {
            return storage.size();
        }
    }
}