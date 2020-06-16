package com.jiacong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.jiacong.utils.RedisOperator;
/**
 * 定义一些常用的,固定的命名空间,用于被其他Controller继承
 * 便可以方便使用对应变量
 * @author jiacongluo
 *
 */
@RestController
public class BasicController {
	//redis工具类
	@Autowired
	public RedisOperator redis;
	
	
	public static final String USER_REDIS_SESSION="user-redis-session";
	//文件保存的命名空间
	public static final String FILE_SPACE="/Users/jiacongluo/Document/Documents/jiacong_videos_dev";
	//ffmpeg所在目录
	public static final String FFMPEG_EXE="/usr/local/Cellar/ffmpeg/4.2.2_2/bin/ffmpeg";
	//每页分页的记录数
	public static final Integer PAGE_SIZE=5;
}
