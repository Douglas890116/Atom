package com.maven.service;

import com.maven.config.SpringContextUtil;
import com.maven.entity.AgentSiteContact;
import com.maven.entity.BrandDomain;

import junit.framework.TestCase;

public class BrandDomainServiceTest extends TestCase {

	public void testTc_save() {
		fail("Not yet implemented");
	}

	public void testTc_saveAgentDomainAndContact() {
		BrandDomainService service = SpringContextUtil.getBean("brandDomainServiceImpl");
		try {
			BrandDomain brandomain = new BrandDomain();
			brandomain.setDomainlink(null);
			service.tc_saveAgentDomainAndContact(brandomain, new AgentSiteContact());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testTc_createAgentDefualtDomain() {
		fail("Not yet implemented");
	}

	public void testTc_updateRegisterLink() {
		fail("Not yet implemented");
	}

	public void testTc_deleteSelect() {
		fail("Not yet implemented");
	}

	public void testTc_logicDeleteByEmployeecode() {
		fail("Not yet implemented");
	}

	public void testQueryByDomainLink() {
		fail("Not yet implemented");
	}

	public void testTakeAllExpandDomain() {
		fail("Not yet implemented");
	}

	public void testTakeBrandDomain() {
		fail("Not yet implemented");
	}

	public void testTakeBrandDomainCount() {
		fail("Not yet implemented");
	}

	public void testQueryDetectionDomainLinkExit() {
		fail("Not yet implemented");
	}

	public void testTc_SetttingDefualtDomain() {
		fail("Not yet implemented");
	}

	public void testTc_DeleteMainDomain() {
		fail("Not yet implemented");
	}

	public void testTakeEDefualtUSiteDomain() {
		fail("Not yet implemented");
	}

	public void testTakeEDefualtASiteDomain() {
		fail("Not yet implemented");
	}

	public void testTakeSecondMainDomain() {
		fail("Not yet implemented");
	}

	public void testTakeDefualtDomainPrefix() {
		fail("Not yet implemented");
	}

	public void testTc_BatchLogicDelete() {
		fail("Not yet implemented");
	}

}
