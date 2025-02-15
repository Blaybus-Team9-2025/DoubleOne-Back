package org.doubleone.domain.license.repository;

import java.util.Optional;
import org.doubleone.domain.license.entity.License;
import org.doubleone.domain.license.entity.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<License, Long> {

  Optional<License> findByLicenseTypeAndLicenseNum(LicenseType licenseType, String s);
}
