<template>
  <el-row class="list-container">
    <el-col :span="20" class="margin-left20 font-title">
      <h3>请选择部门</h3>
      <h5>修改上级部门，本部门，及本部门的下级部门不可选择，其他部门均可选择</h5>
    </el-col>
    <el-col class="margin-top20 margin-left50"></el-col>
    <el-col :offset="3" :span="18">
      <el-tree
        :data="treeData"
        :default-expand-all="true"
        show-checkbox
        ref="tree"
        node-key="id"
        :check-strictly="true"
        @check = "handleCheck">
      </el-tree>
    </el-col>

    <el-col :offset="6" :span="5" class="margin-top50">
      <el-button type="primary" size="normal" @click="onSubmit">保存</el-button>
      <el-button type="primary" size="normal" @click="cancel">取消</el-button>
    </el-col>
  </el-row>

</template>

<script>
  import '../../axios/api';
    export default {
        name: "optional_dept_tree",
      data(){
        return{
          //从后台获取树状结构数据
          treeData:[],
          deptId:'',
          deptName:'',
          superDeptId:'',
          isCurrentPageLink:false
        }
      },
      created:function(){
        this.getTreeData(); //初始化获取部门结构数据
      },
      methods:{
        //更新全局部门树结构
        getAllDeptTree() {
          this.$axios.get('/api/orgmanagement/dept/tree',{
            params:{date: new Date().getTime()}
          }).then(res => {
            if (res.data.code == 200) {
              this.$store.commit('SET_DEPT_TREE', res.data.data);
              this.$router.push("/department/list");
            } else {
              this.$message.error(res.data.msg);
            }
          }).catch(err => {
            this.$message.error(err);
          })
        },
        getTreeData(){
            // 使用axios请求数据，将返回值赋给treeData
            this.deptId=this.$route.params.deptId;
            this.deptName=this.$route.params.deptName;
            this.$axios.get('/api/orgmanagement/department/management/getOptionalDeptTree',
              {params:{deptId:this.deptId,date: new Date().getTime()}})
              .then(response => {
                if (response.data.code === 200) {
                  this.treeData = response.data.data;
                } else {
                  this.$message(response.data.msg);
                }
              })
              .catch(error => {
                this.$message('网络错误，不能访问，请联系管理员');
              })
          },
        handleCheck(node) {
          this.$refs.tree.setCheckedKeys([node.id]);
          this.superDeptId=node.id;
        },
        onSubmit() {
            if(''===this.superDeptId||null===this.superDeptId){
              this.$message("请选择一个可选的部门！");
            }else {
              this.updateRequired();
            }
        },
        updateRequired(){
          this.isCurrentPageLink=true
          this.$axios.get('/api/orgmanagement/department/management/updateSuperDept',
            {params:{deptId:this.deptId,superDeptId:this.superDeptId,date: new Date().getTime()}})
            .then(response=>{
              if (response.data.code === 200) {
                this.$msgbox({
                  title:'提示',
                  showCancelButton: false,
                  confirmButtonText: '确定',
                  type:'success',
                  message:'修改上级部门成功！！'
                }).then(() => {
                  //刷新部门树
                  this.getAllDeptTree();

                }).catch(() => {});
              } else {
                this.$message(response.data.msg);
              }
          }).catch(() => {this.$message('连接错误，修改请求无法提交！');})
        },
        cancel() {
          this.isCurrentPageLink=true;
          this.$confirm('确定取消？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$router.push('/department/list');
          }).catch(() => {});
        },
      },
      // 组件内导航钩子，处理未保存退出的情况
      beforeRouteLeave: function(to, from , next){
        if(this.isCurrentPageLink===false  && to.path !=="/"){
          next(false)
          this.$confirm('是否离开当前页面?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            // 选择确定
            next()
          })
        }else{
          next();
        }
      },
    }
</script>

<style scoped>

</style>
