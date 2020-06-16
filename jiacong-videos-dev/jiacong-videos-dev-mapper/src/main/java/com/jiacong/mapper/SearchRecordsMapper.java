package com.jiacong.mapper;

import java.util.List;

import com.jiacong.pojo.SearchRecords;
import com.jiacong.utils.MyMapper;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
	public List<String> getHotwords() ;
}