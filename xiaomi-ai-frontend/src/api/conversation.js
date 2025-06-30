import axios from "./request";

export function selectConversation(sessionId){
  return axios.get("/conversation?sessionId=" + sessionId);
}

export function selectConversationByUserId(conversation,start,end){
  var queryUrl = "/conversation/"+start+"/"+end+"?userId=" + conversation.userId;
  if(conversation.sessionId != null){
    queryUrl += "&sessionId=" + conversation.sessionId;
  }
  if(conversation.role  != null){
    queryUrl += "&role=" + conversation.role;
  }
  if(conversation.content != null){
    queryUrl += "&content=" + conversation.content;
  }
  return axios.get(queryUrl);

}