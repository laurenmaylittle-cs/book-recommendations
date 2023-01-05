import axios from "axios";

export async function getBookInfo(isbn) {
  const url = "/api/public/book";
  const params = new URLSearchParams();
  params.append("isbn", isbn);
  const result = await axios.get(url, {params: params});
  return result.data;
}
