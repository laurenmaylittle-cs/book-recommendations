import axios from "axios";

export async function searchByAuthor(author, startIndex) {
  return axios.get(
    `/api/search/author?author=${author}&startIndex=${startIndex}`)
  .then(response => response.data)
}
