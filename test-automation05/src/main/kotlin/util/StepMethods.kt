@file:JvmName("StepMethods")

package util

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import kotlin.io.path.toPath

private var currentBrowser: WebDriver? = null

private val browser: WebDriver
    get() {
        return currentBrowser
            ?: throw AssertionError("Web browser has not been opened on any page yet. Use openWebBrowser().")
    }

fun openWebBrowser(url: String) {
    print("\u001B[32m-------------------------------------------------\nTest starting\n-------------------------------------------------\u001b[30m\n\n\n\n")
    val driverPath = findGeckoDriver()
    System.setProperty("webdriver.gecko.driver", driverPath)
    currentBrowser = FirefoxDriver()
//  System.setProperty("webdriver.gecko.driver", "C:/Java-Training/Selenium/geckodriver.exe")
//  browser = FirefoxDriver()
//  System.setProperty("webdriver.chrome.driver", "C:/Java-Training/Selenium/chromedriver.exe")
//  browser = ChromeDriver()
    currentBrowser?.manage()?.timeouts()?.implicitlyWait(10, TimeUnit.SECONDS)
    currentBrowser?.get(url)
}

fun findGeckoDriver(): String {
    val path1 = Paths.get("lib/geckodriver.exe").toAbsolutePath()
    if (Files.exists(path1)) {
        return path1.toString()
    }
    val path2 = Thread.currentThread().contextClassLoader.getResource("util/StepMethods.class")!!.toURI().toPath().resolve("..").resolve("..").resolve("..").resolve("..").resolve("lib").resolve("geckodriver.exe")
    if (Files.exists(path2)) {
        return path2.toRealPath().toString()
    }
    throw AssertionError("Unable to find geckodriver.exe")
}

fun clickOnElement(cssLocator: String) {
    val element: WebElement? = browser.findElement(By.cssSelector(cssLocator))
    element?.click()
}

fun clickOnNthElementOfMany(cssLocator: String, nthElementIndex: Int) {
    val element: WebElement? = browser
        .findElements(By.cssSelector(cssLocator))
        ?.get(nthElementIndex)
    element?.click()
}

fun typeToFocusedElement(text: String) {
    browser.switchTo()?.activeElement()?.sendKeys(text)
}

fun typeToFocusedElement(keyCode: Keys) {
    browser.switchTo()?.activeElement()?.sendKeys(keyCode)
}

fun findElementCount(cssLocator: String): Int {
    val count = browser.findElements(By.cssSelector(cssLocator))?.size
    return count ?: -1;
}

fun closeWebBrowser() {
    browser.quit()
    print("\n\n\n\u001B[32m-------------------------------------------------\nTest successful\n-------------------------------------------------\u001b[30m\n")
}

