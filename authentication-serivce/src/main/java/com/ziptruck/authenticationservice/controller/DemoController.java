package com.ziptruck.authenticationservice.controller;

import com.ziptruck.authenticationservice.repository.UserRepository;
import com.ziptruck.authenticationservice.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from secured url");
    }

    @GetMapping("/admin_only")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Hello from admin only url");
    }

    @GetMapping("/admin_only/get_customer")
    public ResponseEntity<String> vendor() {
        return ResponseEntity.ok("");
    }

    @GetMapping("/admin_only/get_customers")
    public ResponseEntity<String> vendors() {
        return ResponseEntity.ok("[\n" +
                "    {\n" +
                "        \"customerId\": 1,\n" +
                "        \"customerName\": \"Sasank\",\n" +
                "        \"mobileNumber\": \"8500378746\",\n" +
                "        \"age\": 22,\n" +
                "        \"location\": \"NITK\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"customerId\": 2,\n" +
                "        \"customerName\": \"Amith\",\n" +
                "        \"mobileNumber\": \"3882003829\",\n" +
                "        \"age\": 21,\n" +
                "        \"location\": \"kikalur\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"customerId\": 3,\n" +
                "        \"customerName\": \"varshini\",\n" +
                "        \"mobileNumber\": \"2938232323\",\n" +
                "        \"age\": 21,\n" +
                "        \"location\": \"Nellore\"\n" +
                "    }\n" +
                "]");
    }
}
