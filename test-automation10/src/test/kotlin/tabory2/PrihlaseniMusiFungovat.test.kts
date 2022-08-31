import org.junit.jupiter.api.Assertions.*
import util.SemanticStepFunctions.*

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
