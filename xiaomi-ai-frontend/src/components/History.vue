<template>
  <div>
    历史记录
  </div>
</template>

<script>
import { mapMutations } from 'vuex';

export default {
  name: 'Store',
  mounted(){
    this.updateRoot('历史记录');
    this.updatePath('');
  },

  methods:{
    ...mapMutations([
      'updatePath','updateRoot'
    ]),
  }
}
</script>

<style>
@import '@/style/components/history.css';
</style>
