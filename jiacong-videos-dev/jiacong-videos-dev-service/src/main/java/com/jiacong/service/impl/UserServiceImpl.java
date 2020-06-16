package com.jiacong.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiacong.mapper.UsersFansMapper;
import com.jiacong.mapper.UsersLikeVideosMapper;
import com.jiacong.mapper.UsersMapper;
import com.jiacong.mapper.UsersReportMapper;
import com.jiacong.pojo.Users;
import com.jiacong.pojo.UsersFans;
import com.jiacong.pojo.UsersLikeVideos;
import com.jiacong.pojo.UsersReport;
import com.jiacong.service.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UsersMapper userMapper;
	@Autowired
	private UsersFansMapper usersFansMapper;
	@Autowired
	private Sid sid;
	@Autowired
	private UsersLikeVideosMapper usersLikeVideosMapper;
	@Autowired
	private UsersReportMapper usersReportMapper;
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	//查询用户是否存在
	public boolean queryUsernameIsExist(String username) {
		
		Users user = new Users();
		user.setUsername(username);
		
		Users result = userMapper.selectOne(user);
		
		return result == null ? false : true;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	//保存用户信息
	public void saveUser(Users user) {
		
		String userId = sid.nextShort();
		user.setId(userId);
		userMapper.insert(user);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserForLogin(String username, String password) {
		
		Example userExample = new Example(Users.class);  //example User实例
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("username", username);//where "username"=username and "password"=password
		criteria.andEqualTo("password", password);
		Users result = userMapper.selectOneByExample(userExample);
		//返回查询结果
		return result;
	}
	
	//更新用户信息
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateUserInfo(Users user) {
		
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id", user.getId());
		userMapper.updateByExampleSelective(user, userExample);//更新属性值
	}
	//查询用户信息
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserInfo(String userId) {
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id", userId);
		Users user = userMapper.selectOneByExample(userExample);
		return user;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean isUserLikeVideo(String userId, String videoId) {

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
			return false;
		}
		
		Example example = new Example(UsersLikeVideos.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		
		List<UsersLikeVideos> list = usersLikeVideosMapper.selectByExample(example);
		
		if (list != null && list.size() >0) {
			return true;
		}
		
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUserFanRelation(String userId, String fanId) {
		//生成sid
		String relId = sid.nextShort();
		
		UsersFans userFan = new UsersFans();
		userFan.setId(relId);
		userFan.setUserId(userId);
		userFan.setFanId(fanId);
		
		usersFansMapper.insert(userFan);
		
		userMapper.addFansCount(userId);
		userMapper.addFollersCount(fanId);
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteUserFanRelation(String userId, String fanId) {
		
		Example example = new Example(UsersFans.class);
		Criteria criteria = example.createCriteria();
		//先查询userFans
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("fanId", fanId);
		
		usersFansMapper.deleteByExample(example);
		
		userMapper.reduceFansCount(userId);
		userMapper.reduceFollersCount(fanId);
		
	}
	
	@Override
	public boolean queryIfFollow(String userId, String fanId) {

		Example example = new Example(UsersFans.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("fanId", fanId);
		
		List<UsersFans> list = usersFansMapper.selectByExample(example);
		
		if (list != null && !list.isEmpty() && list.size() > 0) {
			return true;
		}
		
		return false;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void reportUser(UsersReport userReport) {
		
		String urId = sid.nextShort();
		userReport.setId(urId);
		userReport.setCreateDate(new Date());
		
		usersReportMapper.insert(userReport);
	}
}

