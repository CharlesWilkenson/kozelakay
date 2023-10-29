package com.management.system.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.management.system.entities.Content;
import com.management.system.service.ContentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/api/content/management/service/views")
@RequiredArgsConstructor
@Slf4j
public class Index {
    private final ContentService contentService;
    @GetMapping("/registerForm")
    public String login() {
        return "register";  
    }
    
    @GetMapping("/editProfile")
    public String editProfile(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);
        return "editProfile";  
    }
    
    @GetMapping("/addContent")
    public String addContent() {
        return "addContent";  
    }
    
    @GetMapping("/viewContent")
    public String viewContent() {
        return "viewContent";  
    }

    @GetMapping("/home")
    public String home(Model model) {
    	List<Content> contents = contentService.getContents();
    	
    	List<Content> twoLastContents = List.of(contents.get(contents.size()-1),contents.get(contents.size()-2));
    	
    	List<Content> list1 = contents.stream().filter(content -> content.getId() != contents.get(contents.size()-1).getId()
    			&& content.getId() != contents.get(contents.size()-2).getId()).collect(Collectors.toList());
    	
    	model.addAttribute("twoLastContents", twoLastContents);
    	model.addAttribute("contents", list1);

        return "home";  
    }
    

}
