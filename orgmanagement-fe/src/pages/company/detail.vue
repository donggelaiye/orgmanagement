<template>
  <div>
    <el-form ref="company" :rules="rules" status-icon :model="company" label-width="120px" size="mini" >
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>公司信息</h3>
        </el-col>
        <el-col :span="24">
          <el-row>
            <el-col :span="8">
              <el-form-item label="统一信用代码：" prop="coCreditCode">
                <el-input v-model="company.coCreditCode" ref="coCreditCode"  v-bind:readonly="this.$route.query.isUpdate === '0' ? false : true" v-bind:unselectable="this.$route.query.isUpdate === '0' ? false : 'on'"></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="企业名称：" prop="coName">
                <el-input v-model="company.coName"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="公司类型：">
                <el-select v-model="company.coType" placeholder="请选择" class="select-width">
                  <el-option label="有限责任公司" value="1"></el-option>
                  <el-option label="股份有限公司" value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="营业期限：" prop="businessTime">
                <el-date-picker
                  v-model="company.businessTime"
                  type="date" style="width: 173px" :clearable="false">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="住所：" prop="coAddress">
                <el-input v-model="company.coAddress"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="经营范围：" prop="businessScope">
                <el-input v-model="company.businessScope"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="法人代表：" prop="legalRepresentative">
                <el-input v-model="company.legalRepresentative"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="注册资本：" prop="registeredCapital">
                <el-input v-model="company.registeredCapital" placeholder="单位为元" oninput = "value=value.replace(/[^\d]/g,'')"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="成立日期：" prop="setDate">
                <el-date-picker
                  v-model="company.setDate"
                  type="date" style="width: 173px" :clearable="false">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="登记机关：" prop="registeredAuthority">
                <el-input v-model="company.registeredAuthority"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="核准日期：" prop="approvalDate">
                <el-date-picker
                  v-model="company.approvalDate"
                  type="date" style="width: 173px">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="登记状态：" prop="registeredStatus">
                <el-input v-model="company.registeredStatus"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-col :span="20" class="margin-left20 font-title">
            <h3>附件信息</h3>
          </el-col>
          <el-row>
           <el-col :span="20">
            <el-form-item label="营业执照：">
              <el-upload
                class="upload-demo"
                ref="upload"
                action="http://localhost:8080/orgmanagement/company/management/test"
                :file-list="fileList"
                :on-exceed = "exceed"
                :on-preview="open"
                :multiple="true"
                :before-remove="beforeRemove"
                :on-remove="Remove"
                :limit="20"
                :on-change="getFile"
                accept="image/png,image/gif,image/jpg,image/jpeg"
                :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                <!--                  <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>-->
                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且文件大小不得超过500k</div>
              </el-upload>
            </el-form-item>
          </el-col>
          </el-row>
          <el-col :span="20" class="margin-left20 font-title">
            <h3>发票信息</h3>
          </el-col>
          <el-row>
            <el-col :span="8">
              <el-form-item label="公司性质：">
                <el-radio-group v-model="company.coNature">
                  <el-radio :label="0">一般纳税人</el-radio>
                  <el-radio :label="1">小规模纳税人</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="注册地址：" prop="registeredAddress">
                <el-input v-model="company.registeredAddress"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="开户银行：" prop="openBank">
                <el-input v-model="company.openBank"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="公司账号：" prop="coAccount">
                <el-input v-model="company.coAccount"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="注册电话：" prop="registeredPhone">
                <el-input v-model="company.registeredPhone"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="总公司标志：">
                <el-radio-group v-model="company.coMark">
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
            <el-col :span="8">
              <el-form-item label="新建时间：">
                <el-input v-model="company.createTime" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="新建人：">
                <el-input v-model="company.createEmpName" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="修改时间：">
                <el-input v-model="company.modifyTime" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="修改人：">
                <el-input v-model="company.modifyEmpName" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="15">
              <el-form-item label="备注：" props="remark">
                <el-input type="textarea" rows="4" v-model="company.remark" maxlength="200"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="6" :span="5" class="margin-top20">
          <el-button type="primary" size="normal" @click="onSubmit('company')">保存</el-button>
          <el-button type="primary" size="normal" @click="cancel">返回</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
  var date = new Date();
    export default {
        name: "detail",
      data(){
          let checkCoCreaditCode = (rule, value, callback) => {
            if (this.$route.query.isUpdate === '0'){
              if (value.length !== 15 && value.length !== 18){
                callback(new Error("长度应为15或者18位"))
              } else {
                callback();
              }
            } else {
              callback();
            }
          };
          var checkBusinessTime = (rule, value, callback) => {
            if (value !== null && this.company.setDate !== null && this.company.setDate !== "") {
              if (value < new Date(this.company.setDate)) {
                callback(new Error('营业期限不能早于成立日期'));
              } else {
                callback();
              }
            } else {
              callback();
            }
          };
        var checkSetTime = (rule, value, callback) => {
          if (value !== null && this.company.businessTime !== null && this.company.businessTime !== "") {
            if (value > new Date(this.company.businessTime)) {
              callback(new Error('成立日期不能晚于营业期限'));
            } else {
              callback();
            }
          } else {
            callback();
          }
        };
          return {
            fileOldUrl:[],
            fileOldName:[],
            fileData:[],
            fileName:[],
            fileLists:[],
            fileList:[],
            company: {
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
              coAccount: "",
              registeredPhone: "",
              coMark: "",
              modifyEmp: "",
              createTime: "",
              modifyTime: "",
              remark: "",
              isDelete: "0",
              createEmp: "",
              status: "1",
            },
            rules: {
              registeredStatus:[
                { min: 2, max: 60, message: '长度在 2 到 60 个字符', trigger: 'blur' }
              ],
              coCreditCode: [
                {required: true, message: '请输入统一信用代码', trigger: 'blur'},
                { validator:checkCoCreaditCode,trigger:'blur' },
              ],
              coName: [
                {required: true, message: '请输入公司名称', trigger: 'blur'},
                {min: 2, max: 60, message: '长度在 2 到 60 个字符', trigger: 'blur'}
              ],
              businessTime: [
                { validator:checkBusinessTime,trigger:'blur' },
              ],
              setDate:[
                { validator:checkSetTime,trigger:'blur' },
              ],
              registeredPhone: [
                {
                  pattern: /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8,9}$/,
                  message: '请输入正确的电话',
                  trigger: 'blur'
                }
              ],
              coAddress: [
                {min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur'}
              ],
              businessScope: [
                {min: 2, max: 200, message: '长度在 2 到 200 个字符', trigger: 'blur'}
              ],
              legalRepresentative: [
                {min: 2, max: 60, message: '长度在 2 到 60 个字符', trigger: 'blur'}
              ],
              registeredCapital: [
                {
                  pattern: /^[0-9]{1,20}$/,
                  message: '只能输入长度范围在1-20之间的数字',
                  trigger: 'blur'
                }
              ],
              registeredAuthority: [
                {min: 2, max: 60, message: '长度在 2 到 60 个字符', trigger: 'blur'}
              ],
              registeredAddress: [
                {min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur'}
              ],
              openBank: [
                {min: 2, max: 60, message: '长度在 2 到 60 个字符', trigger: 'blur'}
              ],
              coAccount: [
                {
                  pattern: /^[0-9]{7,20}$/,
                  message: '只能输入长度范围在7-20之间的数字',
                  trigger: 'blur'
                }
              ],
              remark: [
                {min: 0, max: 200, message: '长度在 0 到 200 个字符', trigger: 'blur'}
              ],
            }
          }
      },
      mounted:function () {
        if (this.$route.query.isUpdate == "1"){
          this.$refs.coCreditCode.readonly = "readonly";
          this.$axios({
            url:"/api/orgmanagement/company/management/getCompanyByCoCreditCode",
            method:"get",
            params:{
              date : new Date(),
              coCreditCode:this.$route.query.coCreditCode,
              accountLogin:sessionStorage.getItem("accountLogin")
            }
          }).then(response => {
            if (response.data.code == 200){
              this.company = response.data.data.company;
              if (null !== this.company.businessTime) {
                this.company.businessTime = this.company.businessTime.substring(0,10);
              }
              if (null !== this.company.approvalDate) {
                this.company.approvalDate = this.company.approvalDate.substring(0,10);
              }
              if (null !== this.company.setDate) {
                this.company.setDate = this.company.setDate.substring(0,10);
              }
              if (null !== response.data.data.company.coType) {
                this.company.coType = response.data.data.company.coType.toString()
              }
              if (null !== response.data.data.fileList) {
                this.fileList = response.data.data.fileList;
              }
            }else {
              this.$message.error(response.data.msg)
            }
          })
        }else {
          this.$axios({
            url:"/api/orgmanagement/employee/management/getEmployeeByAccount",
            method:"get",
            params:{
              date : new Date(),
              accountLogin:sessionStorage.getItem("accountLogin")
            }
          }).then(response => {
            if (response.data.code == 200){
              this.company.createEmp = response.data.data.empId;
              this.company.modifyEmp = response.data.data.empId;
              this.company.createTime = this.getDate();
              this.company.modifyTime = this.getDate();
            }else {
              this.$message.error(response.data.msg)
            }
          })
        }
      },
      methods:{
        exceed(file,fileList){
          this.$message.error("上传的文件个数不应超过20个");
        },
        open(file){
          window.open(file.url);
        },
          getDate(){
            let resultDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" ";
            if (date.getHours() < 10){
              resultDate += "0";
            }
            resultDate += date.getHours() + ":"
            if (date.getMinutes() < 10){
              resultDate += "0";
            }
            resultDate += date.getMinutes() + ":"
            if (date.getSeconds() < 10){
              resultDate += "0"
            }
            resultDate += date.getSeconds();
            return resultDate;
          },
        cancel(){
          this.$router.push('/company/list');
        },
        handleRemove(file, fileList) {
          console.log(file, fileList);
        },
        handlePreview(file) {
          console.log(file);
        },

        getBase64(file) {
          return new Promise(function(resolve, reject) {
            let reader = new FileReader();
            let imgResult = "";
            reader.readAsDataURL(file);
            reader.onload = function() {
              imgResult = reader.result;
            };
            reader.onerror = function(error) {
              reject(error);
            };
            reader.onloadend = function() {
              resolve(imgResult);
            };
          });
        },
        getFile(file, fileList) {
          console.log(fileList);
          if (file.size > 500000){
            this.$message.error("都让你别插入超过500kb的图片了");
            fileList.pop();
          }else {
            this.getBase64(file.raw).then(res => {
              this.fileData.push(res);
            });
            this.fileName.push(file.name);
          }
        },
        beforeRemove(file, fileList){
          return this.$confirm('确定删除图片'+file.name+'？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
        },
        Remove(file, fileList) {
            this.fileList = fileList;
          if (file.status === "ready"){
            for (let i = 0; i < this.fileName.length; i++) {
              if (file.name === this.fileName[i]){
                this.fileName.splice(i,1);
                this.fileData.splice(i,1);
                return true;
              }
            }
          } else if (file.status === "success"){
            for (let i = 0; i < this.fileList.length; i++){
              if (file.name === this.fileList[i].name){
                this.fileList.splice(i,1);
              }
            }
          }
        },
        onSubmit(formName) {
            for (let i = 0; i<this.fileList.length; i++){
              if (this.fileList[i].status === "success"){
                this.fileOldName.push(this.fileList[i].name);
                this.fileOldUrl.push(this.fileList[i].url)
              }
            }
            for (let i = 0; i<this.fileName.length; i++){
              let fileNameElement= this.fileName[i].split(".");
              if (fileNameElement[1] !== "png" && fileNameElement[1] !== "jpg"){
                this.$message.error("文件类型错误，请检查之后重新提交");
                return;
              }
            }
          this.$refs[formName].validate((valid) => {
            if(valid){
              console.log('submit!');
              this.$axios({
                  url:'/api/orgmanagement/company/management/updateCompany',
                  method:"post",
                  headers:{
                    'Content-Type':'application/json; charset=utf-8'
                  },
                data:{
                  date : new Date(),
                    accountLogin:sessionStorage.getItem("accountLogin"),
                    isUpdate:this.$route.query.isUpdate,
                    fileOldName:this.fileOldName,
                    fileOldUrl:this.fileOldUrl,
                    company:this.company,
                    fileName:this.fileName,
                    fileData:this.fileData
                }
                },
                ).then(response=>{
                  if(this.$route.query.isUpdate === "0"){
                    if (response.data.code == 200) {
                      this.$message.success('新增公司成功!');
                      this.$router.push('/company/list');
                    }else {
                      this.$message.error(response.data.msg);
                    }
                  }else if (this.$route.query.isUpdate === "1"){
                    if (response.data.code == 200) {
                      this.$message.success('修改公司成功!');
                      this.$router.push('/company/list');
                    }else {
                      this.$message.error(response.data.msg);
                    }
                  }

              })
            }else {
              return false
            }
          })
        },
      }
    }
</script>

<style scoped>

</style>
