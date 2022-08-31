package tabory1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.SemanticStepFunctions.*

class TestyPrihlasovaniNaTabory {

    @Test
    fun prihlaseniMusiFungovat() {
        // Given
        otevriProhlizec("https://cz-test-dva.herokuapp.com/")
        klikniNaTlacitkoPrihlasitUzivatele()

        // When
        vyplnUzivatelskeJmeno("petr.otec@seznam.cz")
        vyplnHeslo("Czechitas123")
        potvrdPrihlaseni()

        // Then
        val adresa = zjistiAktualniAdresuVProhlizeci()
        assertTrue(adresa.endsWith("/zaci"))
        val nadpisStranky = zjistiNadpisStranky()
        assertEquals("Přihlášky", nadpisStranky)
        zavriProhlizec()
    }

    @Test
    fun rodicSeMusiBytSchopenPrihlasitAVybratTerminKurzuProDite() {
        // Given
        otevriProhlizec("https://cz-test-dva.herokuapp.com/zaci")
        prihlasUzivatele("petr.otec@seznam.cz", "Czechitas123")

        // When
        klikniNaTlacitkoVytvoritNovouPrihlasku()
        vyberKategoriiCislo(2)
        vyberKurzCislo(0)
        vyplnPrihlaskuNaKurz()
        klikniNaTlacitkoOdeslatPrihlasku()

        // Then
        val potvrzeniPrihlasky = najdiNapisNaStrance("Stáhnout potvrzení o přihlášení")
        assertNotNull(potvrzeniPrihlasky)
        zavriProhlizec()
    }

    @Test
    fun rodicMusiBytSchopenVybratDitetiTerminKurzuAPrihlasitHo() {
        // Given
        otevriProhlizec("https://cz-test-dva.herokuapp.com/")

        // When
        vyberKategoriiCislo(2)
        vyberKurzCislo(0)
        prihlasUzivatele("petr.otec@seznam.cz", "Czechitas123")
        val nadpisStranky = zjistiNadpisStranky()
        assertEquals("Nová přihláška", nadpisStranky)
        vyplnPrihlaskuNaKurz()
        klikniNaTlacitkoOdeslatPrihlasku()

        // Then
        val potvrzeniPrihlasky = najdiNapisNaStrance("Stáhnout potvrzení o přihlášení")
        assertNotNull(potvrzeniPrihlasky)
        zavriProhlizec()
    }
}