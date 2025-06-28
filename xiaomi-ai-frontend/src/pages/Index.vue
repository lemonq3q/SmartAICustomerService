<template>
  <div class="index_body">
    <div class="index_header">
      <Header></Header>
    </div>
    <div class="index_container">
      <div class="index_menu">
        <Menu></Menu>
      </div>
      <div class="index_main">
        <router-view></router-view>
      </div>
    </div>
  </div>

</template>

<script>
import Menu from '../components/Menu.vue';
import Header from '../components/Header.vue';

export default {
  name: 'Index',
  components: {
    Menu,Header
  }
}
</script>

<style>
@import '@/style/pages/index.css';
</style>
