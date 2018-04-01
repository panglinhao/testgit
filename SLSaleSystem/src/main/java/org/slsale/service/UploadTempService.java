package org.slsale.service;

import java.util.List;

import org.slsale.pojo.UploadTemp;

public interface UploadTempService {

	public List<UploadTemp> getList(UploadTemp uploadTemp);
	public int add(UploadTemp uploadTemp);
	public int delete(UploadTemp uploadTemp);
}
