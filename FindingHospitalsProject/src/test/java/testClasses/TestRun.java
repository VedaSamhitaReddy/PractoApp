package testClasses;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import utilities.ExcelData;
import baseClasses.BaseClass;
import pageClasses.DisplayHospitalNames;
import pageClasses.DisplayTopCities;
import pageClasses.AlertMessage;
import utilities.DateUtil;
import utilities.ExcelData;
import utilities.ExtentReportManager;

public class TestRun extends BaseClass {
	DisplayHospitalNames displaynames = new DisplayHospitalNames();
	DisplayTopCities topcities = new DisplayTopCities();
	AlertMessage alertmsg = new AlertMessage();

	@BeforeTest
	public void start() {
		try {
			getPropertiesFile();
			ExtentReportManager.getReportInstance();
			invokeBrowser();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	// check if application opens in different browser
	@Test(priority = 0)
	public void TC_FH_001() {
		// create logger report
		logger = report.createTest("Launching the website");
		logger.log(Status.INFO, "Opening the Website");
		OpenApplication();
		String application=propertiesFile.getProperty("baseUrl");
		reportPass(propertiesFile.getProperty("baseUrl") + " launched succesfully");
	}
	// check for application functions with valid location and valid search
	@Test(priority=1)
	public void TC_FH_002() {
		logger=report.createTest("To display Hospital Names with valid location and valid Search");
		String location="Bangalore";
		String search="Hospital";
		// passing inputs and check success status with logger report and taking screenshots
		displaynames.addLocation(location);
		displaynames.selectDropDownLocation();
		displaynames.addSearch(search);
		displaynames.selectDropDownSearch();
		logger.log(Status.INFO,"Selecting Hospitals in Bangalore");
		Assert.assertEquals(displaynames.checkValidResult(location),true);
		takeScreenShot("Invalid Details");
		logger.log(Status.INFO, "Screenshot captured");
	}
	// check for application functions with invalid location and valid search
	@Test(priority=2)
	
	public void TC_FH_003() {
		logger=report.createTest("To display Hospital Names with invalid location and valid Search");
		OpenApplication();
		String location="Hyderabad";
		String search="Hospital";
		// passing inputs and check success status with logger report
		displaynames.addLocation(location);
		displaynames.addSearch(search);
		displaynames.selectDropDownSearch();
		logger.log(Status.INFO,"Hospitals in Hyderabad location are selected");
		Assert.assertEquals(displaynames.checkValidResult(location),false);
		takeScreenShot("Invalid Details");
		logger.log(Status.INFO, "Screenshot captured");
	}
	// check for application functions with valid location and invalid search
	@Test(priority=3)
	public void TC_FH_004() {
		logger=report.createTest("To display Hospital Names with valid location and invalid Search");
		OpenApplication();
		String location="Bangalore";
		String search="xyz";
		// passing inputs and check success status with logger report and taking screenshots
		displaynames.addLocation(location);
		displaynames.addSearch(search);;
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		displaynames.enterKeyAtSearch();
		logger.log(Status.INFO,"xyz cannot be selected in Bangalore location");
		Assert.assertEquals(displaynames.checkValidResult(location),false);
	}
	@Test(priority=4)
	public void TC_FH_005() {
		logger=report.createTest("To display Hospital Names with invalid location and invalid Search");
		OpenApplication();
		String location="xyz";
		String search="xyz";
		displaynames.addLocation(location);
		displaynames.addSearch(search);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		displaynames.enterKeyAtSearch();
		logger.log(Status.INFO,"xyz cannot be selected in Bangalore location");
		Assert.assertEquals(displaynames.checkValidResult(location),false);
	}
	
	@Test(priority=5)
	public void TC_FH_006() throws InterruptedException {
		logger=report.createTest("To select open 24*7 checkbox");
		OpenApplication();
		String location="Bangalor";
		String search="Hospital";
		displaynames.addLocation(location);
		displaynames.addSearch(search);
		Thread.sleep(1000);
		displaynames.enterKeyAtSearch();
		logger.log(Status.INFO,"Selecting Hospitals in Bangalore");
		Thread.sleep(3000);
		displaynames.clickOpen_24X7_checkbox();
		Thread.sleep(3000);
		logger.log(Status.INFO, "Select open 24*7 checkbox");
	}
	@Test(priority=6, dependsOnMethods={"TC_FH_006"})
	public void  TC_FH_007() {
		logger=report.createTest("To select hasparking from all filters dropdown");
		displaynames.applyFilters();
		logger.log(Status.INFO, "Select all filters dropdown");
		displaynames.clickOnHasParking();
		logger.log(Status.INFO, "Select has parking from all filters dropdown");
	}
	
	@Test(priority=7,dependsOnMethods={"TC_FH_007"} )
	public void TC_FH_008() throws InterruptedException {
		logger=report.createTest("Display Hospital Names having rating more than 3.5");
		List<String> hospitals=displaynames.getHospitals();
		for(String str:hospitals)
		{
			System.out.println(str);
		}
		ExcelData excel=new ExcelData();
		try {
			excel.saveToExcel(hospitals,"HospitalNames");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread.sleep(2000);
		logger.log(Status.INFO, " Hospital names should be displayed with rating more than 3.5");
	}
	
	@Test(priority=8)
 public void TC_FH_009() {
		logger=report.createTest("Click on Diagnostics page to display top cities");
		topcities.Back();
		logger.log(Status.INFO, "Navigate back");
		topcities.clickOnDiagnostics();
		logger.log(Status.INFO, "Select Diagnostics on homepage");
		waitForPageLoad();
		topcities.topCitiesNames();
		ArrayList<String> list=(ArrayList) topcities.topCitiesNames();
		for(String str:list)
		{
			System.out.println(str);
		}
		
		ExcelData excel=new ExcelData();
		try {
			excel.saveToExcel(list,"TopCities");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.log(Status.INFO, "Display top cities name");
		waitForPageLoad();
		takeScreenShot("Display topcites");
		logger.log(Status.INFO, "Screenshot captured");
		
	}
	@Test(priority=9)
	public void TC_FH_010() {
		logger=report.createTest("Click For Providers dropdown on homepage");
		alertmsg.back();
		logger.log(Status.INFO, "Navigate back");
		alertmsg.selectForProviders();
		logger.log(Status.INFO, "Click on For providers drop down");
	}
	
	@Test(priority=10 ,dependsOnMethods={"TC_FH_010"} )
	public void TC_FH_011() {
		logger=report.createTest("Select Corporate Wellness from For providers drop down it should navigate to new window");
		alertmsg.selectCorporateWellness();
		logger.log(Status.INFO, "Select Corporate Wellness from For providers drop down");
		alertmsg.windows();
		logger.log(Status.INFO, "Navigate to a new window");
		
	}
	
	@Test(priority=11,dependsOnMethods={"TC_FH_011"})
	public void TC_FH_012() throws Exception {
		logger = report.createTest("To schedule a demo with all null field values");
		alertmsg.setName("");
		alertmsg.setOrganization_name("");
		alertmsg.setOfficial_email_id("");
		alertmsg.setOfficial_phone_no("");
		alertmsg.clickButton();
		Thread.sleep(2000);
		logger.log(Status.INFO, "Schedule a demo with all null fields");
		alertmsg.handleAlert("Null fields");
		logger.log(Status.INFO, "Handle Alerts");
	}
	
	@Test(priority=12,dependsOnMethods= {"TC_FH_010","TC_FH_011"})
	
	public void TC_FH_013() throws Exception {
		logger = report.createTest("To schedule a demo with Name field left empty");
		alertmsg.setName("");
		alertmsg.setOrganization_name("Cognizant");
		alertmsg.setOfficial_email_id("test12@gmail.com");
		alertmsg.setOfficial_phone_no("9876543210");
		alertmsg.clickButton();
		Thread.sleep(2000);
		logger.log(Status.INFO, "Schedule a demo with Name field empty");
		alertmsg.handleAlert("Name field empty");
	     logger.log(Status.INFO, "Handle Alerts");
	    
	}
	@Test(priority=13,dependsOnMethods= {"TC_FH_010","TC_FH_011"})
	public void TC_FH_014() throws Exception {
		logger = report.createTest("To schedule a demo with Organization Name field left empty");
		alertmsg.clear();
		alertmsg.setName("Demo Test");
		alertmsg.setOrganization_name("");
		alertmsg.setOfficial_email_id("test12@gmail.com");
		alertmsg.setOfficial_phone_no("9876543210");
		alertmsg.clickButton();
		Thread.sleep(2000);
		logger.log(Status.INFO, "Schedule a demo with Organization Name field empty");
		alertmsg.handleAlert("Organization Name field empty");
	     logger.log(Status.INFO, "Handle Alerts");
	}
	@Test(priority=14,dependsOnMethods= {"TC_FH_010","TC_FH_011"})
	public void TC_FH_015() throws Exception {
		logger = report.createTest("To schedule a demo with Official emailid field left empty");
		alertmsg.clear();
		alertmsg.setName("Demo Test");
		alertmsg.setOrganization_name("Cognizant");
		alertmsg.setOfficial_email_id("");
		alertmsg.setOfficial_phone_no("9876543210");
		alertmsg.clickButton();
		Thread.sleep(2000);
		logger.log(Status.INFO, "Schedule a demo with Official emailid field empty");
		alertmsg.handleAlert("Official emailid empty");
	     logger.log(Status.INFO, "Handle Alerts");
	}
@Test(priority=15,dependsOnMethods= {"TC_FH_010","TC_FH_011"})
	
	public void TC_FH_016() throws Exception {
		logger = report.createTest("To schedule a demo with Contact Number field left empty");
		alertmsg.clear();
		alertmsg.setName("Demo Test");
		alertmsg.setOrganization_name("Cognizant");
		alertmsg.setOfficial_email_id("test12@gmail.com");
		alertmsg.setOfficial_phone_no("");
		alertmsg.clickButton();
		logger.log(Status.INFO, "Schedule a demo with Contact number field empty");
		Thread.sleep(2000);
		alertmsg.handleAlert("Contact Number field empty");
	     logger.log(Status.INFO, "Handle Alerts");
	}
@Test(priority=16,dependsOnMethods= {"TC_FH_010","TC_FH_011"})
public void TC_FH_017() throws Exception {
	logger = report.createTest("To schedule a demo by giving invalid Official emailid");
	alertmsg.clear();
	alertmsg.setName("Demo Test");
	alertmsg.setOrganization_name("Cognizant");
	alertmsg.setOfficial_email_id("test12gmail.com");
	alertmsg.setOfficial_phone_no("9876543210");
	alertmsg.clickButton();
	Thread.sleep(2000);
	logger.log(Status.INFO, "Schedule a demo by giving invalid Official emailid");
	alertmsg.handleAlert("Invalid Official emailid");
     logger.log(Status.INFO, "Handle Alerts");
}

@Test(priority=17,dependsOnMethods= {"TC_FH_010","TC_FH_011"})
public void TC_FH_018() throws Exception {
	logger = report.createTest("To schedule a demo by giving invalid Contact Number");
	alertmsg.clear();
	alertmsg.setName("Demo Test");
	alertmsg.setOrganization_name("Cognizant");
	alertmsg.setOfficial_email_id("test12@gmail.com");
	alertmsg.setOfficial_phone_no("qdfshkldf");
	alertmsg.clickButton();
	Thread.sleep(2000);
	logger.log(Status.INFO, "Schedule a demo by giving invalid Contact Number");
	alertmsg.handleAlert("Invalid Contact Number");
     logger.log(Status.INFO, "Handle Alerts");
}

@Test(priority=18,dependsOnMethods= {"TC_FH_010","TC_FH_011"})

public void TC_FH_019() throws Exception {
	logger = report.createTest("To schedule a demo by giving all fields with valid data");
	alertmsg.clear();
	alertmsg.setName("Demo Test");
	alertmsg.setOrganization_name("Cognizant");
	alertmsg.setOfficial_email_id("test12@gmail.com");
	alertmsg.setOfficial_phone_no("9876543210");
	alertmsg.clickButton();
	Thread.sleep(2000);
	logger.log(Status.INFO, "Schedule a demo by giving all fields with valid data");
	alertmsg.thankyouMsg();
	logger.log(Status.INFO, "Succesfully scheduled a demo");
	takeScreenShot("Successful Login");
	logger.log(Status.INFO, "ScreesShot captured");
   
}


	@AfterTest
	public void end() {
		logger = report.createTest("Closing the Browser");
		flushReports();
		closeBrowser();
	}
}

