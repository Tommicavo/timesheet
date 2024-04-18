<script>
import AppHeader from "@/components/AppHeader.vue";

export default {
  name: "App",
  data() {
    return {};
  },
  components: {
    AppHeader,
  },
  mounted() {
    this.$authStore.loadState();
  },
};
</script>

<template>
  <AppHeader />
  <AppAlert :alert="$genericStore.alert" />
  <AppLoader />
  <main>
    <div class="container h-100">
      <router-view></router-view>
    </div>
  </main>
</template>

<style lang="scss">
@use "./assets/scss/style.scss" as *;
@use "@/assets/scss/partials/vars.scss" as *;

main {
  height: calc(100vh - $headerHeight);
  background-color: #414a4c;
  color: white;
}
</style>
