import axios from "axios";

export async function getRecs(isbn) {
  return await axios.get(
    '/api/public/book/get-recommendations?isbn=' + isbn)
  .then(response => {
    return response.data;
  })
}

export async function exportData(bookData, token) {
  return await axios.post("/api/private/book", bookData, {
    headers: {
      authorization: `Bearer ${token}`,
    }
  })
}

export async function isAwsEnabled(token) {
  return await axios.get(
    '/api/private/book/is-aws-enabled', {
      headers: {
        authorization: `Bearer ${token}`,
      }
    })
    .then(response => {
      return response.data;
    })
}
