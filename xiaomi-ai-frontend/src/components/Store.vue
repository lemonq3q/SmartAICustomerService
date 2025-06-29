<template>
  <div>
    知识库
  </div>
</template>

<script>
import { mapMutations } from 'vuex';

export default {
  name: 'Store',
  mounted(){
    this.updateRoot('知识库');
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
@import '@/style/components/store.css';
</style>
