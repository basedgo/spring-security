import { createAuth0Client } from './lib/auth0-spa-js.js';

let auth0Client;

async function initAuth0() {
  try {
    auth0Client = await createAuth0Client({
      domain: 'dev-iamssa85wl4jrzqy.us.auth0.com',
      clientId: 'lP9XqtVpTjWebnkhJwj3olEicDGaouJ9',
      authorizationParams: {
        redirect_uri: window.location.origin + "/home.html",
        audience: 'https://spring-security-api',
      }
    });
        if (window.location.search.includes('code=') && window.location.search.includes('state=')) {
            await handleRedirectCallback();
        }

        await updateUI();
    } catch (err) {
    }
}

async function handleRedirectCallback() {
  try {
    await auth0Client.handleRedirectCallback();
    window.history.replaceState({}, document.title, window.location.pathname);
  } catch (err) {
  }
}

async function login() {
  try {
    await auth0Client.loginWithRedirect();
  } catch (err) {
  }
}

async function logout() {
  try {
    await auth0Client.logout({
      logoutParams: {
        returnTo: window.location.origin
      }
    });
  } catch (err) {
  }
}

const loginBtn = document.getElementById("login");
const logoutBtn = document.getElementById("logout")
loginBtn.addEventListener('click', login);
logoutBtn.addEventListener('click', logout);

initAuth0();