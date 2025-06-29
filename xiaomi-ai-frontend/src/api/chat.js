import axios from "./request";

export function chat(requestData) {
  return axios.post("/chat", requestData);
}