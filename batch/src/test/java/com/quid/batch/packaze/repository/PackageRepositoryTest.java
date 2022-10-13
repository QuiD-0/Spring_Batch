package com.quid.batch.packaze.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.quid.batch.packaze.entity.Package;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class PackageRepositoryTest {

    @Autowired
    private PackageRepository packageRepository;


    @Test
    public void save() {
        Package pkg = Package.builder()
            .packageName("12주 챌린지").period(84).build();

        packageRepository.save(pkg);

        assertNotNull(pkg.getPackageSeq());
    }

    @Test
    public void find_pkg_when_after_createdAt() {
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1);

        Package pkg1 = Package.builder()
            .packageName("학생전용 3개월")
            .period(90)
            .build();

        packageRepository.save(pkg1);

        Package pkg2 = Package.builder()
            .packageName("학생전용 6개월")
            .period(180)
            .build();

        packageRepository.save(pkg2);

        final List<Package> packageList = packageRepository.findByCreatedAtAfter(dateTime,
            PageRequest.of(0, 1, Sort.by("packageSeq").descending()));

        assertEquals(1,packageList.size());
        assertEquals(pkg2.getPackageSeq(),packageList.get(0).getPackageSeq());

    }
}