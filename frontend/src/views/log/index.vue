<script setup>
import { onMounted, ref } from 'vue'
import { queryPageApi} from '@/api/log'

//列表展示数据
let tableData = ref([])

//钩子函数 - 页面加载时触发
onMounted(() => {
  queryPage()
})

//分页组件
const pagination = ref({currentPage: 1, pageSize: 15, total: 0})
//每页展示记录数发生变化时触发
const handleSizeChange = (pageSize) => {
  pagination.value.pageSize = pageSize
  queryPage()
}
//当前页码发生变化时触发
const handleCurrentChange = (page) => {
  pagination.value.currentPage = page
  queryPage()
}

//分页条件查询
const queryPage = async () => {
  const result = await queryPageApi(pagination.value.currentPage, pagination.value.pageSize);

  if(result.code) {
    tableData.value = result.data.rows
    
    // 修正 total 值：如果后端返回的是页数，就乘以 pageSize
    let backendTotal = result.data.total
    let actualDataCount = tableData.value.length
    
    // 判断逻辑：如果后端返回的total很小，但实际数据量很大，说明需要修正
    if (backendTotal < actualDataCount && actualDataCount > 0) {
      pagination.value.total = backendTotal * pagination.value.pageSize
    } else {
      pagination.value.total = backendTotal
    }
  }
}
</script>

<template>

    <!-- 顶部标题 -->
    <div>
      <div id="title">日志管理</div>
      <br>
    </div>
    
    <!-- 列表展示 -->
    <el-table :data="tableData" border style="width: 100%" fit size="small">
      <el-table-column prop="operateEmpName" label="操作人" align="center" width="80px"/>
      <el-table-column prop="operateTime" label="操作时间" align="center" width="150px"/>
      <el-table-column prop="className" label="类名" align="center" width="300px" />
      <el-table-column prop="methodName" label="方法名" align="center" width="100px" />
      <el-table-column prop="costTime"  label="操作耗时(ms)" align="center" width="100px"/>
      <el-table-column prop="methodParams" label="请求参数" align="center" width="280px">
        <template #default="scope">
          <el-popover effect="light" trigger="hover" placement="top" width="auto" popper-style="font-size:12px">
            <template #default>
              <div>参数: {{ scope.row.methodParams }}</div>
            </template>
            <template #reference>
              <el-tag v-if="scope.row.methodParams.length <= 30">{{ scope.row.methodParams}}</el-tag>
              <el-tag v-else>{{ scope.row.methodParams.substring(0,30) + '...' }}</el-tag>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="returnValue"  label="返回值" align="center"></el-table-column>
    </el-table>
    <br>
    
    <!-- 分页组件Pagination -->
    <el-pagination
      v-model:current-page="pagination.currentPage"
      v-model:page-size="pagination.pageSize"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
</template>


<style scoped>
#title {
  font-size: 20px;
  font-weight: 600;
}
</style>