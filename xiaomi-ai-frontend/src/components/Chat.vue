<template>
  <div>
    聊天
  </div>
</template>

<script>
import { mapMutations } from 'vuex';

export default {
  name: 'Chat',
  mounted(){
    this.updateRoot('首页');
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
@import '@/style/components/chat.css';
</style>
