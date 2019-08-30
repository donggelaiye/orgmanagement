<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="companyForm" label-width="80px">
        <el-form-item label="企业名称">
          <el-input v-model="companyForm.coName" clearable  maxlength="60"></el-input>
        </el-form-item>
        <el-form-item label="统一社会信用代码" label-width="140px">
          <el-input v-model="companyForm.coCreditCode" clearable maxlength="18"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="companyForm.coType" placeholder="" class="select-width">
            <el-option label="全部" value=""></el-option>
            <el-option label="有限责任公司" value="1"></el-option>
            <el-option label="股份有限公司" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司性质" >
          <el-select v-model="companyForm.coNature" placeholder="" class="select-width">
            <el-option label="全部" value=""></el-option>
            <el-option label="小规模纳税人" value="1"></el-option>
            <el-option label="一般纳税人" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" label-width="60px">
          <el-select v-model="companyForm.status" placeholder="" class="select-width">
            <el-option label="全部" value=""></el-option>
            <el-option label="有效" value="1"></el-option>
            <el-option label="无效" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="修改时间">
          <el-date-picker
            v-model="timeScope"
            type="datetimerange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="pickerOptions">
          </el-date-picker>
<!--                    <el-date-picker-->
<!--            v-model="companyForm.startTime"-->
<!--            type="datetime"-->
<!--            placeholder="选择开始时间">-->
<!--          </el-date-picker>-->
<!--          <span>至</span>-->
<!--          <el-date-picker-->
<!--            v-model="companyForm.endTime"-->
<!--            type="datetime"-->
<!--            placeholder="选择结束时间">-->
<!--          </el-date-picker>-->
        </el-form-item>
      </el-form>
    </el-col>
    <!--操作按钮-->
    <el-col :offset="19" :span="5">
      <el-button v-if="buttonPermission.indexOf('getList') > -1" type="primary" icon="el-icon-search" @click="loadTable">查询</el-button>
      <el-button v-if="buttonPermission.indexOf('exportCompany') > -1" type="primary" icon="el-icon-document" @click="fileExport" >导出</el-button>
    </el-col>
    <el-col :offset="1" :span="23" class="margin-top20">
      <el-button v-if="buttonPermission.indexOf('addCompany') > -1" type="primary" icon="el-icon-edit-outline" @click="createCompany" :disabled="isBtnCreate" round>新建</el-button>
      <el-button v-if="buttonPermission.indexOf('modifyCompany') > -1" type="primary" icon="el-icon-edit-outline" @click="updateCompany" :disabled="isBtnUpdate" round>修改</el-button>
      <el-button v-if="buttonPermission.indexOf('deleteCompany') > -1" type="primary" icon="el-icon-edit-outline" @click="deleteCompany" :disabled="isBtnDelete" round>删除</el-button>
    </el-col>

    <!--展示列表-->
    <el-col class="margin-top20">
      <el-table
        v-loading="isLoading"
        ref="multipleTable"
        :data="tableData"
        :highlight-current-row="true"
        @selection-change="handleSelectionChange"
        @cell-click="handleCellClick"
        class="table—style" border>
        <el-table-column
          type="selection"
          prop="id"
          width="55">
        </el-table-column>
        <el-table-column
          prop="id"
          label="编号">
          <template slot-scope="scope">
            <el-link type="primary">{{scope.row.id}}</el-link>
          </template>
        </el-table-column>
        <el-table-column
          prop="coName"
          label="公司名称">
        </el-table-column>
        <el-table-column
          prop="coCreditCode"
          label="统一社会信用代码">
        </el-table-column>
        <el-table-column
          prop="coType"
          label="公司类型">
          <template slot-scope="scope">
            <span v-if="scope.row.coType === 0">股份有限公司</span>
            <span v-else-if="scope.row.coType === 1">有限责任公司</span>
            <span v-else>无</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="coNature"
          label="公司性质">
          <template slot-scope="scope">
            <span v-if="scope.row.coNature === 0">一般纳税人</span>
            <span v-else-if="scope.row.coNature === 1">小规模纳税人</span>
            <span v-else>无</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="coMark"
          label="总公司标志">
          <template slot-scope="scope">
            <span v-if="scope.row.coMark === 0">总公司</span>
            <span v-else-if="scope.row.coMark === 1">子公司</span>
            <span v-else>无</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 0">无效</span>
            <span v-else-if="scope.row.status === 1">有效</span>
            <span v-else>无</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="modifyEmpName"
          label="修改人">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.modifyEmpName }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="modifyTime"
          label="修改时间">
        </el-table-column>
      </el-table>
    </el-col>
    <!--分页导航-->
    <el-col :span="20" class="margin-top20 margin-left20">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page=page.currentPage
        :page-sizes="[10, 20, 30, 40]"
        :page-size=page.pageSize
        layout="total, sizes, prev, pager, next, jumper"
        :total=page.total>
      </el-pagination>
    </el-col>
    <!--公司详细信息弹窗-->
    <el-dialog ref="dialog" title="公司详细信息" :visible.sync="detailDialogVisible">
      <el-form ref="company" status-icon :model="company" label-width="120px" size="mini" >
        <el-row>
          <el-col :span="20" class="margin-left20 font-title">
            <h3>公司信息</h3>
          </el-col>
          <el-col :span="24">
            <el-row>
              <el-col :span="10">
                <el-form-item label="统一信用代码："   prop="coCreditCode">
                  <el-input v-model="company.coCreditCode" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="企业名称：" prop="coName">
                  <el-input v-model="company.coName" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="公司类型：">
                  <el-select v-model="company.coType"  class="select-width" :disabled="true">
                    <el-option label="有限责任公司" value="1"></el-option>
                    <el-option label="股份有限公司" value="0"></el-option>
                  </el-select>
                </el-form-item>

              </el-col>
              <el-col :span="10">
                <el-form-item label="营业期限：" prop="businessTime">
                  <el-date-picker
                    v-model="company.businessTime" readonly
                    type="datetime" style="width: 173px">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="住所：" prop="coAddress">
                  <el-input v-model="company.coAddress" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="经营范围：" prop="businessScope">
                  <el-input v-model="company.businessScope" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="法人代表：" prop="legalRepresentative">
                  <el-input v-model="company.legalRepresentative" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="注册资本：" prop="registeredCapital">
                  <el-input v-model="company.registeredCapital" readonly placeholder="单位为元" oninput = "value=value.replace(/[^\d]/g,'')"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="成立日期：" prop="setDate">
                  <el-date-picker
                    v-model="company.setDate" readonly
                    type="datetime" style="width: 173px">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="登记机关：" prop="registeredAuthority">
                  <el-input v-model="company.registeredAuthority" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="核准日期：" prop="approvalDate">
                  <el-date-picker
                    v-model="company.approvalDate" readonly
                    type="datetime" style="width: 173px">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="登记状态：" prop="registeredStatus">
                  <el-input v-model="company.registeredStatus" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-col :span="20" class="margin-left20 font-title">
              <h3>营业执照</h3>
            </el-col>
            <el-row>
              <el-col :offset="1" :span="23">
                <el-form-itemq label="营业执照：" class="demo-image__preview">
                  <el-image v-for="file in fileList" :key="file" :src="file.url"
                            class="image-style" :preview-src-list="new Array(file.url)">
                  </el-image>

<!--                  <el-upload-->
<!--                    readonly-->
<!--                    class="upload-demo"-->
<!--                    ref="upload"-->
<!--                    list-type="picture-card"-->
<!--                    disabled = "true"-->

<!--                    :on-preview="open"-->
<!--                    :before-remove="removeFalse"-->
<!--                    action="http://localhost:8080/orgmanagement/company/management/test"-->
<!--                    :file-list="fileList"-->
<!--                    :limit="20"-->
<!--                    accept="image/png,image/gif,image/jpg,image/jpeg"-->
<!--                    :auto-upload="false">-->
<!--                  </el-upload>-->
                </el-form-itemq>
              </el-col>
            </el-row>
            <el-col :span="20" class="margin-left20 font-title">
              <h3>发票信息</h3>
            </el-col>
            <el-row>
              <el-col :span="10">
                <el-form-item label="公司性质：">
                  <el-radio-group v-model="company.coNature" disabled="disabled">
                    <el-radio :label="0">一般纳税人</el-radio>
                    <el-radio :label="1">小规模纳税人</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="注册地址：" prop="registeredAddress">
                  <el-input v-model="company.registeredAddress" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="开户银行：" prop="openBank">
                  <el-input v-model="company.openBank" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="公司账号：" prop="coAccount">
                  <el-input v-model="company.coAccount" readonly oninput = "value=value.replace(/[^\d]/g,'')"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="注册电话：" prop="registeredPhone">
                  <el-input v-model="company.registeredPhone" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="总公司标志：">
                  <el-radio-group v-model="company.coMark" disabled="disabled">
                    <el-radio :label="0">总公司</el-radio>
                    <el-radio :label="1">子公司</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20" class="margin-left20 font-title">
            <h3>其他信息</h3>
          </el-col>
          <el-col :span="24">
            <el-row>
              <el-col :span="10">
                <el-form-item label="新建时间：">
                  <el-input v-model="company.createTime" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="新建人：">
                  <el-input v-model="company.createEmp" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="修改时间：">
                  <el-input v-model="company.modifyTime" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="修改人：">
                  <el-input v-model="company.modifyEmp" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="15">
                <el-form-item label="备注：" props="remark">
                  <el-input type="textarea" rows="4" v-model="company.remark" maxlength="200" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <!--导出弹窗内容-->
    <el-dialog ref="exportDialog" title="请选择导出条件" :visible.sync="exportDialogVisible" @close="closeExportDailog">
      <exportFile v-on:checkInfo="getCheckList" :checkOptions="roleOptions" :sonChecked="sonChecked"></exportFile>
      <el-row>
        <el-col :offset="10" :span="14" class="margin-top20">
          <el-button icon="el-icon-edit-outline" @click="selectAll" round>
            <span v-if="isAll">全选</span>
            <span v-else>取消全选</span>
          </el-button>
          <el-button icon="el-icon-edit-outline" @click="confirm" round>确定导出</el-button>
          <el-button icon="el-icon-edit-outline" @click="dialogClose" round>取消</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </el-row>
</template>

<script>
  import exportFile from '@/pages/common/exportFile'
  import '@/axios/api'
    export default {
      components: {
        exportFile
      },
        name: "list",
      data(){

          return {

            pickerOptions: {
              shortcuts: [{
                text: '最近一周',
                onClick(picker) {
                  const end = new Date();
                  const start = new Date();
                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                  picker.$emit('pick', [start, end]);
                }
              }, {
                text: '最近一个月',
                onClick(picker) {
                  const end = new Date();
                  const start = new Date();
                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                  picker.$emit('pick', [start, end]);
                }
              }, {
                text: '最近三个月',
                onClick(picker) {
                  const end = new Date();
                  const start = new Date();
                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                  picker.$emit('pick', [start, end]);
                }
              }]
            },
              companyForm:{
              coName:"",
              coCreditCode:"",
              coType:"",
              coNature:"",
              status:"",
              startTime:"",
              endTime:""
            },

            srcList:[],
            timeScope:"",
            fileList:[],
            isAll: true, //设置导出弹窗字段全选或取消全选
            exportDialogVisible:false, //导出弹窗
            sonChecked: [],
            exportData:[],
            roleOptions: ['编号', '公司名称', '统一社会信用代码', '公司类型', '公司性质', '总公司标志', '状态', '修改时间', '修改人'], //传显示导出字段的数据到子窗口
            company: {
              id:"",
              coCreditCode: "",
              coName: "",
              coType: "",
              businessTime: "",
              coAddress: "",
              businessScope: "",
              legalRepresentative: "",
              registeredCapital: '',
              setDate: "",
              registeredAuthority: "",
              approvalDate: "",
              registeredStatus: "",
              businessLicense: "",
              coNature: "",
              registeredAddress: "",
              openBank: "",
              coAccount: '',
              // fileList:[],
              registeredPhone: "",
              coMark: "",
              modifyEmp: "0",
              createTime: "",
              modifyTime:"",
              remark: "",
              isDelete: "0",
              createEmp: "0",
              status: "1",
              modifyEmpName:"",
            },
            tableData: [],
            multipleSelection: [],
            detailDialogVisible: false,
            page: {
              total: 10,
              currentPage: 1,
              pageSize: 10
            },
            isLoading: false,
            // 按钮控制判断变量
            isBtnCreate: false,
            isBtnUpdate: true,
            isBtnDelete: true,
            buttonPermission: []
          }
      },
      mounted: function () {
        this.loadTable();
        this.buttonPermission = JSON.parse(sessionStorage.getItem("buttonList")).company;
      },
      methods:{
        open(file){
          window.open(file.url);
        },
        removeFalse(){
          return false;
        },
        fileExport() {
          this.exportDialogVisible = true;
          this.sonChecked = []; //控制第二次弹窗时清除数据
        },
        getCheckList(val){
          this.checkedList = val;
        },
        writeFile() {
          let checked = this.checkedList;
          let array = new Array();
          //选中字段则导出，否则提示选择
          if (checked.length > 0){
            for(let i = 0; i < checked.length; i++){
              switch(checked[i]){
                case "公司名称": array[i] = "coName";break;
                case "编号": array[i] = "id";break;
                case "统一社会信用代码": array[i] = "coCreditCode";break;
                case "公司类型": array[i] = "coType";break;
                case "公司性质": array[i] = "coNature";break;
                case "总公司标志": array[i] = "coMark";break;
                case "状态": array[i] = "status";break;
                case "修改时间": array[i] = "modifyTime";break;
                case "修改人": array[i] = "modifyEmp";break;
              }
            }
            //excel数据导出
            require.ensure([], () => {
              const {
                export_json_to_excel
              } = require('../../excel/Export2Excel');
              const tHeader = checked;
              const filterVal = array;
              const list = this.generate(this.exportData);
              const data = this.formatJson(filterVal, list);
              let excelName = checked.toString() + this.getDate();
              export_json_to_excel(tHeader, data, excelName);
              this.$message('导出成功，请关注下载文件！');
            })
          }else {
            this.$message('请选择要导出的字段！');
          }

        },
        generate(data){
          if(data.length>0)
          {
            for(let i =0;i<data.length;i++)
            {
              if(data[i].status == 0)
              {
                data[i].status ="无效";
              }
              else if(data[i].status ==1)
              {
                data[i].status = "有效";
              }
              if(data[i].coNature == 0)
              {
                data[i].coNature ="一般纳税人";
              }
              else if(data[i].coNature ==1)
              {
                data[i].coNature = "小规模纳税人";
              }
              if(data[i].coMark == 0)
              {
                data[i].coMark = "总公司";
              }
              else if(data[i].coMark == 1)
              {
                data[i].coMark = "子公司";
              }
              if(data[i].coType == 0)
              {
                data[i].coType = "股份有限公司";
              }
              else if(data[i].coType == 1)
              {
                data[i].coType = "有限责任公司";
              }
            }
          }
          return data;
        },
        confirm() {
          this.isLoading = true;
          this.$axios.get('/api/orgmanagement/company/management/getCompanyList',
            {params:
                {
                  date : new Date(),
                  companyForm: this.companyForm,
                  pageNum: 0,
                  pageSize: 0
                }
            })
          //then获取成功；response成功后的返回值（对象）
            .then(response=>{
              if (response.data.code === 200) {
                this.exportData= response.data.data;
                this.writeFile();
                this.dialogClose();
                this.isLoading = false;
              }else {
                this.isLoading = false;
                this.$message(response.data.msg);
              }
            })
            //获取失败
            .catch(error=>{
              this.$message('网络错误，不能访问，请联系管理员');
            })
        },
        formatJson(filterVal, jsonData) {
          return jsonData.map(v => filterVal.map(j => v[j]))
        },
        selectAll() {
          if (this.isAll) {
            this.sonChecked = this.roleOptions;
            this.isAll = false;
          }else {
            this.sonChecked = [];
            this.isAll = true;
          }
        },
        dialogClose() {
          this.exportDialogVisible = false;
          this.isAll = true;
        },
          //更新公司
        updateCompany () {
          if (this.checkChoiceNum()) {
            this.$router.push({
              path: '/company/detail',
              query: {isUpdate: "1", coCreditCode: this.multipleSelection[0].coCreditCode}
            })
          }
        },
        //表格导出
        exportExcel () {
          /* generate workbook object from table */
          let wb = XLSX.utils.table_to_book(document.querySelector('#companyTable'));
          /* get binary string as output */
          let wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' });
          try {
            FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), 'companyTable.xlsx');
          } catch (e)
          {
            if (typeof console !== 'undefined')
              console.log(e, wbout)
          }
          return wbout
        },
        closeExportDailog(){
          this.isAll = true;
        },
        handleSizeChange(val) {
          this.page.pageSize = val;
          this.loadTable('page')
        },
        //  显示第几页数据
        handleCurrentChange(val) {
          this.page.currentPage = val;
          this.loadTable('page')
        },
        // 处理按钮权限
        checkBtnAuthority(row){
          if (0 === row.length ) {
            this.isBtnUpdate = true;
            this.isBtnDelete = true;
          }else{
              this.isBtnUpdate = false;
              this.isBtnDelete = false;
          }
        },
        // 修改选中的数据
        handleSelectionChange(row) {
          this.checkBtnAuthority(row);
          if (0 === row.length ) return;
          if (1 < row.length ) {
            //清除所有选择项
            this.$refs.multipleTable.clearSelection();
            //勾选最后一个点击的项目
            this.multipleSelection = row.pop();
            this.$refs.multipleTable.toggleRowSelection(this.multipleSelection);
          } else {
            this.multipleSelection = row;

          }
        },
        // 处理单元格点击事件,点击公司编号显示公司详情
        handleCellClick(row,column){
          this.fileList = [];
          if("编号" == column.label){
            this.$axios({
              url:"/api/orgmanagement/company/management/getCompanyByCoCreditCode",
              method:"get",
              params:{
                date : new Date(),
                coCreditCode:row.coCreditCode,
                accountLogin:sessionStorage.getItem("accountLogin")
              }
            }).then(response => {
              if (response.data.code == 200){
                this.srcList = [];
                this.company = response.data.data.company;
                if (null !== response.data.data.company.coType) {
                  this.company.coType = response.data.data.company.coType.toString()
                }
                this.fileList = response.data.data.fileList;
                for (let i = 0; i < response.data.data.fileList.length; i++) {
                  this.srcList.push(response.data.data.fileList[i].url);
                }
              }else {
                this.$message.error(response.data.msg)
              }
            })
            this.detailDialogVisible = true ;
          }
        },
        checkChoiceNum() {
          let msg = '';
          if (this.multipleSelection.length == 0) {
            msg = '请选中一条记录进行操作！'

          } else if (this.multipleSelection.length > 1) {
            msg = '只能选中一条记录进行操作!'

          } else {
            return true
          }
          //弹窗提示
          this.$alert(msg, '提示', {
            confirmButtonText: '确定',
          });
        },
        deleteCompany() {
          if (this.checkChoiceNum()) {
            this.$confirm('确定删除公司？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.$axios.get('/api/orgmanagement/company/management/deleteCompanyByCode',
                {
                  params:
                    {
                      date : new Date(),
                      accountLogin: sessionStorage.getItem("accountLogin"),
                      coCreditCode: this.multipleSelection[0].coCreditCode
                    }
                }).then(response => {
                if (response.data.code === 200) {
                  this.$message({
                    type: 'success',
                    message: '删除成功!'
                  });
                  this.loadTable();
                } else {
                  this.$message({
                    type: 'error',
                    message: response.data.msg
                  });
                }
              }).catch(error => {
                this.$message('网络错误，不能访问，请联系管理员');
              })
            }).catch(() => {
            });
          }
        },
        createCompany() {
          this.$router.push({path:'/company/detail',query:{isUpdate:"0"}})
        },
        getDate(){
          let date = new Date();
          let resultDate = date.getFullYear()+"-";
          if ((date.getMonth()+1) < 10){
            resultDate += "0";
          }
          resultDate += "-";
          if (date.getDate() < 10){
            resultDate += "0";
          }
          return resultDate;
        },
        loadTable(type) {
          if (type !== 'page') {
            this.page.currentPage = 1;
          }
          if (this.timeScope !== null && this.timeScope !== ""){
            this.companyForm.startTime = this.timeScope[0];
            this.companyForm.endTime = this.timeScope[1];
          } else {
            this.companyForm.startTime = "";
            this.companyForm.endTime = "";
          }
            this.isLoading = true;
            this.$axios.get('/api/orgmanagement/company/management/getCompanyList',
              {
                params:
                  {
                    date : new Date(),
                    pageNum: this.page.currentPage,
                    pageSize: this.page.pageSize,
                    companyForm: this.companyForm
                  }
              })
            //then获取成功；response成功后的返回值（对象）
              .then(response => {
                if (response.data.code === 200) {
                  this.tableData = response.data.data.pageData;
                  this.page.total = response.data.data.total;
                  this.isLoading = false;
                } else {
                  this.isLoading = false;
                  this.$message(response.data.msg);
                }
              })
              //获取失败
              .catch(error => {
                this.$message('网络错误，不能访问，请联系管理员');
              })
        }
      }
    }
</script>

<style scoped>

.image-style{
  width: 100px;
  height: 100px;
  padding: 5px 5px 5px 5px;
}
</style>
