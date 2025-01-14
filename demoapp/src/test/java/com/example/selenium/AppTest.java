package com.example.selenium;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AppTest {

  private App app;

  @BeforeClass
  public void setUp() {
    app = new App();
    app.openURL("https://magento.softwaretestingboard.com/");
  }

  @Test
  public void testRegistrationAndSignOut() throws IOException {
    // Membaca data registrasi dari file
    String[] registrationData = readRegistrationDataFromFile(
      "C:\\Users\\Hanvy\\Downloads\\cobamaven\\demoapp\\src\\test\\java\\com\\example\\selenium\\data.txt"
    );

    // Klik tombol "Create an Account"
    WebElement createAccountButton = app
      .getDriver()
      .findElement(By.cssSelector("a[href*='customer/account/create']"));
    createAccountButton.click();

    // Isi formulir registrasi dengan data yang dibaca
    fillRegistrationForm(
      registrationData[0],
      registrationData[1],
      registrationData[2],
      registrationData[3],
      registrationData[4]
    );

    // Submit formulir
    WebDriverWait wait = new WebDriverWait(
      app.getDriver(),
      Duration.ofSeconds(5)
    );
    WebElement submitButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.cssSelector("button.action.submit.primary")
      )
    );
    submitButton.click();

    // Verifikasi registrasi berhasil
    verifyRegistrationSuccess(registrationData[0]);

    // Logout
    signOut();
  }

  private String[] readRegistrationDataFromFile(String filePath)
    throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filePath));
    String[] data = new String[5];
    for (int i = 0; i < 5; i++) {
      data[i] = reader.readLine(); // Membaca tiap baris dari file
    }
    reader.close();
    return data;
  }

  private void fillRegistrationForm(
    String firstName,
    String lastName,
    String email,
    String password,
    String confirmPassword
  ) {
    // Isi First Name menggunakan class dan title
    WebElement firstNameField = app
      .getDriver()
      .findElement(By.cssSelector("input.input-text[title='First Name']"));
    firstNameField.sendKeys(firstName);

    // Isi Last Name menggunakan class dan title
    WebElement lastNameField = app
      .getDriver()
      .findElement(By.cssSelector("input.input-text[title='Last Name']"));
    lastNameField.sendKeys(lastName);

    // Isi Email menggunakan class dan title
    WebElement emailField = app
      .getDriver()
      .findElement(By.cssSelector("input.input-text[title='Email']"));
    emailField.sendKeys(email);

    // Isi Password menggunakan class dan title
    WebElement passwordField = app
      .getDriver()
      .findElement(By.cssSelector("input.input-text[title='Password']"));
    passwordField.sendKeys(password);

    // Isi Confirm Password menggunakan class dan title
    WebElement confirmPasswordField = app
      .getDriver()
      .findElement(
        By.cssSelector("input.input-text[title='Confirm Password']")
      );
    confirmPasswordField.sendKeys(confirmPassword);
  }

  private void verifyRegistrationSuccess(String firstName) {
    // Tunggu elemen "Welcome, [First Name]" muncul
    WebDriverWait wait = new WebDriverWait(
      app.getDriver(),
      Duration.ofSeconds(5)
    );
    WebElement welcomeMessage = wait.until(
      ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector(".logged-in")
      )
    );

    // Verifikasi bahwa nama pengguna muncul dalam pesan selamat datang
    Assert.assertTrue(
      welcomeMessage.getText().contains("Welcome, " + firstName),
      "Welcome message does not contain the expected name."
    );
  }

  private void signOut() {
    // Tunggu tombol untuk membuka menu pengguna
    WebDriverWait waitUserMenu = new WebDriverWait(
      app.getDriver(),
      Duration.ofSeconds(10)
    );
    WebElement userMenuButton = waitUserMenu.until(
      ExpectedConditions.elementToBeClickable(
        By.cssSelector("button[data-action='customer-menu-toggle']")
      )
    );
    userMenuButton.click(); // Klik untuk membuka menu

    // Tunggu tombol Sign Out menjadi bisa diklik
    WebDriverWait wait = new WebDriverWait(
      app.getDriver(),
      Duration.ofSeconds(5)
    );
    WebElement signOutButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.cssSelector("a[href*='customer/account/logout']")
      )
    );
    signOutButton.click();

    // Verifikasi berhasil logout (contoh: cek keberadaan tombol "Sign In")
    WebElement signInButton = app
      .getDriver()
      .findElement(By.cssSelector("a[href*='customer/account/login']"));
    Assert.assertTrue(
      signInButton.isDisplayed(),
      "Sign In button not found. Logout might have failed."
    );
  }

  @Test
  public void testRegistrationWithExistingEmail() throws IOException {
    // Membaca data registrasi dari file
    String[] registrationData = readRegistrationDataFromFile(
      "C:\\Users\\Hanvy\\Downloads\\cobamaven\\demoapp\\src\\test\\java\\com\\example\\selenium\\data.txt"
    );

    // Klik tombol "Create an Account"
    WebElement createAccountButton = app
      .getDriver()
      .findElement(By.cssSelector("a[href*='customer/account/create']"));
    createAccountButton.click();

    // Isi formulir registrasi dengan data yang dibaca, gunakan email yang sudah
    // terdaftar
    fillRegistrationForm(
      registrationData[0],
      registrationData[1],
      "existingemail@example.com",
      registrationData[3],
      registrationData[4]
    );

    // Submit formulir
    WebDriverWait wait = new WebDriverWait(
      app.getDriver(),
      Duration.ofSeconds(5)
    );
    WebElement submitButton = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.cssSelector("button.action.submit.primary")
      )
    );
    submitButton.click();

    // Tunggu dan verifikasi pesan error muncul
    WebElement errorMessage = wait.until(
      ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("div[data-bind*='prepareMessageForHtml']")
      )
    );
    Assert.assertTrue(
      errorMessage.isDisplayed(),
      "Error message not displayed."
    );
    Assert.assertTrue(
      errorMessage
        .getText()
        .contains("There is already an account with this email address."),
      "Error message does not contain the expected text."
    );
  }

  @Test
  public void testSortAndAddToCart() {
    WebDriverWait wait = new WebDriverWait(
      app.getDriver(),
      Duration.ofSeconds(15)
    ); // Perpanjang waktu tunggu

    // Hover pada menu Gear untuk memunculkan dropdown dan klik saat menu Watches
    // muncul
    WebElement gearMenu = wait.until(driver ->
      driver.findElement(By.xpath("//a[span[text()='Gear']]"))
    );

    // Melakukan hover pada elemen Gear menggunakan Actions
    Actions actions = new Actions(app.getDriver());
    actions.moveToElement(gearMenu).perform();

    // Tunggu sampai menu dropdown 'Watches' muncul dan klik menu Watches
    WebElement watchesMenu = wait.until(driver ->
      driver.findElement(By.xpath("//a[contains(@href, '/gear/watches.html')]"))
    );
    watchesMenu.click();

    // Pilih Sort By Product Name Ascending
    WebElement sortBySelect = wait.until(driver ->
      driver.findElement(By.id("sorter"))
    );
    WebElement sortOption = sortBySelect.findElement(
      By.xpath("//option[@value='name']")
    );
    sortOption.click();

    // Pilih salah satu jam tangan (Clamber Watch)
    WebElement clamberWatch = wait.until(driver ->
      driver.findElement(By.xpath("//img[@alt='Clamber Watch']"))
    );
    clamberWatch.click();

    // Klik tombol Add to Cart
    WebElement addToCartButton = wait.until(driver ->
      driver.findElement(By.xpath("//button[@title='Add to Cart']"))
    );
    addToCartButton.click();

    // Tunggu sampai elemen keranjang muncul dan mendapatkan jumlah yang benar
    WebElement cartCounter = wait.until(driver -> {
      WebElement counter = driver.findElement(
        By.xpath("//span[@class='counter-number']")
      );
      if (counter.getText().equals("1")) {
        return counter;
      } else {
        return null; // Menunggu counter berubah
      }
    });

    // Verifikasi angka di sebelah gambar keranjang menjadi 1
    String cartCountText = cartCounter.getText();
    Assert.assertEquals(
      cartCountText,
      "1",
      "Cart count is not updated correctly."
    );
  }

  @Test
  public void testSortPriceAndAddToCart() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(
      app.getDriver(),
      Duration.ofSeconds(15)
    ); // Perpanjang waktu tunggu

    // Hover pada menu Men untuk memunculkan dropdown dan klik menu Tops
    WebElement menMenu = wait.until(driver ->
      driver.findElement(By.xpath("//a[span[text()='Men']]"))
    );
    Actions actions = new Actions(app.getDriver());
    actions.moveToElement(menMenu).perform();

    // Hover pada menu 'Tops' untuk memunculkan submenu
    WebElement topsMenu = wait.until(driver ->
      driver.findElement(By.xpath("//a[@id='ui-id-17' and @role='menuitem']"))
    );
    actions.moveToElement(topsMenu).perform();

    // Tunggu hingga elemen 'Hoodies & Sweatshirts' dapat diklik
    WebElement hoodiesSweatshirtsMenu = wait.until(driver ->
      driver.findElement(By.xpath("//a[@id='ui-id-20' and @role='menuitem']"))
    );

    // Klik menu 'Hoodies & Sweatshirts'
    wait.until(ExpectedConditions.elementToBeClickable(hoodiesSweatshirtsMenu));
    hoodiesSweatshirtsMenu.click();

    // Tunggu elemen yang berisi teks "Hoodies & Sweatshirts" muncul
    WebElement pageTitleElement = wait.until(driver ->
      driver.findElement(
        By.xpath("//span[@class='base' and text()='Hoodies & Sweatshirts']")
      )
    );

    // Verifikasi bahwa teks pada elemen tersebut adalah "Hoodies & Sweatshirts"
    String pageTitle = pageTitleElement.getText();
    Assert.assertTrue(
      pageTitle.equals("Hoodies & Sweatshirts"),
      "Page title is not correct."
    );

    // Pilih Sort By Price Ascending (karena opsi yang ada adalah 'price', bukan
    // 'price_desc')
    WebElement sortBySelect = wait.until(driver ->
      driver.findElement(By.id("sorter"))
    );
    WebElement priceOption = sortBySelect.findElement(
      By.xpath("//option[@value='price']")
    );
    priceOption.click();

    // Klik tombol "Set Descending Direction" untuk mengatur pengurutan harga ke
    // descending
    WebElement descendingButton = wait.until(driver ->
      driver.findElement(By.xpath("//a[@title='Set Descending Direction']"))
    );
    wait.until(ExpectedConditions.elementToBeClickable(descendingButton));
    descendingButton.click();

    // Temukan tombol halaman 2
    WebElement secondPageButton = wait.until(driver ->
      driver.findElement(By.xpath("//a[@class='page']//span[text()='2']"))
    );

    // Scroll ke elemen dan klik langsung menggunakan JavaScript
    ((JavascriptExecutor) app.getDriver()).executeScript(
        "arguments[0].scrollIntoView(true); arguments[0].click();",
        secondPageButton
      );

    // Pilih 'Stark Fundamental Hoodie' pada halaman kedua
    WebElement starkFundamentalHoodie = wait.until(driver ->
      driver.findElement(By.xpath("//img[@alt='Stark Fundamental Hoodie']"))
    );
    starkFundamentalHoodie.click();

    // Pilih ukuran L
    WebElement sizeSelect = wait.until(driver ->
      driver.findElement(By.id("option-label-size-143-item-169"))
    );
    sizeSelect.click();

    // Pilih warna purple
    WebElement colorSelect = wait.until(driver ->
      driver.findElement(By.id("option-label-color-93-item-57"))
    );
    colorSelect.click();

    // Set jumlah menjadi 2
    WebElement qtyInput = wait.until(driver -> driver.findElement(By.id("qty"))
    );
    qtyInput.clear();
    qtyInput.sendKeys("2");

    // Klik tombol Add to Cart
    WebElement addToCartButton = wait.until(driver ->
      driver.findElement(By.xpath("//button[@title='Add to Cart']"))
    );
    addToCartButton.click();

    // Tunggu sampai elemen keranjang muncul dan mendapatkan jumlah yang benar
    WebElement cartCounter = wait.until(driver -> {
      WebElement counter = driver.findElement(
        By.xpath("//span[@class='counter-number']")
      );
      if (counter.getText().equals("2")) {
        return counter;
      } else {
        return null; // Menunggu counter berubah
      }
    });

    // Verifikasi angka di sebelah gambar keranjang menjadi 1
    String cartCountText = cartCounter.getText();
    Assert.assertEquals(
      cartCountText,
      "2",
      "Cart count is not updated correctly."
    );
  }

  @Test
  public void testSearchAndAddToCart() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(
      app.getDriver(),
      Duration.ofSeconds(15)
    ); // Waktu tunggu

    // 26. Masukkan pencarian di inputan Search
    WebElement searchInput = wait.until(driver ->
      driver.findElement(By.id("search"))
    ); // ID untuk input search
    searchInput.sendKeys(
      "baju olahraga pria dan wanita dan anak-anak berwarna biru"
    );

    // Klik tombol search (ikon kaca pembesar)
    WebElement searchButton = wait.until(driver ->
      driver.findElement(By.xpath("//button[@title='Search']"))
    );
    searchButton.click();

    // Tunggu sampai produk "Neve Studio Dance Jacket" muncul
    WebElement product = wait.until(driver ->
      driver.findElement(By.xpath("//img[@alt='Neve Studio Dance Jacket']"))
    );
    product.click();

    // Tunggu halaman detail produk dimuat sepenuhnya
    wait.until(driver -> driver.findElement(By.id("product-addtocart-button")));

    // 27. Pilih ukuran S
    WebElement sizeSelect = wait.until(driver ->
      driver.findElement(By.id("option-label-size-143-item-167"))
    );
    sizeSelect.click();

    // Pilih warna orange
    WebElement colorSelect = wait.until(driver ->
      driver.findElement(By.id("option-label-color-93-item-56"))
    );
    colorSelect.click();

    // Klik tombol Add to Cart
    WebElement addToCartButton = wait.until(driver ->
      driver.findElement(By.id("product-addtocart-button"))
    );
    addToCartButton.click();

    // Tunggu sampai elemen keranjang muncul dan mendapatkan jumlah yang benar
    WebElement cartCounter = wait.until(driver -> {
      WebElement counter = driver.findElement(
        By.xpath("//span[@class='counter-number']")
      );
      if (counter.getText().equals("1")) {
        return counter;
      } else {
        return null; // Menunggu counter berubah
      }
    });

    // Verifikasi angka di sebelah gambar keranjang menjadi 1
    String cartCountText = cartCounter.getText();
    Assert.assertEquals(
      cartCountText,
      "1",
      "Cart count is not updated correctly."
    );
  }

  @Test
  public void testAdvancedSearch() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(
      app.getDriver(),
      Duration.ofSeconds(15)
    ); // Waktu tunggu

    // Temukan link Advanced Search
    JavascriptExecutor js = (JavascriptExecutor) app.getDriver();
    WebElement advancedSearchLink = wait.until(driver ->
      driver.findElement(By.cssSelector("a[data-action='advanced-search']"))
    );

    // Scroll ke elemen dan klik langsung menggunakan JavaScript
    js.executeScript(
      "arguments[0].scrollIntoView(true); arguments[0].click();",
      advancedSearchLink
    );

    // Masukkan "capri" di kolom Product Name
    WebElement productNameInput = wait.until(driver ->
      driver.findElement(By.id("name"))
    );
    productNameInput.sendKeys("capri");

    // Masukkan "Relaxed Fit" di kolom Description
    WebElement descriptionInput = wait.until(driver ->
      driver.findElement(By.id("description"))
    );
    descriptionInput.sendKeys("Relaxed Fit");

    // Masukkan rentang harga (40-100 USD)
    WebElement priceFromInput = wait.until(driver ->
      driver.findElement(By.id("price"))
    );
    priceFromInput.sendKeys("40");

    WebElement priceToInput = wait.until(driver ->
      driver.findElement(By.id("price_to"))
    );
    priceToInput.sendKeys("100");

    // Klik tombol Search
    WebElement searchButton = wait.until(driver ->
      driver.findElement(By.cssSelector("button.action.search.primary"))
    );
    searchButton.click();

    // 46. Konfirmasi hasil pencarian (pastikan 1 hasil ditemukan)
    List<WebElement> searchResults = wait.until(driver ->
      driver.findElements(By.cssSelector(".product-item"))
    );
    Assert.assertEquals(
      searchResults.size(),
      1,
      "Expected exactly 1 search result but found: " + searchResults.size()
    );

    // Verifikasi hasil sesuai dengan kriteria pencarian
    WebElement resultName = searchResults
      .get(0)
      .findElement(By.cssSelector(".product-item-link"));
    Assert.assertTrue(
      resultName.getText().toLowerCase().contains("capri"),
      "Product name does not match search criteria."
    );

    WebElement resultPrice = searchResults
      .get(0)
      .findElement(By.cssSelector(".price"));
    String priceText = resultPrice.getText().replaceAll("[^0-9.]", "");
    double price = Double.parseDouble(priceText);
    Assert.assertTrue(
      price >= 40 && price <= 100,
      "Product price is not within the expected range."
    );
  }

  @AfterClass
  public void tearDown() {
    app.closeBrowser();
  }
}
