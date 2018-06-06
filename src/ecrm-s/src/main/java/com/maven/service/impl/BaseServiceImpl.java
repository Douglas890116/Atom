package com.maven.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.service.BaseServcie;
import com.maven.constant.Enum_MSG;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.utility.ClassUtil;

@Service
public abstract class BaseServiceImpl<T> implements BaseServcie<T> {

	public abstract BaseDao<T> baseDao();

	public abstract Class<T> getClazz();
	
	
	@Override
	public List<T> selectAll(Object object)throws Exception{
		 return baseDao().selectAll(ClassUtil.getMapId(getClazz(), new Throwable()), object);
	}
	
	@Override
	public List<T> select(T t)throws Exception{
		return baseDao().select(ClassUtil.getMapId(getClazz(), new Throwable()), t);
	}

	@Override
	public T selectFirst(T object)throws Exception {
		List<T> list = select(object);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}
	
	@Override
	public T selectOne(T object) throws Exception{
		List<T> list = select(object);
		if (list.size() == 1){
			return list.get(0);
		}else if(list.size() > 1){
			throw new LogicTransactionRollBackException(Enum_MSG.数据异常.desc);
		}
		return null;
	}

	@Override
	public T selectByPrimaryKey(String id) {
		return baseDao().selectByPrimaryKey(ClassUtil.getMapId(getClazz(), new Throwable()), id);
	}
	
	@Override
	public int selectAllCount(Object params) throws Exception{
		return baseDao().selectAllCount(ClassUtil.getMapId(getClazz(), new Throwable()), params);
	}
	
	protected T save(T t) {
		return baseDao().save(ClassUtil.getMapId(getClazz(), new Throwable()), t);
	}

	protected int add(Object obj) {
		return baseDao().add(ClassUtil.getMapId(getClazz(), new Throwable()), obj);
	}

	protected int saveRecordBatch(List<T> list) {
		return baseDao().saveRecordBatch(ClassUtil.getMapId(getClazz(), new Throwable()), list);
	}
	
	protected int update(Object object) throws Exception {
		return baseDao().update(ClassUtil.getMapId(getClazz(), new Throwable()), object);
	}
	
	protected int updateBatch(Object object){
		return baseDao().update(ClassUtil.getMapId(getClazz(), new Throwable()), object);
	}

	protected int delete(Object object) throws Exception {
		return baseDao().delete(ClassUtil.getMapId(getClazz(), new Throwable()), object);
	}
	
	protected int logicDelete(Object object) throws Exception{
		return baseDao().logicDelete(ClassUtil.getMapId(getClazz(), new Throwable()), object);
	}

	

}
