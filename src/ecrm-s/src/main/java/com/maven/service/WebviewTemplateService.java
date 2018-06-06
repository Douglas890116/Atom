package com.maven.service;

import java.util.List;
import java.util.Map;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.WebviewTemplate;

public interface WebviewTemplateService extends BaseServcie<WebviewTemplate>{
	
	@DataSource("slave")
	List<WebviewTemplate> queryTemplateData(Map<String, Object> parames);
	
	@DataSource("slave")
	List<WebviewTemplate> queryTemplateInCode(List<String> templatecodes);

	@DataSource("slave")
	int queryTemplateDataCount(Map<String, Object> parames);

	@DataSource("master")
	void saveTemplate(WebviewTemplate webviewTemplate)throws Exception;

	@DataSource("master")
	void deleteTemplate(String[] array)throws Exception;

	@DataSource("slave")
	WebviewTemplate getWebviewTemplate(Map<String, Object> parames)throws Exception;

	@DataSource("master")
	void updateTemplate(WebviewTemplate webviewTemplate)throws Exception;
	
}
