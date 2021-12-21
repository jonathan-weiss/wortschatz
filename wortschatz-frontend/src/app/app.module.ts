import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ApiModule, Configuration, ConfigurationParameters} from "../generated/openapi";
import {HttpClientModule} from "@angular/common/http";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSliderModule} from "@angular/material/slider";
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatDividerModule} from '@angular/material/divider';
import {VocabularyLearningComponent} from "./vocabulary-learning/vocabulary-learning.component";
import {VocabularyLearningCardComponent} from "./vocabulary-learning-card/vocabulary-learning-card.component";
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

export function RestApiConfigurationFactory(): Configuration {
  const configurationParameters: ConfigurationParameters = {
    basePath: '/api'
  }
  return new Configuration(configurationParameters) ;
}


@NgModule({
  declarations: [
    AppComponent,
    VocabularyLearningComponent,
    VocabularyLearningCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ApiModule.forRoot(RestApiConfigurationFactory),
    HttpClientModule,
    BrowserAnimationsModule,
    MatSliderModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    MatProgressSpinnerModule
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
