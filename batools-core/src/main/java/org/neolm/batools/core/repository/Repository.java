package org.neolm.batools.core.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.neolm.batools.core.inspect.FileEntry;

/**
 * 仓库接口
 * 
 * @author neolm
 * */
public interface Repository {
	
	// 登录
	public void login(String username,String password);
	// 关闭会话
	public void close() ;
	// 设置目标读取目录集合
	public void setTargetPathList(ArrayList<String> paths);
	// 设置版本
	public void setRevision(long startRevision) ;
	// 设置截取版本
	public void setEndRevision(long endRevision) ;
	// 获取变化集合
	public void logCollection();
	// 获取变更集合
	public ArrayList<FileEntry> getModificationCollection();
	// 获取文件从仓库中
	public ByteArrayOutputStream getFile(String path, long revision) ;
	
	// 获取最后版本
	public long getLastRevision();
	// 测试与仓库连接
	public boolean testConnection();
	// 清除
	public void clearModification();
	
		
}
