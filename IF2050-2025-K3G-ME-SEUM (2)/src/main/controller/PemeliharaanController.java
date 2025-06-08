package main.controller;

import main.model.dto.PemeliharaanDto;
import main.model.enums.StatusPemeliharaan;
import main.service.interfaces.IPemeliharaanService;
import java.util.List;

/**
 * Controller untuk mengelola pemeliharaan artefak museum
 */
public class PemeliharaanController {
    private final IPemeliharaanService pemeliharaanService;

    public PemeliharaanController(IPemeliharaanService pemeliharaanService) {
        this.pemeliharaanService = pemeliharaanService;
    }

    /**
     * Mengajukan permintaan pemeliharaan artefak baru
     * 
     * @param artefakId ID artefak yang akan dipelihara
     * @throws RuntimeException jika gagal mengajukan pemeliharaan
     */
    public void ajukanPemeliharaan(Long artefakId) {
        try {
            PemeliharaanDto pemeliharaan = new PemeliharaanDto();
            pemeliharaan.setArtefakId(artefakId);
            pemeliharaan.setStatus(StatusPemeliharaan.PENDING);
            pemeliharaanService.createPemeliharaan(pemeliharaan);
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengajukan pemeliharaan: " + e.getMessage());
        }
    }

    /**
     * Memperbarui status pemeliharaan
     * 
     * @param pemeliharaanId ID pemeliharaan
     * @param status Status baru
     * @throws RuntimeException jika gagal memperbarui status
     */
    public void updateStatusPemeliharaan(Long pemeliharaanId, StatusPemeliharaan status) {
        try {
            pemeliharaanService.updateStatus(pemeliharaanId, status);
        } catch (Exception e) {
            throw new RuntimeException("Gagal memperbarui status pemeliharaan: " + e.getMessage());
        }
    }

    /**
     * Mencatat tindakan pemeliharaan
     * 
     * @param pemeliharaanId ID pemeliharaan
     * @param catatan Catatan tindakan yang dilakukan
     * @throws RuntimeException jika gagal mencatat tindakan
     */
    public void catatTindakanPemeliharaan(Long pemeliharaanId, String catatan) {
        try {
            pemeliharaanService.addCatatan(pemeliharaanId, catatan);
        } catch (Exception e) {
            throw new RuntimeException("Gagal mencatat tindakan pemeliharaan: " + e.getMessage());
        }
    }

    /**
     * Mendapatkan daftar pemeliharaan berdasarkan status
     * 
     * @param status Status pemeliharaan yang dicari
     * @return List of PemeliharaanDto
     */
    public List<PemeliharaanDto> getDaftarPemeliharaan(StatusPemeliharaan status) {
        try {
            return pemeliharaanService.getPemeliharaanByStatus(status);
        } catch (Exception e) {
            throw new RuntimeException("Gagal mendapatkan daftar pemeliharaan: " + e.getMessage());
        }
    }

    /**
     * Mendapatkan detail pemeliharaan berdasarkan ID
     * 
     * @param pemeliharaanId ID pemeliharaan
     * @return PemeliharaanDto
     */
    public PemeliharaanDto getDetailPemeliharaan(Long pemeliharaanId) {
        try {
            return pemeliharaanService.getPemeliharaanById(pemeliharaanId);
        } catch (Exception e) {
            throw new RuntimeException("Gagal mendapatkan detail pemeliharaan: " + e.getMessage());
        }
    }
}
