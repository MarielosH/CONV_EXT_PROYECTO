import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  aspirante = true;
  coordinador = false;
  jefev = false;

  constructor(private domSanitizer: DomSanitizer, private router: Router) { }

  ngOnInit() {
  }

  clickAspirante(){
    localStorage.setItem('rol', '1');
    this.aspirante = true;
    this.jefev = false;
    this.coordinador = false;
    this.router.navigate(['/convocatorias-externas/gestion-convocatorias']);
  }

  clickCoordinador(){
    localStorage.setItem('rol', '2');
    this.aspirante = false;
    this.jefev = false;
    this.coordinador = true;
    this.router.navigate(['/convocatorias-externas/gestion-convocatorias']);
  }

  clickJefeV(){
    localStorage.setItem('rol', '3');
    this.aspirante = false;
    this.jefev = true;
    this.coordinador = false;
    this.router.navigate(['/convocatorias-externas/gestion-convocatorias']);
  }
}
