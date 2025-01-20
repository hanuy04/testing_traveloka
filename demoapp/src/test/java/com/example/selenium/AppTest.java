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

  // CARI TARI KECAK, COCOKKAN LOKASINYA (Fun Activities)
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

  // Travel Insurance
  @Test
  public void testTravelInsuranceSearch() {
    // Tambahkan delay untuk mengisi CAPTCHA
    try {
      Thread.sleep(12000); // Delay 10 detik untuk mengisi CAPTCHA
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Menunggu elemen gambar Travel Insurance dapat diklik
    WebElement travelInsuranceButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//img[@alt='Travel Insurance']")));
    clickElement(travelInsuranceButton);

    // Tunggu dropdown autocomplete muncul
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Menunggu elemen SVG dengan teks Domestic dapat diklik
    WebElement domesticButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='css-1dbjc4n r-1awozwy r-18u37iz r-1e081e0 r-5njf8e']//div[text()='Domestic']")));
    clickElement(domesticButton);

    // Tunggu sejenak untuk memastikan bahwa klik pertama selesai
    try {
      Thread.sleep(1000); // Tunggu selama 500ms, Anda bisa menyesuaikan waktunya
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Menunggu elemen dengan tulisan "Coverage End Date"
    WebElement coverageEndDateLabel = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[@data-testid='ins-input-title' and text()='Coverage End Date']")));

    // Setelah elemen ditemukan, ambil input untuk End Date
    WebElement endDateInput = wait.until(
        ExpectedConditions.elementToBeClickable(By.xpath(
            "//div[@data-testid='ins-input-title' and text()='Coverage End Date']//following-sibling::div//input[@placeholder='Select Date']")));

    // Klik input untuk memilih tanggal
    endDateInput.click();

    // Menunggu elemen tanggal 28 agar bisa diklik
    WebElement date28 = wait.until(
        ExpectedConditions.elementToBeClickable(By.xpath(
            "//div[@class='css-901oao r-cwxd7f r-a5wbuh r-ubezar r-majxgm r-135wba7 r-fdjqy7' and text()='28']")));
    // Pilih tanggal 28
    date28.click();

    // Menunggu elemen "Search Insurance" agar bisa diklik
    WebElement searchInsuranceButton = wait.until(
        ExpectedConditions.elementToBeClickable(By.xpath(
            "//div[@class='css-901oao css-bfa6kz r-jwli3a r-a5wbuh r-cygvgh r-b88u0q r-1iukymi r-q4m81j' and text()='Search Insurance']")));
    // Klik tombol "Search Insurance"
    searchInsuranceButton.click();

    // Tunggu sejenak untuk memastikan bahwa klik pertama selesai
    try {
      Thread.sleep(3000); // Tunggu selama 500ms, Anda bisa menyesuaikan waktunya
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Tunggu hingga hasil penelusuran tersedia
    WebElement searchResult = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='css-1dbjc4n r-13awgt0 r-5t7p9m']//h2[contains(text(),'Travel')]")));

    // Validasi hasil penelusuran
    Assert.assertNotNull("Hasil penelusuran tidak ditemukan", searchResult.getText());

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  // MORE
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
            By.xpath("//div[@data-testid='product-pill-More' and contains(@class, 'css-1dbjc4n')]")));
    moreButton.click();

    // Locate the element containing the text 'Events'
    WebElement eventsButton = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h3[contains(text(), 'Events')]")));

    // Scroll to the element to bring it into view
    ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", eventsButton);

    // Ensure the element is clickable
    WebElement clickableEventsButton = wait.until(
        ExpectedConditions.elementToBeClickable(eventsButton));

    // Click the element
    clickableEventsButton.click();

    // Klik input untuk mencari event
    WebElement searchInput = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='Any ideas on what to do for your next trip?']")));
    clickElement(searchInput);

    // Masukkan "music" ke dalam input
    searchInput.sendKeys("music");

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    WebElement autocompleteItem = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h4[contains(normalize-space(text()), 'Music')]")));

    // Klik elemen
    autocompleteItem.click();

    // Tunggu beberapa detik agar hasil loading selesai
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Cek apakah ada elemen gambar dengan atribut src yang mengandung
    // "House-of-Music"
    WebElement imageElement = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//img[contains(@src, 'House-of-Music')]")));

    // Pastikan elemen ditemukan
    Assert.assertNotNull("Image element not found", imageElement.getText());

    // Cetak sumber gambar yang ditemukan
    String imageSrc = imageElement.getAttribute("src");
    System.out.println("Test passed. Found image: " + imageSrc);
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
