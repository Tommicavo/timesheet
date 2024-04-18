import { createPinia } from "pinia";

import { useAuthStore } from "@/assets/scripts/stores/authStore";
import { useUtilStore } from "@/assets/scripts/stores/utilStore";
import { useGenericStore } from "@/assets/scripts/stores/genericStore";

const pinia = createPinia();

export const piniaPlugin = {
  install: (app) => {
    app.use(pinia);
    app.config.globalProperties.$authStore = useAuthStore();
    app.config.globalProperties.$utilStore = useUtilStore();
    app.config.globalProperties.$genericStore = useGenericStore();
  },
};
