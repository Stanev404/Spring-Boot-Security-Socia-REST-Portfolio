package com.ivanstanev.portfolio.service.job;

import com.ivanstanev.portfolio.dao.AppUserDAO;
import com.ivanstanev.portfolio.dao.VerificationTokenDAO;
import com.ivanstanev.portfolio.entity.VerificationToken;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class VerificationTokenJobFactory {

    @Autowired
    private VerificationTokenDAO verificationTokenDAO;

    @Autowired
    @Lazy
    private AppUserDAO appUserDAO;

    public void createAndScheduleVerificationTokenDeleteJob(String verificationToken) {
        JobDetail job = JobBuilder.newJob(VerificationTokenJob.class).usingJobData("verificationToken", verificationToken).build();
        // A simple solution is to set the spring bean in the Job Data Map and then retrieve the bean in the job class, for instance.
        // Since we cant Autowire in the job
        job.getJobDataMap().put("VerificationTokenDAO",this.verificationTokenDAO);
        job.getJobDataMap().put("AppUserDAO",this.appUserDAO);

        LocalDateTime triggerTime = LocalDateTime.now().plusMinutes(30);

        Date date = Date.from(triggerTime.atZone(ZoneId.systemDefault()).toInstant());
        Trigger trigger = TriggerBuilder.newTrigger().startAt(date).build();



        try {

            Scheduler scheduler;


            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            scheduler = schedFact.getScheduler();


            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
