package org.slsale.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.HtmlEncode;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PageSupport;
import org.slsale.common.SQLTools;
import org.slsale.pojo.DataDictionary;
import org.slsale.pojo.Information;
import org.slsale.pojo.UploadTemp;
import org.slsale.pojo.User;
import org.slsale.service.DataDictionaryService;
import org.slsale.service.InformationService;
import org.slsale.service.UploadTempService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class InformationController {

	private Logger logger = Logger.getLogger(InformationController.class);
	@Resource
	private InformationService inforService;
	@Resource
	private DataDictionaryService dataService;
	@Resource
	private UploadTempService uploadTempService;

	// /informanage/information.html 咨讯页面
	@RequestMapping("/informanage/information.html")
	public ModelAndView information(HttpSession session, Model model,
			@RequestParam(value = "p", required = false) Integer currentPage) {
		if (session.getAttribute(Constants.SESSION_BASE_MODEL) == null) {
			return new ModelAndView("redirect:/");
		} else {
			Information information = new Information();
			PageSupport page = new PageSupport();
			page.setPageSize(4);
			List<Information> inforList = null;
			DataDictionary dataDictionary = new DataDictionary();
			List<DataDictionary> dicList = null;
			dataDictionary.setTypeCode("INFO_TYPE");
			try {
				dicList = dataService.getDataDictionaries(dataDictionary);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				page.setTotalCount(inforService.count(information));
			} catch (Exception e) {
				page.setTotalCount(0);
			}
			if (page.getTotalCount() > 0) {
				if (currentPage != null)
					page.setPage(currentPage);
				if (page.getPage() <= 0)
					page.setPage(1);
				if (page.getPage() > page.getPageCount())
					page.setPage(page.getPageCount());
				information.setPageSize(page.getPageSize());
				information.setStarNum((page.getPage() - 1) * page.getPageSize());
				try {
					inforList = inforService.getInformations(information);
				} catch (Exception e) {
					inforList = null;
					if (page == null) {
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(inforList);
			} else {
				page.setItems(null);
			}
			model.addAttribute("page", page);
			model.addAttribute("dicList", dicList);
			return new ModelAndView("/informanage/information");
		}
	}

	// /informanage/viewInfo.html 详情资讯信息
	@RequestMapping(value = "/informanage/viewInfo.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String viewInfo(@RequestParam(value = "id", required = false) String id) {
		String cjson = "";
		if (id == null || "".equals(id)) {
			return "nodata";
		} else {
			try {
				Information information = new Information();
				information.setId(Integer.valueOf(id));
				information = inforService.getInformationById(information);
				if (null != information && information.getTitle() != null) {
					information.setTitle(HtmlEncode.htmlDecode(information.getTitle()));
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
					cjson = JSONObject.fromObject(information, jsonConfig).toString();
				}
			} catch (Exception e) {
				return "failed";
			}
		}
		return cjson;
	}

	// /informanage/modifyInfoState.html 直接修改状态
	@RequestMapping(value = "/informanage/modifyInfoState.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String modifyInfoState(HttpSession session, @RequestParam String inforState) {
		if (inforState == null || "".equals(inforState)) {
			return "nodata";
		} else {
			try {
				JSONObject jsonObject = JSONObject.fromObject(inforState);
				Information information = (Information) JSONObject.toBean(jsonObject, Information.class);
				information.setUploadTime(new Date());
				information.setPublisher(((User) session.getAttribute(Constants.SESSION_USER)).getLoginCode());

				inforService.modifyInformation(information);
			} catch (Exception e) {
				return "failed";
			}
			return "success";
		}
	}

	// /informanage/modifyinformation.html 提交修改的资讯信息
	@RequestMapping(value = "/informanage/modifyinformation.html", method = RequestMethod.POST)
	public ModelAndView modifyinfomation(HttpSession session,
			@ModelAttribute("modifyInfomation") Information information) {
		if (session.getAttribute(Constants.SESSION_BASE_MODEL) == null) {
			return new ModelAndView("redirect:/");
		} else {
			try {
				User user = (User) session.getAttribute(Constants.SESSION_USER);
				information.setPublisher(user.getLoginCode());
				information.setPublishTime(new Date(System.currentTimeMillis()));
				information.setUploadTime(new Date(System.currentTimeMillis()));
				if (null != information.getTitle() && !information.getTitle().equals("")) {
					logger.debug(
							"======= modifyInformation HtmlEncode.htmlEncode(information.getTitle())================"
									+ HtmlEncode.htmlEncode(information.getTitle()));
					information.setTitle(HtmlEncode.htmlEncode(information.getTitle()));
				}
				inforService.modifyInformation(information);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:/informanage/information.html");
	}

	// /informanage/addInformation.html 提交新增的资讯信息
	@RequestMapping(value = "/informanage/addInformation.html", method = RequestMethod.POST)
	public ModelAndView addInformation(HttpSession session, @ModelAttribute("addInfomation") Information information) {
		if (session.getAttribute(Constants.SESSION_BASE_MODEL) == null) {
			return new ModelAndView("redirect:/");
		} else {
			try {
				User user = (User) session.getAttribute(Constants.SESSION_USER);
				information.setPublisher(user.getLoginCode());
				information.setPublishTime(new Date(System.currentTimeMillis()));
				information.setState(1);
				information.setUploadTime(new Date(System.currentTimeMillis()));
				if (null != information.getTitle() && !information.getTitle().equals("")) {
					information.setTitle(HtmlEncode.htmlEncode(information.getTitle()));
				}
				// 文件处理（先删除文件信息）
				UploadTemp uploadTemp = new UploadTemp();
				uploadTemp.setUploader(user.getLoginCode());
				uploadTemp.setUploadType("info");
				uploadTemp
						.setUploadFilePath(information.getFilePath().replaceAll("/", File.separator + File.separator));
				uploadTempService.delete(uploadTemp);

				inforService.addInformation(information);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:/informanage/information.html");
	}

	// /informanage/upload.html 文件上传处理
	@RequestMapping(value = "/informanage/upload.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public Object upload(
			@RequestParam(value = "uploadInformationFile", required = false) MultipartFile uploadInformationFile,
			@RequestParam(value = "uploadInformationFile", required = false) MultipartFile uploadInformationFileM,
			HttpServletRequest request, HttpSession session) {

		String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "infofiles");

		if (uploadInformationFile == null && uploadInformationFileM != null)
			uploadInformationFile = uploadInformationFileM;

		if (uploadInformationFile != null) {
			String oldFileName = uploadInformationFile.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(oldFileName);
			List<DataDictionary> list = null;
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setTypeCode("INFOFILE_SIZE");
			try {
				list = dataService.getDataDictionaries(dataDictionary);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int filesize = 500000000;
			if (null != list) {
				if (list.size() == 1) {
					filesize = Integer.valueOf(list.get(0).getValueName());
				}
			}
			if (uploadInformationFile.getSize() > filesize) {// 上传大小不得超过 500M
				return "1";
			} else {// 上传图片格式不正确
				String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_info." + prefix;
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 保存
				try {
					uploadInformationFile.transferTo(targetFile);
					// add file info to uploadtemp
					User sessionUser = ((User) session.getAttribute(Constants.SESSION_USER));
					UploadTemp uploadTemp = new UploadTemp();
					uploadTemp.setUploader(sessionUser.getLoginCode());
					uploadTemp.setUploadType("info");
					uploadTemp.setUploadFilePath(
							File.separator + "statics" + File.separator + "infofiles" + File.separator + fileName);
					uploadTempService.add(uploadTemp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String url = oldFileName + "[[[]]]" + request.getContextPath() + "/statics/infofiles/" + fileName
						+ "size:" + uploadInformationFile.getSize();
				return url;
			}
		}
		return null;
	}

	// /informanage/delInfoFile.html 删除文件
	@RequestMapping("/informanage/delInfoFile.html")
	@ResponseBody
	public Object delInfoFile(HttpServletRequest request, HttpSession session, @RequestParam String filePath) {
		if (null == filePath || "".equals(filePath)) {
			return "nodata";
		} else {
			try {
				String path = request.getSession().getServletContext().getRealPath("/");

				File file = new File(path + filePath);

				if (file.exists()) {
					file.delete();
				}
				Information information = new Information();
				information.setTypeName(filePath);
				information.setFileName("");
				information.setFilePath("#");
				information.setFileSize(0d);
				information.setUploadTime(new Date());
				inforService.modifyInformationFileInfo(information);

				UploadTemp uploadTemp = new UploadTemp();
				filePath = filePath.replaceAll("/", File.separator + File.separator);
				uploadTemp.setUploadFilePath(filePath);
				uploadTempService.delete(uploadTemp);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
			return "success";
		}

	}

	// /informanage/delInfo.html 删除资讯
	@RequestMapping("/informanage/delInfo.html")
	@ResponseBody
	public String delInfo(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "id", required = false) String id) {
		if (id == null && "".equals(id)) {
			return "nodata";
		} else {
			try {
				Information information = new Information();
				information.setId(Integer.valueOf(id));
				// 删除此资讯前，先查出上传的文件路径，删除文件
				Information _information = new Information();
				_information = inforService.getInformationById(information);
				if (null != _information) {
					String path = request.getSession().getServletContext().getRealPath("/");
					_information.setFilePath(_information.getFilePath().replace("/", File.separator + File.separator));
					File file = new File(path + _information.getFilePath());
					if (file.exists()) {
						file.delete();
					}
					inforService.delInformation(information);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
			return "success";
		}
	}

	// /informanage/downloadcenter.html 下载中心
	@RequestMapping("/informanage/downloadcenter.html")
	public ModelAndView downloadcenter(HttpSession session, Model model,
			@RequestParam(value = "p", required = false) Integer p,
			@RequestParam(value = "k", required = false) String k) {
		List<Information> informationList = null;
		if (session.getAttribute(Constants.SESSION_BASE_MODEL) == null) {
			return new ModelAndView("redirect:/");
		} else {
			PageSupport page = new PageSupport();
			page.setPageSize(8);
			Information information = new Information();
			information.setState(1);
			if (k != null && !"".equals(k)) {
				information.setTitle("%" + SQLTools.transfer(k) + "%");
			}
			try {
				page.setTotalCount(inforService.count(information));
			} catch (Exception e) {
				page.setTotalCount(0);
			}
			if (page.getTotalCount() > 0) {
				if (p != null)
					page.setPage(p);
				if (page.getPage() <= 0)
					page.setPage(1);
				if (page.getPage() > page.getPageCount())
					page.setPage(page.getPageCount());

				information.setStarNum((page.getPage() - 1) * page.getPageSize());
				information.setPageSize(page.getPageSize());

				try {
					informationList = inforService.getInformations(information);
				} catch (Exception e) {
					e.printStackTrace();
					informationList = null;
					if (page == null) {
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(informationList);
			} else {
				page.setItems(null);
			}

			model.addAttribute("page", page);
			model.addAttribute("k", k);
			return new ModelAndView("informanage/downloadcenter");
		}
	}

	// /informanage/portalinfoList.html 在首页点击资讯的more跳转的处理
	@RequestMapping("/informanage/portalinfoList.html")
	public ModelAndView portalinfoList(HttpSession session, Model model,
			@RequestParam(value = "p", required = false) Integer p,
			@RequestParam(value = "k", required = false) String k) {
		List<Information> informationList = null;
		if (session.getAttribute(Constants.SESSION_BASE_MODEL) == null) {
			return new ModelAndView("redirect:/");
		} else {
			PageSupport page = new PageSupport();
			page.setPageSize(8);
			Information information = new Information();
			information.setState(1);
			if (k != null && !"".equals(k)) {
				information.setTitle("%" + SQLTools.transfer(k) + "%");
			}
			try {
				page.setTotalCount(inforService.count(information));
			} catch (Exception e) {
				page.setTotalCount(0);
			}
			if (page.getTotalCount() > 0) {
				if (p != null)
					page.setPage(p);
				if (page.getPage() <= 0)
					page.setPage(1);
				if (page.getPage() > page.getPageCount())
					page.setPage(page.getPageCount());

				information.setStarNum((page.getPage() - 1) * page.getPageSize());
				information.setPageSize(page.getPageSize());

				try {
					informationList = inforService.getInformations(information);
				} catch (Exception e) {
					e.printStackTrace();
					informationList = null;
					if (page == null) {
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(informationList);
			} else {
				page.setItems(null);
			}

			model.addAttribute("page", page);
			model.addAttribute("k", k);
			return new ModelAndView("informanage/portalinfolist");
		}
	}

	// /informanage/portalInfoDetail.html 点击标题（首页和下载中心）的处理
	@RequestMapping(value = "/informanage/portalInfoDetail.html", produces = { "text/html;charset=UTF-8" })
	public ModelAndView portlInfoDetail(HttpSession session, Model model, @RequestParam Integer id) {
		if (session.getAttribute(Constants.SESSION_BASE_MODEL) == null) {
			return new ModelAndView("redirect:/");
		} else {
			if (id == null || "".equals(id)) {
				id = 0;
			} else {
				Information information = new Information();
				information.setId(id);
				try {
					information = inforService.getInformationById(information);
					if (information != null && information.getTitle() != null) {
						model.addAttribute("information", information);
					}
				} catch (Exception e) {
				}
			}
			return new ModelAndView("informanage/portalinfodetail");
		}
	}


}
