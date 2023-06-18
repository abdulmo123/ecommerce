import { Category } from "./category.model";

export interface Product {
    id: number,
    name: string,
    description: string,
    price: number,
    picture: string,
    category: Category
}
