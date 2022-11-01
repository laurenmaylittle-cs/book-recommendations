import axios from "axios";

export default function getBestSellers() {
  return axios.get("/api/home/best-sellers");
};
