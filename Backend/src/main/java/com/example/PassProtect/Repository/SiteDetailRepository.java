package com.example.PassProtect.Repository;

import com.example.PassProtect.Entity.SiteDetail;
import com.example.PassProtect.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SiteDetailRepository extends JpaRepository<SiteDetail, Long> {
    Optional<SiteDetail> findBySiteNameAndUser(String siteName, User user);
    Optional<SiteDetail> findBySiteName(String siteName);
    List<SiteDetail> findByUser(User user);
    
}

