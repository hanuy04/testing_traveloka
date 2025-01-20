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

public class FlightSearch {

  private App app;
  private WebDriverWait wait;

  @BeforeClass
  public void setUp() {
    app = new App();
    app.openURL("https://www.traveloka.com/en-id");
    wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(30));
  }

  // CARI TIKET PESAWAT TANPA ISI RETURN DATE
  @Test
  public void testFlightSearch() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 10 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik button Flights
    WebElement flightsButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[@aria-level='4' and contains(text(),'Flights')]")
      )
    );
    clickElement(flightsButton);

    // Isi input "From" dengan "Surabaya"
    WebElement fromInput = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//input[@type='text' and @placeholder='Origin']")
      )
    );
    fromInput.clear();
    fromInput.sendKeys("Surabaya");

    // Tunggu dropdown autocomplete muncul dan pilih elemen sesuai input
    WebElement surabayaAutocompleteItem = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath(
          "//div[@dir='auto' and contains(@class, 'css-901oao') and contains(text(),'Juanda')]"
        )
      )
    );
    ((JavascriptExecutor) app.getDriver()).executeScript(
        "arguments[0].scrollIntoView(true);",
        surabayaAutocompleteItem
      );
    clickElement(surabayaAutocompleteItem);

    // Klik tombol Search Flights
    WebElement searchButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[contains(text(),'Search Flights')]")
      )
    );
    clickElement(searchButton);

    // Tunggu elemen <div> muncul
    boolean isDivPresent =
      wait
        .until(
          ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.xpath("//div[contains(@class,'css-cens5h')]")
          )
        )
        .size() >
      0;

    // Tampilkan hasil
    if (isDivPresent) {
      System.out.println("Elemen <div> ditemukan.");
    } else {
      System.out.println("Elemen <div> tidak ditemukan.");
    }

    // Verifikasi bahwa elemen div ditemukan
    Assert.assertTrue(
      isDivPresent,
      "Hasil pencarian tiket pesawat tidak ditemukan."
    );
  }

  @Test
  public void testFlightSearchWithReturnDate() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 10 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik button Flights
    WebElement flightsButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@aria-level='4' and contains(text(),'Flights')]")));
    clickElement(flightsButton);

    // Klik checkbox Return Date untuk mengaktifkan form tanggal kembali
    WebElement returnDateCheckbox = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(text(),'Return Date')]//ancestor::div[@aria-checked='false']")));
    clickElement(returnDateCheckbox);

    // Tunggu input Return Date muncul
    WebElement returnDateInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@data-testid='return-date-input']")));

    // Isi Return Date (misalnya tanggal 21)
    // returnDateInput.clear();
    returnDateInput.sendKeys("21 Mar 2025");

    // Tunggu tombol Search Flights muncul
    WebElement searchButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(text(),'Search Flights')]")));
    clickElement(searchButton);

    // Tunggu beberapa detik untuk memastikan hasil pencarian dimuat
    try {
      Thread.sleep(5000); // Delay 5 detik untuk memastikan hasil pencarian dimuat
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Scroll ke hasil pencarian (misalnya untuk memastikan hasil pencarian
    // terlihat)
    WebElement resultDiv = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class,'css-cens5h')]")));
    ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", resultDiv);

    // Tunggu elemen <div> hasil pencarian
    boolean isDivPresent = wait
        .until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class,'css-cens5h')]")))
        .size() > 0;

    // Tampilkan hasil
    if (isDivPresent) {
      System.out.println("Elemen <div> ditemukan.");
    } else {
      System.out.println("Elemen <div> tidak ditemukan.");
    }

    // Verifikasi bahwa elemen div ditemukan
    Assert.assertTrue(
        isDivPresent,
        "Hasil pencarian tiket pesawat tidak ditemukan.");
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
