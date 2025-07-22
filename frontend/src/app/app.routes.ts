import { Routes } from '@angular/router';
import { HomeComponent } from './layouts/home/home.component';

export const routes: Routes = [
  { path: 'home', component: HomeComponent },
  {
    path: 'poissons',
    loadComponent: () =>
      import('./features/list-poissons/list-poissons.component').then(
        (m) => m.ListPoissonsComponent
      ),
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home' }
];
