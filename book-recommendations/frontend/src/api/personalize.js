import axios from "axios";

export async function getRecs(isbn) {
  return await axios('/api/public/book/get-recs?isbn=' + isbn)
    .then(response => {
      return response.data;
    })
}
