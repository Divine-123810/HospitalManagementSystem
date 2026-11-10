package com.example.HospitalManagement.Controller;

import com.example.HospitalManagement.Model.patients;
import com.example.HospitalManagement.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping
    public patients createPatient(@RequestBody patients patient) {
        return patientRepository.save(patient);
    }

    @GetMapping
    public List<patients> getAllPatients() {
        return patientRepository.findAll();
    }
}
