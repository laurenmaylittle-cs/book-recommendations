export async function searchByAuthor(author) {
  return await fetch(
    '/api/search/author?author=' + author).then(
    (response) => response.json())
}

export async function searchByTitle(title) {
  return await fetch(
    '/api/search/title?title=' + title).then(
    (response) => response.json())
}
