<script setup>
import { ref, watch, onMounted } from "vue";
import { queryPageApi, addApi, queryByIdApi, updateApi, deleteApi } from "@/api/clazz"
import { queryAllEmpApi } from '@/api/emp'
import { ElMessage, ElMessageBox } from 'element-plus'
//学科列表数据
const subjects = ref([{ name: 'Java', value: 1 }, { name: '前端', value: 2 }, { name: '大数据', value: 3 }, { name: 'Python', value: 4 }, { name: 'Go', value: 5 }, { name: '嵌入式', value: 6 }])
//搜索表单对象
const searchClazz = ref({ begin: '', end: '', date: [], name: '' })
//班级列表数据
const clazzList = ref([])
//员工列表数据
const emps = ref([])

//钩子函数 - 页面加载时触发
onMounted(() => {
  search()//查询班级列表数据
  queryAllEmps()//查询员工列表数据
})

//加载所有的员工数据
const queryAllEmps = async () => {
  const result = await queryAllEmpApi()
  if (result.code) {
    emps.value = result.data
  }
}

//分页组件
const currentPage = ref(1);//页码
const pageSize = ref(10);//每页记录数
const background = ref(false);//背景色
const total = ref(0);//总记录数
//每页展示记录数发生变化时触发
const handleSizeChange = (val) => {
  pageSize.value = val
  search()
}
//当前页码发生变化时触发
const handleCurrentChange = (page) => {
  currentPage.value = page
  search()
}

//分页条件查询
const search = async () => {
  const result = await queryPageApi(
    searchClazz.value.begin,
    searchClazz.value.end,
    searchClazz.value.name,
    currentPage.value,
    pageSize.value
  );

  if (result.code) {
    clazzList.value = result.data.rows
    total.value = result.data.total
  }
}

//清空搜索栏
const clear = () => {
  searchClazz.value = { begin: '', end: '', date: [], name: '' }
  search()
}

//监听searchClazz的date属性
watch(() => searchClazz.value.date, (newVal, oldVal) => {
  console.log(`newVal : ${newVal} ; oldVal: ${oldVal} `)
  if (newVal.length == 2) {
    console.log('-----------');
    searchClazz.value.begin = newVal[0]
    searchClazz.value.end = newVal[1]
  } else {
    console.log('==========');
    searchClazz.value.begin = ''
    searchClazz.value.end = ''
  }
})



//----------- 新增 / 修改 ---------------------------
//员工对象-表单数据绑定
const clazz = ref({
  id: '',
  name: '',
  room: '',
  beginDate: '',
  endDate: '',
  subject: '',
  masterId: ''
})

// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('')
let labelWidth = ref(80)

//清空表单
const clearClazz = () => {
  clazz.value = {
    id: '',
    name: '',
    room: '',
    beginDate: '',
    endDate: '',
    subject: '',
    masterId: ''
  }
}

//新增班级
const addClazz = () => {
  dialogVisible.value = true
  dialogTitle.value = '新增班级'
  clearClazz()

  //重置表单的校验规则-提示信息
  if (clazzFormRef.value) {
    clazzFormRef.value.resetFields()
  }
}

//修改班级
const edit = async (id) => {
  const result = await queryByIdApi(id);
  if (result.code) {
    dialogVisible.value = true;
    dialogTitle.value = '修改班级';
    clazz.value = result.data;
  }
}

//表单校验规则
const rules = ref({
  name: [
    { required: true, message: '班级名称为必填项', trigger: 'blur' },
    { min: 4, max: 30, message: '用户名长度为4-30个字', trigger: 'blur' }
  ],
  room: [
    { min: 1, max: 20, message: '班级教室长度为1-20个字', trigger: 'blur' }
  ],
  beginDate: [{ required: true, message: '开课时间为必填项', trigger: 'change' }],
  endDate: [{ required: true, message: '结课时间为必填项', trigger: 'change' }],
  subject: [{ required: true, message: '学科为必填项', trigger: 'change' }]
})



//-------------保存班级信息 
//保存
const save = async () => {
  //表单校验
  if (!clazzFormRef.value) return;
  clazzFormRef.value.validate(async (valid) => {//valid 表示是否校验通过：true通过/false不通过
    if (valid) {//验证通过

      let result;
      if (clazz.value.id) {//对保存操作进行判断，如果对象有id，说明是修改操作
        result = await updateApi(clazz.value);
      } else {//没有id，则说明是新增操作
        result = await addApi(clazz.value);
      }

      if (result.code) {//成功
        ElMessage.success("保存成功")
        dialogVisible.value = false;
        search();
      } else {//失败
        ElMessage.error(result.msg);
      }
    } else {//验证不通过
      ElMessage.error("表单校验不通过");
    }
  })
}

//表单引用
const clazzFormRef = ref();

//------- 删除班级
//根据ID删除单个班级
const deleteById = async (id) => {
  ElMessageBox.confirm('您确认删除此数据吗?', '删除班级', { confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning' })
    .then(async () => {
      let result = await deleteApi(id)
      if (result.code) {
        ElMessage.success('删除成功')
        search()
      } else {
        ElMessage.error(result.msg)
      }
    }).catch(() => {
      ElMessage.info('您已取消删除')
    })
}
</script>

<template>
  <!-- 顶部标题 -->
  <div id="title"><h2>班级管理</h2></div>
  <!-- 条件搜索表单 -->
  <div class="container">
    <el-form :inline="true" :model="searchClazz" class="demo-form-inline">
      <el-form-item label="班级名称">
        <el-input v-model="searchClazz.name" placeholder="请输入班级名称" style="width: 180px" />
      </el-form-item>

      <el-form-item label="结课时间">
        <el-date-picker v-model="searchClazz.date" type="daterange" range-separator="到" start-placeholder="开始日期"
          end-placeholder="结束日期" value-format="YYYY-MM-DD" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>
  <!-- 功能按钮 -->
  <el-button type="primary" @click="addClazz">+ 新增班级</el-button>
  <!-- 列表展示 -->
  <!-- 表格 -->
  <div class="container">
    <el-table :data="clazzList" border style="width: 100%">
      <!-- 这样修改后，该列将自动显示行号索引，而不需要依赖数据中的 id 字段 -->
      <el-table-column type="index" label="序号" width="60" align="center" />
      <el-table-column prop="name" label="班级名称" width="150" align="center" />
      <el-table-column prop="room" label="班级教室" width="100" align="center" />
      <el-table-column prop="masterName" label="班主任" width="100" align="center" />
      <el-table-column prop="beginDate" label="开课时间" width="150" align="center" />
      <el-table-column prop="endDate" label="结课时间" width="150" align="center" />
      <el-table-column prop="status" label="状态" width="100" align="center" />
      <el-table-column prop="updateTime" label="最后修改时间" align="center" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-space direction="vertical" alignment="center" :size="8">
            <el-button type="primary" @click="edit(scope.row.id)"><el-icon>
                <Edit />
              </el-icon>编辑</el-button>
            <el-button type="danger" @click="deleteById(scope.row.id)"><el-icon>
                <Delete />
              </el-icon>删除</el-button>
          </el-space>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <!-- 分页条 -->
  <div class="container">
    <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize"
      :page-sizes="[5, 10, 20, 30, 50, 75, 100]" :background="background"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" />
  </div>

  <!-- 新增/修改员工对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="35%">
    <el-form :model="clazz" ref="clazzFormRef" :rules="rules">
      <el-form-item label="班级名称" :label-width="labelWidth" prop="name">
        <el-input v-model="clazz.name" placeholder="请输入班级名称" />
      </el-form-item>

      <el-form-item label="班级教室" :label-width="labelWidth" prop="room">
        <el-input v-model="clazz.room" placeholder="请输入班级教室" />
      </el-form-item>

      <el-form-item label="开课时间" :label-width="labelWidth" prop="beginDate">
        <el-date-picker v-model="clazz.beginDate" type="date" placeholder="请选择开课时间" value-format="YYYY-MM-DD"
          style="width: 100%;" />
      </el-form-item>

      <el-form-item label="结课时间" :label-width="labelWidth" prop="endDate">
        <el-date-picker v-model="clazz.endDate" type="date" placeholder="请选择结课时间" value-format="YYYY-MM-DD"
          style="width: 100%;" />
      </el-form-item>

      <el-form-item label="班主任" :label-width="labelWidth">
        <el-select v-model="clazz.masterId" placeholder="请选择班主任" style="width: 100%;">
          <el-option v-for="(emp, index) in emps" :key="emp.id" :label="emp.name" :value="emp.id" />
        </el-select>
      </el-form-item>

      <el-form-item label="学科" :label-width="labelWidth" prop="subject">
        <el-select v-model="clazz.subject" placeholder="请选择学科" style="width: 100%;">
          <el-option v-for="sub in subjects" :label="sub.name" :value="sub.value" />
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false;">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<style scoped>
#title {
  font-size: 20px;
  font-weight: 600;
}

.container {
  margin: 15px 0px;
}
</style>