package com.boot.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

public class CustomUtils {
	public static String encodeURIComponent(String s) {
		String result = null;
		try {
			result = URLEncoder.encode(s, "UTF-8")
					.replaceAll("\\+", "%20")
					.replaceAll("\\%21", "!")
					.replaceAll("\\%27", "'")
					.replaceAll("\\%28", "(")
					.replaceAll("\\%29", ")")
					.replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			result = s;
		}
		return result;
	}
	
	public static File makeSquareImage(File file) throws IOException {
		BufferedImage srcImg = ImageIO.read(new File(file.getAbsolutePath()));
		int width = srcImg.getWidth();
		int height = srcImg.getHeight();
		
		if (width != height) {
			int wh = Math.min(width, height);
			BufferedImage resultImage = new BufferedImage(wh, wh, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) resultImage.getGraphics();
			graphics.setBackground(Color.WHITE);

			int x = 0, y = 0;
			if (width > height) {
				x = (width - height) / 2 * -1;
			} else {
				y = (height - width) / 2 * -1;
			}

			graphics.drawImage(srcImg, x, y, null);
			ImageIO.write(resultImage, "png", file);
		}

		return file;
	}
	
	public static File makeTempFile(MultipartFile mf, String root) throws Exception {
		String pathname = root + File.separator + "temp" + File.separator;		
		String originalFileName =  mf.getOriginalFilename();
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
		String saveFilename = UUID.randomUUID().toString() + fileExt;

		File file = new File(pathname + saveFilename);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
	    mf.transferTo(file);
	    
		return file;
	}
}
