import axios from "./request";

export function getStoreByUserId(userId){
  return axios.get("/store?userId=" + userId);
}