import VueRouter from 'vue-router';
import Vue from 'vue';
import Login from '@/pages/Login.vue';
import Index from '@/pages/Index.vue';
import Chat from '@/components/Chat.vue';
import Store from '@/components/Store.vue';
import History from '@/components/History.vue';


Vue.use(VueRouter);

const router = new VueRouter({
  routes: [
    {path: '/', redirect: '/login'},
    {path: '/login', component: Login},
    {path: '/index', component: Index, meta: {requiresAuth: true}, children: [
      {path: '', component: Chat, meta: {requiresAuth: true}},
      {path: 'chat', component: Chat, meta: {requiresAuth: true}},
      {path: 'store', component: Store, meta: {requiresAuth: true}},
      {path: 'history', component: History, meta: {requiresAuth: true}}
    ]},
  ]
});

export default router;