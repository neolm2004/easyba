package org.neolm.batools.core.inspect;

import java.util.Date;

/**
 * ²Ö¿âÎÄ¼þ¼ÇÂ¼Bean
 * 
 * @author neolm
 * */
public class FileEntry {
	
	private String filePath ;
	
	private String fileType ;
	
	private String fieName ;
	
	private long revision ;
	
	private Date date ;
	
	private String author ;
	
	private String message ;
	
	private String kind ;
	
	private String description ;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFieName() {
		return fieName;
	}

	public void setFieName(String fieName) {
		this.fieName = fieName;
	}

	public long getRevision() {
		return revision;
	}

	public void setRevision(long revision) {
		this.revision = revision;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public String toString(){
		return "author:"+author+"@kind:"+kind+"@revision;"+revision+"@type:"+fileType+"@"+fieName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
