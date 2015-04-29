package org.neolm.batools.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.neolm.batools.core.inspect.FileEntry;
import org.neolm.batools.core.repository.Repository;
import org.neolm.batools.core.search.SearchFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/search.do")
public class SearchController {

	private static Logger logger = Logger.getLogger(SearchController.class);

	@Autowired
	private SearchFiles searchFiles;

	@Autowired
	private Repository repository;

	@RequestMapping
	public String load(ModelMap modelMap) {

		return "index";
	}

	@RequestMapping(params = "method=search")
	public String search(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		String keyword = request.getParameter("keyword");
		ArrayList<FileEntry> result = searchFiles.doSearch(keyword);
		logger.debug("keyword:" + keyword + " |result size: " + result.size());		
		modelMap.put("hitsnum", result.size());
		modelMap.put("resultList", result);
		modelMap.put("keyword", keyword);
		return "asynsearch";
	}
	
	@RequestMapping(params = "method=asynsearch")
	@ResponseBody
	public Map asynSearch(@RequestParam String keyword)
			throws Exception {
		
		ArrayList<FileEntry> result = searchFiles.doSearch(keyword);
		logger.debug("keyword:" + keyword + " |result size: " + result.size());		
		
		Map resultMap = new HashMap();
		
		resultMap.put("hitsnum", result.size());
		resultMap.put("resultList", result);
		resultMap.put("keyword", keyword);
		
		return resultMap;
	}

	@RequestMapping(params = "method=validation")
	@ResponseBody
	public Boolean validation(@RequestParam String username,
			@RequestParam String password) throws Exception {

		logger.debug(username + " is connecting to our system.");
		//username = "lvming";
		//password = "lvming";
		Map<String, Boolean> result = new HashMap<String, Boolean>();

		// login
		repository.login(username, password);
		logger.debug("repository will validate the username:" + username);
		// testConnection
		//result.put("result", repository.testConnection());

		return repository.testConnection();
	}

	

	@RequestMapping(params = "method=download")
	@ResponseBody
	public ResponseEntity<byte[]> download(@RequestParam String filename,
			@RequestParam String rev) throws Exception {
		
		
		Long revision = Long.parseLong(rev);
		//revision =  Long.parseLong("4580");
		//filename = "/01-计划/2012年特批需求评估列表.xls" ;
		HttpHeaders headers = new HttpHeaders();
		filename = filename.substring(filename.lastIndexOf("\\/")+1); 
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", new String(filename.split("\\/")[filename.split("\\/").length-1].getBytes("gb2312"),"iso8859-1"));
		ByteArrayOutputStream outStream = repository.getFile(filename, revision);

		byte[] res = outStream.toByteArray(); 
		try {
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IOException : ",e);
			
		}
		
		return new ResponseEntity<byte[]>(res, headers, HttpStatus.OK);
	}
	
	

}
