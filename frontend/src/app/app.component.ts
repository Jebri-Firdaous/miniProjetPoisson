import { Component } from '@angular/core';
import { HeaderComponent } from '../app/layouts/header/header.component';
import { FooterComponent } from '../app/layouts/footer/footer.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent, 
    RouterOutlet,  
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'MonApp';
}
