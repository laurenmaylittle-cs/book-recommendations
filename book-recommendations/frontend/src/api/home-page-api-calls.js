import axios from "axios";

export async function getBestSellers() {
   return axios.get("/api/home/best-sellers")
      .then(response => {
          return response.data;
      });
}
