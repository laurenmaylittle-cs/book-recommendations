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

export async function followUser(currentUserEmail, userToFollow, token) {
  return await axios({
    method: 'POST',
    url: '/api/private/users?currentUser=' + currentUserEmail + '&userToFollow='
      + userToFollow,
    headers: {
      authorization: `Bearer ${token}`,
    }
  })
}
