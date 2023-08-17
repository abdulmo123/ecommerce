import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Cart } from 'src/app/model/cart.model';
import { CartService } from 'src/app/service/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  carts: Cart[] = [];
  private currentCartId: number | undefined;
  constructor(public cartService: CartService, private router: Router) {}
  
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

  getSubtotal() {
    let sum = 0;
    for (let i = 0; i < this.carts[0].cartProducts?.length; i++) {
        sum = sum + this.carts[0].cartProducts[i].price;
    }

    return sum;
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
}
