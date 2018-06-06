package com.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StringFindWithList {
	
	public static void main(String[] args) {
		String s = "/EInformation/list,/EOBrand/list,/EEmployee/userJsp/index,/EmployeeInformation/userInfo/index,/PermissionMenu/list,/EInformation/save,/takeDepositRecord/takeDeposit/index?orderType=1,/Enterprise/data,/EEmployee/deleteSelectEmployee,/common/resetLoginPassword,/EmployeeInformation/saveEnterpriseEmployeeInformation,/EmployeeInformation/updateEnterpriseEmployeeInformation,/takeDepositRecord/takeDeposit/index?orderType=2,/workingFlowConfigurationControll/depositIndex,/workingFlowConfigurationControll/saveWorkingFlowConfiguration,/workingFlowConfigurationControll/updateWorkingFlowConfiguration,/workingFlowConfigurationControll/depositWorkingFlowConfigurationDelete,/workingFlowConfigurationControll/takeIndex,/workingFlowConfigurationControll/saveTakeWorkingFlowConfiguration,/workingFlowConfigurationControll/updateTakeWorkingFlowConfiguration,/workingFlowConfigurationControll/takeWorkingFlowConfigurationDelete,/EInformation/update,/EInformation/delete,/EInformation/data,/Department/data,/Department/save,/Department/update,/EOBrand/data,/EOBrand/save,/takeDepositRecord/depositApprove,/takeDepositRecord/takeApprove,/takeDepositRecord/takeDepositRecordAdd,/EEmployee/userJsp/userAdd,/employeeOperating/userJsp/employeeIndex,/enterpriseType/userJsp/enterpriseIndex,/employeeAgent/userJsp/agentEmployeeIndex,/takeDepositRecord/countDepositTakeRecordDatail,/takeDepositRecord/depositTakeRecordDatail,/registerLink/expandDomainAdd,/registerLink/expandDomainIndex,/brandGame/saveBrandGame,enterpriseType/deleteSelectEmployee,/enterpriseType/findEmployee,/common/resetLoginPassword,/common/resetCapitalPassword,/EmployeeMPG/permission,/employeeOperating/findEmployee,/employeeOperating/userJsp/employeeAdd,/common/resetLoginPassword,/employeeOperating/deleteSelectEmployee,/common/resetCapitalPassword,/EmployeeMPG/permission,/registerLink/queryRegisterLink,/EEmployee/findEmployee,/common/resetCapitalPassword,/GCBonus/setting,/employeeAgent/findEmployee,/employeeAgent/deleteSelectEmployee,/common/resetLoginPassword,/common/resetCapitalPassword,/EmployeeMPG/permission,/GCBonus/setting,/EmployeeInformation/unlockingBank,/takeDepositRecord/findDepositApproveRecord,/takeDepositRecord/approve,/takeDepositRecord/findTakeApproveRecord,/takeDepositRecord/approve,/takeDepositRecord/updateTakeDepositRecord,/common/queryDepositTakeApproveRecord,/takeDepositRecord/findTakeDepositRecord,/common/queryDepositTakeApproveRecord,/takeDepositRecord/findTakeDepositRecord,/workingFlowConfigurationControll/addDepositWorkingFlowApproveEmployee,/workingFlowConfigurationControll/queryDepositWorkingFlowConfiguration,/workingFlowConfigurationControll/addDepositWorkingFlowApproveEmployee,/EmployeeInformation/findBankInfo,/workingFlowConfigurationControll/queryTakeWorkingFlowConfiguration,/registerLink/mianDomainSetting,/takeDepositRecord/rejectedPage,/takeDepositRecord/findRejectData,/takeDepositRecord/updateTakeDepositRecord,/registerLink/registerLinkAdd,/registerLink/registerLinkIndex,/HQGame/list,/BBINGame/list,/AGGame/list,/OGGame/list,/PTGame/list,/IBCGame/list,/XCPGame/list";
		String requesturl = "/XCPGame/list";
		long date = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			if(s.indexOf(requesturl)>0){
				
			};
		}
		System.out.println(System.currentTimeMillis()-date);
		List<String> list = new ArrayList<String>();
		list.add("/EInformation/list");
		list.add("/EOBrand/list");
		list.add("/EEmployee/userJsp/index");
		list.add("/EmployeeInformation/userInfo/index");
		list.add("/PermissionMenu/list");
		list.add("/EInformation/save");
		list.add("/takeDepositRecord/takeDeposit/index?orderType=1");
		list.add("/Enterprise/data");
		list.add("/EEmployee/deleteSelectEmployee");
		list.add("/common/resetLoginPassword");
		list.add("/EmployeeInformation/saveEnterpriseEmployeeInformation");
		list.add("/EmployeeInformation/updateEnterpriseEmployeeInformation");
		list.add("/takeDepositRecord/takeDeposit/index?orderType=2");
		list.add("/workingFlowConfigurationControll/depositIndex");
		list.add("/workingFlowConfigurationControll/saveWorkingFlowConfiguration");
		list.add("/workingFlowConfigurationControll/updateWorkingFlowConfiguration");
		list.add("/workingFlowConfigurationControll/depositWorkingFlowConfigurationDelete");
		list.add("/workingFlowConfigurationControll/takeIndex");
		list.add("/workingFlowConfigurationControll/saveTakeWorkingFlowConfiguration");
		list.add("/workingFlowConfigurationControll/updateTakeWorkingFlowConfiguration");
		list.add("/workingFlowConfigurationControll/takeWorkingFlowConfigurationDelete");
		list.add("/EInformation/update");
		list.add("/EInformation/delete");
		list.add("/EInformation/data");
		list.add("/Department/data");
		list.add("/Department/save");
		list.add("/Department/update");
		list.add("/EOBrand/data");
		list.add("/EOBrand/save");
		list.add("/takeDepositRecord/depositApprove");
		list.add("/takeDepositRecord/takeApprove");
		list.add("/takeDepositRecord/takeDepositRecordAdd");
		list.add("/EEmployee/userJsp/userAdd");
		list.add("/employeeOperating/userJsp/employeeIndex");
		list.add("/enterpriseType/userJsp/enterpriseIndex");
		list.add("/employeeAgent/userJsp/agentEmployeeIndex");
		list.add("/takeDepositRecord/countDepositTakeRecordDatail");
		list.add("/takeDepositRecord/depositTakeRecordDatail");
		list.add("/registerLink/expandDomainAdd");
		list.add("/registerLink/expandDomainIndex");
		list.add("/brandGame/saveBrandGame");
		list.add("enterpriseType/deleteSelectEmployee");
		list.add("/enterpriseType/findEmployee");
		list.add("/common/resetLoginPassword");
		list.add("/common/resetCapitalPassword");
		list.add("/EmployeeMPG/permission");
		list.add("/employeeOperating/findEmployee");
		list.add("/employeeOperating/userJsp/employeeAdd");
		list.add("/common/resetLoginPassword");
		list.add("/employeeOperating/deleteSelectEmployee");
		list.add("/common/resetCapitalPassword");
		list.add("/EmployeeMPG/permission");
		list.add("/registerLink/queryRegisterLink");
		list.add("/EEmployee/findEmployee");
		list.add("/common/resetCapitalPassword");
		list.add("/GCBonus/setting");
		list.add("/employeeAgent/findEmployee");
		list.add("/employeeAgent/deleteSelectEmployee");
		list.add("/common/resetLoginPassword");
		list.add("/common/resetCapitalPassword");
		list.add("/EmployeeMPG/permission");
		list.add("/GCBonus/setting");
		list.add("/EmployeeInformation/unlockingBank");
		list.add("/takeDepositRecord/findDepositApproveRecord");
		list.add("/takeDepositRecord/approve");
		list.add("/takeDepositRecord/findTakeApproveRecord");
		list.add("/takeDepositRecord/approve");
		list.add("/takeDepositRecord/updateTakeDepositRecord");
		list.add("/common/queryDepositTakeApproveRecord");
		list.add("/takeDepositRecord/findTakeDepositRecord");
		list.add("/common/queryDepositTakeApproveRecord");
		list.add("/takeDepositRecord/findTakeDepositRecord");
		list.add("/workingFlowConfigurationControll/addDepositWorkingFlowApproveEmployee");
		list.add("/workingFlowConfigurationControll/queryDepositWorkingFlowConfiguration");
		list.add("/workingFlowConfigurationControll/addDepositWorkingFlowApproveEmployee");
		list.add("/EmployeeInformation/findBankInfo");
		list.add("/workingFlowConfigurationControll/queryTakeWorkingFlowConfiguration");
		list.add("/registerLink/mianDomainSetting");
		list.add("/takeDepositRecord/rejectedPage");
		list.add("/takeDepositRecord/findRejectData");
		list.add("/takeDepositRecord/updateTakeDepositRecord");
		list.add("/registerLink/registerLinkAdd");
		list.add("/registerLink/registerLinkIndex");
		list.add("/HQGame/list");
		list.add("/BBINGame/list");
		list.add("/AGGame/list");
		list.add("/OGGame/list");
		list.add("/PTGame/list");
		list.add("/IBCGame/list");
		list.add("/XCPGame/list");
		date = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			Iterator<String> iterator = list.iterator();
	    	while(iterator.hasNext()){
	    		String url = iterator.next();
	    		if(url.indexOf(requesturl)>0){
	    			
	    		}
	    	}
		}
    	System.out.println(System.currentTimeMillis()-date);
	}

}
