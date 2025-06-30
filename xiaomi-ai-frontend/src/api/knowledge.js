import axios from "./request";

export function selectKnowledgeByStore(storeName){
  return axios.get("/knowledge?storeName=" + storeName);
}

export function deleteKnowledge(knowledgeId, storeName){
  return axios.delete("/knowledge?id=" + knowledgeId + "&storeName=" + storeName);
}

export function addKnowledge(store, content){
  return axios.post("/knowledge?content=" + content, store);
}