package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.model.Mahasiswa;
import org.springframework.web.bind.annotation.PathVariable;

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

    // Tambah Data (Tidak Berubah)
    @GetMapping("/add")
    public String add(Model model) {
        return "add";
    }

    // Edit Data (Tidak Berubah)
    @GetMapping("/edit/{nim}")
    public String edit(@PathVariable("nim") String nim, Model model) {
        String sql = "SELECT * FROM mahasiswa WHERE nim = ?";
        Mahasiswa mahasiswa = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Mahasiswa.class), nim);
        model.addAttribute("mahasiswa", mahasiswa);
        return "edit";
    }
}