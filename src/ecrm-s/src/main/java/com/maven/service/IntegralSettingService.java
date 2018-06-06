package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.IntegralSetting;
import com.maven.entity.LogLogin;

@Service
public interface IntegralSettingService extends BaseServcie<IntegralSetting> {

	@DataSource("master")
	int saveRecordBatch(List<IntegralSetting> list);
}
