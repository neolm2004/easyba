package org.neolm.batools.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;

/**
 * Spring上下文管理器
 * 
 * @author neolm
 * */

public class ApplicationContextManager {
	
	private static final Map<String, ApplicationContext> CONTEXT_MGR = new ConcurrentHashMap<String, ApplicationContext>();
	
	public static final String DEFAULT_APPLICATION_CONTEXT = "DEFAULT_APPLICATIONCONTEXT";
	
	public static final String DEFAULT_BEANID_INSPECTREPO = "inspectRepository";
	
	public static final String DEFAULT_BEANID_REPO = "repository";
	
	private ApplicationContextManager() {

	}
	
	public static ApplicationContext getContext(String contextName){
		return CONTEXT_MGR.get(contextName);
	}
	
	public static void setContext(String contextName,ApplicationContext context) {
		CONTEXT_MGR.put(contextName, context);
	}

}
