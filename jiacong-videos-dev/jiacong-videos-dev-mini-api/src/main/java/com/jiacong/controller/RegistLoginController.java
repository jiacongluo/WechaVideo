package com.jiacong.controller;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jiacong.pojo.Users;
import com.jiacong.pojo.vo.UsersVO;
import com.jiacong.service.UserService;
import com.jiacong.utils.IMoocJSONResult;
import com.jiacong.utils.MD5Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="用户注册登录的接口", tags= {"注册和登录的controller"})
public class RegistLoginController extends BasicController{
	@Autowired
	private UserService userService;
	@ApiOperation(value="用户注册", notes="用户注册的接口")
	@PostMapping("/regist")
	public IMoocJSONResult regist(@RequestBody Users user) throws Exception{//参数添加RequestBody是用于接收Content-Type为application/json类型请求
		
		// 1. 判断用户名和密码必须不为空
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return IMoocJSONResult.errorMsg("用户名和密码不能为空");
		}
		
		// 2. 判断用户名是否存在
		boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
		
		// 3. 如果用户不存在,则注册为新用户，保存信息
		if (!usernameIsExist) {
			user.setNickname(user.getUsername());
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));//用MD5加密工具类生成对应密码
			user.setFansCounts(0);
			user.setReceiveLikeCounts(0);
			user.setFollowCounts(0);
			userService.saveUser(user);
		} else {
			return IMoocJSONResult.errorMsg("用户名已经存在，请换一个再试");
		}
		//安全问题,在注册到数据库后,返回结果到小程序端前,将密码设置为空
		user.setPassword("");
		UsersVO userVO = setUserRedisSessionToken(user);
		
		return IMoocJSONResult.ok(userVO);
	}
	//设置用户Redis的Session
	public UsersVO setUserRedisSessionToken(Users userModel) {
		String uniqueToken = UUID.randomUUID().toString();//用java.util的工具类UUID产生唯一识别码
		redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 1000 * 60 * 30);
		
		UsersVO userVO = new UsersVO();
		BeanUtils.copyProperties(userModel, userVO);//间存在名称不相同的属性，则BeanUtils不对这些属性进行处理,相同的则自动帮忙赋值操作
		userVO.setUserToken(uniqueToken);//users实体类没有的UserToken属性则需要手动赋值
		return userVO;
	}
	@ApiOperation(value="用户登陆", notes="用户登陆的接口")
	@PostMapping("/login")
	public IMoocJSONResult login(@RequestBody Users user) throws Exception{
		// @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)
		String username=user.getUsername();
		String password=user.getPassword();
		//1.判断用户名和密码必须不为空
		if(StringUtils.isBlank(username)||StringUtils.isBlank(password)) {
			return IMoocJSONResult.ok("用户名或密码不能为空");
		}
		
		// 2. 判断用户是否存在
		Users userResult = userService.queryUserForLogin(username, 
				MD5Utils.getMD5Str(user.getPassword())); //密码转换回MD5
				
		// 3. 返回
		if (userResult != null) {
			userResult.setPassword("");
			UsersVO userVO = setUserRedisSessionToken(userResult);
			return IMoocJSONResult.ok(userVO);
		} else {
			return IMoocJSONResult.errorMsg("用户名或密码不正确, 请重试...");
		}
	}
	@ApiOperation(value="用户注销", notes="用户注销的接口")
	@ApiImplicitParam(name="userId", value="用户id", required=true, 
						dataType="String", paramType="query")
	@PostMapping("/logout")
	public IMoocJSONResult logout(String userId) throws Exception {
		redis.del(USER_REDIS_SESSION + ":" + userId);
		return IMoocJSONResult.ok();
	}
}
