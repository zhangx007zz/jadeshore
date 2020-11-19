package com.jadeshop.pro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jadeshop.pro.model.JadeShopDto;

@Mapper
public interface JadeShopDao {
	int insertImg (JadeShopDto dto);
	
    int insertJade(JadeShopDto dto);
    
    int updateImg(JadeShopDto dto);
        
    JadeShopDto selectJadeById(String id);    
    
    int delJade(String id);
    
    List<JadeShopDto> selectjade(JadeShopDto dto);
    
    List<JadeShopDto> exportJade(JadeShopDto dto);

}
