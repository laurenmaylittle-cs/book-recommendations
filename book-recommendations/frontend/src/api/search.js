export async function searchByAuthor(author) {
  return await fetch(
    'http://localhost:8080/api/search/author?author=' + author).then(
    (response) => response.json())
}
