export async function searchByAuthor(author, startIndex) {
  return await fetch(
    '/api/search/author?author=' + author + '&startIndex=' + startIndex).then(
    (response) => response.json())
}
