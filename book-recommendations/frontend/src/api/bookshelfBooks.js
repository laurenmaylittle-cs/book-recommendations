import axios from "axios"

/**
 * get the books belonging to a collection
 * @param collectionId The ID of the requested collection
 * @param {string} token Access token to the API
 */
export async function getBooksInCollection(collectionId, token) {
  const result = await axios.get(`/api/private/bookshelf/books?bookshelfId=${collectionId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return result.data;
}

export async function deleteBooksInCollection(deleteBooksParams, token) {
  const result = await axios.delete(
    `/api/private/bookshelf/books/delete`,
    {
      data: deleteBooksParams,
      headers: {Authorization: `Bearer ${token}`}
    }
  );
  return result.data;
}

export async function updateCollectionName(collectionId, token, newName) {
  await axios({
    method: 'PUT',
    url: `/api/private/bookshelf/singleBookshelf?bookshelfId=${collectionId}&newCollectionName=${newName}`,
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}
