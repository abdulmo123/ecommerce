import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, catchError, tap } from 'rxjs';
import { Cart } from 'src/app/model/cart.model';
import { Order } from 'src/app/model/order';
import { User } from 'src/app/model/user.model';
import { CartDataService } from 'src/app/service/cart-data.service';
import { CartService } from 'src/app/service/cart.service';
import { OrderService } from 'src/app/service/order.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-order-conf',
  templateUrl: './order-conf.component.html',
  styleUrls: ['./order-conf.component.css']
})
export class OrderConfComponent {
  users: User[] = [];
  carts: Cart[] = [];
  cart: Cart | undefined;
  orders: Order[] = [];
  cartData: { productId: number; quantity: number; } [] | undefined;
  cartSubtotal : number | undefined;

  order: Order | undefined;
  formattedDate: string | undefined;
  dateObj: string | number | Date | undefined;
  loggedInUserId: number | null = parseInt(localStorage.getItem('userId') || '');

  constructor(private router: Router, private cartDataService: CartDataService, private orderService: OrderService, private cartService: CartService, private userService: UserService) {}

  ngOnInit() {
    this.getAllCarts();
    this.getAllOrders().subscribe((orders) => {
      console.log("Orders loaded:", orders);
      this.formatDate();
    });
    this.getAllUsers();
    this.cartData = this.cartDataService.getCartData();
    this.cartSubtotal = +Number(this.cartDataService.getCartSubtotal()).toFixed(2);
  }

  formatDate() : void {
    const dateObj = new Date(this.orders[0].createdDate!);
    const monthAbbreviation = dateObj.toLocaleString('en-US', { month: 'short' });
    const day = dateObj.getDate();
    const year = dateObj.getFullYear();
    this.formattedDate = `${monthAbbreviation}. ${day}, ${year}`;
  }

  public getAllOrders(): Observable<Order[]> {
    return this.orderService.getAllOrders().pipe(
      tap((response: Order[]) => {
        this.orders = response;
        console.log("orders: =>", response);
        console.log("order creation time: => ", this.orders[0].createdDate!);
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        throw error;
      })
    );
  }

  public getAllCarts(): void {
    this.cartService.getAllCarts().subscribe(
      (response: Cart[]) => {
        this.carts = response;
        console.log("carts: =>", response)
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
        console.log("users: =>", response)
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
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
    this.router.navigate(['/home']);
  }
}
