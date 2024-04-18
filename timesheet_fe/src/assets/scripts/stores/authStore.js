import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

export const useAuthStore = defineStore({
  id: "auth",
  state: () => ({
    isLoggedIn: false,
    accessToken: null,
    userId: null,
    userRole: null,
    loginErrors: { email: [], password: [] },
    signinErrors: { firstName: [], lastName: [], email: [], password: [], birthDate: [] },
  }),

  actions: {
    storeAuthData(token) {
      this.isLoggedIn = true;
      this.accessToken = token;
      this.extractUserDataFromToken(token);
      this.saveState();
    },

    clearAuthData() {
      this.isLoggedIn = false;
      this.accessToken = null;
      this.userId = null;
      this.userRole = null;
      this.saveState();
    },

    loadState() {
      const storedState = localStorage.getItem("authStore");
      if (storedState) {
        this.$state = JSON.parse(storedState);
        this.checkAuth();
      }
    },

    saveState() {
      localStorage.setItem("authStore", JSON.stringify(this.$state));
    },

    extractUserDataFromToken(token) {
      try {
        const decodedToken = jwtDecode(token);
        this.userId = decodedToken.sub;
        this.userRole = decodedToken.role;
      } catch (error) {
        console.error("Error extracting user data from token:", error);
        this.clearAuthData();
      }
    },

    async checkAuth() {
      const storedState = localStorage.getItem("authStore");
      if (storedState) {
        const { accessToken, isLoggedIn } = JSON.parse(storedState);
        if (accessToken && accessToken !== "null" && isLoggedIn) {
          this.isLoggedIn = true;
          this.accessToken = accessToken;
          return true;
        } else {
          this.clearAuthData();
          return false;
        }
      } else {
        this.clearAuthData();
        return false;
      }
    },

    setLoginErrors(errors) {
      this.loginErrors = { email: [], password: [] };
      errors.forEach((error) => {
        const label = error.label;
        const message = error.message;
        this.loginErrors[label].push(message);
      });
      return this.loginErrors;
    },

    setSigninErrors(errors) {
      this.signinErrors = { firstName: [], lastName: [], email: [], password: [], birthDate: [] };
      errors.forEach((error) => {
        const label = error.label;
        const message = error.message;
        this.signinErrors[label].push(message);
      });
      return this.signinErrors;
    },
  },
});
