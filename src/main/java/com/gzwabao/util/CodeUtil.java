package com.gzwabao.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * 验证码生成工具
 * 
 * @since 2015年11月9日 下午2:01:32
 * @author hjielong
 */
public class CodeUtil {

	// 定义可选择的字符
	public static final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8',
			'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
			'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
			'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	public static final int[] pointX = { 0, 1, 2 };
	public static final int[] pointY = { 20, 21, 22, 23, 24, 25, 26, 27 };
	static Random random = new Random();

	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < 10000; i++) {
			String key = getRandomString();
			if (map.get(key) == null) {
				map.put(key, 1);
			} else {
				map.put(key, map.get(key) + 1);
			}
		}
		int counter = 0;
		for (Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
			if (entry.getValue() > 1) {
				counter++;
			}
		}
		System.out.println(counter);
	}

	public static String getRandomString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 4; i++) { // 生成六个字符
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}

	public static Color getRandomColor() { // 得到随机颜色
		return new Color(random.nextInt(255), random.nextInt(255),
				random.nextInt(255));
	}

	public static Color getReverseColor(Color c) { // 得到颜色的反色
		return new Color(255 - c.getRed(), 255 - c.getGreen(),
				255 - c.getBlue());
	}

	public static BufferedImage getCodeImg(String randomString) {
		// 设置图片的宽、高
		int width = 75;
		int height = 39;

		Color bcolor = getRandomColor(); // 前景色
		Color fcolor = getReverseColor(getRandomColor()); // 设置背景色
		// 创建一个彩色图片
		BufferedImage bimage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		// 创建绘图对象
		Graphics2D g = bimage.createGraphics();
		// 字体样式为宋体,加粗，22磅
		g.setFont(new Font(null, Font.PLAIN, 28));
		// 先画出背景色
		g.setColor(bcolor);
		g.fillRect(0, 0, width, height);
		// 再画出前景色
		g.setColor(fcolor);
		// 绘制随机字符
		g.drawString(randomString, pointX[random.nextInt(pointX.length)],
				pointY[random.nextInt(pointY.length)]);
		// 画出干扰点
		for (int i = 0, n = random.nextInt(100); i < n; i++) {
			g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
		}
		return bimage;
	}
}
