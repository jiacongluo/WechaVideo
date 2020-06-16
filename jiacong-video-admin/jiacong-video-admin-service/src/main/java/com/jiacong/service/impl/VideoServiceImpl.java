package com.jiacong.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiacong.enums.BGMOperatorTypeEnum;
import com.jiacong.mapper.BgmMapper;
import com.jiacong.mapper.UsersReportMapperCustom;
import com.jiacong.mapper.VideosMapper;
import com.jiacong.pojo.Bgm;
import com.jiacong.pojo.BgmExample;
import com.jiacong.pojo.Videos;
import com.jiacong.pojo.vo.Reports;
import com.jiacong.service.VideoService;
import com.jiacong.utils.JsonUtils;
import com.jiacong.utils.PagedResult;
import com.jiacong.web.util.ZKCurator;

@Service
public class VideoServiceImpl implements VideoService {
	@Autowired
	private VideosMapper videosMapper;
		
	@Autowired
	private BgmMapper bgmMapper;
	
	@Autowired
	private Sid sid;
	
	@Autowired
	private ZKCurator zkCurator;
	
	@Autowired
	private UsersReportMapperCustom usersReportMapperCustom;
	

	
	@Override
	public PagedResult queryBgmList(Integer page, Integer pageSize) {
		
		PageHelper.startPage(page, pageSize);
		
		BgmExample example = new BgmExample();
		List<Bgm> list = bgmMapper.selectByExample(example);
		
		PageInfo<Bgm> pageList = new PageInfo<>(list);
		
		PagedResult result = new PagedResult();
		result.setTotal(pageList.getPages());//设置总的页数
		result.setRows(list);//设置列数
		result.setPage(page);//当前页数
		result.setRecords(pageList.getTotal());//总的记录数
		
		return result;
	}
	
	@Override
	public void addBgm(Bgm bgm) {
		String bgmId = sid.nextShort();//用工具类idworker的Sid生成一个bgmID
		bgm.setId(bgmId);
		bgmMapper.insert(bgm);
		
		Map<String, String> map = new HashMap<>();
		map.put("operType", BGMOperatorTypeEnum.ADD.type);
		map.put("path", bgm.getPath());
		
		zkCurator.sendBgmOperator(bgmId, JsonUtils.objectToJson(map));
	}
	
	@Override
	public void deleteBgm(String id) {
		Bgm bgm = bgmMapper.selectByPrimaryKey(id);
		
		bgmMapper.deleteByPrimaryKey(id);
		
		Map<String, String> map = new HashMap<>();
		map.put("operType", BGMOperatorTypeEnum.DELETE.type);
		map.put("path", bgm.getPath());
		
		zkCurator.sendBgmOperator(id, JsonUtils.objectToJson(map));
		
	}
	
	@Override
	public PagedResult queryReportList(Integer page, Integer pageSize) {

		PageHelper.startPage(page, pageSize);

		List<Reports> reportsList = usersReportMapperCustom.selectAllVideoReport();

		PageInfo<Reports> pageList = new PageInfo<Reports>(reportsList);

		PagedResult grid = new PagedResult();
		grid.setTotal(pageList.getPages());
		grid.setRows(reportsList);
		grid.setPage(page);
		grid.setRecords(pageList.getTotal());

		return grid;
	}

	@Override
	public void updateVideoStatus(String videoId, Integer status) {
		
		Videos video = new Videos();
		video.setId(videoId);
		video.setStatus(status);
		videosMapper.updateByPrimaryKeySelective(video);
	}

}
