package com.example.PassProtect.Dao;

import com.example.PassProtect.Entity.SiteDetail;
import com.example.PassProtect.Entity.User;
import com.example.PassProtect.Repository.SiteDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class SiteDetailDao {

    @Autowired
    private SiteDetailRepository siteDetailRepository;

    public SiteDetail save(SiteDetail siteDetail) {
        return siteDetailRepository.save(siteDetail);
    }

    public List<SiteDetail> findAll() {
        return siteDetailRepository.findAll();
    }

    public Optional<SiteDetail> findBySiteName(String siteName) {
        return siteDetailRepository.findBySiteName(siteName);
    }

    public Optional<SiteDetail> findBySiteNameAndUser(String siteName, User user) {
        return siteDetailRepository.findBySiteNameAndUser(siteName, user);
    }

    public void delete(SiteDetail siteDetail) {
        siteDetailRepository.delete(siteDetail);
    }

    public List<SiteDetail> findByUser(User user) {
        return siteDetailRepository.findByUser(user);
    }
}
