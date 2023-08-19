import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Order } from 'src/app/model/order';
import { OrderService } from 'src/app/service/order.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent {
  orders: Order[] = [];
  constructor(public orderService: OrderService, private router: Router) {}

  ngOnInit() {
    this.getAllOrders();
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

  goToHome() {
    this.router.navigate(['/home'])
  }

  backToCart() {
    this.router.navigate(['/cart'])
  }
}
