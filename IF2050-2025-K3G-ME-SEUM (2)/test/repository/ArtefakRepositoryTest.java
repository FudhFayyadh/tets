package test.repository;

import main.model.entities.Artefak;
import main.model.enums.StatusArtefak;
import main.repository.impl.ArtefakRepositoryImpl;
import main.repository.interfaces.IArtefakRepository;
import main.utils.DatabaseUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

// Unit test untuk ArtefakRepository
@DisplayName("Artefak Repository Tests")
public class ArtefakRepositoryTest {
    
    @Mock
    private DatabaseUtil databaseUtil;
    
    @Mock
    private Connection connection;
    
    @Mock
    private PreparedStatement preparedStatement;
    
    @Mock
    private ResultSet resultSet;
    
    @InjectMocks
    private ArtefakRepositoryImpl artefakRepository;
    
    private Artefak testArtefak;
    
    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        
        // Setup test data
        testArtefak = new Artefak("Keris Pusaka", "Keris pusaka dari Jawa", "Jawa Tengah", "Abad 15");
        testArtefak.setArtefakId(1L);
        testArtefak.setStatus(StatusArtefak.TERSEDIA);
        testArtefak.setTanggalRegistrasi(LocalDateTime.now());
        
        // Setup mock database connections
        when(databaseUtil.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
            .thenReturn(preparedStatement);
    }
    
    @Test
    @DisplayName("Should save new artefak successfully")
    void testSave_NewArtefak_Success() throws SQLException {
        // Given
        Artefak newArtefak = new Artefak("Batik Tulis", "Batik tulis tradisional", "Yogyakarta", "Abad 18");
        
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(2L);
        
        // When
        Artefak result = artefakRepository.save(newArtefak);
        
        // Then
        assertNotNull(result);
        assertEquals(2L, result.getArtefakId());
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).setString(1, newArtefak.getNamaArtefak());
    }
    
    @Test
    @DisplayName("Should update existing artefak successfully")
    void testSave_ExistingArtefak_Success() throws SQLException {
        // Given - artefak with existing ID
        // When
        Artefak result = artefakRepository.save(testArtefak);
        
        // Then
        assertNotNull(result);
        assertEquals(testArtefak.getArtefakId(), result.getArtefakId());
        verify(preparedStatement, times(1)).executeUpdate();
    }
    
    @Test
    @DisplayName("Should find artefak by ID successfully")
    void testFindById_Success() throws SQLException {
        // Given
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        setupResultSetMocking();
        
        // When
        Optional<Artefak> result = artefakRepository.findById(1L);
        
        // Then
        assertTrue(result.isPresent());
        assertEquals(testArtefak.getNamaArtefak(), result.get().getNamaArtefak());
        verify(preparedStatement, times(1)).setLong(1, 1L);
    }
    
    @Test
    @DisplayName("Should return empty when artefak not found")
    void testFindById_NotFound() throws SQLException {
        // Given
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        
        // When
        Optional<Artefak> result = artefakRepository.findById(999L);
        
        // Then
        assertFalse(result.isPresent());
        verify(preparedStatement, times(1)).setLong(1, 999L);
    }
    
    @Test
    @DisplayName("Should find all artefaks successfully")
    void testFindAll_Success() throws SQLException {
        // Given
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setupResultSetMocking();
        
        // When
        List<Artefak> result = artefakRepository.findAll();
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testArtefak.getNamaArtefak(), result.get(0).getNamaArtefak());
    }
    
    @Test
    @DisplayName("Should find artefaks by nama containing successfully")
    void testFindByNamaContaining_Success() throws SQLException {
        // Given
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setupResultSetMocking();
        
        // When
        List<Artefak> result = artefakRepository.findByNamaContaining("Keris");
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(preparedStatement, times(1)).setString(1, "%keris%");
    }
    
    @Test
    @DisplayName("Should find artefaks by asal daerah successfully")
    void testFindByAsalDaerah_Success() throws SQLException {
        // Given
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setupResultSetMocking();
        
        // When
        List<Artefak> result = artefakRepository.findByAsalDaerah("Jawa Tengah");
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(preparedStatement, times(1)).setString(1, "Jawa Tengah");
    }
    
    @Test
    @DisplayName("Should delete artefak by ID successfully")
    void testDeleteById_Success() throws SQLException {
        // Given
        when(preparedStatement.executeUpdate()).thenReturn(1);
        
        // When & Then
        assertDoesNotThrow(() -> artefakRepository.deleteById(1L));
        verify(preparedStatement, times(1)).setLong(1, 1L);
        verify(preparedStatement, times(1)).executeUpdate();
    }
    
    @Test
    @DisplayName("Should check if artefak exists by ID")
    void testExistsById_Success() throws SQLException {
        // Given
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);
        
        // When
        boolean result = artefakRepository.existsById(1L);
        
        // Then
        assertTrue(result);
        verify(preparedStatement, times(1)).setLong(1, 1L);
    }
    
    @Test
    @DisplayName("Should return false when artefak does not exist")
    void testExistsById_NotFound() throws SQLException {
        // Given
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(0);
        
        // When
        boolean result = artefakRepository.existsById(999L);
        
        // Then
        assertFalse(result);
    }
    
    @Test
    @DisplayName("Should count total artefaks successfully")
    void testCount_Success() throws SQLException {
        // Given
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(5L);
        
        // When
        long result = artefakRepository.count();
        
        // Then
        assertEquals(5L, result);
    }
    
    @Test
    @DisplayName("Should handle SQL exceptions gracefully")
    void testSave_SQLException() throws SQLException {
        // Given
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
            .thenThrow(new SQLException("Database connection failed"));
        
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            artefakRepository.save(testArtefak);
        });
        
        assertTrue(exception.getMessage().contains("Error saving artefak"));
    }
    
    @Test
    @DisplayName("Should handle connection close exceptions")
    void testFindById_ConnectionCloseException() throws SQLException {
        // Given
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        doThrow(new SQLException("Connection close failed")).when(connection).close();
        
        // When & Then
        assertDoesNotThrow(() -> artefakRepository.findById(1L));
    }
    
    // Helper method to setup ResultSet mocking
    private void setupResultSetMocking() throws SQLException {
        when(resultSet.getLong("artefak_id")).thenReturn(testArtefak.getArtefakId());
        when(resultSet.getString("nama_artefak")).thenReturn(testArtefak.getNamaArtefak());
        when(resultSet.getString("deskripsi_artefak")).thenReturn(testArtefak.getDeskripsiArtefak());
        when(resultSet.getString("status")).thenReturn(testArtefak.getStatus().name());
        when(resultSet.getString("gambar")).thenReturn(testArtefak.getGambar());
        when(resultSet.getString("asal_daerah")).thenReturn(testArtefak.getAsalDaerah());
        when(resultSet.getString("periode")).thenReturn(testArtefak.getPeriode());
        when(resultSet.getTimestamp("tanggal_registrasi"))
            .thenReturn(Timestamp.valueOf(testArtefak.getTanggalRegistrasi()));
    }
}