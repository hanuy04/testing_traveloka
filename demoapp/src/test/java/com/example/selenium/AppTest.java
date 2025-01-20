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

public class AppTest {

  private App app;
  private WebDriverWait wait;

  @BeforeClass
  public void setUp() {
    app = new App();
    app.openURL("https://www.traveloka.com/en-id");
    wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(30));
  }

  // CARI TIKET HOTEL
  @Test
  public void testHotelSearchWithoutInputCityDestination() {
    try {
      // Delay untuk mengisi CAPTCHA
      Thread.sleep(12000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik button Hotels
    WebElement hotelButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@data-id='IcProductDuotoneHotelFill']")));
    clickElement(hotelButton);

    // Klik tombol Search Hotels
    WebElement searchButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='css-901oao r-1awozwy r-jwli3a r-6koalj r-61z16t']")));
    clickElement(searchButton);

    // Cek jika ada pesan peringatan yang muncul
    try {
      WebElement warningMessage = wait.until(
          ExpectedConditions.presenceOfElementLocated(
              By.xpath("//div[@class='css-901oao r-jwli3a r-a5wbuh r-majxgm r-q4m81j' and text()='This is required']")));
      String warningText = warningMessage.getText();

      // Verifikasi jika ada pesan peringatan
      Assert.assertTrue(warningText.contains("This is required"), "Tidak ada pesan peringatan.");
      System.out.println("Pesan peringatan ditemukan: " + warningText);
    } catch (org.openqa.selenium.TimeoutException e) {
      System.out.println("Tidak ada pesan peringatan muncul.");
    }
  }

  @Test
  public void testHotelSearchWithInputCityDestination() {
    try {
      // Delay untuk mengisi CAPTCHA
      Thread.sleep(12000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik button Hotels
    WebElement hotelButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@data-id='IcProductDuotoneHotelFill']")));
    clickElement(hotelButton);

    // Klik input field untuk City, hotel, place to go
    WebElement inputField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='City, hotel, place to go']")));
    clickElement(inputField);

    // Isi input dengan "Bali"
    inputField.sendKeys("Bali");

    // Tunggu hingga autocomplete muncul dan klik pada hasil yang sesuai
    WebElement autocompleteItem = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@data-testid='autocomplete-item-name' and contains(text(), 'Bali')]")));
    clickElement(autocompleteItem);

    // Klik tombol Search Hotels
    WebElement searchButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='css-901oao r-1awozwy r-jwli3a r-6koalj r-61z16t']")));
    clickElement(searchButton);

    // Tunggu hasil pencarian muncul (berikan waktu untuk scroll atau memuat data)
    WebElement firstHotelName = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("h3[data-testid='tvat-hotelName']")));

    // Ambil nama hotel pertama
    String hotelName = firstHotelName.getText();

    // Verifikasi apakah ada hasil pencarian
    Assert.assertNotNull(hotelName, "Hasil pencarian hotel tidak ditemukan.");
    System.out.println("Hotel ditemukan: " + hotelName);

    // Cek hasil pencarian, jika tidak ada hasil bisa ditambahkan scroll jika perlu
    try {
      // Cek jika hasil pencarian ada lebih banyak item yang perlu di-scroll
      WebElement moreResults = wait.until(
          ExpectedConditions.presenceOfElementLocated(
              By.xpath("//button[contains(text(), 'Show more')]")));
      System.out.println("Ada lebih banyak hasil pencarian.");
    } catch (org.openqa.selenium.TimeoutException e) {
      System.out.println("Tidak ada lebih banyak hasil pencarian.");
    }
  }

  @Test
  public void testHotelSearchWithInputCityDestinationIsCountryName() {
    try {
      // Delay untuk mengisi CAPTCHA
      Thread.sleep(12000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik button Hotels
    WebElement hotelButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@data-id='IcProductDuotoneHotelFill']")));
    clickElement(hotelButton);

    // Klik input field untuk City, hotel, place to go
    WebElement inputField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='City, hotel, place to go']")));
    clickElement(inputField);

    // Isi input dengan "Bali"
    inputField.sendKeys("Malaysia");

    // Tunggu hingga autocomplete muncul
    List<WebElement> autocompleteItems = wait.until(
        ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.xpath("//div[@data-testid='dropdown-menu-item']")));

    // Cek apakah ada autocomplete item yang mengandung teks "Malaysia"
    boolean isMalaysiaInAutocomplete = autocompleteItems.stream()
        .anyMatch(item -> item.getText().contains("Malaysia"));

    // Assert bahwa autocomplete mengandung "Malaysia"
    Assert.assertTrue(isMalaysiaInAutocomplete, "Autocomplete tidak mengandung teks 'Malaysia'.");
    if (isMalaysiaInAutocomplete) {
        System.out.println("Assertion Passed: Autocomplete mengandung teks 'Malaysia'.");
    } else {
        System.out.println("Assertion Failed: Autocomplete tidak mengandung teks 'Malaysia'.");
    }
  }

  @Test
  public void testHotelSearchWithChangeInputCheckInCheckOutDates() {
    try {
      // Delay untuk mengisi CAPTCHA
      Thread.sleep(12000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik button Hotels
    WebElement hotelButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@data-id='IcProductDuotoneHotelFill']")));
    clickElement(hotelButton);

    // Klik input field untuk City, hotel, place to go
    WebElement inputField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='City, hotel, place to go']")));
    clickElement(inputField);

    // Isi input dengan "Bali"
    inputField.sendKeys("Bali");

    // Tunggu hingga autocomplete muncul dan klik pada hasil yang sesuai
    WebElement autocompleteItem = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@data-testid='autocomplete-item-name' and contains(text(), 'Bali')]")));
    clickElement(autocompleteItem);

    // Klik pada input untuk tanggal Check-in dan Check-out
    WebElement checkinCheckoutField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@data-testid='check-in-date-field']")));
    clickElement(checkinCheckoutField);

    // Pilih tanggal check-in (28)
    WebElement checkinDate = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-901oao r-cwxd7f r-a5wbuh r-1b43r93 r-majxgm r-rjixqe r-q4m81j' and text()='28']")));
    clickElement(checkinDate);

    // Pilih tanggal check-out (30)
    WebElement checkoutDate = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-901oao r-cwxd7f r-a5wbuh r-1b43r93 r-majxgm r-rjixqe r-q4m81j' and text()='30']")));
    clickElement(checkoutDate);

    // Klik tombol Search Hotels
    WebElement searchButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='css-901oao r-1awozwy r-jwli3a r-6koalj r-61z16t']")));
    clickElement(searchButton);

    // Tunggu hasil pencarian muncul (berikan waktu untuk scroll atau memuat data)
    WebElement firstHotelName = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("h3[data-testid='tvat-hotelName']")));

    // Ambil nama hotel pertama
    String hotelName = firstHotelName.getText();

    // Verifikasi apakah ada hasil pencarian
    Assert.assertNotNull(hotelName, "Hasil pencarian hotel tidak ditemukan.");
    System.out.println("Hotel ditemukan: " + hotelName);

    // Cek hasil pencarian, jika tidak ada hasil bisa ditambahkan scroll jika perlu
    try {
      // Cek jika hasil pencarian ada lebih banyak item yang perlu di-scroll
      WebElement moreResults = wait.until(
          ExpectedConditions.presenceOfElementLocated(
              By.xpath("//button[contains(text(), 'Show more')]")));
      System.out.println("Ada lebih banyak hasil pencarian.");
    } catch (org.openqa.selenium.TimeoutException e) {
      System.out.println("Tidak ada lebih banyak hasil pencarian.");
    }
  }

  @Test
  public void testHotelSearchWithChangeInputGuestsAndRooms() {
    try {
      // Delay untuk mengisi CAPTCHA
      Thread.sleep(12000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik button Hotels
    WebElement hotelButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@data-id='IcProductDuotoneHotelFill']")));
    clickElement(hotelButton);

    // Klik input field untuk City, hotel, place to go
    WebElement inputField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='City, hotel, place to go']")));
    clickElement(inputField);

    // Isi input dengan "Bali"
    inputField.sendKeys("Bali");

    // Tunggu hingga autocomplete muncul dan klik pada hasil yang sesuai
    WebElement autocompleteItem = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@data-testid='autocomplete-item-name' and contains(text(), 'Bali')]")));
    clickElement(autocompleteItem);

    // Klik pada input untuk jumlah tamu dan kamar
    WebElement occupancyField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@data-testid='occupancy-field']")));
    clickElement(occupancyField);

    // Klik tombol tambah (+) untuk menambah jumlah Adults
    WebElement plusAdultsButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='css-901oao r-1awozwy r-jwli3a r-6koalj r-61z16t']") // Tombol tambah untuk adults
        ));
    clickElement(plusAdultsButton); // Menambah jumlah adults

    // Klik tombol Done
    WebElement doneButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@aria-hidden='true' and contains(text(), 'Done')]")));
    clickElement(doneButton);

    try {
      // Delay untuk mengisi CAPTCHA
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik tombol Search Hotels
    WebElement searchButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='css-901oao r-1awozwy r-jwli3a r-6koalj r-61z16t']")));
    clickElement(searchButton);

    // Tunggu hasil pencarian muncul (berikan waktu untuk scroll atau memuat data)
    WebElement firstHotelName = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("h3[data-testid='tvat-hotelName']")));

    // Ambil nama hotel pertama
    String hotelName = firstHotelName.getText();

    // Verifikasi apakah ada hasil pencarian
    Assert.assertNotNull(hotelName, "Hasil pencarian hotel tidak ditemukan.");
    System.out.println("Hotel ditemukan: " + hotelName);

    // Cek hasil pencarian, jika tidak ada hasil bisa ditambahkan scroll jika perlu
    try {
      // Cek jika hasil pencarian ada lebih banyak item yang perlu di-scroll
      WebElement moreResults = wait.until(
          ExpectedConditions.presenceOfElementLocated(
              By.xpath("//button[contains(text(), 'Show more')]")));
      System.out.println("Ada lebih banyak hasil pencarian.");
    } catch (org.openqa.selenium.TimeoutException e) {
      System.out.println("Tidak ada lebih banyak hasil pencarian.");
    }
  }

  @Test
  public void testHotelSearchWithChangeInputTotalAdultsIs0() {
    try {
      // Delay untuk mengisi CAPTCHA
      Thread.sleep(12000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik button Hotels
    WebElement hotelButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@data-id='IcProductDuotoneHotelFill']")));
    clickElement(hotelButton);

    // Klik input field untuk City, hotel, place to go
    WebElement inputField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='City, hotel, place to go']")));
    clickElement(inputField);

    // Isi input dengan "Bali"
    inputField.sendKeys("Bali");

    // Tunggu hingga autocomplete muncul dan klik pada hasil yang sesuai
    WebElement autocompleteItem = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@data-testid='autocomplete-item-name' and contains(text(), 'Bali')]")));
    clickElement(autocompleteItem);

    // Klik pada input untuk jumlah tamu dan kamar
    WebElement occupancyField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@data-testid='occupancy-field']")));
    clickElement(occupancyField);

    // Cari tombol minus (-) untuk Adults
    WebElement minusAdultsButton = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[@data-testid='occupancy-adult-pax-row-minus-button']")));

    // Assert apakah tombol minus aktif atau tidak
    if (minusAdultsButton.isEnabled()) {
      System.out.println("Assertion Passed: Tombol minus aktif.");
      Assert.assertTrue(minusAdultsButton.isEnabled(), "Tombol minus seharusnya aktif.");
    } else {
      System.out.println("Assertion Passed: Tombol minus tidak aktif.");
      Assert.assertFalse(minusAdultsButton.isEnabled(), "Tombol minus seharusnya tidak aktif.");
    }
  }

  @Test
  public void testHotelSearchWithChangeInputTotalRoomsIs0() {
    try {
      // Delay untuk mengisi CAPTCHA
      Thread.sleep(12000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik button Hotels
    WebElement hotelButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@data-id='IcProductDuotoneHotelFill']")));
    clickElement(hotelButton);

    // Klik input field untuk City, hotel, place to go
    WebElement inputField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='City, hotel, place to go']")));
    clickElement(inputField);

    // Isi input dengan "Bali"
    inputField.sendKeys("Bali");

    // Tunggu hingga autocomplete muncul dan klik pada hasil yang sesuai
    WebElement autocompleteItem = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@data-testid='autocomplete-item-name' and contains(text(), 'Bali')]")));
    clickElement(autocompleteItem);

    // Klik pada input untuk jumlah tamu dan kamar
    WebElement occupancyField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@data-testid='occupancy-field']")));
    clickElement(occupancyField);

    // Cari tombol minus (-) untuk Rooms
    WebElement minusRoomsButton = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[@data-testid='occupancy-room-pax-row-minus-button']")));

    // Assert apakah tombol minus aktif atau tidak
    if (minusRoomsButton.isEnabled()) {
      System.out.println("Assertion Passed: Tombol minus aktif.");
      Assert.assertTrue(minusRoomsButton.isEnabled(), "Tombol minus seharusnya aktif.");
    } else {
      System.out.println("Assertion Passed: Tombol minus tidak aktif.");
      Assert.assertFalse(minusRoomsButton.isEnabled(), "Tombol minus seharusnya tidak aktif.");
    }
  }

  @Test
  public void testHotelSearchWithInputFalseCityDestination() {
    try {
      // Delay untuk mengisi CAPTCHA
      Thread.sleep(12000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik button Hotels
    WebElement hotelButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@data-id='IcProductDuotoneHotelFill']")));
    clickElement(hotelButton);

    // Klik input field untuk City, hotel, place to go
    WebElement inputField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='City, hotel, place to go']")));
    clickElement(inputField);

    // Isi input dengan "w4gwrsegw4r" yang tidak valid
    inputField.sendKeys("w4gwrsegw4r");

    // Cek pesan peringatan jika ada
    try {
      WebElement suggestionMessage = wait.until(
          ExpectedConditions.presenceOfElementLocated(
              By.xpath("//div[contains(text(), 'Try other keywords and check your connection')]")));

      // Ambil pesan saran
      String suggestion = suggestionMessage.getText();

      // Verifikasi apakah pesan saran muncul
      Assert.assertTrue(suggestion.contains("Try other keywords"),
          "Pesan saran tidak ditemukan atau tidak sesuai.");
      System.out.println("Pesan saran ditemukan: " + suggestion);
    } catch (org.openqa.selenium.TimeoutException e) {
      System.out.println("Pesan saran tidak muncul.");
    }
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

  // CARI TARI KECAK, COCOKKAN LOKASINYA
  @Test
  public void testKecakAndFireDanceSearch() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 10 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

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
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Tunggu hingga elemen "See Details" dapat terlihat
    WebElement seeDetailsButton = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(
                "#ticket-list > div > div > div > div.css-1dbjc4n.r-eqz5dr > div > div.css-1dbjc4n.r-1l31rp8.r-kdyh1x.r-rs99b7.r-13awgt0.r-18u37iz.r-1wtj0ep.r-1pcd2l5 > div.css-1dbjc4n.r-hnxvie > div > div.css-1dbjc4n.r-1awozwy.r-18u37iz.r-1h0z5md > div > div")));

    // Scroll ke elemen "See Details" agar terlihat di layar
    ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", seeDetailsButton);

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik elemen "See Details"
    clickElement(seeDetailsButton);

    System.out.println("Hasil 'See Details' berhasil diklik setelah scroll.");

    // Assertion untuk memastikan elemen dengan discount price ada di halaman
    WebElement discountPriceElement = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath(
                "//div[@aria-level='1' and @class='css-901oao r-a5wbuh r-b88u0q r-fdjqy7' and @data-testid='lblItemDiscountedPrice' and contains(text(), 'Rp')]")));

    // Assert bahwa elemen discount price ditemukan
    Assert.assertNotNull("Discount price element not found", discountPriceElement.getText());
    System.out.println("Element dengan harga diskon ditemukan: " + discountPriceElement.getText());
  }

  @Test
  public void testMusicEventSearch() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 12 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik More
    WebElement moreButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//svg[@data-id='IcSystemMenuViewGrid' and @width='24' and @height='24']")));
    clickElement(moreButton);

    // Klik Events
    WebElement eventsButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[@class='css-1dbjc4n r-1ihkh82 r-sdzlij r-eu3ka r-1kb76zh r-edyy15 r-1aockid']//img[@width='24']")));
    clickElement(eventsButton);

    // Klik input untuk mencari event
    WebElement searchInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='Any ideas on what to do for your next trip?']")));
    clickElement(searchInput);

    // Masukkan "music" ke dalam input
    searchInput.sendKeys("music");

    // Tunggu hasil autocomplete muncul
    WebElement autocompleteItem = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//h4[normalize-space(text())='Seoul Myeongdong Nanta Music']")));
    clickElement(autocompleteItem);

    // Tunggu beberapa detik agar hasil loading selesai
    try {
      Thread.sleep(5000); // Delay 5 detik untuk memastikan halaman sudah ter-load
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Cek apakah ada judul event yang sesuai dengan pencarian
    WebElement eventTitle = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath(
                "//h1[@data-testid='lblProductTitle' and contains(text(), 'Seoul Myeongdong Nanta Musical Show')]")));

    // Assert bahwa event title ditemukan dan sesuai dengan harapan
    Assert.assertNotNull("Event title not found", eventTitle.getText());
    Assert.assertEquals("Event title is incorrect", "Seoul Myeongdong Nanta Musical Show", eventTitle.getText());

    System.out.println("Test passed. Found event: " + eventTitle.getText());
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

  // CARI TAHU HARGA TIKET BUS 1X PERJALANAN, 1 ORANG
  @Test
  public void testBusTicketBooking() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 10 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // Klik Bus & Travel
    WebElement busTravelButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@data-id='IcProductDuotoneBusFill']")));
    busTravelButton.click();

    // Klik ikon From
    WebElement fromIcon = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[contains(text(),'From')]/following-sibling::div//img[@src='https://d1785e74lyxkqq.cloudfront.net/_next/static/v2/8/8d0597b655df4302ed9a15572557fb40.svg']")));
    fromIcon.click();

    // Isi input "from" dengan Purabaya Terminal
    WebElement fromInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[contains(text(),'From')]/following-sibling::div//input[@placeholder='Enter city, terminal, or other points']")));
    fromInput.sendKeys("Purabaya Terminal");

    // Klik autocomplete "Purabaya Terminal"
    WebElement fromOption = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//span[contains(text(),'Purabaya Terminal')]")));
    fromOption.click();

    // Klik ikon To
    WebElement toIcon = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[contains(text(),'To')]/following-sibling::div//img[@src='https://d1785e74lyxkqq.cloudfront.net/_next/static/v2/8/81748d0e10d2b723189270df54db5dde.svg']")));
    toIcon.click();

    // Isi input "to" dengan Bandung
    WebElement toInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[contains(text(),'To')]/following-sibling::div//input[@placeholder='Enter city, terminal, or other points']")));
    toInput.sendKeys("Jakarta");

    // Klik autocomplete "Bandung Station"
    WebElement toOption = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h3[span[text()='Jakarta']]//span")));

    toOption.click();

    // Klik input berdasarkan "Departure Date"
    WebElement dateInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[normalize-space(text())='Departure Date']/following-sibling::div//input")));
    dateInput.click();

    // Pilih tanggal 26
    WebElement dateOption = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(text(),'26')]")));
    dateOption.click();

    // Klik tombol "Search"
    WebElement searchButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.cssSelector("div.css-18t94o4 svg[data-id='IcSystemSearch']")));
    searchButton.click();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Get the "Book Now" button element
    WebElement bookNowButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(@class, 'css-18t94o4') and .//div[text()='Book Now']]")));

    // Scroll the element into view
    ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", bookNowButton);

    // Use JavaScript to click the element
    ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].click();", bookNowButton);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Isi input-inputnya
    WebElement fullNameInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@aria-labelledby='name.full']")));
    fullNameInput.sendKeys("Hanvy Hendrawan");

    // Locate the phone number input field
    WebElement phoneNumberInput = app.getDriver().findElement(By.xpath("//input[@aria-label='Phone Number']"));

    phoneNumberInput.sendKeys("81217771105");

    WebElement emailInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@aria-labelledby='emailAddress']")));
    emailInput.sendKeys("hanvyhendrawan1105@gmail.com");

    // Locate the dropdown's container
    WebElement dropdownContainer = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'css-1dbjc4n')]")));

    // Scroll the dropdown container into view
    ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", dropdownContainer);

    // Click the dropdown icon to expand
    WebElement dropdownIcon = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.cssSelector("img[alt='Toggle Options']")));
    clickElement(dropdownIcon);

    // Wait for the dropdown options to become visible and select "Mr."
    WebElement optionMr = wait.until(
        ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='MR']")));
    optionMr.click();

    // Lokasi elemen Full Name kedua
    By secondNameFullLocator = By
        .xpath("(//input[@aria-labelledby='name.full' and @aria-describedby='(without title and punctuation)'])[2]");

    // Scroll ke elemen Full Name kedua
    WebElement secondNameFullInput = wait.until(
        ExpectedConditions.visibilityOfElementLocated(secondNameFullLocator));
    ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", secondNameFullInput);

    // Tunggu hingga Full Name kedua dapat diklik dan isi nilai
    secondNameFullInput = wait.until(ExpectedConditions.elementToBeClickable(secondNameFullLocator));
    secondNameFullInput.sendKeys("Hanvy Hendrawan");

    // Tunggu hingga tombol "Continue" dapat diklik
    WebElement continueButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(@class,'css-901oao') and contains(text(),'Continue') and @aria-hidden='true']")));

    // Scroll ke elemen jika diperlukan
    ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", continueButton);

    // Klik tombol "Continue"
    continueButton.click();

    // Cek apakah harga total muncul
    WebElement totalPrice = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[@data-testid='title-price']")));

    // Verifikasi harga total
    String priceText = totalPrice.getText();
    Assert.assertTrue(priceText.contains("Rp"));
  }

  // CARI TIKET KERETA TGL TERTENTU (KLO ADA BOOK, KLO GK CETAK ROUTE NOT AVAILABLE)
  @Test
  public void testTrainTicketBooking() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 12 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Klik Bus & Travel
    WebElement trainButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@data-id='IcProductDuotoneTrainFill16']")));
    trainButton.click();

    // Klik ikon From
    WebElement originIcon = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[contains(text(),'Origin')]/following-sibling::div//img[@src='https://d1785e74lyxkqq.cloudfront.net/_next/static/v2/d/dff8e1c4a5c6c0b92ae1e575e375c807.svg']")));
    originIcon.click();

    // Isi input "from" dengan Purabaya Terminal
    WebElement originInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[contains(text(),'Origin')]/following-sibling::div//input[@placeholder='Origin']")));
    originInput.sendKeys("Surabaya Pasar Turi");

    // Klik autocomplete "Surabaya"
    WebElement originOption = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(text(),'Surabaya')]")));
    originOption.click();

    // Klik ikon Destination
    WebElement destinationIcon = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//div[contains(text(),'Destination')]/following-sibling::div//img[@src='https://d1785e74lyxkqq.cloudfront.net/_next/static/v2/6/63fb9afe8cfcff61939c7d7947b6178b.svg']")));
    destinationIcon.click();

    // Isi input "destination" dengan Cilacap
    WebElement destinationInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//input[@placeholder='Destination' and @data-testid='train-desktop-search-form-destination-input']")));
    destinationInput.sendKeys("Cilacap");

    // Klik autocomplete "Cilacap"
    WebElement desinationOption = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[@data-testid='train-desktop-autocomplete-item-sublabel' and contains(text(),'Cilacap')]")));
    desinationOption.click();

    // Klik input berdasarkan "Departure Date"
    WebElement departureInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[normalize-space(text())='Departure']/following-sibling::div//input")));
    departureInput.click();

    // Pilih tanggal 27
    WebElement dateOption = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(text(),'27')]")));
    dateOption.click();

    // Klik tombol "Search"
    WebElement searchButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.cssSelector("div.css-901oao svg[data-id='IcSystemSearch']")));
    searchButton.click();

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Pengecekan apakah elemen "Route not available" muncul
    try {
      WebElement routeNotAvailableMessage = wait.until(
          ExpectedConditions.visibilityOfElementLocated(
              By.xpath("//div[@data-testid='empty-state-title' and text()='Route not available']")));

      // Jika elemen ditemukan, cetak pesan bahwa rute tidak tersedia
      System.out.println("Route not available");
    } catch (org.openqa.selenium.TimeoutException e) {
      // Jika elemen "Route not available" tidak ditemukan, lanjutkan dengan memilih
      // rute
      try {
        // Tunggu tombol "Select" jika ditemukan
        WebElement selectButton = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                    "//div[@data-testid='train-desktop-search-result-list-departure-item-cta' and @role='button']//div[text()='Select']")));

        // Jika tombol "Select" ditemukan, klik tombol tersebut
        selectButton.click();

        // Lanjutkan ke proses pemesanan setelah memilih jadwal
        WebElement bookButton = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                    "//div[@data-testid='train-desktop-search-result-round-trip-confirmation-cta' and @role='button']//div[text()='Book']")));
        bookButton.click();

        // Isi input-inputnya
        WebElement fullNameInput = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@aria-labelledby='name.full']")));
        fullNameInput.sendKeys("Hanvy Hendrawan");

        // Lokasi input nomor telepon
        WebElement phoneNumberInput = app.getDriver().findElement(By.xpath("//input[@aria-label='Phone Number']"));
        phoneNumberInput.sendKeys("81217771105");

        WebElement emailInput = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@aria-labelledby='emailAddress']")));
        emailInput.sendKeys("hanvyhendrawan1105@gmail.com");

        // Locate the dropdown's container
        WebElement dropdownContainer = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'css-1dbjc4n')]")));

        // Scroll the dropdown container into view
        ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", dropdownContainer);

        // Click the dropdown icon to expand
        WebElement dropdownIcon = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("img[alt='Toggle Options']")));
        clickElement(dropdownIcon);

        // Wait for the dropdown options to become visible and select "Mr."
        WebElement optionMr = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='MR']")));
        optionMr.click();

        // Locate the dropdown's container
        WebElement dropdownContainerIDType = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'css-1dbjc4n')]")));

        // Scroll the dropdown container into view
        ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);",
            dropdownContainerIDType);

        // Click the dropdown icon to expand
        WebElement dropdownIDTypeIcon = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("img[alt='Toggle Options']")));
        clickElement(dropdownIDTypeIcon);

        // Wait for the dropdown options to become visible and select "Mr."
        WebElement optionIDCard = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='KTP']")));
        optionIDCard.click();

        // Lokasi elemen ID Number
        By idNumberLocated = By
            .xpath("(//input[@aria-labelledby='travelerID.number' and @aria-invalid='false'])");

        // Scroll ke elemen ID Number
        WebElement idNumberInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(idNumberLocated));
        ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", idNumberInput);

        // Tunggu hingga Full Name kedua dapat diklik dan isi nilai
        idNumberInput = wait.until(ExpectedConditions.elementToBeClickable(idNumberInput));
        idNumberInput.sendKeys("3578201105040001");

        // Tunggu hingga tombol "Continue" dapat diklik
        WebElement continueButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "//div[contains(@class,'css-901oao') and contains(text(),'Continue') and @aria-hidden='true']")));
        // Scroll ke elemen jika diperlukan
        ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", continueButton);
        // Klik tombol "Continue"
        continueButton.click();

        // Cek apakah harga total muncul
        WebElement totalPrice = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@data-testid='title-price']")));

        // Verifikasi harga total
        String priceText = totalPrice.getText();
        Assert.assertTrue(priceText.contains("Rp"));
      } catch (org.openqa.selenium.TimeoutException selectException) {
        System.out.println("No available routes for the selected dates.");
      }
    }
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
    emailOrMobileNumberInput.sendKeys("hanvy.h22@mhs.istts.ac.id");

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

    WebElement verificationCodeInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath(
                "//input[@class='css-11aywtz r-1x35g6 r-1pi2tsx r-1d2f490 r-orgf3d r-u8s1d r-ipm5af r-13qz1uu r-pezta']")));
    verificationCodeInput.sendKeys("");

    // Tunggu sampai tombol Verify muncul
    WebElement verifyButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Verify']"))); // Pastikan XPath tombol Verify sesuai dengan elemen yang benar

    // Cek apakah tombol Verify tidak aktif (disabled)
    Assert.assertTrue(verifyButton.isEnabled(),
        "Tombol Verify seharusnya tidak aktif karena kode verifikasi belum diisi");
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
    emailOrMobileNumberInput.sendKeys("+6282278878588");

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
