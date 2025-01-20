package com.example.selenium;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SignIn {
  private App app;

  @BeforeClass
  public void setup() {
    app = new App();
    app.openURL("https://www.traveloka.com/en-id");
  }

  @Test
  public void testSignInBerhasil() {
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan

      // Menunggu elemen Log In untuk klik
      WebElement loginButton = app.getDriver().findElement(
          By.xpath("//div[@dir='auto' and contains(@class, 'css-901oao') and contains(text(), 'Log In')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        loginButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", loginButton);  // Klik menggunakan JavaScript
      }

      // Delay 5 detik untuk memastikan form sudah siap
      Thread.sleep(5000);  // Menunggu untuk memastikan form login muncul

      // Input email
      WebElement emailInput = app.getDriver().findElement(
          By.xpath("//input[@data-testid='auth-username' and @placeholder='Example: +62812345678 or yourname@email.com']"));
      emailInput.sendKeys("alexis.j22@mhs.istts.ac.id");

      // Delay 2 detik sebelum klik tombol Sign In
      Thread.sleep(5000);  // Menunggu sebentar sebelum melakukan klik tombol Sign In

      // Input password
      WebElement passwordInput = app.getDriver().findElement(
          By.xpath("//input[@data-testid='auth-password' and @placeholder='*********' and @type='password']"));
      passwordInput.sendKeys("Software_Testing");

      // Delay 2 detik sebelum klik tombol Sign In
      Thread.sleep(2000);  // Menunggu sebentar sebelum melakukan klik tombol Sign In

      // Klik tombol Sign In
      WebElement signinButton = app.getDriver().findElement(
          By.xpath("//div[@data-testid='loginBtn' and .//div[text()='Log In']]"));
      try {
        signinButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click for Sign In.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", signinButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(10000);

      // tutup web browser
      app.getDriver().quit();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSignInGagal() {
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan

      // Menunggu elemen Log In untuk klik
      WebElement loginButton = app.getDriver().findElement(
          By.xpath("//div[@dir='auto' and contains(@class, 'css-901oao') and contains(text(), 'Log In')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        loginButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", loginButton);  // Klik menggunakan JavaScript
      }

      // Delay 5 detik untuk memastikan form sudah siap
      Thread.sleep(5000);  // Menunggu untuk memastikan form login muncul

      // Input email
      WebElement emailInput = app.getDriver().findElement(
          By.xpath("//input[@data-testid='auth-username' and @placeholder='Example: +62812345678 or yourname@email.com']"));
      emailInput.sendKeys("alexis.j22@mhs.istts.ac.id");

      // Delay 2 detik sebelum klik tombol Sign In
      Thread.sleep(5000);  // Menunggu sebentar sebelum melakukan klik tombol Sign In

      // Input password
      WebElement passwordInput = app.getDriver().findElement(
          By.xpath("//input[@data-testid='auth-password' and @placeholder='*********' and @type='password']"));
      passwordInput.sendKeys("Software_testing");

      // Delay 2 detik sebelum klik tombol Sign In
      Thread.sleep(2000);  // Menunggu sebentar sebelum melakukan klik tombol Sign In

      // Klik tombol Sign In
      WebElement signinButton = app.getDriver().findElement(
          By.xpath("//div[@data-testid='loginBtn' and .//div[text()='Log In']]"));
      try {
        signinButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click for Sign In.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", signinButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(10000);

      // tutup web browser
      app.getDriver().quit();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSignIn() {
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan

      // Menunggu elemen Log In untuk klik
      WebElement loginButton = app.getDriver().findElement(
          By.xpath("//div[@dir='auto' and contains(@class, 'css-901oao') and contains(text(), 'Log In')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        loginButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", loginButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(5000);

      // tutup web browser
      app.getDriver().quit();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSignInNoEmailAndPassword() {
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan

      // Menunggu elemen Log In untuk klik
      WebElement loginButton = app.getDriver().findElement(
          By.xpath("//div[@dir='auto' and contains(@class, 'css-901oao') and contains(text(), 'Log In')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        loginButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", loginButton);  // Klik menggunakan JavaScript
      }

      // Input email
      WebElement emailInput = app.getDriver().findElement(
          By.xpath("//input[@data-testid='auth-username' and @placeholder='Example: +62812345678 or yourname@email.com']"));
      emailInput.sendKeys("alexis.j22@mhs.istts.ac.id");

      Thread.sleep(5000);

      // tutup web browser
      app.getDriver().quit();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSignInNoPassword() {
    try {
      // Delay 12 detik sebelum klik tombol Log In
      Thread.sleep(12000);  // Delay untuk menunggu CAPTCHA jika diperlukan

      // Menunggu elemen Log In untuk klik
      WebElement loginButton = app.getDriver().findElement(
          By.xpath("//div[@dir='auto' and contains(@class, 'css-901oao') and contains(text(), 'Log In')]"));
      try {
        // Klik menggunakan JavaScript jika terjadi kesalahan klik
        loginButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", loginButton);  // Klik menggunakan JavaScript
      }

      // Delay 5 detik untuk memastikan form sudah siap
      Thread.sleep(5000);  // Menunggu untuk memastikan form login muncul

      // Input email
      WebElement emailInput = app.getDriver().findElement(
          By.xpath("//input[@data-testid='auth-username' and @placeholder='Example: +62812345678 or yourname@email.com']"));
      emailInput.sendKeys("alexis.j22@mhs.istts.ac.id");

      // Delay 2 detik sebelum klik tombol Sign In
      Thread.sleep(5000);  // Menunggu sebentar sebelum melakukan klik tombol Sign In

      // // Input password
      // WebElement passwordInput = app.getDriver().findElement(
      //     By.xpath("//input[@data-testid='auth-password' and @placeholder='*********' and @type='password']"));
      // passwordInput.sendKeys("Software_testing");

      // Delay 2 detik sebelum klik tombol Sign In
      Thread.sleep(2000);  // Menunggu sebentar sebelum melakukan klik tombol Sign In

      // Klik tombol Sign In
      WebElement signinButton = app.getDriver().findElement(
          By.xpath("//div[@data-testid='loginBtn' and .//div[text()='Log In']]"));
      try {
        signinButton.click();
      } catch (ElementClickInterceptedException e) {
        System.out.println("Element Click Intercepted, trying JavaScript click for Sign In.");
        JavascriptExecutor executor = (JavascriptExecutor) app.getDriver();
        executor.executeScript("arguments[0].click();", signinButton);  // Klik menggunakan JavaScript
      }

      Thread.sleep(5000);

      // tutup web browser
      app.getDriver().quit();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
