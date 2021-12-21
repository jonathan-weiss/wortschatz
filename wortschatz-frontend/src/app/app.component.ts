import {Component, OnInit} from '@angular/core';
import {VocabularyCardDto, VocabularyCardSetResponseDto, WortschatzService} from "../generated/openapi";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'wortschatz-app';
  vocabularyCards: Array<VocabularyCardDto> = []

  isLoading = true;


  constructor(private wortschatzService: WortschatzService) {
  }

  ngOnInit(): void {
    this.wortschatzService.vocabularyCardSet().subscribe((response: VocabularyCardSetResponseDto) => {
      this.vocabularyCards.push(... response.vocabularyCards)
      // this.isLoading = false
    })

  }

  resolve(): void {

  }

  nextCard(): void {

  }


}
