import axios from "axios";

export async function searchByAuthor(author, startIndex) {
  return axios.get(
    `/api/search/author?author=${author}&startIndex=${startIndex}`)
  .then(response => response.data)
}

export async function searchByTitle(title, startIndex) {
  return axios.get(
    `/api/search/title?title=${title}&startIndex=${startIndex}`)
    .then(response => response.data)
}

export async function searchByIsbn(isbn) {
  return axios.get(
    `/api/search/isbn?isbn=${isbn}`)
    .then(response => response.data)
}

