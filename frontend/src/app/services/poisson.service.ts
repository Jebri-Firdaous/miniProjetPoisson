import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Poisson {
  id?: number;
  nom: string;
  espece: string;
  poids: number;
  prix: number;
  imageUrl: string;
}

@Injectable({
  providedIn: 'root'
})
export class PoissonService {
  private baseUrl = 'http://localhost:8081/backend/api/poisson';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Poisson[]> {
    return this.http.get<Poisson[]>(`${this.baseUrl}/list-all-poisson`);
  }

  getOne(id: number): Observable<Poisson> {
    return this.http.get<Poisson>(`${this.baseUrl}/get-poisson/${id}`);
  }

addPoissonWithImage(poisson: any, image: File): Observable<any> {
  const formData = new FormData();
  formData.append("poisson", new Blob([JSON.stringify(poisson)], {
    type: "application/json"
  }));
  formData.append("image", image);

  return this.http.post(`${this.baseUrl}/add-poisson-with-image`, formData);
}


updatePoissonWithImage(id: number, poisson: any, image: File): Observable<any> {
  const formData = new FormData();
  formData.append("poisson", new Blob([JSON.stringify(poisson)], {
    type: "application/json"
  }));
  formData.append("image", image);

  return this.http.post(`${this.baseUrl}/update-poisson-with-image/${id}`, formData);
}





  update(poisson: Poisson): Observable<Poisson> {
    return this.http.put<Poisson>(`${this.baseUrl}/modify-poisson/${poisson.id}`, poisson);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/remove-poisson/${id}`);
  }
}
