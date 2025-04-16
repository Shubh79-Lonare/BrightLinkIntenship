package com.example.form.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.form.entity.Form;
import com.example.form.repository.FormRepository;

@Controller
public class FormController {

    @Autowired
    private FormRepository formRepository;

    @GetMapping("/displayform")
    public String displayForm(Model model) {
        model.addAttribute("form", new Form());
        return "index";
    }

    @PostMapping("/submit")
    public String saveFormData(
            @RequestParam("name") String name,
            @RequestParam("mobile") String mobile,
            @RequestParam("email") String email,
            @RequestParam("education") String education,
            @RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
            @RequestParam("gender") String gender,
            @RequestParam("address") String address,
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) throws IOException {
        Form form = new Form();
        form.setName(name);
        form.setMobile(mobile);
        form.setEmail(email);
        form.setEducation(education);
        form.setDob(dob);
        form.setGender(gender);
        form.setAddress(address);

        if (photo != null && !photo.isEmpty()) {
            form.setPhoto(photo.getBytes());
        }

        formRepository.save(form);
        return "redirect:/displayform";
    }

    // Pagination-enabled method
    @GetMapping("/displaydata/page/{pageNum}")
    public String displayInfo(@PathVariable("pageNum") int pageNum, Model model) {
        int pageSize = 10;

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Form> page = formRepository.findAll(pageable);
        List<Form> formList = page.getContent();

        Map<Integer, String> base64Images = new HashMap<>();
        for (Form form : formList) {
            if (form.getPhoto() != null) {
                String base64 = Base64.getEncoder().encodeToString(form.getPhoto());
                base64Images.put(form.getStdId(), base64);
            }
        }

        model.addAttribute("formList", formList);
        model.addAttribute("base64Images", base64Images);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalData", page.getTotalElements());
        return "displaydata";
    }

    //Search
    @GetMapping("/search")
    public String searchData(@RequestParam("keyword") String keyword, Model model) {
        List<Form> formList = formRepository.searchAll(keyword);
        
        Map<Integer, String> base64Images = new HashMap<>();
        for (Form form : formList) {
            if (form.getPhoto() != null) {
                String base64 = Base64.getEncoder().encodeToString(form.getPhoto());
                base64Images.put(form.getStdId(), base64);
            }
        }

        model.addAttribute("formList", formList);
        model.addAttribute("base64Images", base64Images);
        model.addAttribute("currentPage", 1); // optional if paginating later
        model.addAttribute("totalPages", 1);  // optional if paginating later
        model.addAttribute("totalData", formList.size());
        return "displaydata";
    }


    @GetMapping("/edit/{stdId}")
    public String showEditForm(@PathVariable("stdId") int stdId, Model model) {
        try {
            Form student = formRepository.findById(stdId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + stdId));
            model.addAttribute("form", student);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/displaydata/page/1";
        }
        return "editform";
    }

    @PostMapping("/edit/{stdId}")
    public String updateStudent(
            @PathVariable("stdId") int stdId,
            @RequestParam("name") String name,
            @RequestParam("mobile") String mobile,
            @RequestParam("email") String email,
            @RequestParam("education") String education,
            @RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
            @RequestParam("gender") String gender,
            @RequestParam("address") String address,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,  // Default pageNum to 1 if not present
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) throws IOException {

        Form existingForm = formRepository.findById(stdId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + stdId));

        existingForm.setName(name);
        existingForm.setMobile(mobile);
        existingForm.setEmail(email);
        existingForm.setEducation(education);
        existingForm.setDob(dob);
        existingForm.setGender(gender);
        existingForm.setAddress(address);

        if (photo != null && !photo.isEmpty()) {
            existingForm.setPhoto(photo.getBytes());
        }

        formRepository.save(existingForm);

        return "redirect:/displaydata/page/" + pageNum; // Redirecting to the correct page number
    }
    
    
    
    
}
