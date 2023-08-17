import { Component } from '@angular/core';
import { Product } from 'src/app/model/product.model';
import { ProductService } from '../../service/product.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Cart } from 'src/app/model/cart.model';
import { CartService } from 'src/app/service/cart.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  products: Product[] = [];
  carts: Cart[] = [];
  cart: Cart | undefined;
  productByCatArr: Product[] = [];
  private currentCartId: number | undefined;
  

  constructor (private productService: ProductService, private cartService: CartService, private router: Router) {}
  ngOnInit() {
    this.getAllProducts();
    this.getAllCarts();
  }

  public getAllProducts(): void {
    this.productService.getAllProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
        // this.productByCatArr = response;
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

    // this.carts = this.cart?.cartProducts!;
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
    // check if cart id doesn't exist (indicates that cart doesn't exist)
    // if (!this.cart?.id || this.cart?.cartProducts.length === 0) 
    if (localStorage.getItem('cartId') === null) {
      // create a new cart
      this.cartService.createCart().subscribe(
        (newCart: Cart) => {
          // set the new cart to the cart 
          this.cartService.setCartId(JSON.stringify(this.cart?.id));
          console.log(this.cartService.getCartId());
          this.cart = newCart;
          localStorage.setItem('cartId', JSON.stringify(this.cart.id!))
          console.log("new cart created!" + newCart);
          // add the product to the new cart
          this.addProductToCart(productId);
          console.log("product added to new cart!");
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    else {
      // add product to existing cart
      this.addProductToCart(productId);
      console.log('product added to exisiting cart!');
    }
  }

  private addProductToCart(productId: number): void {
    // adds the product to the cart
    var newInt = localStorage.getItem('cartId')
    
    this.currentCartId = +newInt!;
    this.cartService.addProductToCart(this.currentCartId!, productId).subscribe(
      () => {
        console.log("product id is : " + productId);
        console.log("producted added to cart!");
        // this.getCartById(this.cart?.id!);
        // this.carts.push(this.cart?.cartProducts())
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )

    // this.carts.push(this.cart?.cartProducts)
    console.log("all the carts ===> " + this.carts);
    // this.carts.push(this.cart.cartProducts);
    console.log("cart array: ===> " + this.carts.length);
    // console.log("products in cart:" + this.cart?.cartProducts?.length);
    this.getAllCarts();
  }

  goToCart() {
    this.router.navigate(['/cart'])
  }
}
