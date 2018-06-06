package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.constant.Enum_MSG;
import com.maven.dao.EnterpriseEmployeeInformationDao;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.TLogger;
import com.maven.service.EnterpriseEmployeeInformationService;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;
/**
 * 用户银行信息ServiceImpl
 * @author Ethan
 *
 */
@Service
public class EnterpriseEmployeeInformationServiceImpl  extends BaseServiceImpl<EnterpriseEmployeeInformation> 
	implements EnterpriseEmployeeInformationService {
	
	@Autowired
	private EnterpriseEmployeeInformationDao enterpriseEmployeeInformationDao;
	@Override
	public BaseDao<EnterpriseEmployeeInformation> baseDao() {
		return enterpriseEmployeeInformationDao;
	}

	@Override
	public Class<EnterpriseEmployeeInformation> getClazz() {
		return EnterpriseEmployeeInformation.class;
	}
	/**
	 * 保存用户银行信息
	 */
	public int tc_saveEnterpriseEmployeeInformation(EnterpriseEmployeeInformation enterpriseEmployeeInformation) throws Exception{
		AttrCheckout.checkout(enterpriseEmployeeInformation, false, new String[]{"employeecode","parentemployeecode","enterprisecode","bankcode","openningbank","paymentaccount","accountname"});
		if( !enterpriseEmployeeInformation.getBankcode().equals("B019") && !enterpriseEmployeeInformation.getBankcode().equals("B020")) {
			if(StringUtils.isBlank(enterpriseEmployeeInformation.getPaymentaccount())
					||enterpriseEmployeeInformation.getPaymentaccount().length()<16
					||enterpriseEmployeeInformation.getPaymentaccount().length()>19){
				throw new LogicTransactionRollBackException(Enum_MSG.银行卡长度不匹配.desc);
			}
		}
		
		EnterpriseEmployeeInformation __eei = enterpriseEmployeeInformationDao.findEnterpriseEmployeeInformation(enterpriseEmployeeInformation.getEnterprisecode(), enterpriseEmployeeInformation.getPaymentaccount());
		if(__eei!=null) throw new LogicTransactionRollBackException(Enum_MSG.银行卡已绑定.desc);
		return enterpriseEmployeeInformationDao.saveEnterpriseEmployeeInformation(enterpriseEmployeeInformation);
	}
	/**
	 * 分页查询
	 * @param obj
	 * @return List<EnterpriseEmployeeInformation>
	 */
	public List<EnterpriseEmployeeInformation> findEmployeeInfo(Map<String, Object> obj) throws Exception{
		return enterpriseEmployeeInformationDao.findEmployeeInfo(obj);
	}
	/**
	 * 查询统计
	 * @param obj
	 * @return int
	 */
	public int findEmployeeInfoCount(Map<String, Object> obj) {
		return enterpriseEmployeeInformationDao.findEmployeeInfoCount(obj);
	}
	/**
	 * 银行信息单条记录删除
	 * @param request
	 */
	public void tc_deleteBankInfo(String code) throws Exception{
		enterpriseEmployeeInformationDao.deleteBankInfo(code);
	}
	/**
	 * 删除选中的一条或者多条数据
	 * @param request
	 */
	public void tc_deleteSelectBankInfo(String[] array)throws Exception {
		enterpriseEmployeeInformationDao.deleteSelectBankInfo(array);
	}
	/**
	 * 查询单个实体对象
	 * @param informationcode
	 * @return EnterpriseEmployeeInformation
	 */
	public EnterpriseEmployeeInformation findOneBankInfo(String informationcode) {
		return enterpriseEmployeeInformationDao.findOneBankInfo(informationcode);
	}
	
	/**
	 * 锁定与解锁银行卡
	 * @param eei
	 * @return
	 * @throws Exception
	 */
	@Override
	public int tc_lockEmployeeInformation(EnterpriseEmployeeInformation eei)throws Exception{
		return enterpriseEmployeeInformationDao.updateEnterpriseEmployeeInformation(AttrCheckout.checkout(eei, true, new String[]{"informationcode","status"}));
	}
	/**
	 * 用户修改自己银行卡信息
	 * @param e_information
	 */
	@Override
	public int tc_updateEnterpriseEmployeeInformation(EnterpriseEmployeeInformation e_information) throws Exception{
		List<EnterpriseEmployeeInformation> eis = enterpriseEmployeeInformationDao.findEmployeeInfo(
						BeanToMapUtil.convertBean(AttrCheckout.checkout(
								e_information, true, new String[]{"enterprisecode","employeecode","informationcode"}), false));
		if(eis.size()!=1) 
			throw new LogicTransactionRollBackException(Enum_MSG.取款卡不存在.desc);
		EnterpriseEmployeeInformation ei = eis.get(0);
		if(!ei.getStatus().equals(EnterpriseEmployeeInformation.Enum_status.解锁.value)){
			throw new LogicTransactionRollBackException(Enum_MSG.银行卡未解锁.desc);
		}
		TLogger.getLogger().Debug("传入银行卡号参数："+e_information.getPaymentaccount()+"  原有银行卡号："+ei.getPaymentaccount());
		if(StringUtils.isNotBlank(e_information.getPaymentaccount())&&!ei.getPaymentaccount().equals(e_information.getPaymentaccount())){
			EnterpriseEmployeeInformation information  = super.selectOne(
					AttrCheckout.checkout(e_information, true, new String[]{"enterprisecode","paymentaccount"}));
			TLogger.getLogger().Debug("根据传入银行卡号查询对象："+information);
			if(information!=null && !information.getInformationcode().equals(e_information.getInformationcode())){
				TLogger.getLogger().Debug("根据传入银行卡号查询对象编码："+information.getInfomationcomment()+"  传入银行卡号编码："+e_information.getInformationcode());
				throw new LogicTransactionRollBackException(Enum_MSG.银行卡已绑定.desc);
			}
		}
		e_information.setStatus(EnterpriseEmployeeInformation.Enum_status.锁定.value);
		TLogger.getLogger().Debug("校验通过,执行修改");
		enterpriseEmployeeInformationDao.updateEnterpriseEmployeeInformation(
				AttrCheckout.checkout(e_information, false, new String[]{"informationcode","employeecode"}));
		return 1;
	}
	
   /**
     * 管理员修改用户的银行卡信息
     * @param e_information
     */
    @Override
    public int tc_updateEnterpriseEmployeeInformationByAdmin(EnterpriseEmployeeInformation e_information) throws Exception{
        List<EnterpriseEmployeeInformation> eis = enterpriseEmployeeInformationDao.findEmployeeInfo(
                        BeanToMapUtil.convertBean(AttrCheckout.checkout(
                                e_information, true, new String[]{"enterprisecode","employeecode","informationcode"}), false));
        if(eis.size()!=1) throw new LogicTransactionRollBackException(Enum_MSG.取款卡不存在.desc);
        EnterpriseEmployeeInformation ei = eis.get(0);
        TLogger.getLogger().Debug("传入银行卡号参数："+e_information.getPaymentaccount()+"  原有银行卡号："+ei.getPaymentaccount());
        if(StringUtils.isNotBlank(e_information.getPaymentaccount())&&!ei.getPaymentaccount().equals(e_information.getPaymentaccount())){
            EnterpriseEmployeeInformation information  = super.selectOne(
                    AttrCheckout.checkout(e_information, true, new String[]{"enterprisecode","paymentaccount"}));
            TLogger.getLogger().Debug("根据传入银行卡号查询对象："+information);
            if(information!=null && !information.getInformationcode().equals(e_information.getInformationcode())){
                TLogger.getLogger().Debug("根据传入银行卡号查询对象编码："+information.getInfomationcomment()+"  传入银行卡号编码："+e_information.getInformationcode());
                throw new LogicTransactionRollBackException(Enum_MSG.银行卡已绑定.desc);
            }
        }
        e_information.setStatus(EnterpriseEmployeeInformation.Enum_status.锁定.value);
        TLogger.getLogger().Debug("校验通过,执行修改");
        enterpriseEmployeeInformationDao.updateEnterpriseEmployeeInformation(
                AttrCheckout.checkout(e_information, false, new String[]{"informationcode","employeecode"}));
        return 1;
    }
	/**
	 * 根据员工编码删除所有的银行信息
	 * @param employeecode
	 * @throws RuntimeException
	 */
	@Override
	public void tc_deleteEmployeeBankInfo(String employeecode) throws Exception {
		enterpriseEmployeeInformationDao.deleteEmployeeBankInfo(employeecode);
	}
	
	/**
	 * 批量删除不同用户的银行信息
	 * @param array
	 */
	@Override
	public void tc_deleteSelectEmployeeBankInfo(String[] array)throws Exception {
		enterpriseEmployeeInformationDao.deleteSelectEmployeeBankInfo(array);
	}
	
	/**
	 * 根据银行卡查询单个实体对象
	 * @param paymentaccount
	 * @return EnterpriseEmployeeInformation
	 */
	@Override
	public EnterpriseEmployeeInformation findEnterpriseEmployeeInformation(String enterprisecode,String paymentaccount) {
		return enterpriseEmployeeInformationDao.findEnterpriseEmployeeInformation(enterprisecode,paymentaccount);
	}
	
	/**
	 * 查询当前登录用户的信息
	 * @param loginEmployee
	 * @return
	 */
	@Override
	public EnterpriseEmployeeInformation queryAccountData(EnterpriseEmployee loginEmployee) throws Exception {
		return enterpriseEmployeeInformationDao.queryAccountData(loginEmployee);
	}
	
	/**
	 * 根据开户名称与银行卡查询是否存在
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	@Override
	public EnterpriseEmployeeInformation queryBankCardIsNoExist(Map<String, Object> paramter) throws Exception {
		return enterpriseEmployeeInformationDao.checkBankCardIsNoExist(paramter);
	}

	@Override
	public int tc_deleteEmployeeBankCard(String employeecode, String informationcode) throws Exception {
		return enterpriseEmployeeInformationDao.deleteEmployeeBankCard(employeecode, informationcode);
	}
	
	/**
	 * 批量逻辑删除不同用户的银行信息
	 * @param array
	 */
	@Override
	public void tc_logicDeleteSelectEmployeeBankInfo(String[] array)throws Exception {
		enterpriseEmployeeInformationDao.logicDeleteSelectEmployeeBankInfo(array);
	}
}
