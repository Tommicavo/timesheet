<script>
export default {
  name: "Logout",
  data() {
    return {};
  },
  components: {},
  props: {},
  computed: {},
  methods: {
    async logout() {
      try {
        this.$genericStore.isLoading = true;
        await this.$axios.delete("/auth/logout");
        this.$authStore.clearAuthData();
        this.$router.push({ name: "LoginPage" });
      } catch (error) {
        console.error("Logout error:", error.message);
      } finally {
        this.$genericStore.isLoading = false;
      }
    },
  },
};
</script>

<template>
  <router-link class="myDropdownItem dropdown-item" to="#" @click="logout()">Logout</router-link>
</template>

<style lang="scss" scoped></style>
