package org.neolm.batools.core.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.neolm.batools.core.inspect.FileEntry;
import org.neolm.batools.core.inspect.FileEntryConstant;
import org.neolm.batools.core.inspect.InspectRepository;
import org.neolm.batools.core.util.FileUtil;

/**
 * 索引文件处理
 * 
 * @author neolm
 * */
public class IndexFiles {

	private static Logger logger = Logger.getLogger(IndexFiles.class);

	private IndexWriter indexWriter;

	public IndexFiles() {

	}

	/**
	 * 
	 * */
	public void indexDocs(FileEntry fileEntry, String content) {

		try {
			Document doc = new Document();
			logger.debug("Adding index " + fileEntry);

			Field pathField = new StringField("path", fileEntry.getFieName(),
					Field.Store.YES);

			doc.add(pathField);

			doc.add(new StringField("author", fileEntry.getAuthor(),
					Field.Store.YES));

			doc.add(new LongField("revision", fileEntry.getRevision(),
					Field.Store.YES));

			doc.add(new TextField("contents", content, Field.Store.YES));

			indexWriter.addDocument(doc);
			
			
			if (fileEntry.getFileType().equals(FileEntryConstant.FILE_OPEN_MODE_CREATE) ) {

				indexWriter.addDocument(doc);
				logger.debug("Added index " + fileEntry + " successfully .");
			} else {

				logger.debug("updating " + fileEntry.getFieName());
				indexWriter.updateDocument(new Term("path", fileEntry.getFieName()), doc);
				logger.debug("updated " + fileEntry.getFieName());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Indexing File error : IOException :", e);
		}
	}

	public void commit() {
		try {
			indexWriter.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error while commit", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Error while commit", e);
		}
	}

	public void close() {
		try {
			indexWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error while close", e);
		}

	}

	public IndexWriter getIndexWriter() {
		return indexWriter;
	}

	public void setIndexWriter(IndexWriter indexWriter) {
		this.indexWriter = indexWriter;
	}

}
