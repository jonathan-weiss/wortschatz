import {Component, OnInit} from '@angular/core';
import {v4 as uuidv4} from 'uuid';



import {
  AddVocabularyCardRequestDto,
  VocabularyCardDto,
  VocabularyCardSetResponseDto,
  WortschatzService
} from "../generated/openapi";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {



  constructor(private wortschatzService: WortschatzService) {
  }




}
