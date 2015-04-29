package org.neolm.batools.core.engine;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.neolm.batools.core.context.ApplicationContextManager;
import org.neolm.batools.core.job.JobConstant;
import org.neolm.batools.core.job.ScheduleServer;
import org.neolm.batools.core.job.config.JobForRepositoryConfig;
import org.neolm.batools.core.job.config.JobForRepositoryConfig.JobParameters.Param;
import org.neolm.batools.core.util.XsdUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 后天索引主程序入口
 * 
 * @author neolm
 * */
public class IndexFileBaseMain {

	private static Logger logger = Logger.getLogger(IndexFileBaseMain.class);
	
	private static String applicationCtxPath = "classpath*:org/neolm/batools/core/spring/applicationContext-core.xml" ;

	private String cfgFile;

	public IndexFileBaseMain(String initFile) {
		setCfgFile(initFile);
	}

	public void initial() {
		logger.info("Initializing...");

		InputStream in;
		try {
			// 
			logger.info("Loading configuration file: " + cfgFile);
			in = ClassLoader.getSystemResourceAsStream(cfgFile);
			JobForRepositoryConfig jobRepoCfg = XsdUtil.createBean(in,
					JobForRepositoryConfig.class);
			// 载入quartz
			ScheduleServer ss = new ScheduleServer();
			// 
			Map transArgs = new HashMap();
			List<Param> params = jobRepoCfg.getJobParameters().getParam();
			for (Param param : params) {
				transArgs.put(param.getParamName(), param.getParamValue());
			}
			//save entry file name
			transArgs.put(JobConstant.ENTRY_FILE_NAME, jobRepoCfg.getEntrySvnFileName()) ;
			
			List<String> targets = new ArrayList<String>();
			targets = jobRepoCfg.getTargetPaths().getTargetPath();
			// svn target paths
			transArgs.put(JobConstant.TARGET_PATHS, targets);
			
			// sprint context
			ApplicationContext ac = new ClassPathXmlApplicationContext(applicationCtxPath);
			ApplicationContextManager.setContext(ApplicationContextManager.DEFAULT_APPLICATION_CONTEXT, ac);
			logger.info("Loading spring applicationContext ." );
			ss.ScheduleJob("org.neolm.batools.core.job.InspectRepositoryJob",
					"group1", jobRepoCfg.getJobTrigger(), transArgs,
					jobRepoCfg.getJobName());

			

			// start scheduler
			logger.info("Scheduler is starting ");
			ss.startScheduler();

		} catch (Exception e) {
			logger.error("Something wrong with the initialization!",e);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// --Test
		//args = new String[] { "cscwh.xml" };
		// --

		logger.info("Starting engine...");
		if (args.length < 1) {
			logger.error("No configuration found...");
			System.exit(0);
		}
		try {

			IndexFileBaseMain baseMain = new IndexFileBaseMain(args[0].trim());

			baseMain.initial();
			//

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Starting failed!!!");
			e.printStackTrace();
		}

	}

	public String getCfgFile() {
		return cfgFile;
	}

	public void setCfgFile(String cfgFile) {
		this.cfgFile = cfgFile;
	}

}
