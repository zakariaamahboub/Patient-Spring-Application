package com.example.tp2.web;

import com.example.tp2.entities.Patient;
import com.example.tp2.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PatientController {
    @Autowired
private PatientRepository patientRepository;

    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/patients")
    public String list(Model model,
                       @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "4") int size,
                       @RequestParam(name = "keyword",defaultValue = "")String mc
                       )
    {

        Page <Patient> patients = patientRepository.findByNomContains(mc,PageRequest.of(page, size));
        model.addAttribute("patients",patients.getContent());
        model.addAttribute("pages", new int[patients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        model.addAttribute("keyword",mc);
        return "patients";
    }
    @GetMapping("/deletePatient")
    public String delete(Long id ,String keyword ,int page , int size){
        patientRepository.deleteById(id);
        return "redirect:/patients?page="+page+"&size="+size+"&keyword="+keyword;
    }
    @GetMapping("/formPatient")
    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        model.addAttribute("mode","new");
        return "formPatient";
    }
    @PostMapping("/savePatient")
    public String savePatient(Model model,@Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "formPatient";}
        patientRepository.save(patient);
        model.addAttribute("patient",patient);
        return "confirmation";
    }
    @GetMapping("/editPatient")
    public String editPatient(Model model , Long id){
        Patient p = patientRepository.findById(id).get();
        model.addAttribute("patient",p);
        model.addAttribute("mode","edit");
        return "formPatient";
    }
}
