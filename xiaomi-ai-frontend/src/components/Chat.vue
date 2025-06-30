<template>
  <div class="chat_body">
    <div class="session_body">
      <div class="restart_button no_select" @click="handleRestart">
        <img class="restart_button_icon" src="../assets/new_session.png" alt="">
        <div>开启新对话</div>
      </div>
      <div class="session_history_title">
        <img class="restart_button_icon" src="../assets/history.png" alt="">
        <div>历史会话</div>
      </div>
      <div class="session_history_body">
        <div class="session_item no_select" @click="handleSessionClick(item)" v-for="item in sessions" :key="item.id">
          {{ item.sessionName }}
          <img class="delete_button" src="../assets/delete.png" alt="" @click="deleteSession(item)">
        </div>
      </div>
    </div>
    <div class="conversation_body">


      <div class="content-wrapper">
        <div class="inner_chat_body" v-for="(item,i) in historyDialogue">
          <div v-if="item.role==1" style="width: 1rem; "></div>
          <div class="user_ask_body" v-if="item.role==1">
            {{ item.content }}
          </div>
          <div v-if="item.role==0" style="display: flex;">
            <img src="../assets/chat.png" v-if="item.role==0" class="chat_logo">
            <div class="service_response_body" v-if="item.role==0" >
              {{ item.content }} 
            </div>
          </div>
        </div>

        <div class="user_ask_position">
          <div class="new_user_ask_area">
            <textarea class="ask_area" v-model="currentMessage" placeholder="提出你的疑问"></textarea>
            <div class="ask_area_button_container">
              <div>
                <el-select v-model="selectModel" placeholder="选择模型">
                  <el-option
                    size="mini"
                    v-for="item in models"
                    :key="item.id"
                    :label="item.modelName"
                    :value="item.modelName">
                  </el-option>
                </el-select>
                <el-select v-model="selectStore" placeholder="选择知识库">
                  <el-option
                    size="mini"
                    v-for="item in stores"
                    :key="item.id"
                    :label="item.storeName"
                    :value="item.storeName">
                  </el-option>
                </el-select>
              </div>

              <div class="chat_send_button no_select" @click="handleSend" >发送</div>

            </div>
          </div>
        </div>
      </div>

      


    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex';
import {selectSession, createSession, deleteSession} from '../api/session';
import {getUserByUsername} from '../api/login';
import { getAllModel } from '@/api/model';
import { getStoreByUserId } from '@/api/store';
import { selectConversation } from '@/api/conversation';

export default {
  name: 'Chat',
  mounted(){
    this.updateRoot('首页');
    this.updatePath('');
    getUserByUsername(localStorage.getItem("username")).then(res=>{
      if(res.data.code == 200){
        this.updateUserId(res.data.data.id);
        this.selectSessionByUserId();
  
        getAllModel().then(res=>{ 
          if(res.data.code == 200){
            this.models = res.data.data;
            this.selectModel = this.models[0].modelName;
          }
        }).catch(err=>{
          console.log(err);
        });
        getStoreByUserId(this.userId).then(res=>{ 
          if(res.data.code == 200){
            this.stores = res.data.data;
            this.stores.push({
              id: 0,
              storeName: "不使用知识库"
            });
            console.log(this.store);
          }
        }).catch(err=>{
          console.log(err);
        });

      }
    }).catch(err=>{ 
      console.log(err);
    });
    
  },

  computed:{
    userId(){
      return this.$store.state.userId;
    },
    username(){
      return this.$store.state.username;
    }
  },

  data(){
    return{
      sendlock: false,
      sessions: [],
      nowSessionId: -1,
      models: [],

      stores: [
        {
          id: 0,
          storeName: "不使用知识库"
        }
      ],
      currentMessage: '',
      selectModel: '',
      selectStore: '不使用知识库',

      historyDialogue: []
    }
  },

  methods:{
    ...mapMutations([
      'updatePath','updateRoot','updateUserId'
    ]),

    handleRestart(){
      this.nowSessionId = -1;
      this.historyDialogue = [];
    },

    selectSessionByUserId(){ 
      selectSession(this.userId).then(res => { 
        if(res.data.code == 200){
          this.sessions = res.data.data;
          this.sessions.sort((a, b) => b.createTime - a.createTime);
        }
      })
      .catch(err => { 
        console.log(err);
      })
    },

    handleSessionClick(item){
      this.nowSessionId = item.id;
      selectConversation(item.id).then(res => {
        if(res.data.code == 200){
          this.historyDialogue = res.data.data;
          console.log(res.data.data);
        } 
      }).catch(err=>{
        console.log(err);
      })
    },

    handleSend(){
      if(this.sendlock){
        return;
      }
      if(this.currentMessage == null || this.currentMessage == ''){
        return;
      }
      this.sendlock = true;
      this.historyDialogue.push({
        role: 1,
        content: this.currentMessage
      });
      this.historyDialogue.push({
        role: 0,
        content: ''
      });
      if(this.nowSessionId == -1){
        var session = {
          userId: this.userId,
          sessionName: this.currentMessage
        }
        createSession(session).then(res => { 
          if(res.data.code == 200){
            this.nowSessionId = res.data.data.id;
            var requestData = {
            userId: this.userId,
            requestContent: this.currentMessage,
            modelName: this.selectModel,
            sessionId: this.nowSessionId,
            storeName: this.selectStore
            };
            this.selectSessionByUserId();
            this.sendQuestion(requestData);
          }
        }).catch(err => { 
          console.log(err)
        })
      }
      else{
        var requestData = {
          userId: this.userId,
          requestContent: this.currentMessage,
          modelName: this.selectModel,
          sessionId: this.nowSessionId,
          storeName: this.selectStore
        };
        this.sendQuestion(requestData);
      }
    },

    sendQuestion(requestData){
      this.currentMessage = '';
      (async ()=>{
        const response = await fetch('http://localhost:8080/chat', {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        });

        if (!response.body) return;
        const reader = response.body.pipeThrough(new TextDecoderStream()).getReader();
        while (true) {
          var { value, done } = await reader.read();
          if (done) {
            this.sendlock = false;
            break;
          }
          this.historyDialogue[this.historyDialogue.length-1].content += value?.replace('undefined', '')
          if(this.item != ""){
            this.item = "";
          }
          else{
            this.item = ".";
          }
        }
      })();
    },

    deleteSession(session){
      deleteSession(session.id,session.userId).then(res => { 
        if(res.data.code == 200){
          this.selectSessionByUserId();
          if(session.id == this.nowSessionId){
            this.handleRestart();
          }
        }
      }).catch(err => { 
        console.log(err);
      });
    }
  

  }
}
</script>

<style>
@import '@/style/components/chat.css';
</style>
