<script setup>
import { onMounted } from 'vue'
import * as echarts from 'echarts'
import { queryStudentCountDataApi, queryEmpGenderDataApi, queryStudentDegreeDataApi } from '@/api/report'

//钩子函数 - 加载报表
onMounted(() => {
  loadStudentCountChart() //加载班级人数报表
  loadDegreeChart() //加载性别统计报表
})

//获取职位统计报表
const loadStudentCountChart = async () => {
  let result = await queryStudentCountDataApi();
  let clazzList = result.data.clazzList;
  let dataList = result.data.dataList;

  initStudentCountChart(clazzList, dataList)
}

//获取性别统计报表
const loadDegreeChart = async () => {
  let result = await queryStudentDegreeDataApi();
  initDegreeChart(result.data)
}

//班级人数统计报表
function initStudentCountChart(clazzList, dataList) {
  // 基于准备好的dom，初始化echarts实例
  var myChart = echarts.init(document.getElementById('container1'));
  // 绘制图表
  myChart.setOption({
    title: {
      text: '班级人数统计',
      subText: '',
      textStyle: {
        fontSize: 20
      },
      left: 'center'
    },
    grid:{
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel:true
    },
    tooltip: {},
    xAxis: {
      data: clazzList
    },
    yAxis: {},
    series: [
      {
        name: '人数',
        type: 'bar',
        data: dataList,
        itemStyle:{
          // 设置柱状渐变色---
          color:new echarts.graphic.LinearGradient(0, 0, 1, 1, [
            {
              offset: 0,
              color: '#ffbf61'
            },
            {
              offset: 1,
              color: '#dd5f85'
            }
          ])
        }
      }
    ]
  });
}

function initDegreeChart(degreeDataList) {
  // 基于准备好的dom，初始化echarts实例
  var myChart = echarts.init(document.getElementById('container2'));
  let option = {
    title: {
      text: '学员学历统计',
      subText: '',
      textStyle: {
        fontSize: 20
      },
      left: 'center'
    },
    grid:{
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel:true
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '10%',
      left: 'center'
    },
    series: [
      {
        name: '学历',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        top: '5%',
        itemStyle: {
          borderRadius: 5,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 20,
            fontWeight: 'bold'
          }
        },
        data: degreeDataList
      }
    ]
  };
  // 绘制图表
  myChart.setOption(option);
}

</script>

<template>
  <div class="report_container" id="container1">
    
  </div>

  <div class="report_container" id="container2">
    
  </div>
</template>

<style scoped>
.report_container {
  width: 49%;
  height: 90%;
  float: left;
  margin-left: 5px;
}

#container1 {
  border-right: 1px dashed #ccc;
}
</style>