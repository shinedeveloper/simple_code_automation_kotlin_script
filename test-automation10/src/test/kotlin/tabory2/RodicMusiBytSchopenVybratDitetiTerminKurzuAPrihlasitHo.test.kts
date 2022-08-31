import org.junit.jupiter.api.Assertions.*
import util.SemanticStepFunctions.*

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