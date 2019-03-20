package junit.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.TutByHomePage;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
        assertThrows(NoSuchElementException.class, () ->
                homePage.getUserNameElement().isDisplayed());
    }

}
