<template>
  <div class="menu_body">
    <el-menu
      default-active=1
      class="el-menu-vertical-demo">
      <router-link to="/index/chat">
        <el-menu-item index=1>
            <i class="el-icon-menu"></i>
            <span slot="title">智能客服</span>
        </el-menu-item>
      </router-link>

      <router-link to="/index/chat">
        <el-menu-item index=2>
            <i class="el-icon-menu"></i>
            <span slot="title">知识库管理</span>
        </el-menu-item>
      </router-link>

      <router-link to="/index/chat">
        <el-menu-item index=3>
            <i class="el-icon-menu"></i>
            <span slot="title">历史对话</span>
        </el-menu-item>
      </router-link>

      <router-link to="/index/chat">
        <el-menu-item index=4>
            <i class="el-icon-menu"></i>
            <span slot="title">用户中心</span>
        </el-menu-item>
      </router-link>
      
    </el-menu>

    
  </div>
</template>

<script>
export default {
  name: 'Menu',
  comments: {

  }
}

</script>

<style>
@import '@/style/components/menu.css'

</style>

