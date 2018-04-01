package org.slsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.mapper.AfficheMapper;
import org.slsale.pojo.Affiche;
import org.springframework.stereotype.Service;

@Service
public class AfficheServiceImpl implements AfficheService {

	@Resource
	private AfficheMapper afficheMapper;

	@Override
	public List<Affiche> getAffiches(Affiche affiche) {
		// TODO Auto-generated method stub
		return afficheMapper.getAffiches(affiche);
	}

	@Override
	public int count(Affiche affiche) {
		// TODO Auto-generated method stub
		return afficheMapper.count(affiche);
	}

	@Override
	public int addAffiche(Affiche affiche) {
		// TODO Auto-generated method stub
		return afficheMapper.addAffiche(affiche);
	}

	@Override
	public Affiche getAfficheById(Affiche affiche) {
		// TODO Auto-generated method stub
		return afficheMapper.getAfficheById(affiche);
	}

	@Override
	public int modifyAffiche(Affiche affiche) {
		// TODO Auto-generated method stub
		return afficheMapper.modifyAffiche(affiche);
	}

	@Override
	public int delAffiche(Affiche affiche) {
		// TODO Auto-generated method stub
		return afficheMapper.delAffiche(affiche);
	}
	
	
	
}
