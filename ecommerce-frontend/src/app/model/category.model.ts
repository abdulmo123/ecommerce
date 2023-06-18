import { Product } from "./product.model";

export interface Category {
    id: number,
    name: string,
    description: string,
    picture: string,
    products: Product[]
}
