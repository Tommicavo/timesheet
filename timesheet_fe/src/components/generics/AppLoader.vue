<script>
export default {
  name: "AppLoader",
  data() {
    return {};
  },
  components: {},
  props: {},
  computed: {},
  methods: {},
};
</script>

<template>
  <div v-if="$genericStore.isLoading" class="appLoader">
    <div class="spinner-border text-success" role="status"></div>
  </div>
</template>

<style lang="scss" scoped>
.appLoader {
  position: fixed;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 3;
  background-color: rgba(0.5, 0.5, 0.5, 0.8);
}

.spinner-border {
  width: 250px;
  height: 250px;
}
</style>
