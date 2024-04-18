<script>
import SigninBtn from "@/components/buttons/SigninBtn.vue";

export default {
  name: "SigninPage",
  data() {
    return {
      signinForm: { firstName: "", lastName: "", email: "", password: "", birthDate: null },
      errors: {
        firstName: [],
        lastName: [],
        email: [],
        password: [],
        birthDate: [],
      },
    };
  },
  components: {
    SigninBtn,
  },
  props: {},
  computed: {},
  methods: {
    initSignin() {
      this.errors = { firstName: [], lastName: [], email: [], password: [], birthDate: [] };
      this.$genericStore.isLoading = true;
    },
    async signin() {
      const endpoint = "/auth/signin";
      try {
        this.initSignin();
        const res = await this.$axios.post(endpoint, this.signinForm);
        console.log("Response Data: ", res.data);
        this.$router.push({ name: "LoginPage" });
      } catch (err) {
        const data = err.response.data;
        this.errors = this.$authStore.setSigninErrors(data);
        console.log("Validation errors: ", this.errors);
      } finally {
        this.$genericStore.isLoading = false;
      }
    },
  },
};
</script>

<template>
  <div class="signinPage h-100 d-flex flex-column justify-content-evenly">
    <!-- Top Page -->
    <div class="signinPageTop">
      <!-- Title -->
      <h3 class="text-center py-3">Signin Page</h3>

      <!-- Signin Form -->
      <form method="POST" @keyup.enter="signin()">
        <div class="formFieldFlexDouble pb-5">
          <!-- FirstName -->
          <div class="firstName mb-3">
            <label for="firstName" class="form-label">First Name</label>
            <input type="text" class="form-control" id="firstName" v-model="signinForm.firstName" />
            <div class="firstNameErrors">
              <ul>
                <li class="errorMessage" v-for="(error, i) in errors.email" :key="i">
                  {{ error }}
                </li>
              </ul>
            </div>
          </div>
          <!-- LastName -->
          <div class="lastName mb-3">
            <label for="lastName" class="form-label">Last Name</label>
            <input type="text" class="form-control" id="lastName" v-model="signinForm.lastName" />
            <div class="lastNameErrors">
              <ul>
                <li class="errorMessage" v-for="(error, i) in errors.email" :key="i">
                  {{ error }}
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="formFieldFlexDouble pb-5">
          <!-- Email -->
          <div class="email mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="text" class="form-control" id="email" v-model="signinForm.email" />
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
              v-model="signinForm.password"
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
        <div class="formFieldFlexSingle pb-5">
          <!-- BirthDate -->
          <div class="birthDate mb-3">
            <label for="birthDate" class="form-label">Birth Date</label>
            <input type="date" class="form-control" id="birthDate" v-model="signinForm.birthDate" />
            <div class="birthDateErrors">
              <ul>
                <li class="errorMessage" v-for="(error, i) in errors.birthDate" :key="i">
                  {{ error }}
                </li>
              </ul>
            </div>
          </div>
        </div>
      </form>
    </div>
    <div class="signinPageBottom">
      <div class="d-flex justify-content-center my-3">
        <SigninBtn @signinEmit="signin()" />
      </div>
      <div class="my-3">
        <span>Already Registered?</span>
        <span class="mx-2">
          <router-link class="btn btn-primary" :to="{ name: 'LoginPage' }">Login Here!</router-link>
        </span>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.firstName,
.lastName,
.email,
.password,
.birthDate {
  position: relative;
}

.firstNameErrors,
.lastNameErrors,
.emailErrors,
.passwordErrors,
.birthDateErrors {
  position: absolute;
  top: 4.5rem;
}
</style>
