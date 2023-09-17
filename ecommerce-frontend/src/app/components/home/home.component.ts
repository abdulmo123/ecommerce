import { Component } from '@angular/core';
import { Product } from 'src/app/model/product.model';
import { ProductService } from '../../service/product.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Cart } from 'src/app/model/cart.model';
import { CartService } from 'src/app/service/cart.service';
import { Router } from '@angular/router';
import { OrderService } from 'src/app/service/order.service';
import { Order } from 'src/app/model/order';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  products: Product[] = [];
  carts: Cart[] = [];
  cart: Cart | undefined;
  order: Order | undefined;
  productByCatArr: Product[] = [];
  private currentCartId: number | undefined;
  currentCartSize: number | undefined = 0;

  constructor (private orderService: OrderService, private productService: ProductService, private cartService: CartService, private router: Router) {}
  ngOnInit() {
    this.getAllProducts();
    this.getAllCarts();
  }

  public getAllProducts(): void {
    this.productService.getAllProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
        console.log(response);
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
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getCartById(cartId: number): void {
    this.cartService.getCartById(this.cart?.id!).subscribe(
      (response: Cart) => {
        this.cart = response;
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  logout() {
    localStorage.removeItem('userId');
    const cartId = localStorage.getItem('cartId');
    const orderId = localStorage.getItem('orderId');
    if (orderId !== null) {
      this.orderService.deleteOrder(+orderId!).subscribe(
        (response: Order) => {
          this.order = response;
          console.log(this.order);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      )
    }
    if(cartId !== null) {
      this.cartService.deleteCart(+cartId!).subscribe(
        (response: Cart) => {
          this.cart = response;
          console.log(this.cart);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      )
    }

    localStorage.removeItem('cartId');
    localStorage.removeItem('orderId');
    console.log("logout successful!")
    this.router.navigate(['/login']);
  }

  public getAllProductsByCategory(categoryId: number): void {
    this.productService.getAllProductsByCategoryId(categoryId).subscribe(
      (response: Product[]) => {
        this.products = response.filter(product => product.category && product.category.id === categoryId);
        console.log(response);
        this.products = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public searchProducts(key: string) : void {
    const results: Product[] = [];
    for (const product of this.products) {
      if (product.name.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results?.push(product);
      }
    }

    this.products = results;
    if (results.length === 0 || !key) {
      this.getAllProducts();
    }
  }


  public onCartAdd(productId: number) : void {
    const userId = localStorage.getItem('userId');

    if(userId) {
      if (localStorage.getItem('cartId') === null) {
        this.cartService.createCart(+userId).subscribe(
          (newCart: Cart) => {
            this.cart = newCart;
            localStorage.setItem('cartId', JSON.stringify(this.cart.id!))
            console.log("new cart created!" + newCart);
            this.addProductToCart(productId);
            console.log("product added to new cart!");
          },
          (error: HttpErrorResponse) => {
            alert(error.message);
          }
        );
      }
      else {
        this.addProductToCart(productId);
        console.log('product added to exisiting cart!');
      }
    }
    else {
      console.log("user is not logged in or user id not available!");
    }
  }

  private addProductToCart(productId: number): void {
    var newInt = localStorage.getItem('cartId')

    this.currentCartId = +newInt!;
    this.cartService.addProductToCart(this.currentCartId!, productId).subscribe(
      () => {
        console.log("product id is : " + productId);
        console.log("producted added to cart!");
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )

    this.currentCartSize = this.carts[0].cartProducts!.length;
    console.log("current cart size => ", this.currentCartSize);
    this.getAllCarts();
  }

  goToCart() {
    this.router.navigate(['/cart'])
  }
}
