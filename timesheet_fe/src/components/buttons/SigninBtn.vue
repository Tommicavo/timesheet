<script>
export default {
  name: "Signin",
  data() {
    return {};
  },
  components: {},
  props: {},
  computed: {},
  methods: {},
  emits: ["signinEmit"],
};
</script>

<template>
  <button type="button" class="btn btn-primary" @click="$emit('signinEmit')">Signin</button>
</template>

<style lang="scss" scoped></style>
