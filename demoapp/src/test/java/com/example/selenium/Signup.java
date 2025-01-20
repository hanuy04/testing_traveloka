package com.example.selenium;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Signup {

  private App app;
  private WebDriverWait wait;

  @BeforeClass
  public void setUp() {
    app = new App();
    app.openURL("https://www.traveloka.com/en-id");
    wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(30));
  }

  @Test
  public void testSignUpWithoutInputEmail () {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 12 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    WebElement registerButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='css-901oao css-bfa6kz r-jwli3a r-a5wbuh r-1o4mh9l r-b88u0q r-f0eezp r-q4m81j' and text()='Register']")));
    registerButton.click();

    WebElement emailOrMobileNumberInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//input[@class='css-11aywtz r-13awgt0 r-1pi2tsx r-1ny4l3l r-t60dpp r-ct1kkl' and @placeholder='Example: +62812345678 or yourname@email.com']")));
    emailOrMobileNumberInput.sendKeys("");

    WebElement continueButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-1w9mtv9 r-1777fci r-rjixqe r-fdjqy7 r-lrvibr' and text()='Continue']")));

    // Assert bahwa tombol Continue tidak dapat diklik karena input kosong
    Assert.assertTrue(continueButton.isEnabled(), "Tombol Continue seharusnya tidak dapat diklik karena input kosong");
  }

  @Test
  public void testSignUpWithInputEmail() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 12 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    WebElement registerButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-a5wbuh r-1o4mh9l r-b88u0q r-f0eezp r-q4m81j' and text()='Register']")));
    registerButton.click();

    WebElement emailOrMobileNumberInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//input[@class='css-11aywtz r-13awgt0 r-1pi2tsx r-1ny4l3l r-t60dpp r-ct1kkl' and @placeholder='Example: +62812345678 or yourname@email.com']")));
    emailOrMobileNumberInput.sendKeys("hanvy.h22@mhs.istts.ac.id");

    // Tunggu beberapa detik untuk memastikan CAPTCHA muncul dan diberi waktu untuk
    // diisi
    try {
      Thread.sleep(5000); // Delay 15 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Tunggu sampai tombol Register muncul setelah CAPTCHA diisi
    WebElement registerButtonEnd = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-1w9mtv9 r-1777fci r-rjixqe r-fdjqy7 r-lrvibr' and text()='Register']")));

    // Klik tombol Register akhir
    registerButtonEnd.click();

    // Tambahkan pengecekan untuk tombol "Verify"
    WebElement verifyButton = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[text()='Verify']")));

    // Assert bahwa tombol "Verify" muncul setelah register
    Assert.assertTrue(verifyButton.isDisplayed(), "Tombol Verify seharusnya muncul setelah klik Register");
  }

  @Test
  public void testSignUpWithoutInputVerificationCode() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 12 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    WebElement registerButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-a5wbuh r-1o4mh9l r-b88u0q r-f0eezp r-q4m81j' and text()='Register']")));
    registerButton.click();

    WebElement emailOrMobileNumberInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//input[@class='css-11aywtz r-13awgt0 r-1pi2tsx r-1ny4l3l r-t60dpp r-ct1kkl' and @placeholder='Example: +62812345678 or yourname@email.com']")));
    emailOrMobileNumberInput.sendKeys("hariantoevy@gmail.com");

    // Tunggu beberapa detik untuk memastikan CAPTCHA muncul dan diberi waktu untuk
    // diisi
    try {
      Thread.sleep(5000); // Delay 15 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Tunggu sampai tombol Log In muncul setelah CAPTCHA diisi
    WebElement registerButtonEnd = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-1w9mtv9 r-1777fci r-rjixqe r-fdjqy7 r-lrvibr' and text()='Register']")));
    clickElement(registerButtonEnd);

    // Tunggu sampai tombol Verify muncul
    WebElement verifyButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(text(), 'Verify')]"))); // Pastikan XPath tombol Verify sesuai dengan elemen yang benar

    // Cek apakah tombol Verify tidak aktif (disabled)
    Assert.assertTrue(verifyButton.isEnabled(),
        "Tombol Verify seharusnya tidak aktif karena kode verifikasi belum diisi");
  }

  @Test
  public void testSignUpWithFalseInputVerificationCode() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 12 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    WebElement registerButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-a5wbuh r-1o4mh9l r-b88u0q r-f0eezp r-q4m81j' and text()='Register']")));
    registerButton.click();

    WebElement emailOrMobileNumberInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//input[@class='css-11aywtz r-13awgt0 r-1pi2tsx r-1ny4l3l r-t60dpp r-ct1kkl' and @placeholder='Example: +62812345678 or yourname@email.com']")));
    emailOrMobileNumberInput.sendKeys("hariantoevy@gmail.com");

    // Tunggu beberapa detik untuk memastikan CAPTCHA muncul dan diberi waktu untuk
    // diisi
    try {
      Thread.sleep(5000); // Delay 15 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Tunggu sampai tombol Log In muncul setelah CAPTCHA diisi
    WebElement registerButtonEnd = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-1w9mtv9 r-1777fci r-rjixqe r-fdjqy7 r-lrvibr' and text()='Register']")));
    clickElement(registerButtonEnd);

    // Tambahkan delay jika perlu
    try {
      Thread.sleep(3000); // Delay setelah mengisi input
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Tunggu sampai input verification code muncul
    List<WebElement> verificationCodeInputs = wait.until(
        ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.xpath("//input[@maxlength='6']")));

    // Isi semua input verification code
    for (int i = 0; i < verificationCodeInputs.size(); i++) {
      verificationCodeInputs.get(i).sendKeys(String.valueOf(i + 1)); // Mengisi angka 1-6 secara berurutan
    }

    // Tunggu tombol Verify muncul
    WebElement verifyButton = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(text(), 'Verify')]")));

    // Pastikan tombol Verify tidak aktif
    Assert.assertTrue(verifyButton.isEnabled(),
        "Tombol Verify seharusnya tidak aktif jika input belum terisi dengan benar");

    System.out.println("Test passed: Verify button is disabled.");
  }

  // pake notelp
  @Test
  public void testSignUpWithoutInputMobileNumber() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 12 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    WebElement registerButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-a5wbuh r-1o4mh9l r-b88u0q r-f0eezp r-q4m81j' and text()='Register']")));
    registerButton.click();

    WebElement emailOrMobileNumberInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//input[@class='css-11aywtz r-13awgt0 r-1pi2tsx r-1ny4l3l r-t60dpp r-ct1kkl' and @placeholder='Example: +62812345678 or yourname@email.com']")));
    emailOrMobileNumberInput.sendKeys("");

    WebElement continueButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-1w9mtv9 r-1777fci r-rjixqe r-fdjqy7 r-lrvibr' and text()='Continue']")));

    // Assert bahwa tombol Continue tidak dapat diklik karena input kosong
    Assert.assertTrue(continueButton.isEnabled(), "Tombol Continue seharusnya tidak dapat diklik karena input kosong");
  }

  @Test
  public void testSignUpWithInputMobileNumber() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 12 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    WebElement registerButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-a5wbuh r-1o4mh9l r-b88u0q r-f0eezp r-q4m81j' and text()='Register']")));
    registerButton.click();

    WebElement emailOrMobileNumberInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//input[@class='css-11aywtz r-13awgt0 r-1pi2tsx r-1ny4l3l r-t60dpp r-ct1kkl' and @placeholder='Example: +62812345678 or yourname@email.com']")));
    emailOrMobileNumberInput.sendKeys("+6285157145003");

    // Tunggu beberapa detik untuk memastikan CAPTCHA muncul dan diberi waktu untuk
    // diisi
    try {
      Thread.sleep(5000); // Delay 15 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Tunggu sampai tombol Log In muncul setelah CAPTCHA diisi
    WebElement registerButtonEnd = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-1w9mtv9 r-1777fci r-rjixqe r-fdjqy7 r-lrvibr' and text()='Register']")));

    // Klik tombol Register akhir
    registerButtonEnd.click();

    // Tambahkan pengecekan untuk tombol "Verify"
    WebElement verifyButton = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[text()='Verify']")));

    // Assert bahwa tombol "Verify" muncul setelah register
    Assert.assertTrue(verifyButton.isDisplayed(), "Tombol Verify seharusnya muncul setelah klik Register");
  }

  @Test
  public void testSignUpWithInputMobileNumberWhithoutInputVerificationCode() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 12 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    WebElement registerButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-a5wbuh r-1o4mh9l r-b88u0q r-f0eezp r-q4m81j' and text()='Register']")));
    registerButton.click();

    WebElement emailOrMobileNumberInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//input[@class='css-11aywtz r-13awgt0 r-1pi2tsx r-1ny4l3l r-t60dpp r-ct1kkl' and @placeholder='Example: +62812345678 or yourname@email.com']")));
    emailOrMobileNumberInput.sendKeys("+6281554355728");

    // Tunggu sampai tombol Log In muncul setelah CAPTCHA diisi
    WebElement registerButtonEnd = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-1w9mtv9 r-1777fci r-rjixqe r-fdjqy7 r-lrvibr' and text()='Register']")));
    clickElement(registerButtonEnd);

    // Tunggu beberapa detik untuk memastikan CAPTCHA muncul dan diberi waktu untuk
    // diisi
    try {
      Thread.sleep(5000); // Delay 15 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik tombol Verify di awal
    WebElement initialVerifyButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='css-1dbjc4n r-1awozwy r-18u37iz r-h3s6tt r-1777fci r-ymttw5']")));
    initialVerifyButton.click();

    // Tambahkan delay jika perlu
    try {
      Thread.sleep(3000); // Delay setelah mengisi input
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Tunggu sampai input verification code muncul
    List<WebElement> verificationCodeInputs = wait.until(
        ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.xpath("//input[@maxlength='4']")));

    // Isi semua input verification code
    for (int i = 0; i < verificationCodeInputs.size(); i++) {
      verificationCodeInputs.get(i).sendKeys("");
    }

    // Tunggu tombol Verify muncul
    WebElement verifyButton = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(text(), 'Verify')]")));

    // Pastikan tombol Verify tidak aktif
    Assert.assertTrue(verifyButton.isEnabled(),
        "Tombol Verify seharusnya tidak aktif jika input belum terisi dengan benar");

    System.out.println("Test passed: Verify button is disabled.");
  }

  @Test
  public void testSignUpWithInputMobileNumberWithFalseInputVerificationCode() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 12 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    WebElement registerButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-a5wbuh r-1o4mh9l r-b88u0q r-f0eezp r-q4m81j' and text()='Register']")));
    registerButton.click();

    WebElement emailOrMobileNumberInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//input[@class='css-11aywtz r-13awgt0 r-1pi2tsx r-1ny4l3l r-t60dpp r-ct1kkl' and @placeholder='Example: +62812345678 or yourname@email.com']")));
    emailOrMobileNumberInput.sendKeys("+6285157145003");

    // Tunggu beberapa detik untuk memastikan CAPTCHA muncul dan diberi waktu untuk
    // diisi
    try {
      Thread.sleep(5000); // Delay 15 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Tunggu sampai tombol Log In muncul setelah CAPTCHA diisi
    WebElement registerButtonEnd = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath(
                "//div[@class='css-901oao css-bfa6kz r-jwli3a r-1w9mtv9 r-1777fci r-rjixqe r-fdjqy7 r-lrvibr' and text()='Register']")));
    clickElement(registerButtonEnd);

    // Tambahkan delay jika perlu
    try {
      Thread.sleep(3000); // Delay setelah mengisi input
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik tombol Verify di awal
    WebElement initialVerifyButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='css-1dbjc4n r-1awozwy r-18u37iz r-h3s6tt r-1777fci r-ymttw5']")));
    initialVerifyButton.click();

    // Tambahkan delay jika perlu
    try {
      Thread.sleep(3000); // Delay setelah mengisi input
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Tunggu sampai input verification code muncul
    List<WebElement> verificationCodeInputs = wait.until(
        ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.xpath("//input[@maxlength='4']")));

    // Isi semua input verification code
    for (int i = 0; i < verificationCodeInputs.size(); i++) {
      verificationCodeInputs.get(i).sendKeys(String.valueOf(i + 1)); // Mengisi angka 1-6 secara berurutan
    }

    // Tunggu tombol Verify muncul
    WebElement verifyButton = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(text(), 'Verify')]")));

    // Pastikan tombol Verify tidak aktif
    Assert.assertTrue(verifyButton.isEnabled(),
        "Tombol Verify seharusnya tidak aktif jika input belum terisi dengan benar");

    System.out.println("Test passed: Verify button is disabled.");

    // Tambahkan delay jika perlu
    try {
      Thread.sleep(3000); // Delay setelah mengisi input
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void clickElement(WebElement element) {
    try {
      element.click();
    } catch (ElementClickInterceptedException e) {
      JavascriptExecutor js = (JavascriptExecutor) app.getDriver();
      js.executeScript("arguments[0].click();", element);
    }
  }

  @AfterClass
  public void tearDown() {
    app.closeBrowser();
  }
}
