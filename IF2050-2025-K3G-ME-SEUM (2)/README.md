Collecting workspace informationBerdasarkan struktur workspace proyek Museum Nusantara yang Anda miliki, berikut penjelasan lengkap fungsi dari setiap folder dan file:

## **📁 Struktur Root Project**

### **📄 Files di Root**
- **README.md**: Dokumentasi utama proyek, berisi penjelasan aplikasi, cara instalasi, dan penggunaan
- **requirements.txt**: Daftar library/dependency yang dibutuhkan proyek (untuk Java biasanya menggunakan Maven/Gradle)

### **📁 doc/**: 
Folder dokumentasi yang berisi:
- Capture screen setiap tampilan layar aplikasi
- Dokumentasi teknis
- Manual pengguna
- Laporan dan presentasi

### **📁 img/**: 
Folder aset gambar yang berisi:
- Logo museum
- Icon aplikasi  
- Gambar artefak
- Background dan elemen UI lainnya

### **📁 test/**: 
Folder test case yang berisi:
- Unit test untuk setiap kelas
- Integration test
- Test utilities

---

## **📁 src/ - Source Code Utama**

### **📁 src/main/ - Kode Aplikasi Utama**

#### **📁 config/ - Konfigurasi Aplikasi**
- **AppConfig.java**: Konfigurasi umum aplikasi (port, environment, dll)
- **DatabaseConfig.java**: Konfigurasi koneksi database

#### **📁 controller/ - Layer Controller (MVC Pattern)**
- **AuthController.java**: Handle autentikasi dan otorisasi user
- **FeedbackController.java**: Handle operasi feedback pengunjung
- **ManajemenArtefakController.java**: Handle CRUD artefak
- **PameranController.java**: Handle manajemen pameran
- **PemeliharaanController.java**: Handle permintaan dan tracking pemeliharaan
- **TiketController.java**: Handle pembelian dan validasi tiket

#### **📁 model/ - Data Model**

##### **📁 entities/ - Entity Classes**
- **Artefak.java**: Model data artefak museum
- **Pameran.java**: Model data pameran/exhibition
- **Tiket.java**: Model data tiket pengunjung
- **Feedback.java**: Model data feedback pengunjung
- **Pemeliharaan.java**: Model data pemeliharaan artefak
- **User.java**: Model data pengguna (Curator, Cleaner, Customer)

##### **📁 enums/ - Enumerasi**
- **StatusArtefak.java**: Status artefak (Tersedia, Dipelihara, Rusak)
- **StatusPemeliharaan.java**: Status pemeliharaan (Pending, In Progress, Done)
- **StatusTiket.java**: Status tiket (Valid, Used, Expired)
- **UserRole.java**: Role pengguna (CURATOR, CLEANER, CUSTOMER)

##### **📁 dto/ - Data Transfer Objects**
- **ArtefakDto.java**: DTO untuk transfer data artefak antar layer
- **PameranDto.java**: DTO untuk transfer data pameran
- **TiketDto.java**: DTO untuk transfer data tiket
- **FeedbackDto.java**: DTO untuk transfer data feedback

#### **📁 service/ - Business Logic Layer**

##### **📁 interfaces/ - Service Interfaces**
- **IArtefakService.java**: Interface untuk business logic artefak
- **IPameranService.java**: Interface untuk business logic pameran
- **ITiketService.java**: Interface untuk business logic tiket
- **IFeedbackService.java**: Interface untuk business logic feedback
- **IPemeliharaanService.java**: Interface untuk business logic pemeliharaan
- **IUserService.java**: Interface untuk business logic user

##### **📁 impl/ - Service Implementation**
- Implementasi dari semua interface service di atas
- Berisi logic bisnis utama aplikasi
- Validasi data dan business rules

#### **📁 repository/ - Data Access Layer**

##### **📁 interfaces/ - Repository Interfaces**
- **IArtefakRepository.java**: Interface untuk akses data artefak
- **IPameranRepository.java**: Interface untuk akses data pameran
- **ITiketRepository.java**: Interface untuk akses data tiket
- **IFeedbackRepository.java**: Interface untuk akses data feedback
- **IPemeliharaanRepository.java**: Interface untuk akses data pemeliharaan
- **IUserRepository.java**: Interface untuk akses data user

##### **📁 impl/ - Repository Implementation**
- **ArtefakRepositoryImpl.java**: Implementasi CRUD artefak ke database
- Dan implementasi repository lainnya
- Handle koneksi database dan query SQL

#### **📁 ui/ - User Interface Layer**

##### **📁 interfaces/ - UI Interfaces**
- **ManajemenArtefakInterface.java**: Interface untuk UI manajemen artefak
- **PameranInterface.java**: Interface untuk UI pameran
- **TiketInterface.java**: Interface untuk UI tiket
- **FeedbackInterface.java**: Interface untuk UI feedback
- **PemeliharaanInterface.java**: Interface untuk UI pemeliharaan

##### **📁 components/ - Komponen UI Reusable**

###### **📁 common/ - Komponen Umum**
- **NavigationBar.java**: Komponen navigation bar
- **SideBar.java**: Komponen sidebar untuk dashboard
- **Footer.java**: Komponen footer
- **LoadingDialog.java**: Dialog loading

###### **📁 forms/ - Komponen Form**
- **ArtefakForm.java**: Form input/edit artefak
- **PameranForm.java**: Form input/edit pameran
- **TiketForm.java**: Form pembelian tiket
- **FeedbackForm.java**: Form feedback pengunjung
- **LoginForm.java**: Form login

###### **📁 tables/ - Komponen Tabel**
- **ArtefakTable.java**: Tabel daftar artefak
- **PameranTable.java**: Tabel daftar pameran
- **TiketTable.java**: Tabel daftar tiket
- **FeedbackTable.java**: Tabel daftar feedback
- **PemeliharaanTable.java**: Tabel daftar pemeliharaan

##### **📁 views/ - Halaman Aplikasi**

###### **📁 customer/ - Halaman untuk Pengunjung**
- **HomePage.java**: Halaman beranda museum
- **AboutUsPage.java**: Halaman tentang museum
- **CollectionPage.java**: Halaman koleksi artefak
- **EventPage.java**: Halaman daftar pameran/event
- **TiketPage.java**: Halaman tiket dan feedback
- **YourOrdersPage.java**: Halaman riwayat pembelian tiket

###### **📁 curator/ - Halaman untuk Kurator**
- **CuratorDashboard.java**: Dashboard utama kurator
- **ArtefakListPage.java**: Halaman manajemen daftar artefak
- **EventArtefakPage.java**: Halaman manajemen event/pameran
- **MaintenanceListPage.java**: Halaman daftar permintaan pemeliharaan
- **FeedbackListPage.java**: Halaman daftar feedback pengunjung

###### **📁 cleaner/ - Halaman untuk Staff Pemeliharaan**
- **CleanerDashboard.java**: Dashboard staff pemeliharaan
- **MaintenanceTaskPage.java**: Halaman tugas pemeliharaan

###### **📁 auth/ - Halaman Autentikasi**
- **LoginPage.java**: Halaman login
- **RegisterPage.java**: Halaman registrasi

#### **📁 utils/ - Utility Classes**
- **DatabaseUtil.java**: Utility untuk operasi database
- **DateUtil.java**: Utility untuk manipulasi tanggal
- **QRCodeGenerator.java**: Utility untuk generate QR code tiket
- **ValidationUtil.java**: Utility untuk validasi data
- **EmailUtil.java**: Utility untuk mengirim email
- **FileUtil.java**: Utility untuk operasi file

#### **📁 exceptions/ - Custom Exceptions**
- Custom exception classes untuk error handling yang spesifik

---

## **📁 src/resources/ - Resource Files**

- **application.properties**: File konfigurasi aplikasi
- **📁 database/**: Script SQL untuk schema dan data awal
- **📁 config/**: File konfigurasi tambahan (log4j2.xml)
- **📁 images/**: Gambar yang digunakan dalam aplikasi

---

## **🎯 Arsitektur dan Design Pattern**

Struktur ini mengimplementasikan:

1. **MVC Pattern**: Model-View-Controller separation
2. **Repository Pattern**: Abstraksi data access layer
3. **Service Layer Pattern**: Business logic separation
4. **Dependency Injection**: Loose coupling antar komponen
5. **DTO Pattern**: Data transfer antar layer

Struktur ini mendukung **3 role user** sesuai requirement:
- **Customer**: Pengunjung museum (membeli tiket, beri feedback)
- **Curator**: Kurator (kelola artefak, pameran, lihat feedback)
- **Cleaner**: Staff pemeliharaan (kelola tugas maintenance)