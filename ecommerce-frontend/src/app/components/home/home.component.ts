import { Component } from '@angular/core';
import { Product } from 'src/app/model/product.model';
import { ProductService } from '../../service/product.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  products: Product[] = [];
  productByCatArr: Product[] = [];

  constructor (private productService: ProductService) {}
  ngOnInit() {
    this.getAllProducts();
  }

  public getAllProducts(): void {
    this.productService.getAllProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
        this.productByCatArr = response;
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
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

  // filterProductsByCategory(categoryId: number): void {
  //   // console.log('cliekd!');
  //   if (this.products) {
  //     console.log(this.products);
  //     this.productByCatArr = this.products.filter(product => product.category && product.category.id === categoryId);
  //   }
  // }
}
