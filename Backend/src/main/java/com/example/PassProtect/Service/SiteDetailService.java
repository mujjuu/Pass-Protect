package com.example.PassProtect.Service;

import com.example.PassProtect.Dao.SiteDetailDao;
import com.example.PassProtect.Dao.UserDao;
import com.example.PassProtect.Entity.SiteDetail;
import com.example.PassProtect.Entity.User;
import com.example.PassProtect.Exception.DecryptionException;
import com.example.PassProtect.Exception.EmailNotFoundException;
import com.example.PassProtect.Exception.SiteNotFoundException;
import com.example.PassProtect.ResponseStructure.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SiteDetailService {

    @Autowired
    private SiteDetailDao siteDetailDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EncryptionService encryptionService;

    public ResponseEntity<ResponseStructure<SiteDetail>> addSiteDetail(SiteDetail siteDetail, String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        siteDetail.setUser(user);
        siteDetail.setSiteName(siteDetail.getSiteName().toLowerCase());

        siteDetailDao.findBySiteNameAndUser(siteDetail.getSiteName(), user)
                .ifPresent(existing -> {
                    throw new SiteNotFoundException(siteDetail.getSiteName());
                });

        try {
            String[] encrypted = encryptionService.encrypt(siteDetail.getPassword(), siteDetail.getKey());
            siteDetail.setPassword(encrypted[0]);
            siteDetail.setIv(encrypted[1]);
            siteDetail.setSalt(encrypted[2]);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }

        siteDetailDao.save(siteDetail);

        ResponseStructure<SiteDetail> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.CREATED.value());
        structure.setMessage("Success");
        structure.setData(siteDetail);

        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<List<SiteDetail>>> getAllSiteDetails(String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        List<SiteDetail> siteDetails = siteDetailDao.findByUser(user);

        // Decrypt each password
        for (SiteDetail site : siteDetails) {
            try {
                String decryptedPassword = encryptionService.decrypt(
                        site.getPassword(),
                        site.getKey(),
                        site.getIv(),
                        site.getSalt()
                );
                site.setPassword(decryptedPassword);
            } catch (Exception e) {
                site.setPassword("Unable to decrypt");
            }
        }

        ResponseStructure<List<SiteDetail>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Success");
        structure.setData(siteDetails);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<SiteDetail>> findBySiteNameAndUser(String siteName, String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        SiteDetail siteDetail = siteDetailDao.findBySiteNameAndUser(siteName.toLowerCase(), user)
                .orElseThrow(() -> new SiteNotFoundException(siteName));

        try {
            String decryptedPassword = encryptionService.decrypt(
                    siteDetail.getPassword(),
                    siteDetail.getKey(),
                    siteDetail.getIv(),
                    siteDetail.getSalt()
            );
            siteDetail.setPassword(decryptedPassword);
        } catch (Exception e) {
            throw new DecryptionException(siteName);
        }

        ResponseStructure<SiteDetail> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Success");
        structure.setData(siteDetail);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<SiteDetail>> updateSiteDetail(String siteName, SiteDetail updatedDetails) {
        SiteDetail siteDetail = siteDetailDao.findBySiteName(siteName.toLowerCase())
                .orElseThrow(() -> new SiteNotFoundException(siteName));

        try {
            String newSiteName = updatedDetails.getSiteName().toLowerCase();

            String[] encrypted = encryptionService.encrypt(updatedDetails.getPassword(), updatedDetails.getKey());

            siteDetail.setSiteName(newSiteName);
            siteDetail.setPassword(encrypted[0]);
            siteDetail.setIv(encrypted[1]);
            siteDetail.setSalt(encrypted[2]);

            siteDetailDao.save(siteDetail);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed during update", e);
        }

        ResponseStructure<SiteDetail> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Updated successfully");
        structure.setData(siteDetail);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<SiteDetail>> deleteSiteDetail(String siteName, String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        SiteDetail siteDetail = siteDetailDao.findBySiteNameAndUser(siteName.toLowerCase(), user)
                .orElseThrow(() -> new SiteNotFoundException(siteName));

        siteDetailDao.delete(siteDetail);

        ResponseStructure<SiteDetail> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Deleted successfully");

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }
}
