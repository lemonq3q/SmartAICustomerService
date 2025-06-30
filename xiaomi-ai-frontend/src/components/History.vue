<template>
  <div class="store_body">
    
    <div class="knowledge_container">
      <div class="knowledge_top_container" style="margin-bottom: 0.1rem;">
        <div class="knowledge_add_button no_select" @click="handleSearch">
          <img class="knowledge_add_icon" src="../assets/search.png" alt="">
          搜索
        </div>
        <div class="history_select_row">
          <div class="history_select_label">对话角色：</div>
          <el-radio-group v-model="role">
            <el-radio :label="2">全部</el-radio>
            <el-radio :label="1">用户</el-radio>
            <el-radio :label="0">智能助手</el-radio>
          </el-radio-group>
        </div>
        <div class="history_select_row">
          <div class="history_select_label">会话ID：</div>
          <div style="width: 5rem; height: 100%;">
            <el-input v-model="sessionId" placeholder="输入相关内容" style="vertical-align: top !important;"></el-input>
          </div>
        </div>
      </div>
      <div class="knowledge_top_container" style="margin-bottom: 0.4rem; padding-left: 1.64rem;">
        <div class="history_select_row">
          <div class="history_select_label">时间段：</div>
          <el-date-picker
            v-model="time"
            type="datetimerange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="['12:00:00']">
          </el-date-picker>
        </div>
        <div class="history_select_row">
          <div class="history_select_label">相关内容：</div>
          <div style="width: 5rem; height: 100%;">
            <el-input v-model="relativeContent" placeholder="输入相关内容" style="vertical-align: top !important;"></el-input>
          </div>
        </div>
      </div>
      <el-table
        :data="currentData"
        stripe
        border
        style="width: 100%">
        <el-table-column
          prop="sessionId"
          label="会话ID"
          width="80">
        </el-table-column>
        <el-table-column
          prop="time"
          label="时间"
          width="180">
          <template slot-scope="scope">
            {{ handleDate(scope.row.time) }}
          </template>
        </el-table-column>
        <el-table-column
          prop="content"
          label="内容">
        </el-table-column>
        <el-table-column
          prop="role"
          label="角色"
          width="110"
          align="center">
          <template slot-scope="scope">
            <el-tag
            :type="scope.row.role === 0 ? 'warning' : 'success'"
            disable-transitions>{{ scope.row.role==0?"智能客服":"用户" }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <div style="height: 0.5rem;"></div>
      <el-pagination
        background
        :page-size=pageSize
        :current-page.sync="currentPage"
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :total="total">
      </el-pagination>
    </div>



  </div>
</template>

<script>
import { mapMutations } from 'vuex';
import { getStoreByUserId} from '../api/store';
import {transTimestamp} from '../utils/date';
import { selectConversationByUserId } from '../api/conversation';

export default {
  name: 'History',

  computed:{
    userId(){
      return this.$store.state.userId;
    },
  },
  mounted(){
    this.updateRoot('历史记录');
    this.updatePath('');
    this.serachAll();
  },

  data(){
    return {
      role: 2,
      store: {},
      conversations: [],
      currentData: [],
      currentPage: 1,
      total: 0,
      dialogFormVisible: false,
      content: '',
      time: [],
      pageSize: 7,
      relativeContent: '',
      sessionId: ''
    };
  },


  methods:{
    ...mapMutations([
      'updatePath','updateRoot'
    ]),

    handleCurrentChange(item){
      this.currentPage = item;
      this.currentData = this.conversations.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
    },

    handleDate(timestamp){
      return transTimestamp(timestamp);
    },

    reset(){
      selectKnowledgeByStore(this.store.storeName).then(res => {
        if(res.data.code == 200){
          this.knowledges = res.data.data;
          this.total = this.knowledges.length;
        } 
      }).catch(err=>{
        console.log(err);
      });
    },

    search(conversation, start, end){
      selectConversationByUserId(conversation,start,end).then(res=>{ 
        if(res.data.code == 200){
          this.conversations = res.data.data;
          this.total = this.conversations.length;
          this.currentPage = 1;
          this.currentData = this.conversations.slice(0,this.pageSize);
        }
      }).catch(err=>{
        console.log(err);
      });
    },

    serachAll(){
      var conversation = {
        userId: localStorage.getItem("userId")
      };
      this.search(conversation, 0, Date.now());
    },

    handleSearch(){
      var start;
      var end;
      if(this.time == null || this.time.length == 0){
        start = 0;
        end = Date.now();
      }
      else{
        start = this.time[0].getTime();
        end = this.time[1].getTime();
      }
      var conversation = {
        userId: localStorage.getItem("userId"),
        content: this.relativeContent,
        sessionId: null,
        role: null
      }
      if(this.sessionId != null || this.sessionId != ""){
        conversation.sessionId = this.sessionId;
      }
      if(this.role != 2){
        conversation.role = this.role;
      }
      this.search(conversation, start, end); 
    }

  }
}
</script>

<style>
@import '@/style/components/store.css';
@import '@/style/components/history.css';
</style>
