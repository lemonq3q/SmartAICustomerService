import axios from "./request";

export function selectConversation(sessionId){
  return axios.get("/conversation?sessionId=" + sessionId);
}