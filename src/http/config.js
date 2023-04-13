import Cookies from "js-cookie";
export default {
    baseUrl: 'http://localhost:8081',
    timeOut: 5000,
    headers: {
        'Content-Type': 'application/json;charset=UTF-8'
    },
    cookies:Cookies.get("userIdStr"),
}