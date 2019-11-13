package com.ivanstanev.portfolio.service.job;

import com.ivanstanev.portfolio.entity.VerificationToken;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class VerificationTokenJobFactory {



    public void createAndScheduleVerificationTokenDeleteJob(String verificationToken) {
        JobDetail job = JobBuilder.newJob(VerificationTokenJob.class).usingJobData("verificationToken", verificationToken).build();


        LocalDateTime triggerTime = LocalDateTime.now().plusSeconds(10);

        Date date = Date.from(triggerTime.atZone(ZoneId.systemDefault()).toInstant());
        Trigger trigger = TriggerBuilder.newTrigger().startAt(date).build();



        try {

            Scheduler scheduler;


            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            scheduler = schedFact.getScheduler();


            AutowiringSpringBeanJobFactory autowiringSpringBeanJobFactory = new AutowiringSpringBeanJobFactory();
            autowiringSpringBeanJobFactory.setApplicationContext((ApplicationContext) scheduler.getContext());
            scheduler.setJobFactory(autowiringSpringBeanJobFactory);

            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
