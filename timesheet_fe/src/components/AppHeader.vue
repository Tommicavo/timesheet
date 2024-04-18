<script>
import LogoutBtn from "@/components/buttons/LogoutBtn.vue";

export default {
  name: "AppHeader",
  data() {
    return {};
  },
  components: {
    LogoutBtn,
  },
  props: {},
  computed: {},
  methods: {},
};
</script>

<template>
  <header class="px-3">
    <div class="headerLeft d-flex">
      <div>
        <router-link class="navLink" :to="{ name: 'HomePage' }">
          <font-awesome-icon icon="fa-solid fa-house" />
        </router-link>
      </div>
    </div>
    <div class="headerRight">
      <div class="dropdown">
        <button
          class="myUserBtn btn dropdown-toggle"
          type="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          <font-awesome-icon icon="fa-solid fa-user" />
        </button>
        <ul class="myDropdownMenu dropdown-menu">
          <li v-if="!$authStore.isLoggedIn">
            <router-link class="myDropdownItem dropdown-item" :to="{ name: 'LoginPage' }">
              Login
            </router-link>
          </li>
          <li v-if="!$authStore.isLoggedIn">
            <router-link class="myDropdownItem dropdown-item" :to="{ name: 'SigninPage' }">
              Signin
            </router-link>
          </li>
          <li v-if="$authStore.isLoggedIn">
            <LogoutBtn />
          </li>
        </ul>
      </div>
    </div>
  </header>
</template>

<style lang="scss" scoped>
@use "@/assets/scss/partials/vars.scss" as *;
@use "@/assets/scss/partials/colors.scss" as *;

header {
  height: $headerHeight;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: $headerBackgroundColor;
}

.navLink {
  text-decoration: none;
  color: white;
  font-size: 1.5rem;
  font-weight: 600;
  cursor: pointer;
  &:hover {
    color: gold;
  }
}

.btn.myUserBtn {
  color: $headerColor;
  font-size: 1.5rem;
  &:hover {
    color: gold;
  }
  &::after {
    display: none;
  }
}

.myDropdownMenu {
  color: $headerColor;
  background-color: $headerBackgroundColor;
  border: 1px solid $headerColor;
}

.dropdown-item {
  text-align: center;
  font-size: 1rem;
  font-weight: 600;
  color: $headerColor;
  background-color: $headerBackgroundColor;
}

.myDropdownItem,
.dropdown-item.active,
.dropdown-item:active {
  font-size: 1rem;
  font-weight: 600;
  color: $headerColor;
  background-color: $headerBackgroundColor;
}

.myDropdownItem.dropdown-item:hover {
  font-size: 1rem;
  font-weight: 600;
  color: gold;
  background-color: $headerBackgroundColor;
}
</style>
