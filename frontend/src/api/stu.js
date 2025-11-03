// request 是对 axios 对象的封装
import request from '@/utils/request'

//分页条件查询
export const queryPageApi = (clazzId, degree, name, page, pageSize) => 
  request.get(`/students?clazzId=${clazzId}&degree=${degree}&name=${name}&page=${page}&pageSize=${pageSize}`)


//新增
export const addApi = (student)=>request.post('/students',student)

//根据id查询学生
export const queryByIdApi = (id)=>request.get(`/students/${id}`)


//修改
export const updateApi = (student)=>request.put('/students',student)

//删除
export const deleteApi = (ids)=>request.delete(`/students?ids=${ids}`)

//违纪处理
export const handleViolationApi = (id, score) => request.put(`/students/violation/${id}/${score}`) 