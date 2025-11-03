import request from '@/utils/request'

// request 是对 axios 对象的封装


//部门列表查询
export const queryAllApi = ()=>{
return request.get('/depts')}

//增加
export const addApi = (dept)=>request.post('/depts',dept)

//根据id查询部门
export const queryByIdApi = (id)=>request.get(`/depts/${id}`)


//修改
export const updateApi = (dept)=>request.put('/depts',dept)

//删除
export const deleteByIdApi = (id)=>request.delete(`/depts?id=${id}`)
