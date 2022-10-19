package com.quid.batch.pass.controller;

import com.quid.batch.pass.service.BulkPassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BulkPassController {

    private final BulkPassService bulkPassService;

    @GetMapping("/bulk-pass")
    public void addBulkPass(){

    }
}
