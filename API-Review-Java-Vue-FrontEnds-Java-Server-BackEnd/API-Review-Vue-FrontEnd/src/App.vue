<template>
  <div class="container">  <!-- main and ONLY div for the Vue application  -->
    <home-page />  <!-- Use a Vue component called Home-Page                                            -->
    <router-view :key="$route.fullPath" class="routerView" /> <!-- Using Vue router                     -->
    <!-- :key="$route.fullpath" forces Vue to reload the component instead reusing on each reference    -->
    <!--                       to be sure Vue goes thru it's lifecycle so any lifecycle hooks execute   -->
    <!--                       if you have a created() hook, it will only be run once if this not coded -->
    <!--                          to be sure the created() hook runs everytime the page is referenced   -->
    <!--                          code :key="$route.fullpath"                                           -->
  </div>
</template>

<script>
import HomePage from '@/components/HomePage';

export default {
  components: {
   HomePage
  }
};
</script>

<style>
body {
  font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto,
    'Helvetica Neue', Arial, 'Noto Sans', sans-serif, 'Apple Color Emoji',
    'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji';
}
.container {
  width: 100%;
  max-width: 1100px;
  margin: 0 auto;
}
.loading {
  display: flex;
  justify-content: center;
  align-items: center;
}
.routerView {
  width: 79%;
  position: absolute;
  right: 0;
}
.btn {
  display: inline-block;
  font-weight: 400;
  color: #212529;
  text-align: center;
  vertical-align: middle;
  border: 1px solid transparent;
  padding: .375rem .75rem;
  margin-right: 10px;
  margin-bottom: 5px;
  font-size: 1rem;
  line-height: 1.5;
  border-radius: .25rem;
  cursor: pointer;
}
</style>
