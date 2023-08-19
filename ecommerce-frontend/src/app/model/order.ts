import { Cart } from "./cart.model";

export interface Order {
    totalPrice: number,
    createdDate: Date,
    cart: Cart
}
