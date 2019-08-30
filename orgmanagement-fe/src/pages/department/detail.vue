<template>
  <div>
    <el-form ref="deptDetail" :rules="rules" status-icon :model="deptInfo" label-width="120px" size="mini">
      <el-row size="mini">
        <el-col :span="20" class="margin-left20 font-title">
        <h3>基本信息</h3>
      </el-col>
        <el-col :span="24">
            <el-row>
              <el-col :span="8">
                <el-form-item label="部门编号：" prop="deptId" >
                  <el-input v-model="deptInfo.deptId" clearable @blur="setDeptLevel" :disabled="isUpdate"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="部门名称：" prop="deptName">
                  <el-input v-model.trim="deptInfo.deptName"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="负责人ID：" prop="empId">
                  <el-input v-model="deptInfo.empId" readonly></el-input>
                  <el-button @click="empDialogVisible = true">选择</el-button>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="负责人姓名：">
                  <el-input v-model="deptInfo.empName" :disabled="true" placeholder="自动带入"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="手机：" prop="empPhone">
                  <el-input v-model="deptInfo.empPhone"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="邮箱：" prop="empEmail">
                  <el-input v-model="deptInfo.empEmail"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="座机号：" prop="telephone">
                  <el-input v-model="deptInfo.telephone" @change="insertSeparator" ref="telephone">
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="所在城市：" prop="cityBelonging">
                  <el-autocomplete
                    popper-class="my-autocomplete"
                    v-model="cityTest"
                    :fetch-suggestions="querySearch"
                    placeholder="请输入内容"
                    @focus="getCityList"
                    @select="handleSelect"
                    @change="cityChange"
                    @blur="cityChange"
                  >
                    <template slot-scope="scope">
                      <div class="name">{{ scope.item.name }}</div>
                    </template>
                  </el-autocomplete>
                </el-form-item>
              </el-col>
            </el-row>
          <el-row>
            <el-col :span="14">
              <el-form-item label="详细地址：" prop="address">
                <el-input type="textarea"
                          rows="1"
                          v-model="deptInfo.address"
                          maxlength="255"
                          resize="none"
                          placeholder="点击调用地图！"
                          @focus="openMap"
                          :disabled="addressDisabled"
                ></el-input>
              </el-form-item>
            </el-col>

          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="经度：">
                <el-input v-model="deptInfo.lon" :disabled="true">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="纬度：">
                <el-input v-model="deptInfo.lat" :disabled="true">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>



      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>业务信息</h3>
        </el-col>
        <el-col :span="24">
            <el-row>
              <el-col :span="8">
                <el-form-item label="部门级别：">
                  <el-select v-model="deptInfo.deptLevel" placeholder="根据部门编号自动带入" class="select-width" :disabled="true">
                    <el-option label="总部" value="0"></el-option>
                    <el-option label="分公司" value="1"></el-option>
                    <el-option label="管理部" value="2"></el-option>
                    <el-option label="区域" value="3"></el-option>
                    <el-option label="办公点" value="4"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="上级部门">
                  <el-input v-model="deptName" :disabled="true"  placeholder="自动带入"></el-input>
                  <el-input v-model="deptInfo.superDeptId" style="display: none"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="16">
                <el-form-item label="支持业务线：" prop="bussinessLine">
                  <el-checkbox-group v-model="currentBussinessLine">
                  <el-checkbox label="1" :disabled="disabledLine.line1" name="busiLine">买买车</el-checkbox>
                  <el-checkbox label="2" :disabled="disabledLine.line2" name="busiLine">闪贷</el-checkbox>
                  <el-checkbox label="3" :disabled="disabledLine.line3" name="busiLine">租车</el-checkbox>
                  <el-checkbox label="4" :disabled="disabledLine.line4" name="busiLine">专车</el-checkbox>
                  <el-checkbox label="5" :disabled="disabledLine.line5" name="busiLine">保险</el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
              </el-col>

            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="部门类型：" prop="deptType" v-if="isDisplay.typeIsDis===false">
                  <el-select v-model="deptInfo.deptType" placeholder="" class="select-width" :disabled="isDisplay.typeIsDis">
                    <el-option label="门店" value="0"></el-option>
                    <el-option label="停车场" value="1"></el-option>
                    <el-option label="交车中心" value="2"></el-option>
                    <el-option label="维修厂" value="3"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="办公点标识：" prop="officeSpot" v-if="isDisplay.spotIsDis===false">
                  <el-input v-model="deptInfo.officeSpot" :disabled="isDisplay.spotIsDis"></el-input>
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
                <el-input v-model="deptInfo.createTime" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="新建人：">
                <el-input v-model="createEmpName" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="修改时间：">
                <el-input v-model="deptInfo.modifyTime" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="修改人：">
                <el-input v-model="modifyEmpName" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="状态：">
                <el-select v-model="deptInfo.status" placeholder="" class="select-width" :disabled="true">
                  <el-option label="有效" value="1" ></el-option>
                  <el-option label="无效" value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

          </el-row>
          <el-row>
            <el-col :span="15">
              <el-form-item label="备注：" props="remark">
                <el-input type="textarea" rows="1" v-model="deptInfo.remark" maxlength="200" autosize resize="none" :disabled="remarkDisabled"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="6" :span="5" class="margin-top20">
          <el-button type="primary" size="normal" @click="onSubmit('deptDetail')">保存</el-button>
          <el-button type="primary" size="normal" @click="cancel">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-dialog ref="dialog" title="选择负责人ID" :visible.sync="empDialogVisible" width="75%" top="2vh">
    <EmpSelect :parentVal="empDialogVisible" v-on:checkInfo="getEmployee"></EmpSelect>
  </el-dialog>

    <el-dialog ref="dialog" title="详细地址搜索"
               :visible.sync="mapDialogVisible"
               top="5vh"
               v-if="mapDialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
                >
      <Map_dialog :parentVal="center" v-on:checkInfo="getAddress" :inputVal="addressVal"></Map_dialog>
    </el-dialog>
  </div>

</template>

<script>
    import '../../axios/api';
    import EmpSelect from '../department/components/selectEmployee';
    import Map_dialog from "../department/gaode_map/map_dialog";

    export default {
      name: "dept_search_list",
      components:{
        Map_dialog,
        EmpSelect
      },
      data() {
        let bussinessLineValid = (rule, value, callback) => {
          if(this.currentBussinessLine.length===0){
            callback(new Error("至少选择一条业务线！"));
          }else{
            callback();
          }
        };
        let typeNum = this.$route.params.type;
        let deptIdValid=(rule, value, callback)=>{
            if('2'=== typeNum){
              callback();
            }
            this.$axios.get('/api/orgmanagement/department/management/getDeptCount', {
              params: {
                deptId: value,
                date: new Date().getTime()
              }
            }).then(response => {
              if (0 === response.data.data) {
                callback();
              }else{
                callback(new Error('部门编号已存在，不允许重复创建'));
              }
            }).catch(error => {
              this.$message('网络错误，不能访问，请联系管理员');
            })
        };
        let cityValid=(rule, value, callback)=>{
          setTimeout(()=>{
            if(this.cityTest.length===0){
              this.deptInfo.isCity='';
              this.deptInfo.cityInterCode='';
              this.deptInfo.countyInterCode='';
              callback(new Error("请选择一个城市"));
            }else {
                if(this.cityTest!==this.citySelection || this.deptInfo.isCity===''){
                  callback(new Error("请从列表中选中一项"));
                }else {
                  callback();
                }
            }},300)
        };
        let officeSpotValid=(rule, value, callback)=>{
          if('2'=== typeNum){
            callback();
          }else {
            if(this.deptInfo.deptId.indexOf('B')!==0){
              callback();
            }
          }

          setTimeout(()=>{
            if(!this.isDisplay.spotIsDis){
              if(null===this.deptInfo.officeSpot|| ''===this.deptInfo.officeSpot){
                callback(new Error("请输入办公点标识"));
              }else{
                this.$axios.get('/api/orgmanagement/department/management/getSpotCount', {
                  params: {
                    superDeptId: this.deptInfo.superDeptId,
                    officeSpot: value,
                    date: new Date().getTime()
                  }
                }).then(response => {
                  if (0 === response.data.data) {
                    callback();
                  }else{
                    callback(new Error('同上级办公点标识已存在'));
                  }
                }).catch(error => {
                  this.$message('网络错误，不能访问，请联系管理员');
                })
              }
            }
          },200);
        };
        let deptTypeValid=(rule, value, callback)=>{
          if('2'=== typeNum){
            callback();
          }else {
            if(this.deptInfo.deptId.indexOf('B')!==0){
              callback();
            }
          }
          setTimeout(()=>{
            if(!this.isDisplay.typeIsDis){
              if(null===this.deptInfo.deptType|| ''===this.deptInfo.deptType){
                callback(new Error("请选择部门类型"));
              }else{
                callback();
              }
            }
          },200);
        };
        let addressValid=(rule, value, callback)=>{
          if(this.deptInfo.deptId.indexOf('B')!==0){
            callback();
          }else {
            setTimeout(()=>{
              if(this.deptInfo.address.length===0){
                callback(new Error("详细地址不能为空！"));
              }else{
                if(this.deptInfo.address.indexOf(this.cityTest)===-1){
                  callback(new Error("地址与所在城市不匹配！"));
                }else{
                  callback();
                }
              }
            },200);
          }
        };

        return {
          remarkDisabled:false,
          addressDisabled:true,
          subBussinessLine:[],
          center:[],
          isCurrentPageLink:false,
          restaurants:[],
          cityTest:'',
          citySelection:'',
          deptId:'',
          deptName:'',
          typeNum:'',
          areaId:'',
          cityList:[],
          telephoneLength:0,
          isDisplay:{typeIsDis:true,spotIsDis:true},//部门类型与办公点标识是否显示
          disabledLine:{line1:false,line2:false,line3:false,line4:false,line5:false},//业务线是否禁用
          bussinessLine:[],//通过deptId获取上级部门相关业务线
          currentBussinessLine:[],//当前部门支持的业务线
          empDialogVisible:false,
          mapDialogVisible:false, //地图弹窗
          empDialogIsAppendToBody:false,
          createEmpName:'',
          modifyEmpName:'',
          employee:{},
          isUpdate:'',
          deptInfo:{
            id:'',
            deptId:'',
            officeSpot:'',
            deptName:'',
            empId:'',
            empName:'',
            empPhone:'',
            empEmail:'',
            cityInterCode:'',
            countyInterCode:'',
            isCity:'',
            address:'',
            lon:'',
            lat:'',
            telephone:'',
            deptLevel:'',
            superDeptId:'',
            deptType:'',
            status:'',
            isDelete:'',
            createEmp:'',
            createTime:'',
            modifyEmp:'',
            modifyTime:'',
            remark:'',
          },
          addressVal:{
            address:'',
            lon:'',
            lat:'',
            city:''
          }, //初始化地图时将地址传给子组件
          rules: {
            deptId: [
              { required: true, message: '请输部门编号', trigger: 'blur' },
              {
                pattern: /^(Z|F|G|Q|B)[0-9a-zA-Z]{6}$/,
                message: '* 首字母Z|F|G|Q|B，接6位字母数字串',
                trigger: 'blur'
              },
              { validator: deptIdValid, trigger: 'blur'}
            ],
            deptName: [
              { required: true, message: '请输入部门名称', trigger: 'blur' },
              { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: 'blur' }
            ],
            empPhone: [
              {required: true, message: '请输入手机号', trigger: 'blur'},
              {
                pattern: /^(13[0-9]|15[^4,\D]|16[6]|17[0-9]|18[0-9]|14[56789]|19[89])[0-9]{8}$/,
                message: '请输入正确的手机号',
                trigger: 'blur'
              }
            ],
            empEmail: [
              { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
            ],
            telephone:[
              {
                pattern: /^[0-9]{4}-[0-9]?[0-9]{7}$/,
                message: '请输入正确的座机号，11-12位数字',
                trigger: 'blur'
              }
              ],
            remark: [
              { min: 0, max: 200, message: '长度在 0 到 200 个字符', trigger: 'blur' }
            ],
            officeSpot:[
              {
                pattern: /^[0-9][0-9]?[0-9]?$/,
                message: '请输入正确的标识，支持3位数字',
                trigger: 'blur'
              },
              { validator: officeSpotValid, trigger: 'blur'}

            ],
            deptType:[
              { validator: deptTypeValid, trigger: 'blur'}
            ],
            bussinessLine:[
              { validator: bussinessLineValid, trigger: 'blur'}
            ],
            cityBelonging:[
              { validator: cityValid, trigger: 'blur'}
            ],
            address:[
              { validator: addressValid, trigger: 'blur'}
            ]
          }
        }
      },
      created:function(){
        this.checkedType();
      },
      methods:{
        //更新全局部门树结构
        getAllDeptTree() {
          this.$axios.get('/api/orgmanagement/dept/tree',{
            params:{date: new Date().getTime()}
          }).then(res => {
            if (res.data.code === 200) {
              this.$store.commit('SET_DEPT_TREE', res.data.data);
              this.$router.push('/department/list');
            } else {
              this.$message.error(res.data.msg);
            }
          }).catch(err => {
            this.$message.error(err);
          })
        },
        checkedType(){//判断为新建子部门还是修改部门
          this.deptId=this.$route.params.deptId;
          this.deptName=this.$route.params.deptName;
          this.typeNum=this.$route.params.type;
          if(this.typeNum==='1'){
            //新建子部门操作
            this.isUpdate=false;
            this.deptInfo.superDeptId=this.deptId;
            this.getBussinessLine(this.deptId,this.checkedBussiLine);
            this.deptInfo.status = '1';
          }else if(this.typeNum==='2'){
            //修改子部门操作
            this.isUpdate=true;
            this.addressDisabled=false;
            this.remarkDisabled=true;
            this.getDepartment();
          }else{
            this.$message("未知错误");
          }
        },
        getDepartment(){
          this.$axios.get('/api/orgmanagement/department/management/getDepartmentById',
            {params:{deptId:this.deptId,date: new Date().getTime()}})
            .then(response=>{//then获取成功；response成功后的返回值（对象）
              if (response.data.code === 200) {
                this.deptInfo = response.data.data;
                this.center=[this.deptInfo.lon,this.deptInfo.lat];
                this.getSuperDept(this.deptInfo.superDeptId);
                this.getCityName(this.deptInfo.isCity);
                if(this.deptInfo.superDeptId!=null){
                  this.getBussinessLine(this.deptInfo.superDeptId,this.checkedBussiLine);//上级部门业务线
                }
                this.getBussinessLine(this.deptId,null);//当前部门业务线
                this.getSubBussinessLine(this.deptId,this.checkedSubBussiLine);
                this.deptInfo.deptLevel=this.numToString(this.deptInfo.deptLevel);
                if(this.deptInfo.deptType!==null){
                  this.deptInfo.deptType=this.numToString(this.deptInfo.deptType);
                }
                this.deptInfo.status=this.numToString(this.deptInfo.status);
                this.checkDeptType();
               this.getEmpDisplayName(this.deptInfo.createEmp,true);
                this.getEmpDisplayName(this.deptInfo.modifyEmp,false);
              }else {
                this.$message(response.data.msg);
              }
            })
            //获取失败
            .catch(error=>{
              this.$message('修改子部门未知错误');
            })
        },
        getEmpDisplayName(data,bool){
          this.$axios.get('/api/orgmanagement/department/management/getEmpDisplayName',{
            params:{empId:data,date: new Date().getTime()}
          }).then(response=>{
            if(response.data.code===200){
              if(bool){
                this.createEmpName=response.data.data[0];
              }else{
                this.modifyEmpName=response.data.data[0];
              }
            }
          }).catch(()=>{this.$message("未知错误")})
        },
        getBussinessLine(data,callback){
          this.$axios.get('/api/orgmanagement/department/management/getDeptBussinessLine',
            {params:{deptId:data,date: new Date().getTime()}})
          //then获取成功；response成功后的返回值（对象）
            .then(response=>{
              if (response.data.code === 200) {
                if(response.data.data!=null){
                  if(typeof callback==='function'){
                    this.bussinessLine= response.data.data;
                    callback(this.bussinessLine);
                  }else{
                    this.currentBussinessLine=response.data.data;
                  }
                }
                return [];
              }else {
                this.$message(response.data.msg);
              }
            })
            //获取失败
            .catch(error=>{
              this.$message('网络错误，不能获取业务线信息，请联系管理员');
            })
        },
        getSubBussinessLine(data,callback){
          this.$axios.get('/api/orgmanagement/department/management/getSubBussinessLine',
            {params:{deptId:data,date: new Date().getTime()}})
          //then获取成功；response成功后的返回值（对象）
            .then(response=>{
              if (response.data.code === 200) {
                if(response.data.data!=null){
                  this.subBussinessLine=response.data.data;
                  console.log("子部门业务线",this.subBussinessLine);
                  if(typeof callback==="function"){
                    callback(this.subBussinessLine);
                  }
                }
              }else {
                this.$message(response.data.msg);
              }
            })
            //获取失败
            .catch(error=>{
              this.$message('网络错误，不能获取业务线信息，请联系管理员');
            })
        },
        getSuperDept(data){
          this.$axios.get('/api/orgmanagement/department/management/getSuperDeptName',
            {params:{deptId:data,date: new Date().getTime()}})
          //then获取成功；response成功后的返回值（对象）
            .then(response=>{
              if (response.data.code === 200) {
                this.deptName = response.data.data;
                if(this.deptName===null||this.deptName===''){
                  this.deptName="无";
                }
              }else {
                this.$message(response.data.msg);
              }
            })
            //获取失败
            .catch(error=>{
              this.$message('网络错误，不能获取上级部门信息，请联系管理员');
            })
        },
        getCityName(isCity){//根据InterCode查询cityname
          let interCode='';
          if(isCity===1){
            interCode=this.deptInfo.cityInterCode;
          }else if(isCity===2){
            interCode=this.deptInfo.countyInterCode;
          }
          this.$axios.get('/api/orgmanagement/department/management/getCityName',
            {params:{interCode:interCode, isCity: isCity,date: new Date().getTime()}})
          //then获取成功；response成功后的返回值（对象）
            .then(response=>{
              if (response.data.code === 200) {
                this.cityTest = response.data.data;
                this.citySelection=this.cityTest;
              }else {
                this.$message(response.data.msg);
              }
            })
            //获取失败
            .catch(error=>{
              this.$message('网络错误，不能获取上级部门信息，请联系管理员');
            })
        },
        checkedBussiLine(data){//判断父级部门所支持的业务线
          if(data.indexOf('1')===-1){
            this.disabledLine.line1=true;
          }if(data.indexOf('2')===-1){
            this.disabledLine.line2=true;
          }if(data.indexOf('3')===-1){
            this.disabledLine.line3=true;
          }if(data.indexOf('4')===-1){
            this.disabledLine.line4=true;
          }if(data.indexOf('5')===-1){
            this.disabledLine.line5=true;
          }
        },
        checkedSubBussiLine(data){//判断子部门所支持的业务线
          if(data.indexOf('1')>-1){
            this.disabledLine.line1=true;
          }if(data.indexOf('2')>-1){
            this.disabledLine.line2=true;
          }if(data.indexOf('3')>-1){
            this.disabledLine.line3=true;
          }if(data.indexOf('4')>-1){
            this.disabledLine.line4=true;
          }if(data.indexOf('5')>-1){
            this.disabledLine.line5=true;
          }
          console.log(this.disabledLine)
        },
        numToString(str){
          return str.toString();
        },
        getCityList(){//获取城市列表
          if(this.cityList.length<=0){// 使用axios请求数据，将返回值赋给cityList
            this.$axios.get('/api/orgmanagement/area/city/getCityListToDept',{
              params:{date: new Date().getTime()}
            })
              .then(response => {
                if (response.data.code === 200) {
                  this.cityList = response.data.data;
                  this.restaurants=this.cityList;
                } else {
                  this.$message(response.data.msg);
                }
              })
              .catch(error => {
                this.$message('网络错误，不能访问，请联系管理员');
              })
          }
        },
        setDeptLevel(){//设置部门级别
          this.isDisplay={typeIsDis:true,spotIsDis:true};
          this.deptInfo.deptType='';
          this.deptInfo.officeSpot='';
          if(this.deptInfo.deptId.indexOf('Z')===0){
            this.deptInfo.deptLevel='0';
          }
          if(this.deptInfo.deptId.indexOf('F')===0){
            this.deptInfo.deptLevel='1';
          }
          if(this.deptInfo.deptId.indexOf('G')===0){
            this.deptInfo.deptLevel='2';
          }
          if(this.deptInfo.deptId.indexOf('Q')===0){
            this.deptInfo.deptLevel='3';
          }
          if(this.deptInfo.deptId.indexOf('B')===0){
            this.deptInfo.deptLevel='4';
            this.checkDeptType();
          }
        },
        checkDeptType(){
          if(this.deptInfo.deptId.indexOf('B')===0){
            this.isDisplay.typeIsDis=false;
            this.isDisplay.spotIsDis=false;
          }else {
            this.deptInfo.deptType = "";
            this.deptInfo.officeSpot = "";
          }

        },

        querySearch(queryString, cb) {//显示过滤后的内容
          let restaurants = this.restaurants;
          let results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
          // 调用 callback 返回建议列表的数据
          cb(results);
        },
        createFilter(queryString) {//过滤输入的城市搜索内容
          return (restaurant) => {
            return (restaurant.name.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
          };
        },
        handleSelect(item) {
          this.cityTest = item.name;
          this.citySelection=item.name;
          this.deptInfo.isCity=item.type;
          this.addressDisabled=false;
          if(item.type===1){
            this.deptInfo.cityInterCode=item.value;
            this.deptInfo.countyInterCode='';
          }else if(item.type===2){
            this.deptInfo.countyInterCode=item.value;
            this.deptInfo.cityInterCode='';
          }else {
            this.$message('异常错误，错误原因：未知');
          }
        },
        cityChange(){
          if(this.cityTest===null || ""===this.cityTest){
            this.addressDisabled=true;
            this.deptInfo.address='';
            this.deptInfo.lon='';
            this.deptInfo.lat='';
          }else{
            this.addressDisabled=false;
          }
        },
        insertSeparator(val){
          if(val.length===4){
              this.deptInfo.telephone=this.deptInfo.telephone+'-';
          }
        },

        onSubmit(formName) {
          this.$refs[formName].validate((valid) => {
            if(valid){
              if(this.typeNum==='1'){
                this.newSubmit();
              }if(this.typeNum==='2'){
                this.updateSubmit();
              }

            }else {
              return false
            }
          })
        },
        newSubmit(){
          this.isCurrentPageLink=true;
          this.$axios({
            url:"/api/orgmanagement/department/management/addDepartment",
            method:"post",
            headers: {'Content-Type': 'application/json;charset=utf-8'},
            data:{
              deptInfo:this.deptInfo,
              deptBussinessLine:this.currentBussinessLine,
              date: new Date().getTime()
            },
          })
          // this.$axios.get('/api/orgmanagement/department/management/addDepartment',
          //   {params:{deptInfo:this.deptInfo}})
            .then(response => {
              if (response.data.code === 200) {
                this.$msgbox({
                  title:'提示',
                  showCancelButton: false,
                  confirmButtonText: '确定',
                  type:'success',
                  message:'新建子部门成功！'
                }).then(() => {
                  //刷新部门树
                  this.getAllDeptTree();

                }).catch(() => {});
              } else {
                this.$message(response.data.msg);
              }
            })
            .catch(error => {
              this.$message('网络错误，不能访问，请联系管理员');
            })
        },
        updateSubmit(){
          this.isCurrentPageLink=true;
          this.$axios({
            url:"/api/orgmanagement/department/management/updateDepartment",
            method:"post",
            headers: {'Content-Type': 'application/json;charset=utf-8'},
            data:{
              deptInfo:this.deptInfo,
              deptBussinessLine:this.currentBussinessLine,
              date: new Date().getTime()
            }
          })
            .then(response => {
              if (response.data.code === 200) {
                this.$msgbox({
                  title:'提示',
                  showCancelButton: false,
                  confirmButtonText: '确定',
                  type:'success',
                  message:'修改部门成功！'
                }).then(() => {
                  //刷新部门树
                  this.getAllDeptTree();

                }).catch(() => {});
              } else {
                this.$message(response.data.msg);
              }
            })
            .catch(error => {
              this.$message('网络错误，修改部门失败，请联系管理员');
            })
        },
        cancel() {
          this.$confirm('确定取消？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.isCurrentPageLink=true;
            this.$router.push('/department/list');
          }).catch(() => {});
        },
        getEmployee(data){
          if(data!==false){
            if(data===null){
              this.deptInfo.empName='';
              this.deptInfo.empId='';
            }else if(1 === data.isDimission){
              this.$message("不能选择离职员工作为部门负责人");
            } else{
              this.deptInfo.empId=data.empId;
              this.deptInfo.empName=data.empName;
            }
          }
          this.empDialogVisible=false;
        },
        openMap(){//打开地图
          if(this.cityTest!==null || ""!==this.cityTest){
            this.mapDialogVisible=true;
            this.addressVal.address = this.deptInfo.address;
            this.addressVal.lon = this.deptInfo.lon;
            this.addressVal.lat = this.deptInfo.lat;
            this.addressVal.city=this.cityTest;
          }
        },
        getAddress(data){
          this.deptInfo.address=data.address;
          this.deptInfo.lon=data.lon;
          this.deptInfo.lat=data.lat;
          this.center=[this.deptInfo.lon,this.deptInfo.lat];
          this.mapDialogVisible=false;
        }
      },
      watch:{
        'deptInfo.telephone':function () {
          if(this.telephoneLength<this.deptInfo.telephone.length){
            if(this.deptInfo.telephone.length===4){
              this.deptInfo.telephone=this.deptInfo.telephone+"-";
            }
          }
          this.telephoneLength=this.deptInfo.telephone.length;
        }
      },
      mounted() {
        // this.checkedBussiLine();
        // this.restaurants = this.cityList;
      },
      // 组件内导航钩子，处理未保存退出的情况
      beforeRouteLeave: function(to, from , next){
        if(this.isCurrentPageLink===false && to.path != '/'){
          next(false);
          this.$confirm('是否离开当前页面?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            // 选择确定
            next();
          })
        }else{
          next();
        }
      },
    }
</script>

<style scoped>

</style>
