<script>

export default {
  name: 'LoginBtn',
  data() {
    return {}
  },
  components: {},
  props: {},
  computed: {},
  methods: {},
  emits: ['loginEmit']
}
</script>

<template>
  <button type="button" class="btn btn-primary" @click="$emit('loginEmit')">Login</button>
</template>

<style lang="scss" scoped>

</style>