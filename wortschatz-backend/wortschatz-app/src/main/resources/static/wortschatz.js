
function onLoadWortschatz() {
    console.log("Onload Wortschatz")

    fetch('/vocabulary-card-set')
        .then(response => response.json())
        .then(data => console.log(data));
}


class VocabularyCards {
    constructor(vocabularyCardList) {
        this.vocabularyCards = vocabularyCardList;
    }
}

class VocabularyCard {
    constructor(foreignWord, validationWord) {
        this.foreignWord = foreignWord;
        this.validationWord = validationWord;
    }
}

