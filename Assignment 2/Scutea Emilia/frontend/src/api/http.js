import axios from "axios";

export const API_PATH = "http://localhost:8088/api";
export const BOOKS = "/admin/books";
export const BOOKSTORE = "/bookstore";
export const CART = "/cart";
export const REVIEWS = "/reviews";
export const HTTP = axios.create({
  baseURL: API_PATH,
});

export default function authHeader() {
  let user = JSON.parse(localStorage.getItem("user"));
  if (user && user.token) {
    return { Authorization: "Bearer " + user.token };
  } else {
    return {};
  }
}
