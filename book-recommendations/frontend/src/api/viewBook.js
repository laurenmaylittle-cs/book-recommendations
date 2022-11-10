export async function getBookInfo (id) {
  return await fetch('api/book?id='+ id).then((response) => response.json())
}

