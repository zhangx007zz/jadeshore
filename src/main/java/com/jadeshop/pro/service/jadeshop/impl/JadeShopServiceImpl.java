package com.jadeshop.pro.service.jadeshop.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jadeshop.pro.dao.JadeShopDao;
import com.jadeshop.pro.model.JadeShopDto;
import com.jadeshop.pro.service.jadeshop.JadeShopService;

@Service
public class JadeShopServiceImpl implements JadeShopService {
	
	@Autowired
	private JadeShopDao jadeShopDao;

	@Override
	public int addImg(JadeShopDto dto) {
		jadeShopDao.insertImg(dto);
		return dto.getId();
	}

	@Override
	public void addJade(JadeShopDto dto) {
		jadeShopDao.insertJade(dto);
	}

	@Override
	public PageInfo<JadeShopDto> findAllJade(JadeShopDto dto) {
		PageHelper.startPage(dto.getPageNo(),dto.getPageSize());
		List<JadeShopDto> list = jadeShopDao.selectjade(dto);
		PageInfo<JadeShopDto> pageinfo = new PageInfo<JadeShopDto>(list);
		return pageinfo;
	}

	@Override
	public void delJade(String id) {
		jadeShopDao.delJade(id);

	}

	@Override
	public JadeShopDto selectJade(String id) {		
		return jadeShopDao.selectJadeById(id);
	}

	
	@Override
	public void updateImg(JadeShopDto dto) {
		jadeShopDao.updateImg(dto);

	}
	
	@Override
	public List<JadeShopDto> exportJade(JadeShopDto dto) {
		return jadeShopDao.exportJade(dto);
	}

}
