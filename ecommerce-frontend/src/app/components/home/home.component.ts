import { Component } from '@angular/core';
import { Product } from 'src/app/model/product.model';
import { ProductService } from '../../service/product.service';
import { HttpErrorResponse } from '@angular/common/http';
import { keyframes } from '@angular/animations';

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
}
