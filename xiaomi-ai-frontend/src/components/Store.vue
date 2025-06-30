<template>
  <div class="store_body">
    <div class="knowledge_top_container">
      <div class="knowledge_add_button no_select" @click="handleStoreAdd">
        <img class="knowledge_add_icon" src="../assets/add.png" alt="">
        新增
      </div>
      <div class="knowledge_input_body">
        <div class="knowledge_input_contianer">
          <input class="knowledge_input" type="text" placeholder="请输入知识库名称" v-model="storeName">
        </div>
      </div>
    </div>
    <div class="store_card_container">
      <el-card class="box-card" v-for="item in stores" :key=item.id>
        <div slot="header" class="clearfix">
          <span>{{ item.storeName }}</span>
          <div>
            <el-button class="store_card_button" type="text" @click="handleView(item)">查看</el-button>
            <el-button class="store_card_button" style="color: red;" type="text" @click="handleStoreDelete(item)">删除</el-button>
          </div>
        </div>
        <div class="text_item">
          <div class="knowledge_item_row">
            <div>知识条数：</div>
            <div class="knowledge_item_content">{{ item.total }}</div>
          </div>
          <div class="knowledge_item_row">
            <div>类型：</div>
            <div class="knowledge_item_content">{{ item.type==1?"用户知识库":"会话知识库" }}</div>
          </div>
          <div class="knowledge_item_row">
            <div>创建时间：</div>
            <div class="knowledge_item_content">{{ handleDate(item.createTime) }}</div>
          </div>
        </div>
      </el-card>
    </div>


    



    <el-dialog :title="diaologTitle" :visible.sync="dialogFormVisible">
      <div class="knowledge_container">
        <div class="knowledge_top_container">
          <div class="knowledge_add_button no_select" @click="handleAddOperation">
            <img class="knowledge_add_icon" src="../assets/add.png" alt="">
            新增
          </div>
          <div class="knowledge_input_body">
            <div class="knowledge_input_contianer">
              <input class="knowledge_input" type="text" placeholder="请输入待添加的知识" v-model="content">
            </div>
          </div>
        </div>
        <el-table
          :data="displayKnowledges"
          stripe
          border
          style="width: 100%">
          <el-table-column
            prop="createTime"
            label="添加时间"
            width="180">
            <template slot-scope="scope">
              {{ handleDate(scope.row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column
            prop="content"
            label="内容">
          </el-table-column>
          <el-table-column
            label="操作"
            width="100"
            align="center">
            <template slot-scope="scope">
              <img @click="handleDelete(scope.row)" style="width: 0.3rem; height: 0.3rem;" src="../assets/cancel.png" alt="">
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
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleDiaologExit" style="background-color: #409EFF;">返 回</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { mapMutations } from 'vuex';
import { getStoreByUserId, addStore, deleteStore} from '../api/store';
import {selectKnowledgeByStore, deleteKnowledge, addKnowledge} from '../api/knowledge'; 
import {transTimestamp} from '../utils/date';

export default {
  name: 'Store',

  computed:{
    userId(){
      return this.$store.state.userId;
    },
  },
  mounted(){
    this.updateRoot('知识库');
    this.updatePath('');
    this.resetStore();

  },

  data(){
    return {
      store: {},
      stores: [],
      knowledges: [],
      displayKnowledges: [],
      currentPage: 1,
      total: 0,
      dialogFormVisible: false,
      content: '',
      diaologTitle: '',
      pageSize: 4,
      storeName: ''
    };
  },


  methods:{
    ...mapMutations([
      'updatePath','updateRoot'
    ]),

    resetStore(){
      getStoreByUserId(localStorage.getItem("userId")).then(res => {
      if(res.data.code == 200){
        this.stores = res.data.data;
        this.reset();
      }
    });
    },

    handleCurrentChange(item){
      this.currentPage = item;
      this.displayKnowledges = this.knowledges.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
    },

    handleDate(timestamp){
      return transTimestamp(timestamp);
    },

    reset(){
      selectKnowledgeByStore(this.store.storeName).then(res => {
        if(res.data.code == 200){
          this.knowledges = res.data.data;
          this.total = this.knowledges.length;
          this.currentPage = 1;
          this.displayKnowledges = this.knowledges.slice(0,this.pageSize);
        } 
      }).catch(err=>{
        console.log(err);
      });
    },

    handleDelete(item){
      deleteKnowledge(item.id, item.storeName).then(res => {
        if(res.data.code == 200){
          this.reset();
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    handleAdd(content){
      addKnowledge(this.store, content).then(res => { 
        if(res.data.code == 200){
          this.reset();
        }
      }).catch(err => { 
        console.log(err)
      })
    },

    handleAddOperation(){
      if(this.content == null || this.content == ""){
        return;
      }
      this.handleAdd(this.content);
    },

    handleView(item){
      this.dialogFormVisible = true;
      this.diaologTitle = item.storeName;
      this.store = item;
      this.reset();
    },

    handleDiaologExit(){
      this.dialogFormVisible = false;
    },

    handleStoreAdd(){
      if(this.storeName == null || this.storeName == ""){
        return;
      }
      var store = {
        storeName: this.storeName,
        userId: localStorage.getItem("userId"),
        total: 0,
        type: 1,
        createTime: Date.now()
      }
      addStore(store).then(res => { 
        if(res.data.code == 200){
          this.resetStore();
        }
      }).catch(err=>{
        console.log(err);
      })
    },
    handleStoreDelete(item){
      deleteStore(item.id, item.storeName, localStorage.getItem("userId")).then(res=>{ 
        if(res.data.code == 200){
          this.resetStore();
        }
      }).catch(err=>{ 
        console.log(err);
      });
    }

  }
}
</script>

<style>
@import '@/style/components/store.css';
</style>
