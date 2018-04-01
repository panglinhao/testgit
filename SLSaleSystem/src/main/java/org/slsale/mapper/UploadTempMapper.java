package org.slsale.mapper;

import java.util.List;

import org.slsale.pojo.UploadTemp;

/**
 * UploadTempMapper
 * @author bdqn_hl
 * @date 2014-3-7
 */
public interface UploadTempMapper {
	/**
	 * getList
	 * @param uploadTemp
	 * @return
	 * @throws Exception
	 */
	public List<UploadTemp> getList(UploadTemp uploadTemp);
	/**
	 * add
	 * @param uploadTemp
	 * @return
	 * @throws Exception
	 */
	public int add(UploadTemp uploadTemp);
	/**
	 * delete
	 * @param uploadTemp
	 * @return
	 * @throws Exception
	 */
	public int delete(UploadTemp uploadTemp);
}
