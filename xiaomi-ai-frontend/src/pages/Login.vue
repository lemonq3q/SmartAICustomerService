<template>
  <div class="login_body">
    <div class="login_container">
      <div v-if="loginType=='login'">
        <div class="login_title">使用用户名密码登录</div>
        <div class="login_column">
          <img src="../assets/username.png" alt="" class="login_icon">
          <input class="login_input" type="text" placeholder="请输入用户名" v-model="username">
        </div>
        <div class="login_column">
          <img src="../assets/password.png" alt="" class="login_icon">
          <input class="login_input" type="password" placeholder="请输入密码" v-model="password">
        </div>
        <div class="login_button no_select" @click="handleLogin">登  录</div>
        <div class="login_nav">
          <div class="login_nav_item no_select" @click="transType('register')">注册</div>
          <div class="login_nav_item no_select" @click="transType('password')">忘记密码</div>
        </div>
      </div>


      <div v-if="loginType=='register'">
        <div class="login_title">注册新用户</div>
        <div class="login_column">
          <img class="login_icon" src="../assets/phone.png" alt="">
          <input type="text" placeholder="手机号码" v-model="phone">
        </div>
        <div class="login_column">
          <img class="login_icon" src="../assets/username.png" alt="">
          <input type="text" placeholder="用户名" v-model="username">
        </div>
        <div class="login_column">
          <img class="login_icon" src="../assets/password.png" alt="">
          <input type="password" placeholder="密码" v-model="password">
        </div>
        <div class="login_column">
          <img class="login_icon" src="../assets/password.png" alt="">
          <input type="password" placeholder="再次确认密码" v-model="confirmPassword">
        </div>
        <div class="login_button no_select" @click="handleRegister">注 册</div>
        <div class="login_nav">
          <div class="login_nav_item no_select" @click="transType('login')">返回登录</div>
          <div class="login_nav_item no_select" @click="transType('password')">忘记密码</div>
        </div>
      </div>


    </div>

    

  </div>
</template>

<script>
import {login, register, getUserByPhone, updateUserPassword, testLogin} from '../api/login'

export default {
  name: 'Login',
  
  mounted() {
    this.testLogin();
  },

  data(){
    return{
      loginType: 'login',
      username: '',
      password: '',
      phone: '',
      confirmPassword: '',
      registerLock: true,
      updateLock: true, 
      loginLock: true
    }
  },

  methods:{

    reset(){
      this.username = '';
      this.password = '';
      this.phone = '';
      this.confirmPassword = '';
    },
    testLogin(){
      testLogin().then(res => {
        if(res.data.code == 200){
          this.$router.push('/index/chat')
        }
      }).catch(err => {
        console.log(err)
      });
    },

    handleLogin(){
      if(this.username == '' || this.password == ''){
        this.$message({
          type: 'warning',
          message: '请输入用户名和密码'
        })
        return;
      }
      if(!this.loginLock){
        return;
      }
      this.loginLock = false;
      login(this.username, this.password).then(res => { 
        if(res.data.code == 0){
          this.$router.push('/index/chat');
        }
        else{
          this.$message.error("账号或密码错误");
        }
        this.loginLock = true;
      }).catch(err => { 
        this.$message.error("网络连接错误");
        this.loginLock = true;
      });
    },

    handleRegister(){
      console.log(this.phone);
      if(this.phone == '' || this.username == '' || this.password == ''){
        this.$message({
          type: 'warning',
          message: '注册信息不全'
        })
        return;
      }
      if(this.password != this.confirmPassword){
        this.$message({
          type: 'warning',
          message: '两次填写的密码不一致'
        })
        return;
      }
      var user = {
        phone: this.phone,
        username: this.username,
        password: this.password,
      }
      register(user).then(res => { 
        if(res.data.code == 200){
          this.$message.success('注册成功');
          this.reset();
          this.loginType = "login";
        }
      }).catch(err => { 
        this.$message.error('网络连接出错');
      });
    },
    transType(type){
      this.loginType = type;
    }
  }
}
</script>

<style>
@import '@/style/pages/login.css';
</style>
