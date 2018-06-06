<%@ page contentType="image/jpeg"
	import="java.io.*,java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*"
	pageEncoding="UTF-8"%>

<%!

//图片的宽度。
	private int width = 170;
	// 图片的高度。
	private int height = 30;
	// 验证码字符个数
	private int codeCount = 5;
	// 验证码干扰线数
	private int lineCount = 150;
	// 验证码
	private String code = null;
	// 验证码图片Buffer
	private BufferedImage buffImg = null;
	private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',  'J', 'K', 'M', 'N', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	
	/**
	 * ttf字体文件
	 * 
	 * @author dsna
	 *
	 */
	class ImgFontByte {
		public Font getFont(int fontHeight) {
			try {
				Font baseFont = Font.createFont(Font.TRUETYPE_FONT, new ByteArrayInputStream(hex2byte(getFontByteStr())));
				return baseFont.deriveFont(Font.PLAIN, fontHeight);
			} catch (Exception e) {
				return new Font("Arial", Font.PLAIN, fontHeight);
			}
		}

		private byte[] hex2byte(String str) {
			if (str == null)
				return null;
			str = str.trim();
			int len = str.length();
			if (len == 0 || len % 2 == 1)
				return null;
			byte[] b = new byte[len / 2];
			try {
				for (int i = 0; i < str.length(); i += 2) {
					b[i / 2] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
				}
				return b;
			} catch (Exception e) {
				return null;
			}
		}

		/**
		 * ttf字体文件的十六进制字符串
		 * 
		 * @return
		 */
		private String getFontByteStr() {
			return null;
		}
	}	
	
%>

<%
	//设置页面不缓存
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	int x = 0, fontHeight = 0, codeY = 0;
	int red = 0, green = 0, blue = 0;
	x = width / (codeCount + 2);// 每个字符的宽度
	fontHeight = height - 2;// 字体的高度
	codeY = height - 4;
	// 图像buffer
	buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	
	out.clearBuffer(); 
	out = pageContext.pushBody();  
	OutputStream os = response.getOutputStream();
	Graphics2D g = buffImg.createGraphics();
	// 生成随机数
	Random random = new Random();
	// 将图像填充为白色
	g.setColor(Color.WHITE);
	g.fillRect(0, 0, width, height);
	// 创建字体
	ImgFontByte imgFont = new ImgFontByte();
	Font font = imgFont.getFont(fontHeight);
	g.setFont(font);
	for (int i = 0; i < lineCount; i++) {
		int xs = random.nextInt(width);
		int ys = random.nextInt(height);
		int xe = xs + random.nextInt(width / 8);
		int ye = ys + random.nextInt(height / 8);
		red = random.nextInt(255);
		green = random.nextInt(255);
		blue = random.nextInt(255);
		g.setColor(new Color(red, green, blue));
		g.drawLine(xs, ys, xe, ye);
	}
	// randomCode记录随机产生的验证码
	StringBuffer randomCode = new StringBuffer();
	// 随机产生codeCount个字符的验证码。
	for (int i = 0; i < codeCount; i++) {
		String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
		// 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
		red = random.nextInt(255);
		green = random.nextInt(255);
		blue = random.nextInt(255);
		g.setColor(new Color(red, green, blue));
		g.drawString(strRand, (i + 1) * x, codeY);
		// 将产生的四个随机数组合在一起。
		randomCode.append(strRand);
	}
	// 将四位数字的验证码保存到Session中。
	code = randomCode.toString();
	long timestamp = System.currentTimeMillis()/1000;
	session.setAttribute("verifyCode", code + "_" + timestamp);
	//System.out.println("code4验证码："+code);
	
	
	//输出图象到页面
	ImageIO.write(buffImg, "JPEG", os);
	// 释放流资源  
    os.flush();  
    os.close();  
    os = null;  
    
    
%>
