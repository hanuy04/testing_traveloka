package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class SearchHotel {
  private App app;

  @BeforeClass
  public void setup() {
    app = new App();
    app.openURL("https://www.traveloka.com/en-id");
  }

  @Test
  public void SearchExpensiveHotel(){
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan
      /////////////////////////// SEARCH HOTEL ///////////////////////////

      // Menunggu elemen Log In untuk klik
      WebElement HotelButton = app.getDriver().findElement(
          By.xpath("//div[@data-testid='product-pill-Hotels']//div[contains(text(), 'Hotels')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        HotelButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", HotelButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan lokasi

      // Menunggu elemen input untuk memasukkan lokasi
      WebElement locationInput = app.getDriver().findElement(
        By.xpath("//input[@data-testid='autocomplete-field' and @placeholder='City, hotel, place to go']"));
      locationInput.sendKeys("Bali");

      Thread.sleep(3000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen autocomplete item untuk klik
      WebElement autocompleteItem = app.getDriver().findElement(
        By.xpath("//div[@data-testid='autocomplete-item-name']//mark[text()='Bali']"));
      autocompleteItem.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen tombol search untuk klik
      WebElement searchButton = app.getDriver().findElement(
        By.xpath("//div[@role='button' and @data-testid='search-submit-button']"));
      // Klik tombol search
      searchButton.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memilih filter

      // Menunggu elemen dropdown filter untuk klik
      WebElement filterDropdown = app.getDriver().findElement(
        By.xpath("//div[@class='css-1dbjc4n r-14lw9ot r-h1746q r-sdzlij r-rs99b7 r-1loqt21 r-ymttw5 r-5njf8e r-1otgn73 r-1i6wzkk r-lrvibr' and @data-testid='selected-dropdown-item']"));
      filterDropdown.click();

      Thread.sleep(5000);  // Delay 3 detik sebelum memilih opsi filter

      // Menunggu elemen opsi filter untuk klik berdasarkan harga tertinggi
      WebElement highestPriceOption = app.getDriver().findElement(
        By.xpath("//div[@id='dropdown-menu-item']//div[@class='css-1dbjc4n r-1pn2ns4']//div[@class='css-901oao r-a5wbuh r-majxgm r-fdjqy7' and text()='Highest Price']"));
      highestPriceOption.click();

      Thread.sleep(15000);  // Delay 5 detik sebelum memasukkan tanggal
      app.getDriver().quit();  // Tutup browser

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void SearchCheapestHotel(){
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan

      /////////////////////////// SEARCH HOTEL ///////////////////////////

      // Menunggu elemen Log In untuk klik
      WebElement HotelButton = app.getDriver().findElement(
          By.xpath("//div[@data-testid='product-pill-Hotels']//div[contains(text(), 'Hotels')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        HotelButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", HotelButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan lokasi

      // Menunggu elemen input untuk memasukkan lokasi
      WebElement locationInput = app.getDriver().findElement(
        By.xpath("//input[@data-testid='autocomplete-field' and @placeholder='City, hotel, place to go']"));
      locationInput.sendKeys("Bali");

      Thread.sleep(3000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen autocomplete item untuk klik
      WebElement autocompleteItem = app.getDriver().findElement(
        By.xpath("//div[@data-testid='autocomplete-item-name']//mark[text()='Bali']"));
      autocompleteItem.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen tombol search untuk klik
      WebElement searchButton = app.getDriver().findElement(
        By.xpath("//div[@role='button' and @data-testid='search-submit-button']"));
      // Klik tombol search
      searchButton.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memilih filter

      // Menunggu elemen dropdown filter untuk klik
      WebElement filterDropdown = app.getDriver().findElement(
        By.xpath("//div[@class='css-1dbjc4n r-14lw9ot r-h1746q r-sdzlij r-rs99b7 r-1loqt21 r-ymttw5 r-5njf8e r-1otgn73 r-1i6wzkk r-lrvibr' and @data-testid='selected-dropdown-item']"));
      filterDropdown.click();

      Thread.sleep(5000);  // Delay 3 detik sebelum memilih opsi filter

      // Menunggu elemen opsi filter untuk klik berdasarkan skor ulasan
      WebElement reviewScoreOption = app.getDriver().findElement(
        By.xpath("//div[@id='dropdown-menu-item']//div[@class='css-901oao r-a5wbuh r-majxgm r-fdjqy7' and text()='Lowest Price']"));
      reviewScoreOption.click();

      Thread.sleep(15000);  // Delay 5 detik sebelum memasukkan tanggal
      app.getDriver().quit();  // Tutup browser

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void searchRatingHotel(){
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan

      /////////////////////////// SEARCH HOTEL ///////////////////////////

      // Menunggu elemen Log In untuk klik
      WebElement HotelButton = app.getDriver().findElement(
          By.xpath("//div[@data-testid='product-pill-Hotels']//div[contains(text(), 'Hotels')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        HotelButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", HotelButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan lokasi

      // Menunggu elemen input untuk memasukkan lokasi
      WebElement locationInput = app.getDriver().findElement(
        By.xpath("//input[@data-testid='autocomplete-field' and @placeholder='City, hotel, place to go']"));
      locationInput.sendKeys("Bali");

      Thread.sleep(3000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen autocomplete item untuk klik
      WebElement autocompleteItem = app.getDriver().findElement(
        By.xpath("//div[@data-testid='autocomplete-item-name']//mark[text()='Bali']"));
      autocompleteItem.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen tombol search untuk klik
      WebElement searchButton = app.getDriver().findElement(
        By.xpath("//div[@role='button' and @data-testid='search-submit-button']"));
      // Klik tombol search
      searchButton.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memilih filter

      // Menunggu elemen dropdown filter untuk klik
      WebElement filterDropdown = app.getDriver().findElement(
        By.xpath("//div[@class='css-1dbjc4n r-14lw9ot r-h1746q r-sdzlij r-rs99b7 r-1loqt21 r-ymttw5 r-5njf8e r-1otgn73 r-1i6wzkk r-lrvibr' and @data-testid='selected-dropdown-item']"));
      filterDropdown.click();

      Thread.sleep(5000);  // Delay 3 detik sebelum memilih opsi filter

      // Menunggu elemen opsi filter untuk klik berdasarkan skor ulasan
      WebElement reviewScoreOption = app.getDriver().findElement(
        By.xpath("//div[@id='dropdown-menu-item']//div[@class='css-901oao r-a5wbuh r-1b43r93 r-majxgm r-fdjqy7' and text()='Review Score']"));
      reviewScoreOption.click();

      Thread.sleep(15000);  // Delay 5 detik sebelum memasukkan tanggal
      app.getDriver().quit();  // Tutup browser

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void search1StarHotel(){
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan
      /////////////////////////// SEARCH HOTEL ///////////////////////////

      // Menunggu elemen Log In untuk klik
      WebElement HotelButton = app.getDriver().findElement(
          By.xpath("//div[@data-testid='product-pill-Hotels']//div[contains(text(), 'Hotels')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        HotelButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", HotelButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan lokasi

      // Menunggu elemen input untuk memasukkan lokasi
      WebElement locationInput = app.getDriver().findElement(
        By.xpath("//input[@data-testid='autocomplete-field' and @placeholder='City, hotel, place to go']"));
      locationInput.sendKeys("Bali");

      Thread.sleep(3000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen autocomplete item untuk klik
      WebElement autocompleteItem = app.getDriver().findElement(
        By.xpath("//div[@data-testid='autocomplete-item-name']//mark[text()='Bali']"));
      autocompleteItem.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen tombol search untuk klik
      WebElement searchButton = app.getDriver().findElement(
        By.xpath("//div[@role='button' and @data-testid='search-submit-button']"));
      // Klik tombol search
      searchButton.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memilih filter

      // Menunggu elemen opsi filter untuk klik berdasarkan 1 bintang
      WebElement oneStarOption = app.getDriver().findElement(
        By.xpath("//div[@class='css-1dbjc4n' and @data-testid='STAR1']"));
      oneStarOption.click();

      Thread.sleep(15000);  // Delay 5 detik sebelum memasukkan tanggal
      app.getDriver().quit();  // Tutup browser

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void search2StarHotel(){
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan
      /////////////////////////// SEARCH HOTEL ///////////////////////////

      // Menunggu elemen Log In untuk klik
      WebElement HotelButton = app.getDriver().findElement(
          By.xpath("//div[@data-testid='product-pill-Hotels']//div[contains(text(), 'Hotels')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        HotelButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", HotelButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan lokasi

      // Menunggu elemen input untuk memasukkan lokasi
      WebElement locationInput = app.getDriver().findElement(
        By.xpath("//input[@data-testid='autocomplete-field' and @placeholder='City, hotel, place to go']"));
      locationInput.sendKeys("Bali");

      Thread.sleep(3000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen autocomplete item untuk klik
      WebElement autocompleteItem = app.getDriver().findElement(
        By.xpath("//div[@data-testid='autocomplete-item-name']//mark[text()='Bali']"));
      autocompleteItem.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen tombol search untuk klik
      WebElement searchButton = app.getDriver().findElement(
        By.xpath("//div[@role='button' and @data-testid='search-submit-button']"));
      // Klik tombol search
      searchButton.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memilih filter

      // Menunggu elemen opsi filter untuk klik berdasarkan 1 bintang
      WebElement oneStarOption = app.getDriver().findElement(
        By.xpath("//div[@class='css-1dbjc4n' and @data-testid='STAR2']"));
      oneStarOption.click();

      Thread.sleep(15000);  // Delay 5 detik sebelum memasukkan tanggal
      app.getDriver().quit();  // Tutup browser

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void search3StarHotel(){
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan

      /////////////////////////// SEARCH HOTEL ///////////////////////////

      // Menunggu elemen Log In untuk klik
      WebElement HotelButton = app.getDriver().findElement(
          By.xpath("//div[@data-testid='product-pill-Hotels']//div[contains(text(), 'Hotels')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        HotelButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", HotelButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan lokasi

      // Menunggu elemen input untuk memasukkan lokasi
      WebElement locationInput = app.getDriver().findElement(
        By.xpath("//input[@data-testid='autocomplete-field' and @placeholder='City, hotel, place to go']"));
      locationInput.sendKeys("Bali");

      Thread.sleep(3000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen autocomplete item untuk klik
      WebElement autocompleteItem = app.getDriver().findElement(
        By.xpath("//div[@data-testid='autocomplete-item-name']//mark[text()='Bali']"));
      autocompleteItem.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen tombol search untuk klik
      WebElement searchButton = app.getDriver().findElement(
        By.xpath("//div[@role='button' and @data-testid='search-submit-button']"));
      // Klik tombol search
      searchButton.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memilih filter

      // Menunggu elemen opsi filter untuk klik berdasarkan 1 bintang
      WebElement oneStarOption = app.getDriver().findElement(
        By.xpath("//div[@class='css-1dbjc4n' and @data-testid='STAR3']"));
      oneStarOption.click();

      Thread.sleep(15000);  // Delay 5 detik sebelum memasukkan tanggal
      app.getDriver().quit();  // Tutup browser

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void search4StarHotel(){
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan

      /////////////////////////// SEARCH HOTEL ///////////////////////////

      // Menunggu elemen Log In untuk klik
      WebElement HotelButton = app.getDriver().findElement(
          By.xpath("//div[@data-testid='product-pill-Hotels']//div[contains(text(), 'Hotels')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        HotelButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", HotelButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan lokasi

      // Menunggu elemen input untuk memasukkan lokasi
      WebElement locationInput = app.getDriver().findElement(
        By.xpath("//input[@data-testid='autocomplete-field' and @placeholder='City, hotel, place to go']"));
      locationInput.sendKeys("Bali");

      Thread.sleep(3000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen autocomplete item untuk klik
      WebElement autocompleteItem = app.getDriver().findElement(
        By.xpath("//div[@data-testid='autocomplete-item-name']//mark[text()='Bali']"));
      autocompleteItem.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen tombol search untuk klik
      WebElement searchButton = app.getDriver().findElement(
        By.xpath("//div[@role='button' and @data-testid='search-submit-button']"));
      // Klik tombol search
      searchButton.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memilih filter

      // Menunggu elemen opsi filter untuk klik berdasarkan 1 bintang
      WebElement oneStarOption = app.getDriver().findElement(
        By.xpath("//div[@class='css-1dbjc4n' and @data-testid='STAR4']"));
      oneStarOption.click();

      Thread.sleep(15000);  // Delay 5 detik sebelum memasukkan tanggal
      app.getDriver().quit();  // Tutup browser

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void search5StarHotel(){
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan
      /////////////////////////// SEARCH HOTEL ///////////////////////////

      // Menunggu elemen Log In untuk klik
      WebElement HotelButton = app.getDriver().findElement(
          By.xpath("//div[@data-testid='product-pill-Hotels']//div[contains(text(), 'Hotels')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        HotelButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", HotelButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan lokasi

      // Menunggu elemen input untuk memasukkan lokasi
      WebElement locationInput = app.getDriver().findElement(
        By.xpath("//input[@data-testid='autocomplete-field' and @placeholder='City, hotel, place to go']"));
      locationInput.sendKeys("Bali");

      Thread.sleep(3000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen autocomplete item untuk klik
      WebElement autocompleteItem = app.getDriver().findElement(
        By.xpath("//div[@data-testid='autocomplete-item-name']//mark[text()='Bali']"));
      autocompleteItem.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen tombol search untuk klik
      WebElement searchButton = app.getDriver().findElement(
        By.xpath("//div[@role='button' and @data-testid='search-submit-button']"));
      // Klik tombol search
      searchButton.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memilih filter

      // Menunggu elemen opsi filter untuk klik berdasarkan 1 bintang
      WebElement oneStarOption = app.getDriver().findElement(
        By.xpath("//div[@class='css-1dbjc4n' and @data-testid='STAR5']"));
      oneStarOption.click();

      Thread.sleep(15000);  // Delay 5 detik sebelum memasukkan tanggal
      app.getDriver().quit();  // Tutup browser

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void searchBestPriceHotel(){ 
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan
      /////////////////////////// SEARCH HOTEL ///////////////////////////

      // Menunggu elemen Log In untuk klik
      WebElement HotelButton = app.getDriver().findElement(
          By.xpath("//div[@data-testid='product-pill-Hotels']//div[contains(text(), 'Hotels')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        HotelButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", HotelButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan lokasi

      // Menunggu elemen input untuk memasukkan lokasi
      WebElement locationInput = app.getDriver().findElement(
        By.xpath("//input[@data-testid='autocomplete-field' and @placeholder='City, hotel, place to go']"));
      locationInput.sendKeys("Bali");

      Thread.sleep(3000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen autocomplete item untuk klik
      WebElement autocompleteItem = app.getDriver().findElement(
        By.xpath("//div[@data-testid='autocomplete-item-name']//mark[text()='Bali']"));
      autocompleteItem.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memasukkan tanggal

      // Menunggu elemen tombol search untuk klik
      WebElement searchButton = app.getDriver().findElement(
        By.xpath("//div[@role='button' and @data-testid='search-submit-button']"));
      // Klik tombol search
      searchButton.click();

      Thread.sleep(5000);  // Delay 5 detik sebelum memilih filter

      // Menunggu elemen opsi filter untuk klik berdasarkan 1 bintang
      WebElement oneStarOption = app.getDriver().findElement(
        By.xpath("//div[@class='css-1dbjc4n' and @data-testid='1010_MERCH_CUSTOM_FILTER_ID']"));
      oneStarOption.click();

      Thread.sleep(15000);  // Delay 5 detik sebelum memasukkan tanggal
      app.getDriver().quit();  // Tutup browser

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
