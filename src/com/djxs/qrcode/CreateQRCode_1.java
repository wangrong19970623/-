package com.djxs.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class CreateQRCode_1 {
	public static void CreateQRCode(String str,String logoPath,String qrCodeImagepath) throws IOException {
		int ver =5;
		//创建对象
		Qrcode qrcdoe = new Qrcode();
		qrcdoe.setQrcodeVersion(ver);
		
		/*
		 * ver =1  21
		 * 
		 * ver = 2  25
		 * 
		 * ver = 3  29
		 * 
		 * ver = n 21+(n-1)*4
		 * 
		 * 二维码每一个点 占 3个像素		 
		 * 
		 * (21+(n-1)*4)*3 = 63+(n-1)*12
		 *  
		 * */
		
		int  imageSize = 67+12*(ver-1);
		//图片缓存对象
		BufferedImage bufferedImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
		//创建画板
		Graphics2D gs = bufferedImage.createGraphics();
		//设置背景色
		gs.setBackground(Color.WHITE);
		//设置前景色
		gs.setColor(Color.BLACK);
		//情况画板
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeErrorCorrect('M');	// 纠错级别（L 7%、M 15%、Q 25%、H 30%）和版本有关
		qrcode.setQrcodeEncodeMode('B');	
		qrcode.setQrcodeVersion(7);		// 设置Qrcode包的版本
		gs.clearRect(0, 0, imageSize, imageSize);
		
	    
		int startR = 0;
		int startG = 109;
		int startB = 255;
		
		int endR = 175;
		int endG = 238;
		int endB = 238;
		
		//得到二位数组  可以确定二维码那个点有颜色
		boolean[][] calQrcode = qrcdoe.calQrcode(str.getBytes());
		int x = 2;
		for (int j = 0; j < calQrcode.length; j++) {
			for (int i = 0; i < calQrcode.length; i++) {
				if(calQrcode[j][i]){
					int num1 = startR + (endR - startR)*(i+1)/calQrcode.length;
					int num2 = startG + (endG - startG)*(i+1)/calQrcode.length;
					int num3 = startB + (endB - startB)*(i+1)/calQrcode.length;
					
					Color color = new Color(num1, num2, num3);
					
					gs.setColor(color);
					//填充颜色
					gs.fillRect(i*3+x, j*3+x, 3, 3);
				}
			}
		}
		Image logo=ImageIO.read(new File(logoPath));
		int logosize=30;
		int logox=(imageSize-logosize)/2;
		gs.drawImage(logo,logox,logox,logosize,logosize,null);	
		gs.dispose();
		bufferedImage.flush();
		//输出二维码图片
		ImageIO.write(bufferedImage, "png", new File(qrCodeImagepath));
	}
}
