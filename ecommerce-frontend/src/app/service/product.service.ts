import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { Product } from '../model/product.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  private apiServerUrl = environment.hostUrl;

  public getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiServerUrl}/api/products/all`);
  }

  public getAllProductsByCategoryId(categoryId: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiServerUrl}/api/categories/${categoryId}/products/all`);
  }
}
