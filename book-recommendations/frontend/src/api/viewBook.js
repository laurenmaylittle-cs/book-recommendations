export async function getBookInfo (isbn) {
  return await fetch('api/book?isbn='+ isbn).then((response) => response.json())
}


