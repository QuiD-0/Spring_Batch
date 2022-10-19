package com.quid.batch.pass.service;

import com.quid.batch.pass.repository.BulkPassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BulkPassServiceImpl implements BulkPassService{

    private final BulkPassRepository bulkPassRepository;
}
