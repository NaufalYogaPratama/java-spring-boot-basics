package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.IrsDetail;
import com.example.model.Mahasiswa;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MahasiswaController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Halaman Utama dengan Fitur Search
    @GetMapping("/")
    public String index(@RequestParam(required = false) String keyword, Model model) {
        String sql = "SELECT * FROM mahasiswa";
        if (keyword != null && !keyword.isEmpty()) {
            sql += " WHERE LOWER(nama) LIKE LOWER(?) OR LOWER(nim) LIKE LOWER(?)";
            String searchKeyword = "%" + keyword + "%";
            List<Mahasiswa> mahasiswa = jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(Mahasiswa.class), searchKeyword, searchKeyword);
            model.addAttribute("mahasiswa", mahasiswa);
            model.addAttribute("keyword", keyword); // Kirim keyword kembali ke view
        } else {
            List<Mahasiswa> mahasiswa = jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(Mahasiswa.class));
            model.addAttribute("mahasiswa", mahasiswa);
        }
        return "index";
    }

    // Tambah Data
    @GetMapping("/add")
    public String add(Model model) {
        return "add";
    }

    @PostMapping("/add")
    public String add(Mahasiswa mahasiswa, RedirectAttributes redirectAttributes) {
        String sql = "INSERT INTO mahasiswa VALUES(?,?,?,?)";
        jdbcTemplate.update(sql, mahasiswa.getNim(),
                mahasiswa.getNama(), mahasiswa.getAngkatan(), mahasiswa.getGender());
        redirectAttributes.addFlashAttribute("successMessage", "Data berhasil ditambahkan.");
        return "redirect:/";
    }
    // Edit Data
    @GetMapping("/edit/{nim}")
    public String edit(@PathVariable("nim") String nim, Model model) {
        String sql = "SELECT * FROM mahasiswa WHERE nim = ?";
        Mahasiswa mahasiswa = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Mahasiswa.class), nim);
        model.addAttribute("mahasiswa", mahasiswa);
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(Mahasiswa mahasiswa, RedirectAttributes redirectAttributes) {
        try {
            String sql = "UPDATE mahasiswa SET nama = ?, angkatan = ?, gender = ? WHERE nim = ?";
            jdbcTemplate.update(sql, mahasiswa.getNama(), mahasiswa.getAngkatan(), mahasiswa.getGender(),
                    mahasiswa.getNim());
            redirectAttributes.addFlashAttribute("successMessage", "Data berhasil diperbarui.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal memperbarui data: " + e.getMessage());
        }
        return "redirect:/";
    }

    // Hapus Data
    @GetMapping("/delete/{nim}")
    public String delete(@PathVariable("nim") String nim) {
        String sql = "DELETE FROM mahasiswa WHERE nim = ?";
        jdbcTemplate.update(sql, nim);
        return "redirect:/";
    }


    // Detail Mahasiswa
    @GetMapping("/detail/{nim}")
    public String detail(@PathVariable("nim") String nim, Model model) {
        // Ambil data mahasiswa
        String sqlMahasiswa = "SELECT * FROM mahasiswa WHERE nim = ?";
        Mahasiswa mahasiswa = jdbcTemplate.queryForObject(sqlMahasiswa,
                BeanPropertyRowMapper.newInstance(Mahasiswa.class), nim);

        // Ambil data IRS dan mata kuliah yang diambil oleh mahasiswa
        String sqlIrs = "SELECT IRS.IRS_ID, IRS.STATUS, MATA_KULIAH.MATKUL_NAMA, MATA_KULIAH.SKS, MATA_KULIAH.HARI " +
                        "FROM IRS " +
                        "JOIN MATA_KULIAH ON IRS.MATKUL_ID = MATA_KULIAH.MATKUL_ID " +
                        "WHERE IRS.NIM = ?";
        List<IrsDetail> irsList = jdbcTemplate.query(sqlIrs, new Object[]{nim},
                BeanPropertyRowMapper.newInstance(IrsDetail.class));

        model.addAttribute("mahasiswa", mahasiswa);
        model.addAttribute("irsList", irsList);
        return "detail"; // Halaman detail.html
    }
}
