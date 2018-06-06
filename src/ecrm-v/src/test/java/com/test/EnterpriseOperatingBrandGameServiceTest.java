package com.test;

import java.util.ArrayList;
import java.util.List;

import com.maven.config.SpringContextUtil;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.service.EnterpriseOperatingBrandGameService;
import com.maven.service.EnterpriseOperatingBrandService;

import junit.framework.TestCase;

public class EnterpriseOperatingBrandGameServiceTest extends TestCase {

	public void testBrandGameAccredit() {
		try {
			EnterpriseOperatingBrandGameService brandGameService = SpringContextUtil.getBean("enterpriseOperatingBrandGameServiceImpl");
			EnterpriseOperatingBrandGame brandGame = new EnterpriseOperatingBrandGame();
			brandGame.setBrandcode("EB000001");
			EnterpriseOperatingBrandGame brandGame2 = new EnterpriseOperatingBrandGame();
			brandGame2.setBrandcode("EB000001");
			List<EnterpriseOperatingBrandGame> list = new ArrayList<EnterpriseOperatingBrandGame>();
			list.add(brandGame);
			list.add(brandGame2);
			brandGameService.tc_brandGameAccredit("EB000001",list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testTakeBrandGames() {
		EnterpriseOperatingBrandGameService brandGameService = SpringContextUtil.getBean("enterpriseOperatingBrandGameServiceImpl");
		try {
			List<EnterpriseOperatingBrandGame> list  = brandGameService.takeBrandGames(new EnterpriseOperatingBrandGame("EB000001"));
			for (EnterpriseOperatingBrandGame e : list) {
				System.out.println(e.getBrandcode());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testGetAllBand(){
		try {
			EnterpriseOperatingBrandService ss = SpringContextUtil.getBean("enterpriseOperatingBrandServiceImpl");
			List<EnterpriseOperatingBrand> list = ss.getAllBrand(null);
			for (EnterpriseOperatingBrand e : list) {
				System.out.println(e.getBrandname());
			}
			System.out.println(ss.getAllBrandCount(null));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	

}
