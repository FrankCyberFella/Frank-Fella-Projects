<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/squirrel-hotel-2.jpg" />
    <home-page />
  </div>
</template>

<script>
               // @ is an alias to /src
import HomePage from "@/components/HomePage.vue";

export default {
  name: "HomePageView",
  components: {
    HomePage
  }
};
</script>
