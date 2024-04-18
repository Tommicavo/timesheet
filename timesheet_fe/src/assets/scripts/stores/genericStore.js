import { defineStore } from "pinia";

export const useGenericStore = defineStore({
  id: "generic",
  state: () => ({
    isLoading: false,
    isAlertActive: false,
    alert: { status: "", title: "", message: "" },
  }),

  actions: {
    setAlert(status, title, message) {
      this.isAlertActive = true;
      this.alert = { status: status, title: title, message: message };
    },
    dismissAlert() {
      this.isAlertActive = false;
      this.alert = { status: "", title: "", message: "" };
    },
  },
});
