package org.neolm.batools.core.job;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.CronScheduleBuilder.cronSchedule;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class ScheduleServer {

	private static Logger logger = Logger.getLogger(ScheduleServer.class);

	private Scheduler scheduler = null;

	public ScheduleServer() throws Exception {
		initScheduler();
	}

	private void initScheduler() throws Exception {

		SchedulerFactory sf = new StdSchedulerFactory();
		this.scheduler = sf.getScheduler();

	}

	public void startScheduler() throws Exception {

		scheduler.start();

	}

	public void stopScheduler() throws Exception {
		scheduler.shutdown();
	}

	public void ScheduleJob(String jobClass, String jobGroup,
			String jobTrigger, Map args, String jobName) throws Exception {
		try {
			JobDetail job = null;
			//
			Class<? extends Job> clazz;
			clazz = (Class<? extends Job>) Class.forName(jobClass);
			logger.info("Loading JobClass : "+ jobClass);
			job = JobBuilder.newJob(clazz)
					.withIdentity(jobName, jobGroup)
					.build();

			CronTrigger trigger = newTrigger()
					.withIdentity("trigger" + jobName, "group" + jobGroup)
					.withSchedule(cronSchedule(jobTrigger)).build();
			//
			Iterator it = args.keySet().iterator();
			while (it.hasNext()) {
				String keyName = it.next().toString();
				job.getJobDataMap().put(keyName, args.get(keyName));
			}
			scheduler.scheduleJob(job, trigger);
			logger.info("JobClass : "+ jobClass + " is Loaded");
		} catch (ClassNotFoundException e) {
			logger.info("Exception: Class " + jobClass + "was not found!");
		} catch (Exception e) {
			logger.info("Exception: errors occured!");
			e.printStackTrace();
		}

	}

}
