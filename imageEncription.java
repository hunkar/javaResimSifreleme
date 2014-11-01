//Hünkar Purtul
//03.07.2014 - Image Encryption

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Main {

	public static void main(String[] args) 
	{
			BufferedImage rawImage = null, decryptedImage = null, encryptedImage = null;
			int red, green, blue, enRed, enGreen, enBlue, tempRed, tempGreen, tempBlue, value = 0, deCount=0;

			int  enCount = 0;
			
			try
			{
				rawImage = ImageIO.read(new File("F:\\raw.jpg"));
				encryptedImage = ImageIO.read(new File("F:\\transitImage.jpg"));
				decryptedImage = new BufferedImage(rawImage.getWidth(), rawImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			} 
			catch(IOException e){ System.out.println("Error"); }
			
			
//þifreleme		
			for(int count1=0; count1 < rawImage.getHeight(); count1++)
			{	
				enCount = 0;
				for(int count2=0; count2 < rawImage.getWidth(); count2++)
				{
					red = new Color(rawImage.getRGB(count2, count1)).getRed();
					green = new Color(rawImage.getRGB(count2, count1)).getGreen();
					blue = new Color(rawImage.getRGB(count2, count1)).getBlue();
					
					for(int count3 = 0; count3 < 4; count3++)
					{
						enRed = new Color(encryptedImage.getRGB(enCount, count1)).getRed();
						enGreen = new Color(encryptedImage.getRGB(enCount, count1)).getGreen();
						enBlue = new Color(encryptedImage.getRGB(enCount, count1)).getBlue();
					
						enRed =  (enRed & 252);
						enGreen =  (enGreen & 252);
						enBlue =  (enBlue & 252);
						
						switch(count3)
						{
						 case 0: value = 3; break;    //00000011
						 case 1: value = 12; break;   //00001100
						 case 2: value = 48; break;   //00110000
						 case 3: value = 192; break;  //11000000
						}
						
						
						
						tempRed =  ((red & value)>>(count3*2))&255;
						tempGreen =  ((green & value)>>(count3*2))&255;
						tempBlue =  ((blue & value)>>(count3*2))&255;
						
						enRed =  (enRed | tempRed);
						enGreen =  (enGreen | tempGreen);
						enBlue =  (enBlue | tempBlue);
						
						encryptedImage.setRGB(enCount, count1, new Color(enRed, enGreen, enBlue).getRGB());
						enCount++;
						
					}
					
				}
				
			}
			
			
//deþifreleme			
			for(int count1 = 0; count1 < encryptedImage.getHeight(); count1++)
			{
				deCount = 0;
				for(int count2 = 0; count2+4 < encryptedImage.getWidth();)
				{
					tempRed = tempGreen = tempBlue = 0;
					
					
					for(int count3 = 0; count3 < 4; count3++)
					{
					
						red = new Color(encryptedImage.getRGB(count2, count1)).getRed();
						green = new Color(encryptedImage.getRGB(count2, count1)).getGreen();
						blue = new Color(encryptedImage.getRGB(count2, count1)).getBlue();
						
						switch(count3)
						{
							case 0: value=3; break;
							case 1: value=12; break;
							case 2: value=48; break;
							case 3: value=192; break;
						}
						
						red =  ((red << 2*count3)&value)&255;
						green =  ((green << 2*count3)&value)&255;
						blue =  ((blue << 2*count3)&value)&255;
					
						tempRed =  (tempRed | red)&255;
						tempGreen =  (tempGreen | green)&255;
						tempBlue =  (tempBlue | blue)&255;	
						++count2;
						
					}
					try
					{
						decryptedImage.setRGB(deCount, count1, new Color(tempRed, tempGreen, tempBlue).getRGB());
						deCount++;
					}catch(Exception e){break;}
				}
			
			}
			
			decryptedImage = decryptedImage.getSubimage(0, 0, rawImage.getWidth(), rawImage.getHeight());

			
			try
			{
				ImageIO.write(encryptedImage, "jpg", new File("F:\\encripted.jpg"));
				ImageIO.write(decryptedImage, "jpg", new File("F:\\decripted.jpg"));
			} 
			catch(IOException e){}
			
	}

}