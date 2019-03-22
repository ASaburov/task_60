package junit.tests;


import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.TutByHomePage;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertEquals;


public class TutByLoginLogoutTests {

    private static final String URL = "https://tut.by";
    private static final String LOGIN = "seleniumtests@tut.by";
    private static final String PASSWORD = "123456789zxcvbn";


    private WebDriver driver;

    @BeforeEach
    public void openBrowser() {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get(URL);
    }

    @AfterEach
    public void closeBrowser() {
        driver.close();
    }

    //test logs in the app and check whether username appeared in the corresponding field
    @Test
    public void loginProcessTest() {
        TutByHomePage homePage = new TutByHomePage(driver);
        homePage.expandEnterPopup();
        homePage.login(LOGIN, PASSWORD);
        assertEquals("Selenium Test", homePage.getUserNameElement().getText());
    }

    //test logs out the app and check whether username is not displayed
    @Test
    public void logoutProcessTest() {
        TutByHomePage homePage = new TutByHomePage(driver);
        homePage.expandEnterPopup();
        homePage.login(LOGIN, PASSWORD);
        homePage.expandEnterPopup();
        homePage.logout();
        //taking a screenshot and saving it to the file
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile,new File("src/main/screenshots" + "logoutImage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThrows(NoSuchElementException.class, () ->
                homePage.getUserNameElement().isDisplayed());
    }


}