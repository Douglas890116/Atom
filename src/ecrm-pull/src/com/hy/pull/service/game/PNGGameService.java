package com.hy.pull.service.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.FtpUtils;
import com.hy.pull.common.util.game.TAGGame;
import com.hy.pull.mapper.PngGameinfoMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;

/**
 * PNG游戏服务类（此类没有用到。因为PNG与TAG是一起的）
 * 创建日期 2016-12-29
 * @author temdy
 */
@Deprecated
@Service
public class PNGGameService extends BaseService {

	
	Logger logger = LogManager.getLogger(PNGGameService.class.getName());  
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private PngGameinfoMapper pngGameinfoMapper;

	/**
	 * 按条件拉取数据的方法
	 * @param entity 条件集合 {GAME_ID:游戏编号,PROXY_ID:代理编号,MAX_VALUE:上次拉取的最大值,GAME_KIND_ID:游戏种类编号}
	 * @return 数据行数
	 */
	@Override
	public Integer pullData(Map<String, Object> entity) throws Exception {
		int count = 0;//累计执行总数量
		//System.out.println("PNG游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为PNG游戏
			entity.put("GAME_ID", "15");//设置所属游戏编号
		}//获取PNG游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();
		if(size > 0){
			String ip = null;//ftp服务器IP
			String agent = null;//代理名称
			String max = null;//上次拉取的最大值
			String code = null;//代理编码
			if(entity.get("MAX_VALUE") != null){//判断是否存在最大值
				max = entity.get("MAX_VALUE").toString();
			}
			String userName = null;//用户名
			String password = null;//密码
			String dir = null;//数据文件目录
			String GAME_KIND_ID = null;//游戏种类编号
			if(entity.get("GAME_KIND_ID") != null){//判断是否存在游戏种类
				GAME_KIND_ID = entity.get("GAME_KIND_ID").toString();
			}
			int port = 21;//端口号
			int len = 0;
			int klen = 0;
			List<Map<String,Object>> data = null;
			String[] kinds = null;
			if(entity.get("GAME_KIND_ID") != null){//判断是否存在种类编号
				GAME_KIND_ID = entity.get("GAME_KIND_ID").toString();
				kinds = new String[]{GAME_KIND_ID};
			}else{
				kinds = new String[]{"AGIN","XIN","HUNTER"};
			}
			FtpUtils ftp = null;
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				password = entity.get("PROXY_API_URL").toString();
				ip = entity.get("PROXY_KEY2").toString();
				userName = entity.get("PROXY_NAME").toString();
				agent = entity.get("PROXY_NAME").toString();
				code = entity.get("PROXY_CODE") == null ? agent : entity.get("PROXY_CODE").toString();
				if(entity.get("PROXY_KEY1")!=null){
					port = Integer.parseInt(entity.get("PROXY_KEY1").toString());
				}
				ftp = new FtpUtils(ip,port,userName,password,"/");	
				klen = kinds.length;
				for(int j = 0; j < klen; j++){
					dir = kinds[j];//获取游戏数据目录
					data = TAGGame.getTAGData(agent, dir, max, ftp,code);//获取拉取数据列表;
					if(data != null){
						len = data.size();
						count += len;
						if(len > 0){//如果有数据就入库
							pngGameinfoMapper.batchInsert(data);//批量入库
						}
					}
				}
			}
		}
		return count;
	}
}
