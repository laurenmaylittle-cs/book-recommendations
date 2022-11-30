import axios from "axios";

export async function getBookInfo(isbn) {
  return await axios('/api/public/book?isbn=' + isbn)
    .then(response => {
      return response.data;
    });
}
