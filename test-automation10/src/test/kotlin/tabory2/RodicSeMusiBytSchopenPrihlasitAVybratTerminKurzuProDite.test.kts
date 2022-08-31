import org.junit.jupiter.api.Assertions.*
import util.SemanticStepFunctions.*

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