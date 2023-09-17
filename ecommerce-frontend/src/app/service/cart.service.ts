import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { Cart } from '../model/cart.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private httpClient: HttpClient) { }

  private apiServerUrl = environment.hostUrl;

  public getAllCarts(): Observable<Cart[]> {
    return this.httpClient.get<Cart[]>(`${this.apiServerUrl}/api/carts/all`);
  }

  public getCartById(cartId: number): Observable<Cart> {
    return this.httpClient.get<Cart>(`${this.apiServerUrl}/api/carts/find/${cartId}`);
  }

  public addProductToCart(cartId: number, productId: number): Observable<Cart> {
    return this.httpClient.post<Cart>(`${this.apiServerUrl}/api/carts/${cartId}/products/add/${productId}`, {cartId, productId});
  }

  public createCart(userId: number): Observable<Cart> {
    const uid = { userId };
    return this.httpClient.post<Cart>(`${this.apiServerUrl}/api/carts/add`, uid);
  }

  public clearProductsFromCart(cartId: number): Observable<Cart> {
    return this.httpClient.delete<Cart>(`${this.apiServerUrl}/api/carts/${cartId}/products/delete`, {});
  }

  public removeProductByIdFromCart(cartId: number, productId: number): Observable<Cart> {
    return this.httpClient.delete<Cart>(`${this.apiServerUrl}/api/carts/${cartId}/products/delete/${productId}`, {});
  }

  public deleteCart(cartId: number): Observable<Cart> {
    return this.httpClient.delete<Cart>(`${this.apiServerUrl}/api/carts/delete/${cartId}`, {});
  }
}
