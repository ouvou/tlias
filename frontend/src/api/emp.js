import request from '@/utils/request'

// request 是对 axios 对象的封装

//查询全部员工
export const queryAllEmpApi = ()=>request.get('/emps/list')

//员工列表分页查询
export const queryPageApi = (name,gender,begin,end,page,pageSize)=>{
return request.get(`/emps?name=${name}&gender=${gender}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)}


//新增
export const addApi = (emp)=>request.post('/emps',emp)

//根据id查询员工
export const queryByIdApi = (id)=>request.get(`/emps/${id}`)


//修改
export const updateApi = (emp)=>request.put('/emps',emp)

//删除
export const deleteApi = (ids)=>request.delete(`/emps?ids=${ids}`)


//修改员工密码
// 导入封装好的 request 实例而不是 axios

// 使用 request 替代 axios
// 备选方案：保持原来的参数形式
// 修改员工密码 - 修正这个方法
export const updatePasswordApi = (userPassword) => {
  return request({
    url: '/emps/password',
    method: 'put',
    data: userPassword  // 使用 data 而不是 params
  })
}