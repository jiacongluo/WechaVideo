package com.jiacong.cofig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration   //定义配置类
@ConfigurationProperties(prefix="com.jiacong")
@PropertySource("classpath:resource.properties")  //获取resource.properties文件配置的数据
public class ResourceConfig {

	private String zookeeperServer;
	private String bgmServer;
	private String fileSpace;

	public String getZookeeperServer() {
		return zookeeperServer;
	}
	public void setZookeeperServer(String zookeeperServer) {
		this.zookeeperServer = zookeeperServer;
	}
	public String getBgmServer() {
		return bgmServer;
	}
	public void setBgmServer(String bgmServer) {
		this.bgmServer = bgmServer;
	}
	public String getFileSpace() {
		return fileSpace;
	}
	public void setFileSpace(String fileSpace) {
		this.fileSpace = fileSpace;
	}
}
