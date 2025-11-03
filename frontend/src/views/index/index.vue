<script scope>
import { Promotion, Histogram, Tools } from '@element-plus/icons-vue'

export default {
  components: {
    Promotion,
    Histogram,
    Tools
  }
}
</script>
<template>
  <div class="simple-home">
    <div class="welcome-card">
      <img src="../../assets/index.png" alt="Logo" class="home-logo">
      <h1 class="main-title">Tlias </h1>
      <hr style="width: 80px; margin: 0 auto; border: blueviolet; border-top: 4px solid #ccc;"></hr>
      <h2 style="color: #075074;font-family: '黑体'">智能学习辅助系统</h2>
      <p class="sub-title">Intelligent Learning Assistance System</p>
            <!-- 特性介绍区域 - 缩小版 -->
      <div class="features-section">
        <el-row :gutter="20" class="features-row">
          <el-col :xs="24" :sm="12" :md="8" class="feature-card">
            <div class="feature-item">
              <div class="feature-icon">
                <el-icon size="36" color="#409EFF">
                  <Promotion />
                </el-icon>
              </div>
              <h3>智能管理</h3>
              <p>全面管理班级、学员、员工信息</p>
            </div>
          </el-col>
          
          <el-col :xs="24" :sm="12" :md="8" class="feature-card">
            <div class="feature-item">
              <div class="feature-icon">
                <el-icon size="36" color="#67C23A">
                  <Histogram />
                </el-icon>
              </div>
              <h3>数据分析</h3>
              <p>多维度数据统计支持决策</p>
            </div>
          </el-col>
          
          <el-col :xs="24" :sm="12" :md="8" class="feature-card">
            <div class="feature-item">
              <div class="feature-icon">
                <el-icon size="36" color="#E6A23C">
                  <Tools />
                </el-icon>
              </div>
              <h3>系统管理</h3>
              <p>完善的权限管理和配置</p>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="welcome-text">
        <p>tips:欢迎使用智能学习管理系统，请从左侧菜单选择功能</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.simple-home {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
  padding: 40px 20px;
}

.welcome-card {
  text-align: center;
  background: white;
  padding: 50px 40px;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  max-width: 900px;
  width: 100%;
}

.home-logo {
  max-width: 80px;
  margin-bottom: 25px;
}

.main-title {
  font-size: 2.2rem;
  font-weight: 700;
  color: #00547d;
  margin-bottom: 12px;
  font-family: 'Microsoft YaHei', '楷体', sans-serif;
}

.sub-title {
  font-size: 1.1rem;
  color: #007fa4;
  margin-bottom: 40px;
  font-family: 'Arial', sans-serif;
  letter-spacing: 1px;
}

/* 特性介绍区域样式 - 缩小版 */
.features-section {
  margin: 40px 0 30px 0;
}

.features-row {
  justify-content: center;
}

.feature-card {
  margin-bottom: 15px;
}

.feature-item {
  text-align: center;
  padding: 25px 15px;
  background: #f8f9fa;
  border-radius: 12px;
  box-shadow: 0 3px 12px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
  height: 100%;
  border: 1px solid #f0f0f0;
}

.feature-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 5px 18px rgba(0, 0, 0, 0.1);
  background: white;
}

.feature-icon {
  margin-bottom: 15px;
}

.feature-item h3 {
  font-size: 1.1rem;
  color: #303133;
  margin-bottom: 10px;
  font-weight: 600;
}

.feature-item p {
  color: #606266;
  line-height: 1.5;
  font-size: 0.85rem;
  margin: 0;
}

.welcome-text {
  margin-top: 35px;
  padding-top: 25px;
  border-top: 1px solid #eee;
}

.welcome-text p {
  color: #666;
  font-size: 1rem;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .simple-home {
    padding: 20px 10px;
    min-height: 50vh;
  }
  
  .welcome-card {
    padding: 30px 20px;
  }
  
  .main-title {
    font-size: 1.8rem;
  }
  
  .sub-title {
    font-size: 1rem;
  }
  
  .home-logo {
    max-width: 60px;
  }
  
  .feature-item {
    padding: 20px 10px;
  }
  
  .feature-item h3 {
    font-size: 1rem;
  }
  
  .feature-item p {
    font-size: 0.8rem;
  }
}
</style>