package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.entities.Patient;
import com.uisrael.medical_service.repositories.IPatientRepository;
import com.uisrael.medical_service.services.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    private IPatientRepository patientRepository;

    @Override
    public List<Patient> getAll() {
        return patientRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, Patient patient) {
        Patient patientDb = patientRepository.findById(id).orElse(null);
        if(patient != null)
        {
            patientDb.setName(patient.getName());
            patientDb.setLastName(patient.getLastName());
            patientDb.setDepartment(patient.getDepartment());
            patientDb.setGender(patient.getGender());
            patientDb.setSize(patient.getSize());
            patientDb.setWeight(patient.getWeight());
            patientDb.setYear(patient.getYear());
            patientDb.setObservation(patient.getObservation());
            patientDb.setStatus(patient.getStatus());
            return patientRepository.save(patientDb);
        }
        else{
            return null;
        }

    }

    @Override
    public boolean deletePatient(Long id) {
        Patient patientDb = patientRepository.findById(id).orElse(null);
        if(patientDb != null)
        {
            patientDb.setStatus(0);
            patientRepository.save(patientDb);
            return true;
        }
        else{
            return false;
        }
    }


    @Override
    public Long countPatient() {
        return patientRepository.count();
    }
}
