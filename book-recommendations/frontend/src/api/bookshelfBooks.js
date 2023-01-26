import axios from "axios"

/**
 * get the books belonging to a collection
 * @param collectionId The ID of the requested collection
 * @param {string} token Access token to the API
 */
export async function getBooksInCollection(collectionId, token) {
  const result = await axios.get("/api/private/bookshelf/singleBookshelf", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return result.data;
}
