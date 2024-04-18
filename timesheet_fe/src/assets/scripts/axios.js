import axios from "axios";
import { useAuthStore } from "@/assets/scripts/stores/authStore";
import { useGenericStore } from "@/assets/scripts/stores/genericStore";
import { router } from "./router";

const instance = axios.create({
  baseURL: "http://localhost:9090",
});

instance.interceptors.request.use(
  (config) => {
    const authStore = useAuthStore();
    const token = authStore.accessToken;
    if (!config.headers.Authorization && token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

instance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      const authStore = useAuthStore();
      const genricStore = useGenericStore();
      authStore.clearAuthData();
      router.push({ name: "LoginPage" });
      genricStore.setAlert(
        "warning",
        "Session Expired",
        "Your token has expired. Login again to continue your journey on Timesheet App!"
      );
    }
    return Promise.reject(error);
  }
);

export default instance;
