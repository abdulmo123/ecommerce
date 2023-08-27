import { PathLocationStrategy } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Cart } from 'src/app/model/cart.model';
import { Order } from 'src/app/model/order';
import { CartService } from 'src/app/service/cart.service';
import { OrderService } from 'src/app/service/order.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  productData: any[] = [];
  carts: Cart[] = [];
  cart: Cart | undefined;
  order: Order | undefined;
  orders: Order[] = [];
  private currentCartId: number | undefined;
  selectedQuantity: { [productId: number] : number } = {};
  totalPrice: number = 0;
  private currentOrderId : number | undefined;
  prodObj: { productId: string; quantity: number; } = {
    productId: '',
    quantity: 0
  };

  constructor(public cartService: CartService, private orderService: OrderService, private router: Router) {}
  
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

  onOrderAdd(cartId: number) : void {
    let tempid = JSON.parse(localStorage.getItem('cartId') || '');
    cartId = +tempid!;
    this.router.navigate(['/order'])
    // add cart object to order
    if (localStorage.getItem('orderId') === null) {
      this.orderService.createOrder().subscribe(
        (newOrder: Order) => {
          this.order = newOrder;
          localStorage.setItem('orderId', JSON.stringify(this.order.id!));
          console.log("new order created!" + newOrder.id);

          this.addCartToOrder(cartId);
          console.log("cart added to new order");
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      )
    }

    else {
      this.addCartToOrder(cartId);
      console.log("cart added to existing order");
    }
  }

  private addCartToOrder(cartId: number): void {
    var newInt = localStorage.getItem('orderId');

    this.currentOrderId = +newInt!;
    this.orderService.addCartToOrder(this.currentOrderId!, cartId).subscribe(
      () => {
        console.log("cart id is : " + cartId);
        console.log("cart added to order!");
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )

    this.getAllOrders();

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
    this.carts[0].currentPrice = sum;

    return this.carts[0].currentPrice!.toFixed(2);
  }
  
  updateTotalPrice() : number {
    let sum = 0;

    // Create an object to store product data
    type ProductData = { [key: string]: number };

    // Create an object to store product data
    const productDataMap: ProductData = {};
    for (const productId in this.selectedQuantity) {
      if (this.selectedQuantity.hasOwnProperty(productId)) {
        const product = this.carts[0].cartProducts.find(p => p.id === +productId);
        const quantity = this.selectedQuantity[productId];

        if (product && !isNaN(quantity)) {
          sum += (product.price * quantity);
        }

        if (productDataMap.hasOwnProperty(productId)) {
          productDataMap[productId] += quantity;
        }
        else {
          productDataMap[productId] = quantity
        }
      }
    }

    // Convert productDataMap to an array of objects if needed
    this.productData = Object.keys(productDataMap).map(productId => ({
      productId: productId,
      quantity: productDataMap[productId],
    }));

    console.log("product data: " , this.productData);

    this.totalPrice = sum;
    return Number(this.totalPrice.toFixed(2));
  }
}