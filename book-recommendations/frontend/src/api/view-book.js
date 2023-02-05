import axios from "axios";

export async function getBookInfo(isbn) {
  return await axios('/api/public/book?isbn=' + isbn)
  .then(response => {
    return response.data;
  });
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

export async function exportData(isbn, title, author, genre, publisher, token) {
  return await axios({
    method: 'POST',
    url: '/api/public/book?isbn=' + isbn + '&title=' + title
      + '&author=' + author + '&genre=' + genre +'&publisher=' + publisher,
    headers: {
      authorization: `Bearer ${token}`,
    },
    data: {
      isbn: isbn,
      title: title,
      author: author,
      genre: genre,
      publisher: publisher
    }
  })
}
