export async function searchByAuthor(author) {
  return await fetch(
    '/api/search/author?author=' + author).then(
    (response) => response.json())
}

export async function searchByAuthorWithPagination(author, startIndex) {
  return await fetch(
    '/api/search/authorWithPagination?author=' + author + '&startIndex='
    + startIndex)
  .then((response) => response.json())
}
