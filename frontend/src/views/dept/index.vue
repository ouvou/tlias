<script setup>
import { ref, onMounted } from 'vue'
import { queryAllApi, addApi, queryByIdApi, updateApi, deleteByIdApi } from '@/api/dept'
import { ElMessage, ElMessageBox } from 'element-plus'
//钩子函数
onMounted(() => {
  search();
})

//查询
const search = async () => {
  // const result = await axios.get("https://m1.apifoxmock.com/m1/7241972-6968595-default/depts")
  // if (result.data.code) {//JS隐式类型转换 0 - false 其他数字 - true; '' - flase; 非空字符串 - true; null, undefined -- false 
  //   deptList.value = result.data.data;
  const result = await queryAllApi();
  if (result.code) {
    deptList.value = result.data;
  }
}
const deptList = ref([]);

//Dialog对话框
const dialogFormVisible = ref(false);
const dept = ref({ name: '' });
const formTitle = ref('');



//新增部门按钮
const addDept = () => {
  dialogFormVisible.value = true;
  formTitle.value = '新增部门';
  //清空输入内容
  dept.value = { name: '' };

  //重置表单的校验规则-提示信息
  if (deptFormRef.value) {
    deptFormRef.value.resetFields()
  }
}

//保存部门
const save = async () => {
  //表单校验
  if (!deptFormRef.value) return;
  deptFormRef.value.validate(async (valid) => {//valid 表示是否校验通过：true通过/false不通过
    if (valid) {//验证通过
      // 使用 let 声明，因为后续需要根据条件重新赋值
      let result;
      if (dept.value.id) {//如果有ID 进行修改操作
        result = await updateApi(dept.value);
      } else {//如果没有，则进行添加操作
        result = await addApi(dept.value);
      }
      if (result.code) {//成功
        //提示信息
        ElMessage.success("操作成功");
        //关闭对话框
        dialogFormVisible.value = false;
        //查询
        search();
      } else {//失败
        ElMessage.error(result.msg);
      }
    } else {//验证不通过
      ElMessage.error("表单校验不通过");
    }
  })
}

//表单校验规则
const rules = ref({
  name: [{ required: true, message: '请输入部门名称', trigger: 'blur' },
  { min: 2, max: 10, message: '部门名称长度在2-10之间', trigger: 'blur' }]
})

//接收表单对象
const deptFormRef = ref();

//编辑
const edit = async (id) => {
  const result = await queryByIdApi(id);
  if (result.code) {
    dialogFormVisible.value = true;
    dept.value = result.data;
    formTitle.value = '编辑部门';
    //重置表单的校验规则-提示信息
    if (deptFormRef.value) {
      deptFormRef.value.resetFields()
    }
  }
}

//删除
const delById = async (id) => {
  ElMessageBox.confirm('您确认要删除该部门？', '提示',
    { confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning' }
  ).then(async () => { // 成功回调：用户点击确认时执行
    const result = await deleteByIdApi(id);
    if (result.code) {
      ElMessage({ type: 'success', message: '删除成功' })
      search();
    } else {
      ElMessage.error(result.msg);
    }

  }).catch(() => {// 失败回调：用户点击取消或出现错误时执行
    ElMessage({ type: 'info', message: '您已取消删除' })
  })
}

</script>

<!-- 定义HTML,控制的是页面的结构(HTML) -->
<template>
  <h1>部门管理</h1>
  <div class="container">
    <el-button type="primary" @click="addDept"><el-icon>
        <CirclePlusFilled />
      </el-icon>&nbsp;&nbsp;新增部门</el-button>
  </div>

  <!-- 表格 -->
  <div class="container">
    <el-table :data="deptList" border style="width: 100%">
      <!-- 这样修改后，该列将自动显示行号索引，而不需要依赖数据中的 id 字段 -->
      <el-table-column type="index" label="序号" width="180" align="center" />
      <el-table-column prop="name" label="部门名称" width="180" align="center" />
      <el-table-column prop="updateTime" label="最后修改时间" align="center" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" @click="edit(scope.row.id)"><el-icon>
              <Edit />
            </el-icon>编辑</el-button>
          <el-button type="danger" @click="delById(scope.row.id)"><el-icon>
              <Delete />
            </el-icon>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- Dialog对话框 -->
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500">
    <!--ref将组件实例绑定到名为 deptFormRef 的模板引用上-->
    <el-form :model="dept" :rules="rules" ref="deptFormRef">
      <el-form-item label="部门名称" label-width="80px" prop="name">
        <el-input v-model="dept.name" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </div>
    </template>
  </el-dialog>



</template>

<style scoped>
.container {
  margin: 10px 0px;
}
</style>
