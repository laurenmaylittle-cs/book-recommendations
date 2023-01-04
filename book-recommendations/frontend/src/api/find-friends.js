import axios from "axios";

export async function getUsersSearch(name, token) {
  return axios.get("/api/private/users?name=" + name, {
    headers: {
      authorization: `Bearer ${token}`,
    }
  })
  .then(response => {
    return response.data;
  });
}
