package com.example.HospitalManagement.Service;

import com.example.HospitalManagement.Model.Doctors;
import com.example.HospitalManagement.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctors saveDoctor(Doctors doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctors> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctors> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}