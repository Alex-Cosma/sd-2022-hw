<template>
  <sign-up-form
      :display="showSignUpForm"
      @close="showSignUpForm = false"
      @successfully="signIn"
  ></sign-up-form>
  <sign-in-form
      :display="showSignInForm"
      @close="showSignInForm = false"
      @successfully="signIn"
  ></sign-in-form>
  <div id="main-bar" class="centered-flex-container-row blur-container left-aligned">
    <SiteLogo></SiteLogo>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-btn class="mx-1" style="background-color: var(--primary); color: white"
           @click="showSignInForm = true"
    >Sign-in</v-btn>
    <v-btn class="mx-1 mr-6"
           style="background-color: var(--secondary); color: white"
           @click="showSignUpForm = true"
    >Sign-up</v-btn>
  </div>
  <div id="hero">
  </div>
  <display-currencies></display-currencies>
  <main-footer></main-footer>
</template>

<script>
import MainFooter from "@/components/MainFooter.vue";
import DisplayCurrencies from "@/components/DisplayCurrencies.vue";
import SiteLogo from "@/components/SiteLogo.vue";
import SignUpForm from "@/components/SignUpForm.vue";
import SignInForm from "@/components/SignInform.vue";
import AuthService from "@/service/AuthService";
export default {

  name: "PublicPage",
  components: {SignInForm, SignUpForm, SiteLogo, DisplayCurrencies, MainFooter},
  data() {
    return {
      collapseOnScroll: true,
      showSignUpForm: false,
      showSignInForm: false,
    }
  },

  methods: {
    signIn(signInForm) {
      AuthService.signIn(signInForm)
          .then(response => {
            console.log(response)
            const eligibleRoute = this.$router
                .getRoutes()
                .find(route => route.meta?.roles?.find(role => response.roles.map(r => r.toUpperCase())
                        .includes(role.toUpperCase())
                    )
                );
            this.$router.push(eligibleRoute);
          })
          .finally(() => {
            this.showSignInForm = false
          })
    }
  }

}
</script>

<style scoped>
#main-bar {
  width: 100vw;
  height: var(--bar-height);
  background-image: var(--main-gradient-opacity);
  background-size: 600%;
  animation: main-bar-animation var(--animation-time) infinite alternate;
  border: none;
  box-shadow: var(--primary-shadow);
  position: fixed;
  z-index: 100;
}

#hero {
  background-image: var(--hero-background-url);
  width: 100vw;
  height: var(--hero-height);
  background-size: cover;
  background-position: center;
}

@keyframes main-bar-animation {
  0% {
    background-position: left;
  }

  100% {
    background-position: right;
  }
}

</style>