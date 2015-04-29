package org.neolm.batools.core.document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.helpers.DefaultHandler;
import org.neolm.batools.core.repository.SVNRepositoryAdapter;

/**
 * TikaÎÄµµ´¦ÀíÆ÷
 * 
 * @author neolm
 * */
public class TikaFileHandler implements FileHandler {
	
	private static Logger logger = Logger.getLogger(TikaFileHandler.class);

	@Override
	public String getContentFromBytes(String path, ByteArrayOutputStream out) {
		// TODO Auto-generated method stub
		String result = null ;
		ByteArrayInputStream in =null ;
		
		try {
			
            Metadata metadata = new Metadata();
            
            //metadata.add(Metadata.RESOURCE_NAME_KEY, path);
            //metadata.add(Metadata.CONTENT_ENCODING, "UTF8");
            AutoDetectParser parser = new AutoDetectParser();
            
            DefaultHandler handler = new BodyContentHandler(18*1024*1024);
            //
            if(out!=null){
            	in = new ByteArrayInputStream(out.toByteArray());
            	
            	parser.parse(in, handler, metadata);
            	
            
            	result = handler.toString() ;
            }            

        } catch (Exception e) {
            logger.error("Error while handle file:"+path+" :", e);
        } finally {
            try {
            	if(out!=null)out.close();
                if(in!=null)in.close();
                
                logger.debug("Closing inputstream and outstream");
                return result;
            } catch (Exception e) {
            	logger.error("Exception while closing file: ", e);
            }
        }
		return result;
		
	}

}
