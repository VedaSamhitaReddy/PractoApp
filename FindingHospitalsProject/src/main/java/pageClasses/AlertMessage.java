package pageClasses;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import javax.imageio.ImageIO;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;

import com.aventstack.extentreports.Status;

import baseClasses.BaseClass;
import utilities.DateUtil;

public class AlertMessage extends BaseClass {
	public void back() {
		driver.navigate().back();
		logger.log(Status.INFO, "Navigating back");
	}

	public void selectForProviders() {
		driver.findElement(By.xpath(propertiesFile.getProperty("forproviders"))).click();
	}

	public void selectCorporateWellness() {
		driver.findElement(By.xpath(propertiesFile.getProperty("corporatewellness"))).click();
		logger.log(Status.INFO, "Select Corporate Wellness from for providers dropdown");
	}

	public void windows() {
		String mainWindowHandle = driver.getWindowHandle();
		Set<String> allWindowHandles = driver.getWindowHandles();
		Iterator<String> iterator = allWindowHandles.iterator();
		String MainWindow = iterator.next();
		String ChildWindow = iterator.next();
		driver.switchTo().window(ChildWindow);
		waitForPageLoad();
		logger.log(Status.INFO, "Navigate to new window");
	}

	public void setName(String name) {
		driver.findElement(By.xpath(propertiesFile.getProperty("name"))).sendKeys(name);
		logger.log(Status.INFO, "Sending Name to textbox");
	}

	public void setOrganization_name(String orgname) {
		driver.findElement(By.xpath(propertiesFile.getProperty("organizationname"))).sendKeys(orgname);
		logger.log(Status.INFO, "Sending Organization Name to textbox");
	}

	public void setOfficial_email_id(String emailid) {
		driver.findElement(By.xpath(propertiesFile.getProperty("emailid"))).sendKeys(emailid);
		logger.log(Status.INFO, "Sending Official Email Id to textbox");
	}

	public void setOfficial_phone_no(String phoneno) {
		driver.findElement(By.xpath(propertiesFile.getProperty("contactno"))).sendKeys(phoneno);
		logger.log(Status.INFO, "Sending Contact Number to textbox");
	}

	public void clickButton() {
		driver.findElement(By.xpath(propertiesFile.getProperty("button"))).click();
		logger.log(Status.INFO, "Submit button- Schedule a demo");

	}

	public void clear() {
		driver.findElement(By.xpath(propertiesFile.getProperty("name"))).clear();
		driver.findElement(By.xpath(propertiesFile.getProperty("organizationname"))).clear();
		driver.findElement(By.xpath(propertiesFile.getProperty("emailid"))).clear();
		driver.findElement(By.xpath(propertiesFile.getProperty("contactno"))).clear();
	}

	public void handleAlert(String name) throws Exception {
		System.out.println("*****************Alert Message***********************");
		String screenshot = System.getProperty("user.dir") + ".\\ScreenShots\\" +name+" "+ DateUtil.getTimeStamp() + ".png";
		BufferedImage image = new Robot()
				.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIO.write(image, "png", new File(screenshot));
		Thread.sleep(2000);
		Alert alt = driver.switchTo().alert();
		System.out.println(alt.getText());
		alt.accept();
	}
	public boolean isAlertPresent() 
	{ 
	    try 
	    { 
	        driver.switchTo().alert(); 
	        return true; 
	    } 
	    catch (NoAlertPresentException Ex) 
	    { 
	        return false; 
	    }
	}

	public void thankyouMsg() {
		System.out.println("*****************Schedule a demo with valid details******");
		String text = driver.findElement(By.xpath(propertiesFile.getProperty("msg"))).getText();
		System.out.println(text);
	}
}

