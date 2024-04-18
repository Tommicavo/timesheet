<script>
export default {
  name: "",
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
  <div v-if="$utilStore.isLoading"><AppLoader /></div>
  <div v-else>
    <h3 class="text-center py-3">Title Page</h3>
  </div>
</template>

<style lang="scss" scoped></style>
