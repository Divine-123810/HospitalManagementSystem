package com.example.HospitalManagement.Service;

import com.example.HospitalManagement.Model.patients;
import com.example.HospitalManagement.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public patients savePatient(patients patient)
    {
        return patientRepository.save(patient);
    }

    public List<patients> getAllPatients() {

        return patientRepository.findAll();
    }

    public Optional<patients> getPatientById(Long id)
    {
        return patientRepository.findById(id);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}

