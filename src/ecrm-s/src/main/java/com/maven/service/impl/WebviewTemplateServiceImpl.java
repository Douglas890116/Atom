package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.WebviewTemplateDao;
import com.maven.entity.WebviewTemplate;
import com.maven.service.WebviewTemplateService;
@Service
public class WebviewTemplateServiceImpl extends BaseServiceImpl<WebviewTemplate> implements WebviewTemplateService {

	@Autowired
	private WebviewTemplateDao webviewTemplateDao;
	
	@Override
	public List<WebviewTemplate> queryTemplateData(Map<String, Object> parames) {
		return webviewTemplateDao.queryTemplateData(parames);
	}

	@Override
	public int queryTemplateDataCount(Map<String, Object> parames) {
		return webviewTemplateDao.queryTemplateDataCount(parames);
	}

	@Override
	public void saveTemplate(WebviewTemplate webviewTemplate) throws Exception {
		webviewTemplateDao.saveTemplate(webviewTemplate);
	}

	@Override
	public void deleteTemplate(String[] array) throws Exception {
		webviewTemplateDao.deleteTemplate(array);
	}

	@Override
	public WebviewTemplate getWebviewTemplate(Map<String, Object> parames) throws Exception {
		return webviewTemplateDao.getWebviewTemplate(parames);
	}

	@Override
	public void updateTemplate(WebviewTemplate webviewTemplate) throws Exception {
		webviewTemplateDao.updateTemplate(webviewTemplate);
	}

	@Override
	public List<WebviewTemplate> queryTemplateInCode(List<String> templatecodes) {
		return webviewTemplateDao.queryTemplateInCode(templatecodes);
	}

	@Override
	public BaseDao<WebviewTemplate> baseDao() {
		return webviewTemplateDao;
	}

	@Override
	public Class<WebviewTemplate> getClazz() {
		return WebviewTemplate.class;
	}

}
