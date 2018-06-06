package com.maven.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 域名帮助类，用于对域名的相关操作
 * 
 * @author klay
 *
 */
public class DomainUtils {
	/**
	 * 目前世界上的所有顶级域名
	 */
	public static final String[] TOP_LEVEL_DOMAIN = {
			// 常见顶级域名
			".com", ".top", ".cx", ".red", ".net", ".org", ".gov", ".edu", ".mil", ".biz", ".name", ".info", ".mobi",
			// 国家地区顶级域名
			".ac", ".ad", ".ae", ".af", ".ag", ".ai", ".al", ".am", ".ao", ".aq", ".ar", ".as", ".asia", ".at", ".au", ".aw", ".ax", ".az", ".ba",
			".bb", ".bd", ".be", ".bf", ".bg", ".bh", ".bi", ".bj", ".bm", ".bn", ".bo", ".br", ".bs", ".bt", ".bw", ".by", ".bz",
			".ca", ".cc", ".cd", ".cf", ".cg", ".ch", ".ci", ".ck", ".cl", ".cm", ".cn", ".co", ".cr", ".cu", ".cv", ".cw", ".cx", ".cy", ".cz",
			".de", ".dj", ".dk", ".dm", ".do", ".dz",
			".ec", ".ee", ".eg", ".er", ".es", ".et", ".eu",
			".fi", ".fj", ".fk", ".fm", ".fo", ".fr",
			".ga", ".gd", ".ge", ".gf", ".gg", ".gh", ".gi", ".gl", ".gm", ".gn", ".gp", ".gq", ".gr", ".gs", ".gt", ".gu", ".gw", ".gy",
			".hk", ".hm", ".hn", ".hr", ".ht", ".hu",
			".id", ".ie", ".il", ".im", ".in", ".io", ".iq", ".ir", ".is", ".it",
			".je", ".jm", ".jo", ".jp",
			".ke", ".kg", ".kh", ".ki", ".km", ".kn", ".kp", ".kr", ".kw", ".ky", ".kz",
			".la", ".lb", ".lc", ".li", ".lk", ".lr", ".ls", ".lt", ".lu", ".lv", ".ly",
			".ma", ".mc", ".md", ".me", ".mg", ".mh", ".mk", ".ml", ".mm", ".mn", ".mo", ".mp", ".mq", ".mr", ".ms", ".mt", ".mu", ".mv", ".mw", ".mx", ".my", ".mz",
			".na", ".nc", ".ne", ".nf", ".ng", ".ni", ".nl", ".no", ".np", ".nr", ".nu", ".nz",
			".om",
			".pa", ".pe", ".pf", ".pg", ".ph", ".pk", ".pl", ".pm", ".pn", ".pr", ".ps", ".pt", ".pw", ".py",
			".qa",
			".re", ".ro", ".rs", ".ru", ".rw",
			".sa", ".sb", ".sc", ".sd", ".se", ".sg", ".sh", ".si", ".sk", ".sl", ".sm", ".sn", ".so", ".sr", ".ss", ".st", ".su", ".sv", ".sx", ".sy", ".sz",
			".tc", ".td", ".tf", ".tg", ".th", ".tj", ".tk", ".tl", ".tm", ".tn", ".to", ".tr", ".tt", ".tv", ".tw", ".tz",
			".ua", ".ug", ".uk", ".us", ".uy", ".uz", ".va", ".vc", ".ve", ".vg", ".vi", ".vn", ".vu",
			".wf", ".ws",
			".ye", ".yt",
			".za", ".zm", ".zw"
	};
	/**
	 * 后台【修改会员站点域名】【修改代理站点域名】使用
	 * 验证域名是否正确
	 * 不能单独使用顶级域名: .com|.net|.cn|.com.cn|.net.cn 等
	 * 不能单独使用一级域名: xxx.com|xxx.net|xxx.com.cn|xxx.net.cn 等
	 * @param domain
	 * @return
	 */
	public static boolean checkDomain(String domain) {
		if(domain.split("[.]").length <= 2) return false;
		domain = removeTopLevelDomain(domain);
		if(domain.split("[.]").length != 2) return false;
		return true;
	}
	/**
	 * 去除顶级域名
	 * @param domain
	 * @return
	 */
	public static String removeTopLevelDomain(String domain) {
		if (null == domain) return "";
		for (String d1 : TOP_LEVEL_DOMAIN) {
			if (domain.endsWith(d1)) {
				Pattern p = Pattern.compile(d1);
				Matcher m = p.matcher(domain); 
				domain = m.replaceAll("");
				domain = removeTopLevelDomain(domain);
				break;
			}
		}
		return domain;
	}
	/**
	 * 域名是否解析
	 * 
	 * @param domain
	 * @return
	 * @throws UnknownHostException 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static boolean isDomainResolution(String domainName) {
		try {
			InetAddress ip = InetAddress.getByName(domainName);
			if (ip != null) {
				System.out.println("[" + domainName + "]指向的IP地址为：" + ip.getHostAddress());
				return true;
			}
		} catch (UnknownHostException e) { 
			return false;			
		}
		return false;
	}
}