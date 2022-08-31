package util

fun clickOnAcceptCookiesButton() {
    clickOnElement("#didomi-notice-agree-button")
}

fun clickOnJourneyFromTextBox() {
    clickOnNthElementOfMany(".idos-autosuggest__container", 0)
}

fun clickOnJourneyToTextBox() {
    clickOnNthElementOfMany(".idos-autosuggest__container", 1)
}

fun clickOnJourneySearchButton() {
    clickOnElement("div.submit button")
}

fun findJourneyConnectionCount(): Int {
    return findElementCount(".connection")
}