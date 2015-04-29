package org.neolm.batools.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;

public class XsdUtil {

	public static <T> T createBean(String path, Class<T> className) {

		Object returnBean = null;
		try {
			File file = new File(path) ;
			JAXBContext jaxbContext = JAXBContext.newInstance(className);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			returnBean = jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return (T) returnBean;
	}

	public static <T> T createBean(InputStream is, Class<T> className) {

		Object returnBean = null;
		try {
			//File file = new File(path) ;
			JAXBContext jaxbContext = JAXBContext.newInstance(className);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			returnBean = jaxbUnmarshaller.unmarshal(is);
			

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return (T) returnBean;
	}
}
