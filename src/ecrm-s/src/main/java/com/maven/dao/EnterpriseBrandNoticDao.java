package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EnterpriseBrandNotic;

@Repository
public interface EnterpriseBrandNoticDao extends BaseDao<EnterpriseBrandNotic>{
	/**
	 * 逻辑删除一条数据
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int logicDelete(Object object) throws Exception;
	
	/**
	 * 查询公告
	 * @param notic
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseBrandNotic> showUserNotic(EnterpriseBrandNotic notic)throws Exception;

}
