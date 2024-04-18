<script>
export default {
  name: "AppAlert",
  data() {
    return {};
  },
  components: {},
  props: { alert: Object },
  computed: {},
  methods: {
    dismissAlert() {
      this.$genericStore.dismissAlert();
    },
  },
};
</script>

<template>
  <div v-if="$utilStore.isLoading"><AppLoader /></div>
  <div v-else>
    <div
      v-if="$genericStore.isAlertActive"
      class="alertContainer d-flex justify-content-center align-items-center"
    >
      <div :class="`myAlert alert alert-${alert.status}`" role="alert">
        <div class="title">{{ alert.title }}</div>
        <div class="message">{{ alert.message }}</div>
        <button type="button" class="closeAlertBtn btn btn-close" @click="dismissAlert()"></button>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.myAlert {
  position: absolute;
  top: 1rem;
  left: 50%;
  translate: -50% 0;
  width: 50%;
  text-align: center;
}

.title {
  font-size: 1rem;
  font-weight: 600;
  padding-bottom: 0.5rem;
}

.closeAlertBtn {
  position: absolute;
  top: 1rem;
  right: 1rem;
}
</style>
