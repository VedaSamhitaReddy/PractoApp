package pageClasses;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import com.aventstack.extentreports.Status;
import baseClasses.BaseClass;

public class DisplayHospitalNames extends BaseClass {

	public void addLocation(String location) {
		WebElement loc = driver.findElement(By.xpath(propertiesFile.getProperty("location")));
		loc.clear();
		waitForPageLoad();
		loc.sendKeys(location);
		logger.log(Status.INFO, "Locating textbox and sending city to the textbox");
	}

	public void selectDropDownLocation() {
		driver.findElement(By.xpath(propertiesFile.getProperty("enterlocation"))).click();
		logger.log(Status.INFO, "Selecting location from the dropdown results");
	}

	public void addSearch(String search) {
		WebElement hospitalsearch = driver.findElement(By.xpath(propertiesFile.getProperty("searchhospitals")));
		hospitalsearch.clear();
		hospitalsearch.sendKeys(search);
		logger.log(Status.INFO, "Locating and sending Hospital to the textbox");
	}

	public void enterKeyAtSearch() {
		WebElement search = driver.findElement(By.xpath(propertiesFile.getProperty("searchhospitals")));
		search.sendKeys(Keys.ENTER);
	}

	public void selectDropDownSearch() {
		driver.findElement(By.xpath(propertiesFile.getProperty("entertext"))).click();
		logger.log(Status.INFO, "Selecting search from the dropdown results");
	}

	public void clickOpen_24X7_checkbox() {
		driver.findElement(By.xpath(propertiesFile.getProperty("open24*7"))).click();
		logger.log(Status.INFO, "Selecting open 24*7 checkbox");
	}

	public void applyFilters() {
		driver.findElement(By.xpath(propertiesFile.getProperty("allfilters"))).click();
		logger.log(Status.INFO, "Selecting allfiters dropdown");
	}

	public void clickOnHasParking() {
		driver.findElement(By.xpath(propertiesFile.getProperty("hasparking"))).click();
		logger.log(Status.INFO, "Selecting has parking from all filters dropdown");
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public boolean checkValidResult(String location) {
		try {
			WebElement validR1 = driver.findElement(By.xpath(propertiesFile.getProperty("results")));
		} catch (Exception e) {
			return false;
		}
		String validResult = driver.findElement(By.xpath(propertiesFile.getProperty("results"))).getText();

		String expected = "Hospital in " + location;

		System.out.println("valid---->" + validResult);
		System.out.println("expected---->" + expected);

		if (validResult.equalsIgnoreCase(expected)) {
			return true;
		} else {
			return false;
		}
	}


	public List<String> getHospitals() 
	{
		ArrayList<String> list=new ArrayList<String>();

		for(int i=0;i<=20;i++)
		{
			JavascriptExecutor js	=((JavascriptExecutor) driver);
			js.executeScript("window.scrollTo(0,document.body.scrollHeight-1000)");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		List<WebElement> cards = driver.findElements(By.xpath("//div[@class='c-card']"));
		int n = cards.size();
		System.out.println("***************Display Hospital Names*********************");
		int cc=0;
		for(int i=0;i<n;i++)
		{
			WebElement E = cards.get(i);
			String rating;
			try {
				rating=E.getAttribute("innerHTML").split("class=\"common__star-rating__value\">")[1].split("</span><span class=\"\">")[0];
			}
			catch(Exception e)
			{
				continue;
			}
			double value = Double.parseDouble(rating);
			if (value > 3.5)
			{
				String hospital_t;
				hospital_t=E.getAttribute("innerHTML").split("class=\"u-title-font u-c-pointer u-bold\">")[1].split("</h2></a><div")[0];
				list.add(hospital_t);
				cc++;
			}
		}
		return list;
}
}
