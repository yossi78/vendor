package com.example.learn.schedule;

import com.example.learn.services.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;





@Service
@EnableScheduling
public class VendorScheduler {

    @Value("${vendorSvc.vendorScheduler.files}")
    private List<String> files;

    @Value("${vendorSvc.vendorScheduler.urls}")
    private List<String> urls;

    private Logger logger = LoggerFactory.getLogger(VendorScheduler.class);
    private VendorService vendorService;


    @Autowired
    public VendorScheduler(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @Scheduled(fixedDelayString = "${personSvc.personScheduler.delay:20000}")
    public void hello() {
        System.out.println("HELLO FROM SCHEDULER");

    }




}
