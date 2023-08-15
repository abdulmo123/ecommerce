import { Product } from "./product.model";

export interface Cart {
    id: number;
    name: string;
    currentPrice: number;
    cartProducts: Product[];
}
