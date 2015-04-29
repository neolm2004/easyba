package org.neolm.batools.core.repository;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.neolm.batools.core.document.TikaFileHandler;
import org.neolm.batools.core.index.IndexFiles;
import org.neolm.batools.core.inspect.InspectRepository;
import org.neolm.batools.core.util.FileUtil;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// Repository repo = RepositoryFactory.createInstance("lvming",
			// "lvming", "http://10.4.144.233/svn/csc-wh/") ;
			// Repository repo = RepositoryFactory.createInstance("neolm",
			// "1q2w3e4r", "http://192.168.0.102/svn/test") ;
			ApplicationContext ac = new ClassPathXmlApplicationContext(
					"applicationContext.xml");
			Repository repo = (Repository) ac.getBean("repository");

			// InspectRepository ins = new InspectRepository();
			// ins.setFileHandler(new TikaFileHandler());
			// ins.setIndexFile(new IndexFiles());
			InspectRepository ins = (InspectRepository) ac
					.getBean("inspectRepository");
			boolean fileIsCreated = false ;
			String entryfilename = "e:\\svnentry.txt";
			
			if (FileUtil.isFile(entryfilename)) {
				fileIsCreated = true ;
				String lastRevision = FileUtil.getFileContent(entryfilename);
				Long recordLastRev = Long.parseLong(lastRevision) ;
				long sysLastRev = repo.getLastRevision() ;
							
				recordLastRev++;
				if(sysLastRev>=recordLastRev){
					repo.setRevision(recordLastRev);
					ins.inspect();
				}
				FileUtil.newFile(entryfilename, String.valueOf(sysLastRev));
			} else {
				repo.setRevision(0);
				FileUtil.newFile(entryfilename, String.valueOf(0));
			}
			
			
			

		

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;

	}

}
