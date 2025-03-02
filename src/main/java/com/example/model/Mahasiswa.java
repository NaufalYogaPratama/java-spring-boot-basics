package com.example.model;

/**
 * Kelas Mahasiswa merepresentasikan entitas Mahasiswa
 * dengan atribut NIM, Nama, Angkatan, dan Gender.
 */
public class Mahasiswa {

    // ===============================
    // Properties
    // ===============================
    private String nim;
    private String nama;
    private String angkatan;
    private String gender;

    // ===============================
    // Constructors
    // ===============================
    /**
     * Constructor default untuk kelas Mahasiswa.
     */
    public Mahasiswa() {
        // Default constructor
    }

    /**
     * Constructor dengan parameter untuk inisialisasi semua atribut.
     *
     * @param nim      Nomor Induk Mahasiswa
     * @param nama     Nama Mahasiswa
     * @param angkatan Tahun Angkatan Mahasiswa
     * @param gender   Jenis Kelamin Mahasiswa
     */
    public Mahasiswa(String nim, String nama, String angkatan, String gender) {
        this.nim = nim;
        this.nama = nama;
        this.angkatan = angkatan;
        this.gender = gender;
    }

    // ===============================
    // Getters and Setters
    // ===============================
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // ===============================
    // toString Method (Optional)
    // ===============================
    /**
     * Mengembalikan representasi string dari objek Mahasiswa.
     *
     * @return String representasi objek Mahasiswa
     */
    @Override
    public String toString() {
        return "Mahasiswa{" +
                "nim='" + nim + '\'' +
                ", nama='" + nama + '\'' +
                ", angkatan='" + angkatan + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}