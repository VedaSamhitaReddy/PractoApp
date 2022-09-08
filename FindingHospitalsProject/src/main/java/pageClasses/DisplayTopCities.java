package pageClasses;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.Status;
import baseClasses.BaseClass;

public class DisplayTopCities extends BaseClass {
	public void Back()  {
		driver.navigate().back();
		waitForPageLoad();
		logger.log(Status.INFO, "Navigating back");
	}

	public void clickOnDiagnostics()  {
		System.out.println("****************Display Top cities*******************");
		driver.findElement(By.xpath(propertiesFile.getProperty("diagnostics"))).click();
		waitForPageLoad();
		logger.log(Status.INFO, "Click diagnostics");
	}

	public List<String> topCitiesNames()  {
		ArrayList<String> list=new ArrayList<String>();
		List<WebElement> topcitiesname = driver.findElements(By.xpath(propertiesFile.getProperty("cities")));
		logger.log(Status.INFO, "Display top cities");
		for (WebElement cities : topcitiesname) {
			waitForPageLoad();
			String city = cities.getText();
			list.add(city);
		}
		return list;
	}
}
