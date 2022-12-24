package com.bestreads.bookrecommendations.auth0;

import java.io.Serializable;
import java.util.List;

record Auth0Users(String created_at,
                  String email,
                  Boolean email_verified,
                  String family_name,
                  String given_name,
                  List<Identity> identities,
                  String locale,
                  String name,
                  String nickname,
                  String picture,
                  String updated_at,
                  String user_id,
                  String last_login,
                  String last_ip,
                  String logins_count) implements Serializable {

}
