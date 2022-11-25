export async function searchByAuthor(author) {
  return await fetch(
    '/api/search/author?author=' + author).then(
    (response) => response.json())
}
