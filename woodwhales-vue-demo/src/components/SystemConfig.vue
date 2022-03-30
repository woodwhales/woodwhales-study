<template>
  <el-table
    :data="tableData"
    style="width: 100%">
    <el-table-column
      prop="date"
      label="日期"
      width="180">
    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名"
      width="180">
    </el-table-column>
    <el-table-column
      prop="address"
      label="地址">
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: "SystemConfig",
  data() {
    return {
      tableData: null
    }
  },
  mounted() {
    this.getTableData();
  },
  methods: {
    getTableData() {
      this.$axios.get('http://localhost:3000/tableData')
        .then(resp => {
          this.tableData = resp.data.data;
        })
        .catch(error => {
          console.log(error)
        });
    }
  }
}
</script>

<style scoped>
</style>
