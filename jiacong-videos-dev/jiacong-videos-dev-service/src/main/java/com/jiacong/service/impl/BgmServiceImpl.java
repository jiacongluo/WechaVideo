package com.jiacong.service.impl;


import java.util.List;


import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiacong.mapper.BgmMapper;
import com.jiacong.pojo.Bgm;
import com.jiacong.service.BgmService;





@Service
public class BgmServiceImpl implements BgmService {
	@Autowired
	private BgmMapper bgmMapper;
	@Autowired
	private Sid sid;
	
	//如果其他bean调用这个方法,在其他bean中声明事务,那就用事务.如果其他bean没有声明事务,那就不用事务.
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<Bgm> queryBgmList() {
		
		return bgmMapper.selectAll();   //返回一个list列表
		
		
	}

	//如果有事务, 那么加入事务, 没有的话新建一个(默认情况下)
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Bgm queryBgmById(String bgmId) {
		return bgmMapper.selectByPrimaryKey(bgmId);
	}

}

