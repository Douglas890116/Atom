package com.maven.dao;

import java.util.List;
import java.util.Map;

import com.maven.base.dao.BaseDao;
import com.maven.entity.WebviewTemplate;

public interface WebviewTemplateDao extends BaseDao<WebviewTemplate>{

	List<WebviewTemplate> queryTemplateData(Map<String, Object> parames);
	
	List<WebviewTemplate> queryTemplateInCode(List<String> templatecodes);

	int queryTemplateDataCount(Map<String, Object> parames);

	void saveTemplate(WebviewTemplate webviewTemplate);

	void deleteTemplate(String[] array);

	WebviewTemplate getWebviewTemplate(Map<String, Object> parames);

	void updateTemplate(WebviewTemplate webviewTemplate);
	
}
