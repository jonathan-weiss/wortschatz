import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {VocabularyCardDto, VocabularyCardSetResponseDto, WortschatzService} from "../../generated/openapi";

@Component({
  selector: 'vocabulary-learning-card',
  templateUrl: './vocabulary-learning-card.component.html',
  styleUrls: ['./vocabulary-learning-card.component.css']
})
export class VocabularyLearningCardComponent {
  @Input() vocabularyCard!: VocabularyCardDto

  @Input() isCovered!: boolean

  @Input() hasNext!: boolean

  @Output() uncoverVocabularyCard: EventEmitter<void> = new EventEmitter<void>()
  @Output() nextVocabularyCard: EventEmitter<void> = new EventEmitter<void>()


  uncoverCard(): void {
    this.uncoverVocabularyCard.emit()
  }

  nextCard(): void {
    this.nextVocabularyCard.emit()
  }


}
