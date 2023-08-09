package Tdd.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BookMyShow {

	public static void main(String[] args) throws InterruptedException {

		 WebDriverManager.chromedriver().setup();

	        WebDriver driver = new ChromeDriver();

	        driver.get("https://in.bookmyshow.com/explore/home/");
	        driver.manage().window().maximize();

	        WebElement City = driver.findElement(By.xpath("//span[text()='Bengaluru']"));
	        City.click();

	        WebElement Signin = driver.findElement(By.xpath("//div[@class='sc-dtInlm bnklgU']"));
	        Signin.click();

	        WebElement Sign = driver.findElement(By.xpath("//div[text()='Continue with Email']"));
	        Sign.click();

	        WebElement Email = driver.findElement(By.xpath("//input[@id='emailId']"));
	        Email.click();
	        Email.sendKeys("selauto@yopmail.com");

	        WebElement button1 = driver.findElement(By.xpath("//button[text()='Continue']"));
	        button1.click();

	        driver.get("https://yopmail.com");

	        WebElement selauto = driver.findElement(By.xpath("//input[@id='login']"));
	        selauto.sendKeys("selauto@yopmail.com");

	        WebElement button2 = driver.findElement(By.cssSelector("input[type='submit']"));
	        button2.click();
	        Thread.sleep(2000);

	        // Switch to the yopmail iframe
	        driver.switchTo().frame("ifinbox");

	        // Click on the first email
	        WebElement button = driver.findElement(By.cssSelector(".slientext"));
	        button.click();
	        Thread.sleep(2000);

	        // Switch to the email content iframe
	        driver.switchTo().frame("ifmail");

	        // Extract OTP from the email content
	        String emailContent = driver.findElement(By.id("mailmillieu")).getText();
	        String otp = extractOTPFromEmail(emailContent);

	        // Switch back to the main page
	        driver.switchTo().defaultContent();

	        // Switch back to the original tab
	        String originalTab = driver.getWindowHandle();
	        for (String tab : driver.getWindowHandles()) {
	            if (!tab.equals(originalTab)) {
	                driver.switchTo().window(tab);
	                break;
	            }
	        }

	        // Continue with using the extracted OTP
	        WebElement otp2 = driver.findElement(By.id("otp"));
	        otp2.sendKeys(otp);
	        		
	        WebElement login = driver.findElement(By.id("loginOTP"));
	        login.click();

	        WebElement greetings = driver.findElement(By.cssSelector(".user-greeting span"));
	        if (greetings.getText().equals("Hi, Guest")) {
	            System.out.println("User successfully signed in.");
	        } else {
	            System.out.println("Sign-in failed.");
	        }

	        driver.quit();
	    }

	    private static String extractOTPFromEmail(String emailContent) {
	        return emailContent.substring(emailContent.indexOf("OTP:") + 5, emailContent.indexOf("OTP:") + 10).trim();
	    }
	}