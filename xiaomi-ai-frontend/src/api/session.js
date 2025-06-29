import axios from "./request";

export function selectSession(userId){
  return axios.get("/session?userId=" + userId);
}

export function createSession(session){
  return axios.post("/session", session)
}

export function deleteSession(sessionId, userId){
  return axios.delete("/session?id=" + sessionId + "&userId=" + userId);
}