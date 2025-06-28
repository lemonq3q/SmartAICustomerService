<template>
  <div class="header">
    <div class="left">
      <!-- 下拉菜单 -->
      <el-dropdown @command="handleCommand">
        <i class="menu_icon el-icon-s-fold"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item icon="el-icon-plus">刷新数据</el-dropdown-item>
          <el-dropdown-item icon="el-icon-circle-plus" command="refresh">退出登陆</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <span class="index_tip"><span style="color: white;">{{ root }}</span>&thinsp;&thinsp;&thinsp;&thinsp;
      {{path==''?'':'/'}}&thinsp;&thinsp;&thinsp;&thinsp;<span  style="color: #49586e;">{{ path==''?'':path }}</span></span>
    </div>
    <div class="right">
      <!-- <img class="header_img" src="http://123.57.91.218/images/g2622.jpg" alt="">
      <span class="header_username">{{ username }}</span> -->
      <span class="header_date" style="width: 200px; color: white;">{{ date }}</span>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import {logout} from '../api/login';

export default {
  name: "Header",
  computed: {
    ...mapState([
      'path','userheadshot','username','root'
    ]),
  },

  data: function() {
    return {
      date: '',
      headshot: 'http://'+this.$ip+':80/images/g2622.jpg'
    }
  },

  mounted() {
    setInterval(this.getCurrentDate,1000);
  },

  methods: {
    ...mapMutations([
      'setLogin'
    ]),

    //生成时间
    getCurrentDate(){
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1);
      const day = String(now.getDate());
      const hours = String(now.getHours()).slice(-2);
      const minutes = String(now.getMinutes()).slice(-2);
      const seconds = String(now.getSeconds()).slice(-2);
      this.date =  `${year}/${month}/${day} ${hours}:${minutes}:${seconds}`;
    },

    handleCommand(command){
      if(command=="refresh"){
        this.handleExit();
      }
    },

    handleExit() {
      logout()
      .then(res=>{
        this.setLogin(false);
        this.$router.push('/login');
      })
      .catch(err=>{
        console.log(err);
        this.$message.error('网络连接错误');
      })
    }
  }
}

</script>

<style>
@import '@/style/components/header.css';
</style>
