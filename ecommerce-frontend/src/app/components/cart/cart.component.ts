import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Cart } from 'src/app/model/cart.model';
import { Order } from 'src/app/model/order';
import { CartDataService } from 'src/app/service/cart-data.service';
import { CartService } from 'src/app/service/cart.service';
import { OrderService } from 'src/app/service/order.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  cartData: any[] = [];
  carts: Cart[] = [];
  cart: Cart | undefined;
  order: Order | undefined;
  orders: Order[] = [];
  private currentCartId: number | undefined;
  selectedQuantity: { [productId: number] : number } = {};
  totalPrice: number = 0;
  private currentOrderId : number | undefined;
  prodObj: { productId: number; quantity: number; } = {
    productId: 0,
    quantity: 0
  };

  constructor(public cartService: CartService, private cartDataService: CartDataService, private orderService: OrderService, private router: Router) {}
  
  ngOnInit() {
    this.getAllCarts();
  }
  
  public getAllCarts(): void {
    this.cartService.getAllCarts().subscribe(
      (response: Cart[]) => {
        this.carts = response;
        console.log("response", response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  goToHome() {
    this.router.navigate(['/home'])
  }

  public getAllOrders(): void {
    this.orderService.getAllOrders().subscribe(
      (response: Order[]) => {
        this.orders = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  goToOrder() {
    this.router.navigate(['/order'])
  }

  public clearCart() : void {
    var newInt = localStorage.getItem('cartId');
    this.currentCartId = +newInt!;
    this.cartService.clearProductsFromCart(this.currentCartId!).subscribe(
      () => {
        console.log("cart cleared!");
        this.getAllCarts();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public onProductRemoveFromCart(productId: number): void {
    var tempCartId = localStorage.getItem('cartId')
    this.currentCartId = +tempCartId!;

    this.cartService.removeProductByIdFromCart(this.currentCartId!, productId).subscribe(
      () => {
          console.log("product with id: ", productId, " removed from cart!");
          this.getAllCarts();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  getSubtotal() {
    let sum = 0;
    sum = this.updateTotalPrice();
    // this.carts[0].currentPrice = sum;
    this.cartDataService.setCartSubtotal(sum);

    return sum;
  }
  
  updateTotalPrice() : number {
    let sum = 0;

    // Create an object to store product data
    type CartData = { [key: number]: number };

    // Create an object to store product data
    const cartDataMap: CartData = {};
    for (const productIdAsString in this.selectedQuantity) {
      if (this.selectedQuantity.hasOwnProperty(productIdAsString)) {
        const productId = parseInt(productIdAsString, 10);
        const product = this.carts[0].cartProducts.find(p => p.id === +productId);
        const quantity = parseInt(this.selectedQuantity[productId].toString(), 10);

        if (product && !isNaN(quantity)) {
          sum += (product.price * quantity);
        }
        
        if (cartDataMap.hasOwnProperty(productId)) {
          cartDataMap[productId] += quantity;
        }
        else {
          cartDataMap[productId] = quantity
        }
      }
    }

    // Convert cartDataMap to an array of objects if needed
    this.cartData = Object.keys(cartDataMap).map(productId => ({
      productId: parseInt(productId, 10),
      quantity: cartDataMap[parseInt(productId, 10)],
    }));

    console.log("cart data: " , this.cartData);

    this.totalPrice = sum;

    this.cartDataService.setCartData(this.cartData);
    return Number(this.totalPrice.toFixed(2));
  }
}