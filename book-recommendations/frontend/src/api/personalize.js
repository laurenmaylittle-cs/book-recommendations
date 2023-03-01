import axios from "axios";

export async function getRecs(isbn) {
  return await axios.get(
    '/api/public/book/recommendations?isbn=' + isbn)
  .then(response => {
    return response.data;
  })
}

export async function exportData(bookData, token) {
  return await axios.post("/api/private/book/add-book", bookData, {
    headers: {
      authorization: `Bearer ${token}`,
    }
  })
}

export async function isAwsEnabled() {
  return await axios.get('/api/public/book/personalise-status')
  .then(response => {
    return response.data;
  })
}
