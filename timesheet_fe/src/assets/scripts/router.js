import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "./stores/authStore";
import HomePage from "@/components/pages/HomePage.vue";
import LoginPage from "@/components/pages/LoginPage.vue";
import SigninPage from "@/components/pages/SigninPage.vue";
import NotFoundPage from "@/components/pages/NotFoundPage.vue";

const authGuard = async (to, from, next) => {
  const authStore = useAuthStore();
  const isValidAuth = await authStore.checkAuth();

  if (to.meta.requiresAuth && !isValidAuth) {
    next({ name: "LoginPage" });
  } else {
    next();
  }
};

const routes = [
  { path: "/", name: "HomePage", component: HomePage, meta: { requiresAuth: true } },
  { path: "/login", name: "LoginPage", component: LoginPage },
  { path: "/signin", name: "SigninPage", component: SigninPage },
  { path: "/notFoundPage", name: "NotFoundPage", component: NotFoundPage },
  { path: "/:pathMatch(.*)*", redirect: "/notFoundPage" },
];

const router = createRouter({
  history: createWebHistory(),
  linkExactActiveClass: "active",
  routes,
});

router.beforeEach(authGuard);

export { router };
