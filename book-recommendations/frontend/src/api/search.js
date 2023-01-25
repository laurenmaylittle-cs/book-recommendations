import axios from "axios";

export async function searchByAuthor(author, startIndex) {
  return axios.get(
    `/api/search/author?author=${author}&startIndex=${startIndex}`)
  .then(response => response.data)
}

export async function searchByTitle(title) {
  return await fetch(
    '/api/search/title?title=' + title).then(
    (response) => response.json())
}
