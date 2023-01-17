import axios from "axios";

/**
 * getting collections that belong to the user
 * @param token - access token to the api
 */
export async function getCollectionsForUser(token) {
  const result = await axios.get("/api/private/bookshelf/collections", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return result.data;
}

/**
 * for creating a new collection for the user
 * @param collectionName - the name of the collection
 * @param {string} token - access token to the api
 */
export async function createNewCollection(collectionNameURLSearchParam, token) {
  if (!(collectionNameURLSearchParam instanceof URLSearchParams)) {
    throw new Error(
      "collectionNameURLSearchParam must be a type of URLSearchParams");
  }

  const createResult = await axios.post("/api/private/bookshelf/collections",
    collectionNameURLSearchParam, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

  return createResult;

}

/**
 * @param pathForAccessingCollection - the path for accessing the collection
 * @param {string} token - access token to the api
 */
export async function getCollection(pathForAccessingCollection, token) {
  const result = await axios.get(pathForAccessingCollection, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return result.data;
}
