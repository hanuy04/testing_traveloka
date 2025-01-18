package com.example.selenium;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AppTest {

  private App app;
  private WebDriverWait wait;

  @BeforeClass
  public void setUp() {
    app = new App();
    app.openURL("https://www.traveloka.com/en-id");
    wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(30));
  }

  @Test
  public void testHotelSearch() {
    // Klik button Hotels
    WebElement hotelButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[@aria-level='4' and contains(text(),'Hotels')]")
      )
    );
    clickElement(hotelButton);

    // Isi input pencarian dengan "Bali"
    WebElement searchInput = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath(
          "//input[@type='text' and @placeholder='City, hotel, place to go']"
        )
      )
    );
    searchInput.clear();
    searchInput.sendKeys("Bali");

    // Tunggu dropdown autocomplete muncul
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik item autocomplete untuk Bali
    WebElement baliAutocompleteItem = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.cssSelector(
          "div[data-testid='autocomplete-item-name'][class*='css-901oao'][class*='r-cwxd7f']"
        )
      )
    );
    ((JavascriptExecutor) app.getDriver()).executeScript(
        "arguments[0].scrollIntoView(true);",
        baliAutocompleteItem
      );
    clickElement(baliAutocompleteItem);

    // Klik tombol Search Hotels
    WebElement searchButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath(
          "//div[contains(text(),'Search Hotels') and @aria-hidden='true']"
        )
      )
    );
    clickElement(searchButton);

    // Tunggu hasil pencarian muncul
    WebElement firstHotelName = wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.cssSelector("h3[data-testid='tvat-hotelName']")
      )
    );
    String hotelName = firstHotelName.getText();

    // Verifikasi bahwa hasil pencarian muncul
    Assert.assertNotNull(hotelName, "Hasil pencarian hotel tidak ditemukan.");
    System.out.println("Hotel ditemukan: " + hotelName);
  }

  @Test
  public void testFlightSearch() {
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
  public void testKecakAndFireDanceSearch() {
    // Klik Fun Activities
    WebElement funActivitiesButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//a[contains(@href, 'activities')]")
      )
    );
    clickElement(funActivitiesButton);

    // Tunggu dropdown autocomplete muncul
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik "Pick a Destination"
    WebElement pickDestinationText = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[contains(text(), 'Pick a destination')]")
      )
    );
    clickElement(pickDestinationText);

    // Pilih destinasi Bali
    WebElement baliButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//h2[contains(text(),'Bali')]")
      )
    );
    clickElement(baliButton);

    // Tunggu dropdown autocomplete muncul
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik Kecak and Fire Dance
    WebElement kecakAndFireDanceButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//a[contains(@href, 'kecak-and-fire-dance')]")
      )
    ); // Ubah sesuai URL atau teks yang relevan
    clickElement(kecakAndFireDanceButton);

    // Verifikasi lokasi
    WebElement location = wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.xpath(
          "//div[@data-testid='lblProductLocation' and contains(text(),'Pura Uluwatu. Pecatu, South Kuta, Badung Regency, Bali, Indonesia')]"
        )
      )
    );
    Assert.assertNotNull(location, "Lokasi tidak ditemukan.");
    System.out.println("Lokasi ditemukan: " + location.getText());
  }

  @Test
  public void testFindOutPriceWithDepartureOnly() {
    // Tunggu dropdown autocomplete muncul
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // Klik input Select departure date
    WebElement departureDateInput = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//input[@placeholder='Select date']")
      )
    );
    clickElement(departureDateInput);

    // Pilih tanggal keberangkatan
    WebElement departureDate = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[@dir='auto' and contains(text(),'14')]")
      )
    ); // Ubah sesuai tanggal yang diinginkan
    clickElement(departureDate);

    // Klik tombol Search Flights (elemen diperbarui)
    WebElement searchButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[@data-testid='desktop-default-search-button']")
      )
    );
    clickElement(searchButton);

    // Tunggu dropdown autocomplete muncul
    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Tunggu hingga hasil pencarian selesai dimuat
    // wait.until(ExpectedConditions.presenceOfElementLocated(
    //     By.xpath("//div[contains(@class, 'css-901oao') and contains(.,'100%')]")));

    // Klik elemen baru "Choose"
    WebElement chooseButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath(
          "//div[@dir='auto' and @aria-hidden='true' and contains(@class, 'css-bfa6kz') and text()='Choose']"
        )
      )
    );
    chooseButton.click();

    // Klik tombol Continue
    WebElement continueButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[contains(text(),'Continue')]")
      )
    );
    clickElement(continueButton);
  }

  @Test
  public void testCarRentalBooking() {
    // Tunggu elemen dropdown autocomplete muncul
    try {
      Thread.sleep(20000); // Waktu tunggu disesuaikan
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik tombol "Car Rental"
    WebElement carRentalButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath(
          "//div[@class='css-1dbjc4n r-18u37iz r-1777fci r-1mnahxq']//div[text()='Car Rental']"
        )
      )
    );
    carRentalButton.click();

    // Isi input lokasi dengan "Surabaya"
    WebElement locationInput = wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.xpath("//input[@placeholder='Enter city or region']")
      )
    );
    locationInput.sendKeys("Surabaya");
    try {
      Thread.sleep(2000); // Tunggu autocomplete muncul
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    locationInput.sendKeys(Keys.ARROW_DOWN);
    locationInput.sendKeys(Keys.ENTER);

    // Klik input "End Time" dan isi dengan "20.00"
    WebElement endTimeInput = wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.xpath("//input[@data-testid='rental-search-form-time-input-end']")
      )
    );
    endTimeInput.click();
    endTimeInput.clear();
    endTimeInput.sendKeys("20.00");

    // Klik tombol "Search"
    WebElement searchButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[@data-testid='rental-search-form-cta']")
      )
    );
    searchButton.click();

    // Tunggu elemen pencarian selesai dimuat
    try {
      Thread.sleep(5000); // Waktu tunggu disesuaikan
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik "Passenger Capacity"
    WebElement passengerCapacity = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[contains(text(),'Passenger Capacity')]")
      )
    );
    passengerCapacity.click();

    // Centang kapasitas 5-6
    WebElement capacityCheckbox = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[contains(text(),'5 - 6')]")
      )
    );
    capacityCheckbox.click();

    // Klik "Transmission"
    WebElement transmissionButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[contains(text(),'Transmission')]")
      )
    );
    transmissionButton.click();

    // Centang "Automatic"
    WebElement automaticCheckbox = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//svg[@accentColor='#CDD0D1']")
      )
    );
    automaticCheckbox.click();

    // Klik "Car Type"
    WebElement carTypeButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[contains(text(),'Car Type')]")
      )
    );
    carTypeButton.click();

    // Centang "SUV"
    WebElement suvCheckbox = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//svg[@fillColor='#0194F3']")
      )
    );
    suvCheckbox.click();

    // Klik "Provider"
    WebElement providerButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//div[contains(text(),'Provider')]")
      )
    );
    providerButton.click();

    // Pilih "Lio Transport & Vvip Limousine Surabaya"
    WebElement providerOption = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath(
          "//div[contains(text(),'Lio Transport & Vvip Limousine Surabaya')]"
        )
      )
    );
    providerOption.click();

    // Tunggu hingga elemen "Toyota Raize" muncul
    WebElement toyotaRaize = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//h3[contains(text(),'Toyota Raize')]")
      )
    );
    clickElement(toyotaRaize);

    // Klik tombol "Continue" pada hasil pencarian Toyota Raize
    WebElement continueButtonToyota = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath(
          "//div[contains(@class, 'css-18t94o4') and .//div[contains(text(),'Continue')]]"
        )
      )
    );
    clickElement(continueButtonToyota);

    // Pilih rental provider pertama dengan menekan tombol "Continue"
    WebElement continueButtonProvider = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath(
          "(//div[contains(@class, 'css-18t94o4') and .//div[contains(text(),'Continue')]])[1]"
        )
      )
    );
    clickElement(continueButtonProvider);

    // Verifikasi informasi yang ditampilkan
    // Lokasi dan tanggal sewa
    WebElement rentalInfo = wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.xpath(
          "//div[contains(text(),'Surabaya • Sun, 19 Jan 2025, 09.00 - Tue, 21 Jan 2025, 20.00')]"
        )
      )
    );
    Assert.assertNotNull(rentalInfo);

    // Nama mobil
    WebElement carName = wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.xpath("//h2[contains(text(),'Toyota Raize')]")
      )
    );
    Assert.assertNotNull(carName);

    // Penyedia layanan
    WebElement providerInfo = wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.xpath(
          "//div[contains(text(),'Provided by Lio Transport & Vvip Limousine Surabaya')]"
        )
      )
    );
    Assert.assertNotNull(providerInfo);

    // Kapasitas tempat duduk
    WebElement seatInfo = wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.xpath("//div[contains(text(),'4 seats')]")
      )
    );
    Assert.assertNotNull(seatInfo);

    // Jenis transmisi
    WebElement transmissionInfo = wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.xpath("//div[contains(text(),'AUTOMATIC')]")
      )
    );
    Assert.assertNotNull(transmissionInfo);

    // Cetak hasil jika semua verifikasi berhasil
    System.out.println("Informasi sesuai dengan pilihan awal.");
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
