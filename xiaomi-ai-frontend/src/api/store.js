import axios from "./request";

export function getStoreByUserId(userId){
  return axios.get("/store?userId=" + userId);
}

export function addStore(store){
  return axios.post("/store", store);
}

export function deleteStore(id, storeName, userId){
  return axios.delete("/store?id=" + id + "&storeName=" + storeName + "&userId=" + userId);
}