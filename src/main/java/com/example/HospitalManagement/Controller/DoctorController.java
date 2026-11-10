package com.example.HospitalManagement.Controller;

import com.example.HospitalManagement.Model.Doctors;
import com.example.HospitalManagement.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping
    public Doctors createDoctor(@RequestBody Doctors doctor) {
        return doctorRepository.save(doctor);
    }

    @GetMapping
    public List<Doctors> getAllDoctors() {
        return doctorRepository.findAll();
    }
}