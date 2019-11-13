package com.ivanstanev.portfolio.service.job;

import com.ivanstanev.portfolio.dao.AppUserDAO;
import com.ivanstanev.portfolio.dao.VerificationTokenDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@Service
@Transactional
public class VerificationTokenJob implements Job {


    @Override
    public void execute(JobExecutionContext context) {
        String verificationToken = context.getJobDetail().getJobDataMap().getString("verificationToken");
        System.out.println("In job execute : " + verificationToken);
        VerificationTokenDAO verificationTokenDAO = (VerificationTokenDAO) context.getMergedJobDataMap().get("VerificationTokenDAO");
        // find token id
        System.out.println("IS THIS NULL: " + verificationTokenDAO.getTokenId(verificationToken));
        Long verificationTokenId = verificationTokenDAO.getTokenId(verificationToken);
        // delete verification token from DB
        verificationTokenDAO.deleteToken(verificationTokenId);
    }

}
