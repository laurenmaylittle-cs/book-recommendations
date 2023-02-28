import axios from "axios";

export async function getRecs(isbn) {
  return await axios.get(
    '/api/public/book/get-recommendations?isbn=' + isbn)
  .then(response => {
    return response.data;
  })
}
