package org.imaginea.test.automation.framework.pagemodel;

import org.imaginea.test.automation.framework.config.DefaultConfig;
import org.imaginea.test.automation.framework.pagemodel.PageException;
import org.imaginea.test.automation.framework.pagemodel.TestClass;
import org.imaginea.test.automation.framework.pagemodel.pagelocator.GoogleHomeLocatorPage;
import org.imaginea.test.automation.framework.pagemodel.pagelocator.GoogleResultsLocatorPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


public class PageDriverTest extends TestClass{
	
	@Test
	public void diabledInbuildDriverTest(){
		DefaultConfig config = browser.getConfig();
		config.setConfigValue("use.inbuilt.driver", "false");

		WebDriver driver = new FirefoxDriver();
		browser.setDriver(driver);
		try {
			
			GoogleHomeLocatorPage homePage = to(GoogleHomeLocatorPage.class);

			homePage.searchForString("Testing");

			util.waitForPage(browser, GoogleResultsLocatorPage.class);

			GoogleResultsLocatorPage resultsPage = at(GoogleResultsLocatorPage.class);

			resultsPage.clickOnResult();

			WebDriverWait wait = new WebDriverWait(browser.getDriver(), 60);
			wait.until(ExpectedConditions.titleContains("Software testing"));
			
		} finally {
			
			config.setConfigValue("use.inbuilt.driver", "true");
			driver.quit();
			browser.setDriver(null);
		}
		
	}
	
	@Test(expectedExceptions=PageException.class)
	public void verifyExceptionOnDisabledInbuildDriver(){
		DefaultConfig config = browser.getConfig();
		config.setConfigValue("use.inbuilt.driver", "false");
		try {
			to(GoogleHomeLocatorPage.class);
		} finally {
			config.setConfigValue("use.inbuilt.driver", "true");
		}
	}

}
