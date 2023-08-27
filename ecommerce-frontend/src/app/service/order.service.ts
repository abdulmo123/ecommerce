import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { Order } from '../model/order';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private httpClient: HttpClient) { }

  private apiServerUrl = environment.hostUrl;

  public getAllOrders(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(`${this.apiServerUrl}/api/orders/all`);
  }

  public addCartToOrder(orderId: number, cartId: number): Observable<Order> {
    return this.httpClient.post<Order>(`${this.apiServerUrl}/api/orders/${orderId}/carts/add/${cartId}`, {orderId, cartId});
  }

  public createOrder(): Observable<Order> {
    return this.httpClient.post<Order>(`${this.apiServerUrl}/api/orders/add`, {});
  }
}
