package org.neolm.batools.core.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.neolm.batools.core.inspect.FileEntry;
import org.neolm.batools.core.inspect.FileEntryConstant;
import org.tmatesoft.svn.core.ISVNDirEntryHandler;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLock;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNMergeInfoInheritance;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNPropertyValue;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.ISVNFileRevisionHandler;
import org.tmatesoft.svn.core.io.ISVNInheritedPropertiesHandler;
import org.tmatesoft.svn.core.io.ISVNLocationEntryHandler;
import org.tmatesoft.svn.core.io.ISVNLocationSegmentHandler;
import org.tmatesoft.svn.core.io.ISVNLockHandler;
import org.tmatesoft.svn.core.io.ISVNReplayHandler;
import org.tmatesoft.svn.core.io.ISVNReporterBaton;
import org.tmatesoft.svn.core.io.ISVNSession;
import org.tmatesoft.svn.core.io.ISVNWorkspaceMediator;
import org.tmatesoft.svn.core.io.SVNCapability;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * SVNRepository仓库适配器类
 * svn主要操作
 * 
 * @author neolm
 * */

public class SVNRepositoryAdapter implements Repository {

	private static Logger logger = Logger.getLogger(SVNRepositoryAdapter.class);

	private SVNRepository repository;
	//初始版本号
	private long startRevision;
	//最晚版本号
	private long endRevision;
	//检出目录
	private String[] targetPaths;
	//变更文件集合
	private ArrayList<SVNLogEntry> changeLogEntry;

	private ArrayList<FileEntry> modificationEntry;

	public SVNRepositoryAdapter(SVNRepository svnRepository) {
		setRepository(svnRepository);
		// http:// https://
		DAVRepositoryFactory.setup();
		// svn:
		SVNRepositoryFactoryImpl.setup();
		// file:///
		FSRepositoryFactory.setup();
		changeLogEntry = new ArrayList<SVNLogEntry>();

		modificationEntry = new ArrayList<FileEntry>();

	}

	/**
	 * svn登入
	 * */
	@Override
	public void login(String username, String password) {
		// TODO Auto-generated method stub
		logger.info("Loadind AuthManager .");
		ISVNAuthenticationManager authManager = SVNWCUtil
				.createDefaultAuthenticationManager(username, password);
		repository.setAuthenticationManager(authManager);
	}

	/**
	 * svn关闭session
	 * */
	public void close() {
		logger.info("Closing the repository Session.");
		if (repository != null) {
			repository.closeSession();
			logger.info("Repository session closed.");
		} else {
			logger.error("Close repository session Failed! ");
		}
	}
	
	/**
	 * 检出svn仓库变更集合
	 * */
	public void logCollection() {
		// TODO Auto-generated method stub
		logger.info("Collecting modification set.");
		// this is a test
		// startRevision = 0;
		// endRevision = 10;
		// --
		targetPaths = (targetPaths == null) ? new String[] { "" } : targetPaths;
		if(modificationEntry!=null)modificationEntry.clear();
		try {
			logger.info("startRevision:"+startRevision+"|endRevision:"+endRevision);
			repository.log(targetPaths, startRevision, endRevision, true, true,
					new ISVNLogEntryHandler() {
					
						public void handleLogEntry(SVNLogEntry logEntry) {
							

							for (String key : logEntry.getChangedPaths()
									.keySet()) {
								FileEntry fe = new FileEntry();
								fe.setAuthor(logEntry.getAuthor());
								fe.setRevision(logEntry.getRevision());
								fe.setFieName(key);
								fe.setFileType(String.valueOf(logEntry
										.getChangedPaths().get(key).getType()));
								fe.setKind(logEntry.getChangedPaths().get(key)
										.getKind().toString());
								logger.debug(fe);
								if(fe.getKind().equals(FileEntryConstant.KIND_FILE)){
									modificationEntry.add(fe);
								}

							}
							
						}
					});
			logger.info("Collected "+modificationEntry.size()+ " files modified.");

		} catch (SVNException e) {
			// TODO Auto-generated catch block
			logger.error("Error while collecting log information ! ", e);
		}
	}
	
	/**
	 * 检出svn具体文件
	 * */

	@Override
	public ByteArrayOutputStream getFile(String path, long revision) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();

			repository.getFile(path, revision, null, baos);
			logger.debug("Got file: "+ path);

			return baos;
		} catch (SVNException e) {
			logger.error("Error while getting files:" + path + ": ", e);
			if(baos!=null)
				try {
					baos.close();
					
				} catch (IOException ioe) {
					// TODO Auto-generated catch block
					logger.error("IOException with byteArrayOutputStream ",ioe);
				}
			return null;
		} catch (Exception e) {
			logger.error("Other errors :", e);
			if(baos!=null)
				try {
					baos.close();
					
				} catch (IOException ioe) {
					// TODO Auto-generated catch block
					logger.error("IOException with byteArrayOutputStream ",ioe);
				}
			return null;
		} catch(Throwable t)
		{   logger.error("Some error unknown ,Maybe out of memory ",t);
		if(baos!=null)
			try {
				baos.close();
				
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				logger.error("IOException with byteArrayOutputStream ",ioe);
			}
		return null;} 
		
		
		
	}
	
	
	/**
	 * 获取仓库最后提交版本
	 * */
	@Override
	public long getLastRevision() {
		// TODO Auto-generated method stub
		long revision = 0;
		try {
			revision = repository.getLatestRevision();
			//setEndRevision(revision);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			logger.error("Error while getting LastRevision:", e);
		}
		return revision;
	}
	
	@Override
	public void clearModification() {
		// TODO Auto-generated method stub
		modificationEntry.clear();
		logger.info("Current modification entry set cleared!");
	}


	@Override
	public ArrayList<FileEntry> getModificationCollection() {
		// TODO Auto-generated method stub
		return getModificationEntry();
	}

	@Override
	public void setTargetPathList(ArrayList<String> paths) {
		// TODO Auto-generated method stub
		setTargetPaths((String[]) paths.toArray(new String[paths.size()]));

	}

	@Override
	public void setRevision(long startRevision) {
		// TODO Auto-generated method stub
		setStartRevision(startRevision);
	}

	public SVNRepository getRepository() {
		return repository;
	}

	public void setRepository(SVNRepository repository) {
		this.repository = repository;
	}

	public ArrayList<SVNLogEntry> getLogEntry() {
		return changeLogEntry;
	}

	public void setLogEntry(ArrayList<SVNLogEntry> logEntry) {
		this.changeLogEntry = logEntry;
	}

	public long getStartRevision() {
		return startRevision;
	}

	public void setStartRevision(long startRevision) {
		this.startRevision = startRevision;
	}

	public long getEndRevision() {
		return endRevision;
	}

	public void setEndRevision(long endRevision) {
		this.endRevision = endRevision;
	}

	public ArrayList<FileEntry> getModificationEntry() {
		return modificationEntry;
	}

	public void setModificationEntry(ArrayList<FileEntry> modificationEntry) {
		this.modificationEntry = modificationEntry;
	}

	public String[] getTargetPaths() {
		return targetPaths;
	}

	public void setTargetPaths(String[] targetPaths) {
		this.targetPaths = targetPaths;
	}

	@Override
	public boolean testConnection() {
		// TODO Auto-generated method stub
		boolean result = false ;

		try {
			repository.testConnection();
			result = true ;
		}catch(SVNAuthenticationException e ){
			logger.error("SVNAuthenticationException:",e);
			return false;
		} 
		catch (SVNException e) {
			// TODO Auto-generated catch block
			logger.error("SVNException:",e);
			return false;
		}
		return result;
	}

	
}
