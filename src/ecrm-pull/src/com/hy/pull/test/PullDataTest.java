package com.hy.pull.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hy.pull.common.util.game.ZJGame;
import com.hy.pull.mapper.ZjGameinfoMapper;
import com.hy.pull.service.PullDataService;
import com.hy.pull.service.game.BBINGameService;
import com.hy.pull.service.game.SAGameService;

/**
 * 拉取数据单元测试类
 * 创建日期 2016-10-06 
 * 更新日期 2016-10-13 
 * @author temdy
 */
public class PullDataTest extends BaseTest {
	
	@Autowired
	private PullDataService pullDataService;
	
	@Autowired
	private ZjGameinfoMapper zjGameinfoMapper;
	
	@Autowired
	private BBINGameService bBINGameService;
	
	@Autowired
	private SAGameService sAGameService;

	/**
	 * 拉取TAG游戏数据测试方法(已测试通过)
	 */
	@Test
	public void testTAG() {	
		try {
			Map<String, Object> entity = new HashMap<String, Object>();
			entity.put("GAME_ID", "3");
			//entity.put("PROXY_ID", "5");
			entity.put("GAME_KIND_ID", "4");
			entity.put("ROUND_DATE", "2016-11-03");
			entity.put("START_TIME", "00:00:00");
			entity.put("END_TIME", "23:59:59");
			//List<Map<String,Object>> list = ZJGame.getZJData("http://zjapi.cg8080.com/zhouji/app/api.do","6750","lilongapi_05302907-42f9-4701-a427-62","6750");
			sAGameService.pullData(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//pullDataService.tag();
	}

	/**
	 * 拉取东方游戏数据测试方法(已测试通过)
	 */
//	@Test
//	public void testAOI() {
//		pullDataService.aoi();
//	}

//	/**
//	 * 拉取波音游戏数据测试方法(已测试通过)
//	 */
//	@Test
//	public void testBBIN() {
//		pullDataService.bbin();
//	}
//
//	/**
//	 * 拉取沙巴体育游戏数据测试方法(已测试通过)
//	 */
//	@Test
//	public void testIBC() {
//		pullDataService.ibc();
//	}
//
//	/**
//	 * 拉取新环球游戏数据测试方法(已测试通过)
//	 */
//	@Test
//	public void testNHQ() {
//		pullDataService.nhq();
//	}
//
//	/**
//	 * 拉取沙龙游戏数据测试方法(已测试通过)
//	 */
//	@Test
//	public void testSA() {
//		pullDataService.sa();
//	}

}
