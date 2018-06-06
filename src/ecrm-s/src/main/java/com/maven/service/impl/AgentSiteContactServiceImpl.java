package com.maven.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.AgentSiteContactDao;
import com.maven.entity.AgentSiteContact;
import com.maven.service.AgentSiteContactService;
import com.maven.util.AttrCheckout;

@Service
public class AgentSiteContactServiceImpl 
	extends BaseServiceImpl<AgentSiteContact> implements AgentSiteContactService{
	
	@Autowired
	private AgentSiteContactDao agentSiteContactDao;

	@Override
	public BaseDao<AgentSiteContact> baseDao() {
		return agentSiteContactDao;
	}

	@Override
	public Class<AgentSiteContact> getClazz() {
		return AgentSiteContact.class;
	}

	@Override
	public AgentSiteContact selectByDomaincode(Integer domaincode) throws Exception {
		AgentSiteContact __contact = new AgentSiteContact();
		__contact.setDomaincode(domaincode);
		return super.selectFirst(__contact);
	}

	@Override
	public int saveContact(AgentSiteContact contact) throws Exception {
		return super.add(AttrCheckout.checkout(contact, false, new String[]{"domaincode"},new String[]{"qq","skype","vchat","email","phone"}));
	}

	@Override
	public int updateContact(AgentSiteContact contact) throws Exception {
		return super.update(AttrCheckout.checkout(contact, false, new String[]{"id"}));
	}

}
