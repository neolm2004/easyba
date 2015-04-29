package org.neolm.batools.core.inspect;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.neolm.batools.core.document.FileHandler;
import org.neolm.batools.core.index.IndexFiles;
import org.neolm.batools.core.repository.Repository;
import org.neolm.batools.core.repository.SVNRepositoryAdapter;

/**
 * 检测仓库变更并进行处理主要类
 * 
 * @author neolm
 * */
public class InspectRepository {

	private static Logger logger = Logger.getLogger(InspectRepository.class);

	// 仓库处理
	private Repository repository;
	//
	private IndexFiles indexFile;
	//
	private FileHandler fileHandler;

	/**
	 * 检查更新
	 * */
	public void inspect() throws Exception {
		// 搜索变更集合
		repository.logCollection();
		ArrayList<FileEntry> modicationList = repository
				.getModificationCollection();
		try {
			ByteArrayOutputStream out = null ;
			// 遍历仓库变更集合
			for (FileEntry fileEntry : modicationList) {
				//文件内容
				String content = null;
				//从仓库获取文件流
				logger.debug("Getting file content: "+fileEntry.getFieName());
				out = repository.getFile(
						fileEntry.getFieName(), fileEntry.getRevision());
				logger.debug("Getting file content: "+fileEntry.getFieName()+"done!");
				if(out!=null){
					content = fileHandler.getContentFromBytes(fileEntry.getFieName(), out);
				}					
				// 索引文件
				if(content!=null){
					logger.debug("I will index the File "+fileEntry.getFieName());
					indexFile.indexDocs(fileEntry, content);
					
				}else{
					logger.debug(fileEntry.getFieName()+" content is null!");
				}
							
			}
			indexFile.commit();	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			logger.error("Error while indexing file ",e);
		} 
		finally{
			// 索引关闭			
			indexFile.close();
			logger.info("Index file is closed!");
		}

	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public IndexFiles getIndexFile() {
		return indexFile;
	}

	public void setIndexFile(IndexFiles indexFile) {
		this.indexFile = indexFile;
	}

	public FileHandler getFileHandler() {
		return fileHandler;
	}

	public void setFileHandler(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

}
