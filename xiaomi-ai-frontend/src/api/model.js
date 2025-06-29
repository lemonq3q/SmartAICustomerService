import axios from "./request";

export function getAllModel(){
  return axios.get("/model");
}