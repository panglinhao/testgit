package org.slsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.InformationMapper;
import org.slsale.pojo.Information;
import org.springframework.stereotype.Service;

@Service
public class InformationServiceImpl implements InformationService {
	@Resource
	private InformationMapper informationMapper;

	@Override
	public List<Information> getInformations(Information information) {
		// TODO Auto-generated method stub
		return informationMapper.getInformations(information);
	}

	@Override
	public int count(Information information) {
		// TODO Auto-generated method stub
		return informationMapper.count(information);
	}

	@Override
	public Information getInformationById(Information information) {
		// TODO Auto-generated method stub
		return informationMapper.getInformationById(information);
	}

	@Override
	public int modifyInformation(Information information) {
		// TODO Auto-generated method stub
		return informationMapper.modifyInformation(information);
	}

	@Override
	public int addInformation(Information information) {
		// TODO Auto-generated method stub
		return informationMapper.addInformation(information);
	}

	@Override
	public int delInformation(Information information) {
		// TODO Auto-generated method stub
		return informationMapper.delInformation(information);
	}

	@Override
	public int modifyInformationFileInfo(Information information) {
		// TODO Auto-generated method stub
		return informationMapper.modifyInformationFileInfo(information);
	}

}
