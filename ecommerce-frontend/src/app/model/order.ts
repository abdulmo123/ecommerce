import { Cart } from "./cart.model";

export interface Order {
    id: number,
    name: string,
    totalPrice: number,
    createdDate: Date,
    cart: Cart
}
