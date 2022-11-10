export async function getBookInfo (id) {
  return await fetch('http://localhost:8080/api/book?id='+ id).then((response) => response.json())
}

