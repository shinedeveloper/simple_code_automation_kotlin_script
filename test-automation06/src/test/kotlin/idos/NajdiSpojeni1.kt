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
        clickOnAcceptCookiesButton()

        // Zadej Praha a Olomouc do vyhledavacich poli (odkud->kam)
        clickOnJourneyFromTextBox()
        typeToFocusedElement("Brno")
        clickOnJourneyToTextBox()
        typeToFocusedElement("Olomouc")
        typeToFocusedElement(Keys.ESCAPE)

        // Vyhledej spojeni
        clickOnJourneySearchButton()

        // Zkontroluj pocet nalezenych spoju z Prahy do Olomouce
        val connectionCount: Int = findJourneyConnectionCount()
        assertTrue(connectionCount > 0)
        closeWebBrowser()
    }

}