import axios from "axios";

export async function getBookInfo(isbn) {
  return await axios('/api/public/book?isbn=' + isbn)
  .then(response => {
    return response.data;
  });
}

export async function getUserRating(email, isbn) {
  return await axios(
    '/api/private/user-rating?isbn=' + isbn + '&email=' + email)
  .then(response => {
    return response.data
  })
}
