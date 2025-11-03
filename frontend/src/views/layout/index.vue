<script setup>
import { Menu as MenuIcon } from '@element-plus/icons-vue'
import { ref, onMounted, reactive, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'
import { updatePasswordApi } from '@/api/emp.js'

//当前登录员工姓名
const loginName = ref("");

//获取router实例
const router = useRouter();

//钩子函数
onMounted(() => {
  //获取员工数据
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if (loginUser && loginUser.name) {
    loginName.value = loginUser.name;
  }
})



//退出登录
const logout = () => {
  ElMessageBox.confirm('您确认要退出？', '提示',
    { confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning' }
  ).then(() => { // 成功回调：用户点击确认时执行
    ElMessage.success('退出成功');
    localStorage.removeItem('loginUser');
    //跳转页面到首页
    router.push('/login');
  }).catch(() => {// 失败回调：用户点击取消或出现错误时执行
    ElMessage({ type: 'info', message: '您已取消退出' });
  })
}


// ========== 修改密码功能 ==========
const passwordDialogVisible = ref(false)
const passwordFormRef = ref()

// 修改密码表单数据 - 使用 ref 替代 reactive
const passwordForm = ref({
  id: 0,
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 密码验证规则 - 使用 ref 替代 reactive
const passwordRules = ref({
  oldPassword: [
    { required: true, message: '请输入旧密码', trigger: 'blur' },
    { min: 6, message: '密码至少6位', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码至少6位', trigger: 'blur' },
    { 
      validator: (rule, value, callback) => {
        if (value === passwordForm.value.oldPassword) {
          callback(new Error('新密码不能与旧密码相同'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { 
      validator: (rule, value, callback) => {
        if (value !== passwordForm.value.newPassword) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
})

// 打开修改密码对话框
const openPasswordDialog = () => {
  // 通过token获取当前登录用户信息
  const loginUser = JSON.parse(localStorage.getItem('loginUser'))
  if (!loginUser || !loginUser.id) {
    ElMessage.error('用户信息获取失败，请重新登录')
    return
  }
  
  // 确保 id 是整数类型
  const userId = Number(loginUser.id)
  if (isNaN(userId)) {
    ElMessage.error('用户ID格式错误，请重新登录')
    return
  }
  
  // 重置表单 - 注意这里使用 .value
  passwordForm.value = {
    id: userId,
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
  }
  
  console.log('修改密码用户ID:', passwordForm.value.id, '类型:', typeof passwordForm.value.id)
  
  passwordDialogVisible.value = true
  
  // 重置表单验证
  if (passwordFormRef.value) {
    nextTick(() => {
      passwordFormRef.value.clearValidate()
    })
  }
}

// 提交修改密码
const submitPassword = async () => {
  if (!passwordFormRef.value) return
  
  passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // 构造请求数据 - 注意这里使用 .value
        const requestData = {
          id: passwordForm.value.id,
          oldPassword: passwordForm.value.oldPassword,
          newPassword: passwordForm.value.newPassword
        }
        
        console.log('提交的修改密码数据:', requestData)
        
        const result = await updatePasswordApi(requestData)
        
        if (result.code === 1) {
          ElMessage.success('密码修改成功')
          passwordDialogVisible.value = false
          
          // 修改密码后提示重新登录
          ElMessageBox.confirm('密码修改成功，为了安全请重新登录', '提示', {
            confirmButtonText: '重新登录',
            cancelButtonText: '稍后',
            type: 'success'
          }).then(() => {
            localStorage.removeItem('loginUser')
            router.push('/login')
          }).catch(() => {
            // 用户选择稍后重新登录
          })
        } else {
          ElMessage.error(result.msg || '密码修改失败')
        }
      } catch (error) {
        console.error('修改密码错误:', error)
        ElMessage.error('密码修改失败，请重试')
      }
    } else {
      ElMessage.error('表单验证失败，请检查输入')
    }
  })
}

// 关闭密码对话框
const closePasswordDialog = () => {
  passwordDialogVisible.value = false
}
</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- Header 区域 -->
      <el-header class="header">
        <span class="title">Tlias智能学习辅助系统</span>
        <span class="right_tool">
          <a href="javascript:void(0)" @click="openPasswordDialog">
            <el-icon>
              <EditPen />
            </el-icon> 修改密码 &nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;
          </a>
          <a href="javascript:void(0)" @click="logout">
            <el-icon>
              <SwitchButton />
            </el-icon> 退出登录 【{{ loginName }}】
          </a>
        </span>
      </el-header>

      <el-container>
        <!-- 左侧菜单 - 深蓝色 -->
        <el-aside width="220px" class="aside">
          <!-- 左侧菜单栏 -->
          <el-menu router class="nav-menu" background-color="#1e2a3a" text-color="#b0b8c4" active-text-color="#ffffff">
            <!-- 首页菜单 -->
            <el-menu-item index="/index" class="nav-item">
              <el-icon>
                <Promotion />
              </el-icon>
              <span>首页</span>
            </el-menu-item>

            <!-- 班级管理菜单 -->
            <el-sub-menu index="/manage" class="nav-submenu">
              <template #title>
                <el-icon>
                  <MenuIcon />
                </el-icon>
                <span>班级学员管理</span>
              </template>
              <el-menu-item index="/clazz" class="nav-item">
                <el-icon>
                  <HomeFilled />
                </el-icon>
                <span>班级管理</span>
              </el-menu-item>
              <el-menu-item index="/stu" class="nav-item">
                <el-icon>
                  <UserFilled />
                </el-icon>
                <span>学员管理</span>
              </el-menu-item>
            </el-sub-menu>

            <!-- 系统信息管理 -->
            <el-sub-menu index="/system" class="nav-submenu">
              <template #title>
                <el-icon>
                  <Tools />
                </el-icon>
                <span>系统信息管理</span>
              </template>
              <el-menu-item index="/dept" class="nav-item">
                <el-icon>
                  <HelpFilled />
                </el-icon>
                <span>部门管理</span>
              </el-menu-item>
              <el-menu-item index="/emp" class="nav-item">
                <el-icon>
                  <Avatar />
                </el-icon>
                <span>员工管理</span>
              </el-menu-item>
            </el-sub-menu>

            <!-- 数据统计管理 -->
            <el-sub-menu index="/report" class="nav-submenu">
              <template #title>
                <el-icon>
                  <Histogram />
                </el-icon>
                <span>数据统计管理</span>
              </template>
              <el-menu-item index="/empReport" class="nav-item">
                <el-icon>
                  <InfoFilled />
                </el-icon>
                <span>员工信息统计</span>
              </el-menu-item>
              <el-menu-item index="/stuReport" class="nav-item">
                <el-icon>
                  <Share />
                </el-icon>
                <span>学员信息统计</span>
              </el-menu-item>
              <el-menu-item index="/log" class="nav-item">
                <el-icon>
                  <Document />
                </el-icon>
                <span>日志信息统计</span>
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>

        <!-- 主内容区域 - 浅色背景 -->
        <el-main class="main-content">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>

  <!-- 修改密码对话框 -->
  <el-dialog 
    v-model="passwordDialogVisible" 
    title="修改密码" 
    width="400px"
    @close="closePasswordDialog"
  >
    <el-form
      :model="passwordForm"
      :rules="passwordRules"
      ref="passwordFormRef"
      label-width="80px"
    >
      <el-form-item label="旧密码" prop="oldPassword">
        <el-input
          v-model="passwordForm.oldPassword"
          type="password"
          placeholder="请输入当前密码"
          show-password
        />
      </el-form-item>
      
      <el-form-item label="新密码" prop="newPassword">
        <el-input
          v-model="passwordForm.newPassword"
          type="password"
          placeholder="请输入新密码"
          show-password
        />
      </el-form-item>
      
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input
          v-model="passwordForm.confirmPassword"
          type="password"
          placeholder="请再次输入新密码"
          show-password
        />
      </el-form-item>
    </el-form>
    
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closePasswordDialog">取消</el-button>
        <el-button type="primary" @click="submitPassword">确认修改</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.common-layout {
  height: 100vh;
  background: #f8fafc;
}

/* Header 样式 */
.header {
  background: linear-gradient(135deg, #042b6b 0%, #5e11da 100%);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 100;
}

.title {
  color: white;
  font-size: 40px;
  font-family: 楷体;
  line-height: 60px;
  font-weight: bold;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.right_tool {
  float: right;
  line-height: 60px;
}

a {
  color: rgba(255, 255, 255, 0.9);
  text-decoration: none;
  transition: color 0.3s ease;
  font-size: 14px;
}

a:hover {
  color: #ffffff;
}

/* 左侧导航栏 - 深蓝色 */
.aside {
  background: #1e2a3a;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.1);
  height: calc(100vh - 60px);
  overflow-y: auto;
}

/* 导航菜单样式 */
.nav-menu {
  border: none;
  background: #1e2a3a;
}

.nav-menu .nav-item {
  height: 50px;
  line-height: 50px;
  margin: 4px 8px;
  border-radius: 6px;
  transition: all 0.3s ease;
}

.nav-menu .nav-item:hover {
  background-color: #2d3e50 !important;
}

.nav-menu .nav-item.is-active {
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%) !important;
  color: #ffffff;
}

.nav-submenu {
  margin: 4px 8px;
  border-radius: 6px;
}

.nav-submenu :deep(.el-sub-menu__title) {
  height: 50px;
  line-height: 50px;
  border-radius: 6px;
  transition: all 0.3s ease;
}

.nav-submenu :deep(.el-sub-menu__title:hover) {
  background-color: #2d3e50 !important;
}

/* 主内容区域 - 浅色背景 */
.main-content {
  background: #ffffff;
  padding: 24px;
  margin: 0;
  height: calc(100vh - 60px);
  overflow-y: auto;
  box-shadow: inset 0 0 20px rgba(0, 0, 0, 0.02);
}

/* 滚动条样式 */
.aside::-webkit-scrollbar {
  width: 6px;
}

.aside::-webkit-scrollbar-track {
  background: #1e2a3a;
}

.aside::-webkit-scrollbar-thumb {
  background: #4b5563;
  border-radius: 3px;
}

.aside::-webkit-scrollbar-thumb:hover {
  background: #6b7280;
}

.main-content::-webkit-scrollbar {
  width: 8px;
}

.main-content::-webkit-scrollbar-track {
  background: #f1f5f9;
}

.main-content::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 4px;
}

.main-content::-webkit-scrollbar-thumb:hover {
  background: #94a3b8;
}
</style>