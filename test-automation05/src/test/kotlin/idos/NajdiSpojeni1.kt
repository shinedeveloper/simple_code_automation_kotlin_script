package idos

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.openqa.selenium.Keys
import util.*

class NajdiSpojeni1 {

    @Test
    fun najdiSpojeni1() {
        openWebBrowser("https://idos.idnes.cz/vlakyautobusymhdvse/spojeni/")

        // Potvrd cookies
        clickOnElement("#didomi-notice-agree-button")

        // Zadej Praha a Olomouc do vyhledavacich poli (odkud->kam)
        clickOnNthElementOfMany(".idos-autosuggest__container", 0)
        typeToFocusedElement("Brno")
        clickOnNthElementOfMany(".idos-autosuggest__container", 1)
        typeToFocusedElement("Olomouc")
        typeToFocusedElement(Keys.ESCAPE)

        // Vyhledej spojeni
        clickOnElement("div.submit button")

        // Zkontroluj pocet nalezenych spoju z Prahy do Olomouce
        val connectionCount: Int = findElementCount(".connection")
        assertTrue(connectionCount > 0)
        closeWebBrowser()
    }
}