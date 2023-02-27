import axios from "axios";

export async function getRecs(isbn) {
  return await axios.get(
    '/api/public/book/get-recs?isbn=' + isbn)
  .then(response => {
    return response.data;
  })
}

export async function exportData(bookData, token) {
  return await axios.post("/api/private/book", bookData, {
    headers: {
      authorization: `Bearer ${token}`,
    }
  });
}

export async function addUser(userData, token) {
  return await axios.post("/api/private/book", userData, {
    headers: {
      authorization: `Bearer ${token}`,
    }
  });
}



