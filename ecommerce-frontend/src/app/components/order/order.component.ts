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

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent {
  users: User[] = [];
  carts: Cart[] = [];
  orders: Order[] = [];
  constructor(public orderService: OrderService, private cartService: CartService, private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.getAllCarts();
    this.getAllOrders();
    this.getAllUsers();
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

  goToHome() {
    this.router.navigate(['/home'])
  }

  backToCart() {
    this.router.navigate(['/cart'])
  }

  // getSubtotal() {
  //   let subtotal = new CartComponent(this.cartService, this.router);

  //   return subtotal.getSubtotal();
  // }
}
