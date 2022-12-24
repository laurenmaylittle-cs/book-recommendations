import axios from "axios";

export async function getUsersSearch(email, token) {
  return axios.get("/api/private/users?email=" + email, {
    headers: {
      authorization: `Bearer ${token}`,
    }
  })
  .then(response => {
    return response.data;
  });
}
