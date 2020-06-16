package com.jiacong.mapper;

import java.util.List;

import com.jiacong.pojo.Comments;
import com.jiacong.pojo.vo.CommentsVO;
import com.jiacong.utils.MyMapper;

public interface CommentsMapperCustom extends MyMapper<Comments> {
	
	public List<CommentsVO> queryComments(String videoId);
}