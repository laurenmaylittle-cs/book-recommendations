import axios from "axios";

export async function getBookInfo(isbn, title, authors) {
  const url = "/api/public/book";
  const params = new URLSearchParams();
  params.append("isbn", isbn);
  params.append("title", title);
  params.append("authors", authors);

  return (await axios.get(url, {params: params})).data;
}

export async function getUserRating(email, isbn, token) {
  return await axios({
    method: 'GET',
    url: '/api/private/user-rating?isbn=' + isbn + '&email=' + email,
    headers: {
      authorization: `Bearer ${token}`,
    }
  })
  .then(response => {
    return response.data
  })
}

export async function saveUserRating(email, isbn, rating, token) {
  return await axios({
    method: 'POST',
    url: '/api/private/user-rating?isbn=' + isbn + '&email=' + email
      + '&rating=' + rating,
    headers: {
      authorization: `Bearer ${token}`,
    }
  })
}

export async function updateUserRating(email, isbn, rating, token) {
  return await axios({
    method: 'PUT',
    url: '/api/private/update-user-rating?isbn=' + isbn + '&email=' + email
      + '&rating=' + rating,
    headers: {
      authorization: `Bearer ${token}`,
    }
  })
}

export async function getUserCollections(token, isbn) {
  const url = "/api/private/bookshelf/collections/book";
  const params = new URLSearchParams();
  params.append("isbn", isbn);

  return (await axios.get(url, {
    params: params,
    headers: {
      authorization: `Bearer ${token}`,
    }
  })).data;
}

export async function saveUserCollections(token, collectionsAndBook) {
  const url = "/api/private/bookshelf/collections/book/update"

  return (await axios.put(url, collectionsAndBook, {
    headers: {
      authorization: `Bearer ${token}`,
    }
  })).data;
}

export async function exportData(bookData, token) {
  await axios.post("/api/private/book-data", bookData, {
    headers: {
      authorization: `Bearer ${token}`,
    }
  })
}
