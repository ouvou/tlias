<script setup>
import { ref, watch, onMounted } from "vue";
import { queryPageApi, addApi, queryByIdApi, updateApi, deleteApi } from "@/api/emp";
import { queryAllApi as queryAllDeptApi } from "@/api/dept";
import { ElMessage, ElMessageBox } from 'element-plus'

//元数据
//职位列表数据
const jobs = ref([{ name: '班主任', value: 1 }, { name: '讲师', value: 2 }, { name: '学工主管', value: 3 }, { name: '教研主管', value: 4 }, { name: '咨询师', value: 5 }, { name: '其他', value: 6 }])
//性别列表数据
const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }])
//部门列表数据
const depts = ref([])
//搜索表单对象
const searchEmp = ref({ name: '', gender: '', date: [], begin: '', end: '' });

//侦听searchEmp的date属性
watch(
  () => { return searchEmp.value.date }, (newVal, oldVal) => {
    if (newVal.length == 2) {
      searchEmp.value.begin = newVal[0];
      searchEmp.value.end = newVal[1];
    } else {
      searchEmp.value.begin = ''
      searchEmp.value.end = '';
    }
  }
)

//钩子函数
onMounted(() => {
  search();//查询员工列表数据
  queryAllDepts();//查询所有部门列表数据
  getToken();//获取token
})

//获取token
const token = ref("");

//获取token 文件上传认证
const getToken = () => {
  const loginUser = JSON.parse(localStorage.getItem("loginUser"));
  if (loginUser && loginUser.token) {
    token.value = loginUser.token;
  }
}

//查询所有部门数据
const queryAllDepts = async () => {
  const result = await queryAllDeptApi();
  if (result.code) {
    depts.value = result.data
  }
}

//查询所有员工
const search = async () => {
  const result = await queryPageApi(searchEmp.value.name,
    searchEmp.value.gender, searchEmp.value.begin, searchEmp.value.end, currentPage.value, pageSize.value)
  if (result.code) {
    empList.value = result.data.rows;
    total.value = result.data.total;
  }else {
    ElMessage.error(result.msg);
  }
}

//清空
const clear = () => {
  searchEmp.value = { name: '', gender: '', date: [], begin: '', end: '' };
  search();
}

/*
//1. 侦听一个响应式数据
const a = ref("")
watch(a, (newVal, oldVal) => {
  console.log(`变化后的值：${newVal}, 变化前的值: ${oldVal}`)
})

//2. 侦听一个对象(侦听对象的全部属性)
const user = ref({name:'',age:10});

// 浅层侦听 - 只有重新赋值整个对象才会触发
watch(user, (newVal,oldVal)=>{
  console.log(newVal)
}) 
// 这种修改不会触发浅层侦听
user.value.name = 'newName' 

// 深层侦听 - 对象内部属性改变也会触发
watch(user, (newVal,oldVal)=>{
  console.log(newVal)
},{deep:true})//深度侦听 
// 这种修改会触发浅层侦听
user.value = {name:'newName', age:20}


3.侦听对象中的某一个属性
const user = ref({name:'',age:10});
//先获得响应式对象，再获得属性值
watch(()=>{return user.value.age}, (newVal,oldVal)=>{
// 执行过程：
// 1. 先获得响应式对象: user (这是响应式的)
// 2. 再获得属性值: .age (通过响应式对象访问属性，建立依赖追踪)
// 3. Vue 就知道："哦，这个 watcher 依赖于 user 对象的 age 属性"
  console.log(`变化后的值：${newVal}, 变化前的值: ${oldVal}`)
}) 
*/

const empList = ref([])



const currentPage = ref(1);//页码
const pageSize = ref(10);//每页记录数
const background = ref(false);//背景色
const total = ref(0);//总记录数

//每页展示记录数变化
const handleSizeChange = (val) => {
  console.log(`每页展示 ${val} 条记录`);
  //pageSize.value = val;当用户操作分页组件时，pageSize 和 currentPage 的值会自动更新
  search();
}

//页码变化时触发
const handleCurrentChange = (val) => {
  console.log(`当前页码: ${val}`);
  //currentPage.value = val; v-model 已经完成了这个赋值操作
  search();
}


// 新增员工
const addEmp = async () => {
  // 初始化表单
  employee.value = {
    username: '', name: '', gender: '', phone: '', job: '',
    salary: '', deptId: '', entryDate: '', image: '', exprList: []
  };

  dialogVisible.value = true;
  dialogTitle.value = "新增员工";

  //重置表单的校验规则-提示信息
  if (empFormRef.value) {
    empFormRef.value.resetFields()
  }
}

//员工信息数据模型
const employee = ref({
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
})

// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('')

//文件上传
// 图片上传成功后触发
const handleAvatarSuccess = (response) => {
  console.log(response);
  employee.value.image = response.data;
}
// 文件上传之前触发
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传10M以内图片')
    return false
  }
  return true
}

//添加工作经历
const addExprItem = () => {
  employee.value.exprList.push({ company: '', job: '', begin: '', end: '', exprDate: '' })
}

//删除工作经历
const delExprItem = (index) => {
  employee.value.exprList.splice(index, 1);
}

//侦听-employee员工对象中的工作经历信息
// 监听员工工作经历列表的变化
// 使用深度监听来捕获数组内对象属性的变化
watch(
  // 侦听源：返回employee.value.exprList数组
  () => { return employee.value.exprList },
  // 侦听回调：当exprList或其内部元素发生变化时执行
  (newVal, oldVal) => {
    // 检查工作经历列表是否存在且不为空
    if (employee.value.exprList && employee.value.exprList.length > 0) {
      // 遍历每个工作经历项，同步exprDate到begin和end字段
      employee.value.exprList.forEach((expr) => {
        // 检查exprDate是否存在且包含两个日期值
        if (expr.exprDate && expr.exprDate.length === 2) {
          expr.begin = expr.exprDate[0];
          expr.end = expr.exprDate[1];
        } else {
          // 如果exprDate无效，则清空begin和end字段
          expr.begin = "";
          expr.end = "";
        }
      })
    }
    // 注意：当exprList为空数组时，不会执行任何操作
  },
  // 配置选项：启用深度监听，监听数组内部对象属性的变化
  { deep: true }
)


//保存
const save = async () => {
  //表单校验
  if (!empFormRef.value) return;
  empFormRef.value.validate(async (valid) => {//valid 表示是否校验通过：true通过/false不通过
    if (valid) {//验证通过

      let result;
      if (employee.value.id) {//对保存操作进行判断，如果对象有id，说明是修改操作
        result = await updateApi(employee.value);
      } else {//没有id，则说明是新增操作
        result = await addApi(employee.value);
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
const empFormRef = ref();


//表单校验规则
const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度应在2到20个字符之间', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    /**
     * 正则表达式: ／ ...... / ;  ^: 以...开始; $:以...结束
     * [3-9]：范围3-9之间
     * \d：数字，[0-9]
     * {9}：量词
     */
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ]
});

//修改
const edit = async (id) => {
  const result = await queryByIdApi(id);
  if (result.code) {
    dialogVisible.value = true;
    dialogTitle.value = '修改员工';
    employee.value = result.data;
  }

  //对工作经历进行处理
  let exprList = employee.value.exprList;
  if (exprList && exprList.length > 0) {
    exprList.forEach((expr) => {
      expr.exprDate = [expr.begin, expr.end];
    })
  }
}


//删除
const deleteById = (id) => {
  ElMessageBox.confirm('您确认要删除该员工？', '提示',
    { confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning' }
  ).then(async () => { // 成功回调：用户点击确认时执行
    const result = await deleteApi(id);
    if (result.code) {
      ElMessage({ type: 'success', message: '删除成功' })
      search();
    } else {
      ElMessage.error(result.msg);
    }

  }).catch(() => {// 失败回调：用户点击取消或出现错误时执行
    ElMessage({ type: 'info', message: '取消删除' })
  })
}

//记录要勾选员工的id
const selectedIds = ref([]);

//复选框勾选发生变化时触发  selection:当前选中的记录 (数字)
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map((item) => { return item.id });
}


//批量删除员工
const deleteByIds = () => {
  ElMessageBox.confirm('您确认要删除该员工？', '提示',
    { confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning' }
  ).then(async () => { // 成功回调：用户点击确认时执行
    if (selectedIds.value && selectedIds.value.length > 0) {
      const result = await deleteApi(selectedIds.value);
      if (result.code) {
        ElMessage({ type: 'success', message: '删除成功' })
        search();
      } else {
        ElMessage.error(result.msg);
      }
    } else {
      ElMessage.warning('您没有选择任何记录!')
    }
  }).catch(() => {// 失败回调：用户点击取消或出现错误时执行
    ElMessage({ type: 'info', message: '您已取消删除' })
  })
}

</script>

<template>
  <h1>员工管理</h1>
  <!-- 搜索栏 -->
  <div class="container">
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchEmp.name" placeholder="请输入员工姓名" style="width: 180px" />
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="searchEmp.gender" placeholder="请选择" style="width: 180px">
          <el-option label="男" value="1" />
          <el-option label="女" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="入职时间">
        <el-date-picker v-model="searchEmp.date" type="daterange" range-separator="到" start-placeholder="开始日期"
          end-placeholder="结束日期" value-format="YYYY-MM-DD" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <!-- 功能按钮 -->
  <div class="container">
    <el-button type="primary" @click="addEmp">+ 新增员工</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <!-- 数据展示表格 -->
  <div class="container">
    <el-table :data="empList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="name" label="姓名" width="120" align="center" />
      <el-table-column label="性别" width="120" align="center">
        <template #default="scope">
          {{ scope.row.gender == 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column label="头像" width="120" align="center">
        <template #default="scope">
          <img :src="scope.row.image" height="30px">
        </template>
      </el-table-column>
      <el-table-column prop="deptName" label="所属部门" width="120" align="center" />
      <el-table-column prop="job" label="职位" width="120" align="center">
        <template #default="scope">
          <span v-if="scope.row.job == 1">班主任</span>
          <span v-else-if="scope.row.job == 2">讲师</span>
          <span v-else-if="scope.row.job == 3">学工主管</span>
          <span v-else-if="scope.row.job == 4">教研主管</span>
          <span v-else-if="scope.row.job == 5">咨询师</span>
          <span v-else>其他</span>
        </template>
      </el-table-column>
      <el-table-column prop="entryDate" label="入职日期" width="180" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="200" align="center" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-space direction="vertical" alignment="center" :size="8">
            <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon>
                <EditPen />
              </el-icon> 编辑</el-button>
            <el-button type="danger" size="small" @click="deleteById(scope.row.id)"><el-icon>
                <Delete />
              </el-icon> 删除</el-button>
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


  <!-- 新增/修改员工的对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form :model="employee" :rules="rules" ref="empFormRef" label-width="80px">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="employee.username" placeholder="请输入员工用户名，2-20个字"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="employee.name" placeholder="请输入员工姓名，2-10个字"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="employee.gender" placeholder="请选择性别" style="width: 100%;">
              <!-- 标签属性讲解
              v-for="gender in genders"：
              Vue 指令，用于遍历 genders 数组
              每次迭代将当前元素赋值给临时变量 gender
              例如第一次迭代：gender = { name: '男', value: 1 }

              :key="index"：
              为每个循环项设置唯一标识
              使用数组索引作为 key（注意：这不是最佳实践）
              更推荐使用唯一值如 :key="gender.value"

              :label="gender.name"：
              设置选项显示的文本内容
              绑定到 gender 对象的 name 属性
              显示值为："男" 或 "女"

              :value="gender.value"：
              设置选项选中时的实际值
              绑定到 gender 对象的 value 属性
              实际值为：1（男）或 2（女） -->
              <el-option v-for="gender in genders" :key="gender.value" :label="gender.name"
                :value="gender.value"></el-option>

            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="employee.phone" placeholder="请输入员工手机号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="职位">
            <el-select v-model="employee.job" placeholder="请选择职位" style="width: 100%;">
              <el-option v-for="(job, index) in jobs" :key="job.value" :label="job.name" :value="job.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="薪资">
            <el-input v-model="employee.salary" placeholder="请输入员工薪资"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="所属部门">
            <el-select v-model="employee.deptId" placeholder="请选择部门" style="width: 100%;">
              <el-option v-for="(dept, index) in depts" :key="dept.id" :label="dept.name" :value="dept.id"></el-option>

            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入职日期">
            <el-date-picker v-model="employee.entryDate" type="date" style="width: 100%;" placeholder="选择日期"
              format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="头像">
            <el-upload class="avatar-uploader" action="/api/upload" :headers="{ 'token': token }" :show-file-list="false"
              :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
              <img v-if="employee.image" :src="employee.image" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon">
                <Plus />
              </el-icon>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>


      <!-- 工作经历按钮 -->
      <!-- 第六行 -->
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form-item label="工作经历">
            <el-button type="success" size="small" @click="addExprItem">+ 添加工作经历</el-button>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第七行 ...  工作经历 -->
      <el-row :gutter="3" v-for="(expr, index) in employee.exprList">
        <el-col :span="10">
          <el-form-item size="small" label="时间" label-width="80px">
            <el-date-picker type="daterange" v-model="expr.exprDate" range-separator="至" start-placeholder="开始日期"
              end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item size="small" label="公司" label-width="60px">
            <el-input placeholder="请输入公司名称" v-model="expr.company"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item size="small" label="职位" label-width="60px">
            <el-input placeholder="请输入职位" v-model="expr.job"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="2">
          <el-form-item size="small" label-width="0px">
            <el-button type="danger" @click="delExprItem(index)">- 删除</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 对话框底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.container {
  margin: 10px 0px;
}

.avatar {
  height: 40px;
}

.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border-radius: 10px;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}
</style>