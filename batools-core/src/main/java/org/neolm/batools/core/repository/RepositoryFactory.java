package org.neolm.batools.core.repository;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * 仓库实例生成工厂
 * 
 * @author neolm
 * */
public class RepositoryFactory {

	private static Logger logger = Logger.getLogger(RepositoryFactory.class);

	public static Repository createInstance(String repoType,String url) {
		// svn repository
		Repository repository = null;
		try {
			logger.info("Trying to create an Repository instance.");
			SVNRepository svnRepository  = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(url));
			
			repository = new SVNRepositoryAdapter(svnRepository);
			logger.info("SvnRepository object generated");
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			logger.error("Error while creationg SVNRepository.");
			logger.error("SVNException ", e);
		}

		return repository;
	}
}
