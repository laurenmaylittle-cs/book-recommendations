import axios from "axios";

export async function getBookInfo(isbn) {
  const url = "/api/public/book";
  const params = new URLSearchParams();
  params.append("isbn", isbn);

  try {
    const result = await axios.get(url, {params: params});
    return result.data;
  } catch (error) {
    return null;
  }
}
