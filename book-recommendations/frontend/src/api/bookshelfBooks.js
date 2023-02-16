import axios from "axios"

/**
 * get the books belonging to a collection
 * @param collectionId The ID of the requested collection
 * @param {string} token Access token to the API
 */
export async function getBooksInCollection(collectionId, token) {
  const result = await axios.get(`/api/private/bookshelf/singleBookshelf?bookshelfId=${collectionId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return result.data;
}

export async function deleteBooksInCollection(deleteBooksParams, token) {
  await axios.post(`/api/private/bookshelf/singleBookshelf/delete`,
    deleteBooksParams, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}
