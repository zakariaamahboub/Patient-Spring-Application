package com.example.tp2;

import com.example.tp2.entities.Patient;
import com.example.tp2.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Tp2Application implements CommandLineRunner {
    @Autowired
private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(Tp2Application.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
patientRepository.save(new Patient(null,"Zakariaa",new Date(),321,false));
patientRepository.save(new Patient(null,"Yassine",new Date(),645,false));
patientRepository.save(new Patient(null,"Mohammed",new Date(),378,true));
patientRepository.findAll().forEach(p->{
    System.out.println(p.toString());
});
    }
}
