package org.neolm.batools.core.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class InspectRepoJobJobListener implements JobListener {
	
	private static Logger logger = Logger.getLogger(InspectRepoJobJobListener.class);
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0) {
		// TODO Auto-generated method stub
		logger.info("jobExecutionVetoed");
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext arg0) {
		// TODO Auto-generated method stub
		logger.info("jobToBeExecuted");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext arg0,
			JobExecutionException arg1) {
		// TODO Auto-generated method stub
		logger.info("jobWasExecuted");
	}

}
