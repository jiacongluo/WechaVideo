package com.jiacong.service;

import com.jiacong.pojo.Users;
import com.jiacong.utils.PagedResult;

public interface UsersService {

	public PagedResult queryUsers(Users user, Integer page, Integer pageSize);
	
}
