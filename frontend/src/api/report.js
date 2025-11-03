import request from '@/utils/request'

//统计各个职位员工的人数
export const queryEmpJobDataApi = () => request.get(`/report/empJobData`)

//统计员工性别信息
export const queryEmpGenderDataApi = () => request.get(`/report/empGenderData`)

//统计每一个班级的人数
export const queryStudentCountDataApi = () => request.get(`/report/studentCountData`)

//统计学员的学历信息
export const queryStudentDegreeDataApi = () => request.get(`/report/studentDegreeData`)
