package org.slsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.UploadTempMapper;
import org.slsale.pojo.UploadTemp;
import org.springframework.stereotype.Service;

@Service
public class UploadTempServiceIpl implements UploadTempService {
	
	@Resource
	private UploadTempMapper uploadMapper;

	@Override
	public List<UploadTemp> getList(UploadTemp uploadTemp) {
		// TODO Auto-generated method stub
		return uploadMapper.getList(uploadTemp);
	}

	@Override
	public int add(UploadTemp uploadTemp) {
		// TODO Auto-generated method stub
		return uploadMapper.add(uploadTemp);
	}

	@Override
	public int delete(UploadTemp uploadTemp) {
		// TODO Auto-generated method stub
		return uploadMapper.delete(uploadTemp);
	}

}
