import { Product } from "./product.model";
import { User } from "./user.model";

export interface Cart {
    id: number;
    name: string;
    currentPrice: number;
    cartProducts: Product[];
    user: User;
}
