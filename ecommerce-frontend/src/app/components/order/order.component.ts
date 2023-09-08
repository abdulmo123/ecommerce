import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Cart } from 'src/app/model/cart.model';
import { Order } from 'src/app/model/order';
import { CartService } from 'src/app/service/cart.service';
import { OrderService } from 'src/app/service/order.service';
import { CartComponent } from '../cart/cart.component';
import { User } from 'src/app/model/user.model';
import { UserService } from 'src/app/service/user.service';
import { CartDataService } from 'src/app/service/cart-data.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent {
  users: User[] = [];
  carts: Cart[] = [];
  cart: Cart | undefined;
  orders: Order[] = [];
  order: Order | undefined;
  private currentCartId: number | undefined;
  private currentOrderId : number | undefined;
  cartData: { productId: number; quantity: number; } [] | undefined;
  cartSubtotal : number | undefined;
  constructor(public orderService: OrderService, private cartService: CartService, private cartDataService: CartDataService, private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.getAllCarts();
    this.getAllOrders();
    this.getAllUsers();
    this.cartData = this.cartDataService.getCartData();
    this.cartSubtotal = +Number(this.cartDataService.getCartSubtotal()).toFixed(2);
    console.log("cart data: => ", this.cartData);
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

  public getAllCarts(): void {
    this.cartService.getAllCarts().subscribe(
      (response: Cart[]) => {
        this.carts = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getAllUsers(): void {
    this.userService.getAllUsers().subscribe(
      (response: User[]) => {
        this.users = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  onOrderAdd(cartId: number) : void {
    let tempid = JSON.parse(localStorage.getItem('cartId') || '');
    cartId = +tempid!;
    this.router.navigate(['/home'])
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

    this.router.navigate(['/orderconf']);
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

  getQuantityForProduct(productId: number) {
    const cartData = this.cartData;
  
    const cartProduct = cartData?.find(item => item.productId === productId);
    if (cartProduct) {
      return cartProduct.quantity;
    }
  
    return 0;
  }

  goToHome() {
    this.router.navigate(['/home'])
  }

  backToCart() {
    this.router.navigate(['/cart'])
  }

}