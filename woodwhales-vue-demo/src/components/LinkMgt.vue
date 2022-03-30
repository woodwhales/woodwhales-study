<template>
  <div ref="myEcharts" style="width: 600px;height:400px;"></div>
</template>

<script>
export default {
  mounted() {
    this.init();
  },
  methods: {
    init() {
      let myChart = this.$echarts.init(this.$refs.myEcharts);
      // 绘制图表
      let option;
      option = {
        title: {
          text: 'ECharts demo'
        },
        xAxis: {
          data: ["Java", "C", "C++", "Python", "Go", "Ruby"]
        },
        yAxis: {},
        series: [{
          name: '熟练度',
          type: 'bar',
          data: [5, 20, 36, 10, 10, 20]
        }]
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    }
  }
}
</script>

<style scoped>

</style>
