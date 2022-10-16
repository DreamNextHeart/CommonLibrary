import request from "@/utils/request";

//获取树状菜单栏
export function getMenuTree(){
    return request({
        url: '/getMenuTree',
        method: 'get'
    })
}
