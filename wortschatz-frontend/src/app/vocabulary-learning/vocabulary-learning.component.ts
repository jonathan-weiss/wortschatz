import {Component, OnInit} from '@angular/core';
import {VocabularyCardDto, VocabularyCardSetResponseDto, WortschatzService} from "../../generated/openapi";

@Component({
  selector: 'vocabulary-learning',
  templateUrl: './vocabulary-learning.component.html',
  styleUrls: ['./vocabulary-learning.component.css']
})
export class VocabularyLearningComponent implements OnInit {
  vocabularyCards: Array<VocabularyCardDto> = []

  isLoading = true;

  vocabularyCardIndex = 0;

  isCardCovered = true;

  constructor(private wortschatzService: WortschatzService) {
  }

  ngOnInit(): void {
    this.wortschatzService.vocabularyCardSet().subscribe((response: VocabularyCardSetResponseDto) => {
      this.vocabularyCards.push(... response.vocabularyCards)
      this.isLoading = false
    })

  }

  currentVocabularyCard(): VocabularyCardDto {
    if(this.isLoading || this.vocabularyCards.length <= this.vocabularyCardIndex) {
      throw Error("Cannot get vocabulary card when loading.")
    }

    return this.vocabularyCards[this.vocabularyCardIndex]
  }

  uncoverCard(): void {
    this.isCardCovered = false;

  }

  hasNext(): boolean {
    return this.vocabularyCards.length > 0 && (this.vocabularyCardIndex + 1) < this.vocabularyCards.length
  }

  nextCard(): void {
    if(!this.hasNext()) {
      throw Error("End reached, no next card.")
    }

    this.vocabularyCardIndex++;
    this.isCardCovered = true;
  }

  isLearning(): boolean {
    return !this.isLoading
  }


}
