package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.model.Mahasiswa;

@Controller
public class MahasiswaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String index(Model model) {
        String sql = "SELECT * FROM mahasiswa";
        List<Mahasiswa> mahasiswa = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Mahasiswa.class));
        model.addAttribute("mahasiswa", mahasiswa);
        return "index";
    }
    @GetMapping("/add")
    public String add(Model model) {
        return "add";
    }

    @PostMapping("/add")
    public String add(Mahasiswa mahasiswa) {
        String sql = "INSERT INTO mahasiswa VALUES(?,?,?,?)";
        jdbcTemplate.update(sql, mahasiswa.getNim(),
                mahasiswa.getNama(), mahasiswa.getAngkatan(), mahasiswa.getGender());
        return "redirect:/";
    }
}