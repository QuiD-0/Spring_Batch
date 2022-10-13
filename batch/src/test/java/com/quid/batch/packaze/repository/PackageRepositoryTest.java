package com.quid.batch.packaze.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.quid.batch.packaze.entity.Package;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest
class PackageRepositoryTest {

    @Autowired
    private PackageRepository packageRepository;


    @Test
    public void test_save() {
        Package pkg = Package.builder()
            .packageName("12주 챌린지").period(84).build();

        packageRepository.save(pkg);

        assertNotNull(pkg.getPackageSeq());
    }
}