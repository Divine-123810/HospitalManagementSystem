package com.example.HospitalManagement.Repository;

import com.example.HospitalManagement.Model.patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<patients, Long> {
}