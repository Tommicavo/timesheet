<script>
import LoginBtn from "@/components/buttons/LoginBtn.vue";

export default {
  name: "LoginPage",
  data() {
    return {
      loginForm: { email: "", password: "Password1!" },
      errors: { email: [], password: [] },
    };
  },
  components: {
    LoginBtn,
  },
  props: {},
  computed: {},
  methods: {
    initLogin() {
      this.errors = { email: [], password: [] };
      this.$genericStore.isLoading = true;
    },
    async login() {
      const endpoint = "/auth/login";
      try {
        this.initLogin();
        const res = await this.$axios.post(endpoint, this.loginForm);
        this.$authStore.storeAuthData(res.data);
        this.$router.push({ name: "HomePage" });
      } catch (err) {
        const data = err.response.data;
        this.errors = this.$authStore.setLoginErrors(data);
        console.log("Validation errors: ", this.errors);
      } finally {
        this.$genericStore.isLoading = false;
      }
    },
  },
};
</script>

<template>
  <div class="loginPage h-100 d-flex flex-column justify-content-evenly">
    <!-- Top Page -->
    <div class="loginPageTop">
      <!-- Title -->
      <h3 class="text-center py-3">Login Page</h3>

      <!-- Login Form -->
      <form method="POST" @keyup.enter="login()">
        <div class="formFieldFlexDouble">
          <!-- Email -->
          <div class="email mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="text" class="form-control" id="email" v-model="loginForm.email" />
            <div class="emailErrors">
              <ul>
                <li class="errorMessage" v-for="(error, i) in errors.email" :key="i">
                  {{ error }}
                </li>
              </ul>
            </div>
          </div>
          <!-- Password -->
          <div class="password mb-3">
            <label for="password" class="form-label">Password</label>
            <input
              type="password"
              class="form-control"
              id="password"
              v-model="loginForm.password"
            />
            <div class="passwordErrors">
              <ul>
                <li class="errorMessage" v-for="(error, i) in errors.password" :key="i">
                  {{ error }}
                </li>
              </ul>
            </div>
          </div>
        </div>
      </form>
    </div>

    <!-- Bottom Page -->
    <div class="loginPageBottom">
      <div class="d-flex justify-content-center my-3">
        <LoginBtn @loginEmit="login()" />
      </div>
      <div class="my-3">
        <span>Not Registered yet?</span>
        <span class="mx-2">
          <router-link class="btn btn-primary" :to="{ name: 'SigninPage' }">
            Signin Here!
          </router-link>
        </span>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.email,
.password {
  position: relative;
}

.emailErrors,
.passwordErrors {
  position: absolute;
  top: 4.5rem;
}
</style>
