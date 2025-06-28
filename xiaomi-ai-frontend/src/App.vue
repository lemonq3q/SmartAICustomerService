<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>


export default {
  name: 'App',
  mounted(){
    this.autoSize();
    window.addEventListener("resize", ()=>{
      this.autoSize();
    })
  },

  methods:{
    autoSize(){
      var psdWidth = 1920;
      var preFontSize = 96;
      var curScreenWidth = document.documentElement.clientWidth;
      if(curScreenWidth > 1700) {
        curScreenWidth = 1700;
      }
      else if(curScreenWidth < 750){
        curScreenWidth = 750
      }
      var curFontSize = (curScreenWidth*preFontSize) / psdWidth;
      document.documentElement.style.fontSize = curFontSize + "px";
    },
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

* {
  box-sizing: border-box;
}

body {
  margin: 0;
}

a {
  text-decoration: none; 
  color: inherit;
  font-weight: inherit; 
  font-style: inherit;  
}

.no_select {
  user-select: none; /* 设置文本不可选中 */
  -webkit-user-select: none; /* 兼容Safari */
  -moz-user-select: none; /* 兼容Firefox */
  -ms-user-select: none; /* 兼容IE10+ */
}

input {
  /* 移除边框和轮廓 */
  border: none;
  outline: none;
  
  /* 移除背景色 */
  background: none;
  
  /* 重置内边距和外边距 */
  padding: 0;
  margin: 0;
  
  /* 清除浏览器特定样式 */
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  
  /* 可选：统一字体和尺寸 */
  font-family: inherit;
  font-size: 16px;
  box-sizing: border-box;
}

</style>
