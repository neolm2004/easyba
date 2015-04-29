package org.neolm.batools.core.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.neolm.batools.core.context.ApplicationContextManager;
import org.neolm.batools.core.inspect.InspectRepository;
import org.neolm.batools.core.repository.Repository;
import org.neolm.batools.core.util.FileUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;

/**
 * 检出文件进行索引JOB
 * 
 * @author neolm
 * */
@DisallowConcurrentExecution
public class InspectRepositoryJob implements Job {
	
	private static Logger logger = Logger.getLogger(InspectRepositoryJob.class);
	
	@Override
	public void execute(JobExecutionContext jobContext) {
		// TODO Auto-generated method stub
		// job参数
		Map<String, Object> params  = jobContext.getJobDetail().getJobDataMap();
		Repository repo = null ;
		
		try {
			
			// 载入上下文管理器
			ApplicationContext ac = ApplicationContextManager.getContext(ApplicationContextManager.DEFAULT_APPLICATION_CONTEXT) ;
			
			logger.info(" Getting repository interface .");
			repo = (Repository) ac.getBean(ApplicationContextManager.DEFAULT_BEANID_REPO);
			
			// repository登入
			repo.login(params.get(JobConstant.LOGIN_USER_NAME).toString(), params.get(JobConstant.LOGIN_PASSWORD).toString());
			// repository检出目录
			repo.setTargetPathList((ArrayList<String>)params.get(JobConstant.TARGET_PATHS));
			InspectRepository ins = (InspectRepository)ac.getBean(ApplicationContextManager.DEFAULT_BEANID_INSPECTREPO);
			
			// 检查文件检出记录
			boolean fileIsCreated = false ;
			String entryfilename = (String) params.get(JobConstant.ENTRY_FILE_NAME);
			// 设置每次取版本号
			long maxPerDocNum = Long.parseLong(params.get(JobConstant.MAX_DOC_NUM).toString());
			
			if (FileUtil.isFile(entryfilename)) {
				logger.debug(entryfilename + " is exist .");
				
				fileIsCreated = true ;
				String lastRevision = FileUtil.getFileContent(entryfilename).trim();
				long recordLastRev = Long.parseLong(lastRevision) ;							
				
				recordLastRev++;
				long sysLastRev =  repo.getLastRevision();			

				sysLastRev = (maxPerDocNum+recordLastRev)>=sysLastRev ?sysLastRev: maxPerDocNum+recordLastRev;
				
				if(sysLastRev>=recordLastRev){
					repo.setRevision(recordLastRev);
					repo.setEndRevision(sysLastRev);
					logger.info("---------------------------------------");
					logger.info("last revision:" + recordLastRev);
					logger.info("new revision:" + sysLastRev);
					logger.info("---------------------------------------");
					
					logger.info("Begin to update from the repository.");
					ins.inspect();
					logger.info("Updated!" );
					FileUtil.newFile(entryfilename, String.valueOf(sysLastRev));
				}else{
					ins.getIndexFile().close();
					logger.info("I have nothing to do !" );
					
				}
				
				
				
			} else {
				// 从最初版本开始
				repo.setRevision(0);
				repo.setEndRevision(maxPerDocNum);
				FileUtil.newFile(entryfilename, String.valueOf(0));
				logger.info("Never checkout from repository.checkout it now!");
				ins.inspect();
			}
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			logger.error(" NumberFormatException ",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(" IOException ",e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(" Exception ",e);
		}  finally{
			//清除变更记录
			repo.clearModification();
			logger.info("Modification set cleared!");
			
			// 关闭仓库会话
			repo.close();
			logger.info("Repository closed!");
			
		}
	}

	

}
