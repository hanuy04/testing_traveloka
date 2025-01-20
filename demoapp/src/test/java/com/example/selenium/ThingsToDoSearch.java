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

public class ThingsToDoSearch {

  private App app;
  private WebDriverWait wait;

  @BeforeClass
  public void setUp() {
    app = new App();
    app.openURL("https://www.traveloka.com/en-id");
    wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(30));
  }

  @Test
  public void testArumJeramSearch() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 10 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    WebElement thingsToDoButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@data-testid='product-pill-Things to Do']")));
    clickElement(thingsToDoButton);

    WebElement ideasInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='Any ideas on what to do for your next trip?' and @type='text']")));
    clickElement(ideasInput);

    // Isi input dengan teks "arum jeram"
    ideasInput.sendKeys("arum jeram");

    // Tunggu hasil autocomplete muncul
    WebElement autocompleteItem = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//h4[normalize-space(text())='Arum Jeram']")));

    // Klik hasil autocomplete yang sesuai dengan teks "Arum Jeram"
    clickElement(autocompleteItem);

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Memeriksa gambar dengan atribut src
    WebElement imageElement = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//img[contains(@src, 'Full-Day-Tour-Banyuwangi-Arum-Jeram-Rumah-Apung')]")));

    // Scroll ke gambar agar terlihat
    JavascriptExecutor js = (JavascriptExecutor) app.getDriver();
    js.executeScript("arguments[0].scrollIntoView(true);", imageElement);

    // Verifikasi gambar ada
    Assert.assertNotNull("Image not found", imageElement.getAttribute("src"));

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  // CARI THINGS TO DO, GAMES & ACTIVITIES, GOKART
  @Test
  public void testFindCarGocar() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 10 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik "Things To Do"
    WebElement thingsToDoButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='css-901oao r-nk2qpz r-a5wbuh r-b88u0q r-q4m81j' and text()='Things to Do']")));
    clickElement(thingsToDoButton);

    // Klik "Games & Activities"
    WebElement gamesAndActivitiesButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-901oao r-jwli3a r-a5wbuh r-majxgm r-fdjqy7' and text()='Games & Activities']")));
    clickElement(gamesAndActivitiesButton);

    // Klik gambar "Gokart"
    WebElement gokartImage = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//h2[text()='Gokart']")));
    clickElement(gokartImage);

    // Tunggu hasil penelusuran muncul
    WebElement searchResult = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h2[contains(text(),'Bali International Go-Kart Circuit')]")));

    // Verifikasi apakah hasil penelusuran ditemukan
    Assert.assertNotNull("Hasil penelusuran tidak ditemukan", searchResult.toString());
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
