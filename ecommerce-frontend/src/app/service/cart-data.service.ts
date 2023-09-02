import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartDataService {
  private cartData: { productId: number; quantity: number }[] = [];
  private cartSubtotal : number = 0;

  constructor() { }

  setCartData(cartData: { productId: number; quantity: number }[]) {
    this.cartData = cartData;
  }

  getCartData() {
    return this.cartData;
  }

  setCartSubtotal(cartSubtotal: number) {
    this.cartSubtotal = cartSubtotal;
  }

  getCartSubtotal() {
    return this.cartSubtotal.toFixed(2);
  }
}
