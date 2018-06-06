package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.WebviewTemplateDao;
import com.maven.entity.WebviewTemplate;
@Repository
public class WebviewTemplateDaoImpl extends BaseDaoImpl<WebviewTemplate> implements WebviewTemplateDao {

	@Override
	public List<WebviewTemplate> queryTemplateData(Map<String, Object> parames) {
		return getSqlSession().selectList("WebviewTemplate.select", parames);
	}

	@Override
	public int queryTemplateDataCount(Map<String, Object> parames) {
		return getSqlSession().selectOne("WebviewTemplate.selectCount",parames);
	}
	
	@Override
	public void saveTemplate(WebviewTemplate webviewTemplate) {
		getSqlSession().insert("WebviewTemplate.saveTemplate", webviewTemplate);
	}

	@Override
	public void deleteTemplate(String[] array) {
		getSqlSession().delete("WebviewTemplate.deleteTemplate", array);
	}

	@Override
	public WebviewTemplate getWebviewTemplate(Map<String, Object> parames) {
		return getSqlSession().selectOne("WebviewTemplate.getWebviewTemplate",parames);
	}

	@Override
	public void updateTemplate(WebviewTemplate webviewTemplate) {
		getSqlSession().update("WebviewTemplate.updateTemplate", webviewTemplate);
	}

	@Override
	public List<WebviewTemplate> queryTemplateInCode(List<String> templatecodes) {
		return getSqlSession().selectList("WebviewTemplate.selectWithIn", templatecodes);
	}
}
