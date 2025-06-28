import axios from "./request";

export function login(username, password){
  return axios.post("/check_login?username="+username+"&password="+password);
}

export function register(data){
  return axios.post("/user/registered",data);
}

export function getUserByPhone(phone){
  return axios.get("/user?phone="+phone);
}

export function updateUserPassword(id, password){
  var user = {
    id: id,
    password: password
  }
  return axios.put("/user",user);
}

export function testLogin(){
  return axios.get("/login/test");
}

export function logout(){
  return axios.post("/logout");
}
