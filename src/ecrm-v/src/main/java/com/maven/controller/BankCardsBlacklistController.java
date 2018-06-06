package com.maven.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.cache.SystemCache;
import com.maven.constant.Constant.BooleanByte;
import com.maven.entity.BankCardsBlacklist;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BankCardsBlacklistService;

@Controller
@RequestMapping("/cardsblacklist")
public class BankCardsBlacklistController extends BaseController {
	private static LoggerManager log = LoggerManager.getLogger(BanksCardBinController.class.getName(), OutputManager.BANK_CARDS_BLACKLIST);
	@Override
	public LoggerManager getLogger() { return log; }
	
	@Autowired
	private BankCardsBlacklistService blacklistService;
	
	@RequestMapping("/index")
	public String index() {
		return "/userinfo/bankCardsBlacklist";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(HttpServletRequest request) {
		try {
			Map<String,Object> object = getRequestParamters(request);
			List<BankCardsBlacklist> list = blacklistService.selectAll(object);
			int count = blacklistService.selectAllCount(object);
			return formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error("获取银行卡黑名单错误!", e);
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public Map<String, Object> save(BankCardsBlacklist obj) {
		try {
			if (obj == null || obj.getUsername() == null)
				return packJSON(BooleanByte.NO, "参数不能为空");
			int result = blacklistService.add(obj);
			if (result > 0) {
				//重新初始化
				SystemCache.getInstance().initBankCardsBlacklist();
				return packJSON(BooleanByte.YES, "保存成功!");
			} else {
				return packJSON(BooleanByte.NO, "保存失败, 请稍后再试!");
			}
		} catch (Exception e) {
			log.Error("新增银行卡黑名单错误!", e);
			return packJSON(BooleanByte.NO, "系统错误, 新增黑名单失败!");
		}
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(BankCardsBlacklist obj) {
		try {
			if (obj == null || obj.getUsername() == null)
				return packJSON(BooleanByte.NO, "参数不能为空");
			int result = blacklistService.update(obj);
			if (result > 0) {
				//重新初始化
				SystemCache.getInstance().initBankCardsBlacklist();
				return packJSON(BooleanByte.YES, "修改成功!");
			} else {
				return packJSON(BooleanByte.NO, "修改失败, 请稍后再试!");
			}
		} catch (Exception e) {
			log.Error("修改银行卡黑名单错误!", e);
			return packJSON(BooleanByte.NO, "系统错误, 修改黑名单失败!");
		}
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam(value="lsh", required=true) Integer lsh) {
		try {
			int result = blacklistService.delete(lsh);
			if (result > 0) {
				//重新初始化
				SystemCache.getInstance().initBankCardsBlacklist();
				return packJSON(BooleanByte.YES, "删除成功!");
			} else {
				return packJSON(BooleanByte.NO, "修改失败, 请稍后再试!");
			}
		} catch (Exception e) {
			log.Error("删除银行卡黑名单错误!", e);
			return packJSON(BooleanByte.NO, "系统错误, 删除黑名单失败!");
		}
	}
}
