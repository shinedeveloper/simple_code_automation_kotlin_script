package util;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SemanticStepFunctions {

    private static WebDriver prohlizec;


    public static WebElement najdiNapisNaStrance(String text) {
        return prohlizec.findElement(By.xpath("//*[text()='" + text + "']"));
    }

    public static void klikniNaTlacitkoOdeslatPrihlasku() {
        WebElement tlacitkoOdeslat = prohlizec.findElement(By.xpath("//input[@type='submit']"));
        tlacitkoOdeslat.click();
    }

    //-------------------------------------------------------------------------

    public static void otevriProhlizec(String url) {
        System.out.print("\u001B[32m-------------------------------------------------\nTest starting\n-------------------------------------------------\u001b[30m\n\n\n\n");
        if (prohlizec == null) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/IdeaProjects/Selenium/geckodriver.exe");
            prohlizec = new FirefoxDriver();
            prohlizec.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        prohlizec.navigate().to(url);
    }

    public static String zjistiAktualniAdresuVProhlizeci() {
        return prohlizec.getCurrentUrl();
    }

    public static void klikniNaTlacitkoPrihlasitUzivatele() {
        WebElement odkazPrihlasit = prohlizec.findElement(By.linkText("Přihlásit"));
        odkazPrihlasit.click();
    }

    public static void prihlasUzivatele(String jmeno, String heslo) {
        String nadpisStranky = zjistiNadpisStranky();
        Assertions.assertEquals("Přihlášení", nadpisStranky);

        vyplnUzivatelskeJmeno(jmeno);
        vyplnHeslo(heslo);
        potvrdPrihlaseni();
    }

    public static String zjistiNadpisStranky() {
        WebElement elementNadpisu = prohlizec.findElement(By.xpath("//header//h1"));
        return elementNadpisu.getText();
    }

    public static void vyplnUzivatelskeJmeno(String uzivJmeno) {
        WebElement polickoEmail = prohlizec.findElement(By.id("email"));
        polickoEmail.sendKeys(uzivJmeno);
    }

    public static void vyplnHeslo(String heslo) {
        WebElement polickoHeslo = prohlizec.findElement(By.id("password"));
        polickoHeslo.sendKeys(heslo);
    }

    public static void potvrdPrihlaseni() {
        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.xpath("//form//button[contains(text(), 'Přihlásit')]"));
        tlacitkoPrihlasit.click();
    }

    public static void klikniNaTlacitkoVytvoritNovouPrihlasku() {
        WebElement tlacitkoVytvoritNovouPrihlasku = prohlizec.findElement(By.linkText("Vytvořit novou přihlášku"));
        tlacitkoVytvoritNovouPrihlasku.click();
    }

    public static void vyberKurzCislo(int poradiKurzu) {
        List<WebElement> tlacitkaKurzuVytvoritPrihlasku = prohlizec.findElements(By.xpath("//div[@class = 'card']//a[text() = 'Vytvořit přihlášku']"));
        WebElement tlacitkoVytvoritPrihlasku = tlacitkaKurzuVytvoritPrihlasku.get(poradiKurzu);
        tlacitkoVytvoritPrihlasku.click();
    }

    public static void vyberKategoriiCislo(int poradiKategorie) {
        List<WebElement> tlacitkaKurzuViceInformaci = prohlizec.findElements(By.xpath("//div[@class = 'card']//a[text() = 'Více informací']"));
        WebElement tlacitkoViceInformaci = tlacitkaKurzuViceInformaci.get(poradiKategorie);
        tlacitkoViceInformaci.click();
    }

    public static void vyplnPrihlaskuNaKurz() {
        WebElement menuVyberteTermin = prohlizec.findElement(By.xpath("//div[text()='Vyberte termín...']"));
        menuVyberteTermin.click();

        WebElement polickoTerminu = prohlizec.findElement(By.xpath("//div[@class='bs-searchbox']//input"));
        polickoTerminu.sendKeys("28\n");

        WebElement krestniJmenoZaka = prohlizec.findElement(By.id("forename"));
        krestniJmenoZaka.sendKeys("Karel");

        WebElement prijmeniZaka = prohlizec.findElement(By.id("surname"));
        prijmeniZaka.sendKeys("Synek");

        WebElement datumNarozeniZaka = prohlizec.findElement(By.id("birthday"));
        datumNarozeniZaka.sendKeys("24.12.2000");

        WebElement zaplatitHotove = prohlizec.findElement(By.xpath("//label[@for='payment_cash']"));
        zaplatitHotove.click();

        WebElement zaskrtavatkoSouhlas = prohlizec.findElement(By.xpath("//label[@for='terms_conditions']"));
        zaskrtavatkoSouhlas.click();
    }

    public static void zavriProhlizec() {
        prohlizec.quit();
        System.out.print("\n\n\n\u001B[32m-------------------------------------------------\nTest successful\n-------------------------------------------------\u001b[30m\n");
    }
}
