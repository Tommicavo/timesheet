// Vue
import { createApp } from "vue";
import App from "./App.vue";
import axios from "./assets/scripts/axios";

// Pinia
import { piniaPlugin } from "./pinia-plugin";

// Vue Router
import { router } from "./assets/scripts/router";

// bootstrap
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";

// Generics Components
import AppLoader from "@/components/generics/AppLoader.vue";
import AppAlert from "@/components/generics/AppAlert.vue";

// FontAwesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
// FontAwesome Components
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { faHouse } from "@fortawesome/free-solid-svg-icons";

// Including fontawesome icon
library.add(faUser, faHouse);

// App
const app = createApp(App);

app.config.globalProperties.$axios = axios;

app.use(piniaPlugin);
app.use(router);

app.component("AppLoader", AppLoader);
app.component("AppAlert", AppAlert);
app.component("font-awesome-icon", FontAwesomeIcon);

app.mount("#app");
