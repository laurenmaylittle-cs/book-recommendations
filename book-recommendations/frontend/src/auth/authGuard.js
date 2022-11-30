import {getInstance} from './index';

/**
 * JS class that verifies a user is authenticated before hitting an endpoint.
 * If the user is authenticated, access is granted.
 * If unauthenticated, the user will be redirected to the main Auth0 login page and when logged in the user will be
 * redirected to the page where access was requested.
 * Should be used with the beforeEnter directive within a router when defining a view.
 * https://github.com/auth0/auth0-vue/blob/main/tutorial/vue2-login.md#secure-the-profile-page
 */

export const authGuard = (to, from, next) => {
  const authService = getInstance();

  const fn = () => {
    // If the user is authenticated, continue with the route
    if (authService.isAuthenticated) {
      return next();
    }

    // Otherwise, log in
    authService.loginWithRedirect({ appState: { targetUrl: to.fullPath } });
  };

  // If loading has already finished, check our auth state using `fn()`
  if (!authService.loading) {
    return fn();
  }

  // Watch for the loading property to change before we check isAuthenticated
  authService.$watch('loading', loading => {
    if (loading === false) {
      return fn();
    }
  });
};
