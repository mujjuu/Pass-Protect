package com.example.PassProtect.Controller;

import com.example.PassProtect.Entity.SiteDetail;
import com.example.PassProtect.ResponseStructure.ResponseStructure;
import com.example.PassProtect.Service.SiteDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sites")
public class SiteDetailController {

    @Autowired
    private SiteDetailService siteDetailService;

    //  Add a new site detail for a user
    @PostMapping("/add/{email}")
    public ResponseEntity<ResponseStructure<SiteDetail>> addSiteDetails(
            @PathVariable String email,
            @RequestBody SiteDetail siteDetail) {
        return siteDetailService.addSiteDetail(siteDetail, email);
    }

    //  Get all site details for a user
    @GetMapping("/getAll/{email}")
    public ResponseEntity<ResponseStructure<List<SiteDetail>>> getAllSiteDetails(
            @PathVariable String email) {
        return siteDetailService.getAllSiteDetails(email);
    }

    //  Get a site detail by site name
    @GetMapping("/findBySiteName/{siteName}/{email}")
    public ResponseEntity<ResponseStructure<SiteDetail>> getSiteDetailBySiteName(
            @PathVariable String siteName,@PathVariable String email) {
    	
        return siteDetailService.findBySiteNameAndUser(siteName.toLowerCase(), email);
    }

    //  Update a site detail by site name
    @PutMapping("/update/{siteName}")
    public ResponseEntity<ResponseStructure<SiteDetail>> updateSiteDetailBySiteName(
            @PathVariable String siteName,
            @RequestBody SiteDetail updatedDetails) {
        return siteDetailService.updateSiteDetail(siteName, updatedDetails);
    }

    //  Delete a site detail by site name and email
    @DeleteMapping("/delete/{siteName}/{email}")
    public ResponseEntity<ResponseStructure<SiteDetail>> deleteSiteDetail(
            @PathVariable String siteName, @PathVariable String email) {
        return siteDetailService.deleteSiteDetail(siteName, email);
    }
}
