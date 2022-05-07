package com.google.www.utility;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Utility {

	/*
	 * Function Name: captureScreenshot
	 * Objective: To capture screenshot
	 * Date Created: 03/20/2019
	 * Date Modified: 01/24/2020
	 * Changes Made: Removed fileName from arguments list
	 */
	public static synchronized File captureScreenshot(WebDriver browser, boolean fullScreenshot) throws IOException {
		try {
			long imgName = 0;
			imgName = System.currentTimeMillis();
			if(fullScreenshot) {
				Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1500)).takeScreenshot(browser);
				String imagePath = System.getProperty("user.dir")+"\\screenshots\\" + imgName + ".png";
				ImageIO.write(fpScreenshot.getImage(),"png",new File(imagePath));
				return new File(imagePath);
			} else {
				File src = ((TakesScreenshot)browser).getScreenshotAs(OutputType.FILE);
				File dest = new File(System.getProperty("user.dir")+"\\screenshots\\" + imgName + ".png");
				try {	
					FileUtils.copyFile(src, dest);
				}catch (IOException e) {
				  System.out.println(e.getMessage()); 
				}
				return dest;
			} 
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
