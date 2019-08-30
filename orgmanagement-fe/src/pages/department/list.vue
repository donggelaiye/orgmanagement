<template>
  <el-row class="list-container">
    <el-col :offset="1" :span="23" class="margin-top20">
      <el-button
        v-if="buttonPermission.indexOf('addSubDepartment') > -1"
        icon="el-icon-edit-outline"
        type="primary"
        @click="createDept"
        round
        :disabled="isBtnAdd">新建子部门
      </el-button>
      <el-button
        v-if="buttonPermission.indexOf('modifyDepartment') > -1"
        icon="el-icon-edit-outline"
        type="primary"
        @click="updateDept"
        round
        :disabled="isBtnUpdate">修改
      </el-button>
      <el-button
        v-if="buttonPermission.indexOf('deleteDepartment') > -1"
        icon="el-icon-edit-outline"
        type="primary"
        @click="deleteDept"
        round
        :disabled="isBtnDelete">删除
      </el-button>
      <el-button
        v-if="buttonPermission.indexOf('modifySupDepartment') > -1"
        icon="el-icon-edit-outline"
        type="primary"
        @click="updateSuperDept"
        round
        :disabled="isBtnUpdateSup">修改上级部门
      </el-button>
      <el-button
        v-if="buttonPermission.indexOf('linkCompany') > -1"
        icon="el-icon-edit-outline"
        type="primary"
        @click="associatedCompany"
        round
        :disabled="isBtnCompany">关联公司
      </el-button>
    </el-col >
    <el-col class="margin-top50 margin-left50"></el-col>
    <el-col :offset="3" :span="18">
      <el-tree
        :data="treeData"
        :default-expand-all="true"
        show-checkbox
        ref="tree"
        node-key="id"
        :check-strictly="true"
        :render-content="renderContent"
        @check="handleCheck">
      </el-tree>
    </el-col>
  </el-row>
</template>

<script>
  import '../../axios/api';

  export default {
    name: "department_list",
    data(){
      return{
        treeData:[],              // 从后台获取树状结构数据
        deptId:'',                // 用来存储当前选择的部门id
        deptName:'',              // 用来存储当前选择的部门名称
        validChildExist:false,    // 用来存储判断子节点是否存在的bool值
        isLoading: false,         // 用来判断数据是否正在加载
        isBtnAdd: true,           // 按钮控制-新建子部门
        isBtnUpdate: true,        // 按钮控制-修改部门
        isBtnDelete: true,        // 按钮控制-删除部门
        isBtnUpdateSup: true,     // 按钮控制-修改上级部门
        isBtnCompany: true,       // 按钮控制-关联公司
        buttonPermission: []      // 用来存储按钮权限
      }
    },
    created:function(){
      this.getTreeData(); //初始化获取部门结构数据
      this.buttonPermission = JSON.parse(sessionStorage.getItem("buttonList")).department;
    },
    methods: {
      // 更新全局部门树结构
      getAllDeptTree() {
        this.$axios.get('/api/orgmanagement/dept/tree',{
          params:{date: new Date().getTime()}
        }).then(res => {
            if (res.data.code === 200) {
              this.$store.commit('SET_DEPT_TREE', res.data.data);
            } else {
              this.$message.error(res.data.msg);
            }
          }).catch(err => {
            this.$message.error(err);
          })
      },

      // 从后台获取树状结构数据
      getTreeData() {
        this.$axios.get('/api/orgmanagement/department/management/getDepartmentTree',{
          params:{date: new Date().getTime()}
        }).then(response => {
            if (response.data.code === 200) {
              this.treeData = response.data.data;
            } else {
              this.$message(response.data.msg);
            }
          })
          .catch(() => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },

      // 树节点选择事件
      handleCheck(node) {
        let setBool=this.setValidChildExist;
        this.$refs.tree.setCheckedKeys([node.id]);
        this.deptId =node.id;
        this.deptName=node.label;
        this.validChildExist=false;
        if(node.children.length>0){
          node.children.forEach(function (child){
            if(child.disabled===false){
              setBool(true);
            }
          })
        }
        // 判断按钮权限
        this.isBtnAdd = false;
        this.isBtnUpdate = false;
        this.isBtnCompany = false;
        if("Z000001" === node.id){
          this.isBtnDelete = true;
          this.isBtnUpdateSup = true;
        }else{
          this.isBtnDelete = false;
          this.isBtnUpdateSup = false;
        }
      },

      // 设置判断子节点是否存在的bool值
      setValidChildExist:function(bool){
        this.validChildExist=bool;
      },

      // 新增子部门点击事件
      createDept() {
        if(this.checkChoiceNum()){
          this.$router.push({
            name:'department_detail',
            params:{
              'deptId':this.deptId,
              'deptName':this.deptName,
              'type':'1'  // 新建1，修改2
            }
          })
        }
      },

      // 修改部门信息点击事件
      updateDept() {
        if(this.checkChoiceNum()){
          this.$router.push({
            name:'department_detail',
            params:{
              'deptId':this.deptId,
              'deptName':this.deptName,
              'type':'2'  //新建1，修改2
            }
          })
        }
      },

      // 删除部门点击事件
      deleteDept() {//  删除
        if(this.checkChoiceNum()){
          this.$confirm('确定删除部门？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.checkChildExist()
          }).catch(() => {});
        }
      },

      // 判断节点是否存在子节点
      checkChildExist(){
        if(this.validChildExist){
          this.$message({
            type: 'error',
            message: '删除失败，请先删除子节点!'
          });
        }else {
          this.deleteRequired();
        }
      },

      // 删除部门的后台请求
      deleteRequired(){
        this.$axios.get('/api/orgmanagement/department/management/deleteDepartment',
          {params:{deptId:this.deptId,date: new Date().getTime()}})
          .then(response=>{
            if (response.data.code === 200) {
              this.$msgbox({
                title:'提示',
                showCancelButton: false,
                confirmButtonText: '确定',
                type:'success',
                message:'删除部门成功！'
              }).then(() => {
                this.getAllDeptTree();  // 刷新部门树
                this.$refs.tree.getCheckedNodes()[0].disabled = true;
                this.$refs.tree.setCheckedKeys([]);
                this.validChildExist=false;
              }).catch(() => {});
            } else {
              this.$message(response.data.msg);
            }
          }).catch(()=>{
            this.$message("网络错误，删除部门失败，请提示管理员！");
          })
      },

      // 修改上级部门点击事件
      updateSuperDept(){
        if(this.checkChoiceNum()){
          this.$router.push({
            name:'optional_dept_tree',
            params:{
              'deptId':this.deptId,
              'deptName':this.deptName,
            }
          })
        }
      },

      // 关联公司点击事件
      associatedCompany(){
        if(this.checkChoiceNum()){
          this.$router.push({
            name:'associated_co_list',
            params:{
              'deptId':this.deptId,
              'deptName':this.deptName,
            }
          })
        }
      },

      // 判断是否有选择相应的节点，未选择则进行提示
      checkChoiceNum() {
        if (this.deptId===null||this.deptId===''){//用deptId判断
          this.$msgbox({
            title:'提示',
            showCancelButton: false,
            confirmButtonText: '确定',
            type:'warning',
            message:'请选择需要进行操作的部门！'
          }).then(() => {
          }).catch(() => {});
        }else {
          return true
        }
      },

      // 自定义树节点内容样式
      renderContent(h, { node, data, store }) {
        if(node.disabled===true){
          return (
            <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
            <span>
            <span style="color:red;">{node.label}</span>
            </span>
            </span>);
        }else{
          return (
            <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
            <span>
            <span>{node.label}</span>
            </span>
            </span>);
        }
      }
    },
  }
</script>

<style scoped>

</style>
