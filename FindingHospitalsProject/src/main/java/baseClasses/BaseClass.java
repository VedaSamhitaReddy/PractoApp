package baseClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utilities.DateUtil;
import utilities.ExtentReportManager;

public class BaseClass {

	public static Properties propertiesFile;
	public static WebDriver driver;
	public static ExtentTest logger;
	public static String propertiesFilePath="\\src\\main\\java\\resources\\Properties\\config.properties";
	public ExtentReports report = ExtentReportManager.getReportInstance();

	/******************** Import properties File ****************/
	public void getPropertiesFile() {
		try {
			FileInputStream file = new FileInputStream(System.getProperty("user.dir")+propertiesFilePath);
			propertiesFile = new Properties();
			propertiesFile.load(file);

			if (propertiesFile != null) {
				file.close();
			} else {
				System.out.println("Connection Failed with property file");
			}
		} catch (Exception e) {
			System.out.println("Error in ReadPrperties Class");
		}
	}

	/************************ Invoke Browser **********************/
	public static void invokeBrowser() {
		String browserName = propertiesFile.getProperty("browserName");
		try {

			if (browserName.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + propertiesFile.getProperty("chromepath"));
				driver = new ChromeDriver();
			} else if (browserName.equalsIgnoreCase("Mozila")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + propertiesFile.getProperty("firefoxpath"));
				driver = new FirefoxDriver();
			} else if (browserName.equalsIgnoreCase("Opera")) {
				System.setProperty("webdriver.opera.driver",
						System.getProperty("user.dir") + propertiesFile.getProperty("operapath"));
				driver = new OperaDriver();
			} else {
				System.out.println("The driver could not be found");
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	/****************** OpenApplication ***********************/
	public void OpenApplication() {
		try {
			driver.get(propertiesFile.getProperty("baseUrl"));
			logger.log(Status.INFO, "Opening the Website");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/****************** Reporting Functions ***********************/
	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}

	/****************** Capture Screen Shot ***********************/
	public void takeScreenShot(String name) {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(System.getProperty("user.dir") + "\\ScreenShots\\"+name+" "+ DateUtil.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "\\ScreenShots\\" +name+" "+ DateUtil.getTimeStamp() + ".png");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/******************************Wait Load Functions**************/
	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		int i = 0;
		while (i != 180) {
			String pageState = (String) js.executeScript("return document.readyState;");
			if (pageState.equals("complete")) {
				break;
			} else {
				waitLoad(1);
			}
		}

		waitLoad(2);

		i = 0;
		while (i != 180) {
			Boolean jsState = (Boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if (jsState) {
				break;
			} else {
				waitLoad(1);
			}
		}
	}

	public void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/************************** Close Browser ***********************/

	public  void closeBrowser() {
		driver.close();
		driver.quit();
		logger.log(Status.INFO, "Driver Closed");
	}

	/*************************** Report Flush *****************************/
	public void flushReports() {
		report.flush();
	}
}
