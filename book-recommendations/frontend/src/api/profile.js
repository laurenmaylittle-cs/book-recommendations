import axios from "axios";

export async function getFollowersAndFollowing(email, token) {
  return axios.get(
    "/api/private/users/followers-following-details?currentUser=" + email, {
      headers: {
        authorization: `Bearer ${token}`,
      }
    })
  .then(response => {
    return response.data;
  });
}
