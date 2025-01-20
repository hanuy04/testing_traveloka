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

public class HotelSearch {

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

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

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

    // Klik tombol Adults sebanyak 3 kali
    for (int i = 0; i < 3; i++) {
      clickElement(minusAdultsButton);
      System.out.println("Clicked Adults button: " + (i + 1) + " times");
    }

    // Langkah terakhir: Cek apakah div memiliki nilai 1 atau lebih
    WebElement totalAdultsDiv = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath(
                "//div[@class='css-1dbjc4n r-1awozwy r-5j04e9 r-13awgt0 r-1777fci r-vkv6oe r-s1qlax r-5njf8e']//div[@class='css-901oao r-cwxd7f r-a5wbuh r-majxgm r-fdjqy7']")));

    String totalAdultsText = totalAdultsDiv.getText();

    // Hapus semua karakter non-angka dari teks
    String numericText = totalAdultsText.replaceAll("[^0-9]", "");

    // Cek apakah numericText kosong atau tidak
    if (!numericText.isEmpty()) {
      // Ubah teks menjadi angka
      int totalAdults = Integer.parseInt(numericText);

      // Assert bahwa nilai dalam div minimal 1
      Assert.assertTrue(
          totalAdults >= 1,
          "Jumlah Adults harus minimal 1, ditemukan: " + totalAdults);
          System.out.println("Test passed! Jumlah adults minimal " + totalAdults);
    } else {
      Assert.fail("Tidak ditemukan angka valid di dalam elemen: " + totalAdultsText);
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

    clickElement(minusRoomsButton);

    // Langkah terakhir: Cek apakah div memiliki nilai 1 atau lebih
    WebElement totalRoomsDiv = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath(
                "//div[@class='css-1dbjc4n r-1awozwy r-5j04e9 r-13awgt0 r-1777fci r-vkv6oe r-s1qlax r-5njf8e']//div[@class='css-901oao r-cwxd7f r-a5wbuh r-majxgm r-fdjqy7']")));

    String totalRoomsText = totalRoomsDiv.getText();

    // Hapus semua karakter non-angka dari teks
    String numericText = totalRoomsText.replaceAll("[^0-9]", "");

    // Cek apakah numericText kosong atau tidak
    if (!numericText.isEmpty()) {
      // Ubah teks menjadi angka
      int totalRooms = Integer.parseInt(numericText);

      // Assert bahwa nilai dalam div minimal 1
      Assert.assertTrue(
          totalRooms >= 1,
          "Jumlah Rooms harus minimal 1, ditemukan: " + totalRooms);
      System.out.println("Test passed! Jumlah adults minimal " + totalRooms);
    } else {
      Assert.fail("Tidak ditemukan angka valid di dalam elemen: " + totalRoomsText);
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
