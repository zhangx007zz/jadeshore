package com.jadeshop.pro.service.jadeshop;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jadeshop.pro.model.JadeShopDto;

public interface JadeShopService {
	int addImg (JadeShopDto dto);
	
	void addJade(JadeShopDto dto);
	
    PageInfo<JadeShopDto> findAllJade(JadeShopDto dto);
    
	void delJade(String id);
	
	JadeShopDto selectJade(String id);
			
	void updateImg(JadeShopDto dto);
	
	List<JadeShopDto> exportJade(JadeShopDto dto);
}
