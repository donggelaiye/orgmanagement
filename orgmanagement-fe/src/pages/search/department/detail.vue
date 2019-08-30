<template>

  <div>

    <el-form ref="deptInfoDetail" status-icon :model="deptInfo" label-width="120px" size="mini">
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>基本信息</h3>
        </el-col>
        <el-col :offset="1" :span="23">
          <el-row>
            <el-col :span="11">
              <el-form-item label="部门编号：">
                <el-input v-model="deptInfo.dept.deptId" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="部门名称：">
                <el-input v-model="deptInfo.dept.deptName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="负责人ID：">
                <el-input v-model="deptInfo.dept.empId" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="负责人姓名：">
                <el-input v-model="deptInfo.dept.empName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="手机：">
                <el-input v-model="deptInfo.dept.empPhone" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="邮箱：">
                <el-input v-model="deptInfo.dept.empEmail" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="座机号：">
                <el-input v-model="deptInfo.dept.telephone" readonly>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="所在城市：">
                <el-input v-model="deptInfo.cityName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="22">
              <el-form-item label="详细地址：" prop="address">
                <el-input type="textarea"
                          rows="1"
                          v-model="deptInfo.dept.address"
                          maxlength="255"
                          resize="none"
                          readonly
                ></el-input>
              </el-form-item>
            </el-col>

          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="经度：">
                <el-input v-model="deptInfo.dept.lon" readonly>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="纬度：">
                <el-input v-model="deptInfo.dept.lat" readonly>
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
        <el-col :offset="1" :span="23">
          <el-row>
            <el-col :span="11">
              <el-form-item label="部门级别：">
                <el-input v-model="deptInfo.dept.deptLevel" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="上级部门">
                <el-input v-model="deptInfo.superDeptName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="22">
              <el-form-item label="支持业务线：">
                <el-checkbox-group v-model="deptInfo.bussinessLineArr">
                  <el-checkbox label="1" :disabled="true">买买车</el-checkbox>
                  <el-checkbox label="2" :disabled="true">闪贷</el-checkbox>
                  <el-checkbox label="3" :disabled="true">租车</el-checkbox>
                  <el-checkbox label="4" :disabled="true">专车</el-checkbox>
                  <el-checkbox label="5" :disabled="true">保险</el-checkbox>
                </el-checkbox-group>
              </el-form-item>
            </el-col>

          </el-row>

          <el-row>
            <el-col :span="11">
              <el-form-item label="部门类型：">
                <el-input v-model="deptInfo.dept.deptType" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="办公点标识：">
                <el-input v-model="deptInfo.dept.officeSpot" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>关联公司列表</h3>
        </el-col>
        <el-col :span="24">
          <el-col>
            <el-table
              ref="multipleTable"
              :data="deptInfo.companyList"
              class="table—style" border
              size="mini">
              <el-table-column
                prop="id"
                label="编号"
                width="50">
              </el-table-column>
              <el-table-column
                prop="coName"
                label="公司名称"
                width="80">
              </el-table-column>
              <el-table-column
                prop="coCreditCode"
                label="统一社会信用代码"
                width="120">
              </el-table-column>
              <el-table-column
                prop="coType"
                label="类型"
                width="100">
                <template slot-scope="scope">
                  <span v-if="scope.row.coType === 0">股份有限公司</span>
                  <span v-else="scope.row.coType === 1">有限责任公司</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="coNature"
                label="公司性质"
                width="100">
                <template slot-scope="scope">
                  <span v-if="scope.row.coNature === 0">一般纳税人</span>
                  <span v-else="scope.row.coNature === 1">小规模纳税人</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="coMark"
                label="总公司标志"
                width="100">
                <template slot-scope="scope">
                  <span v-if="scope.row.coMark === 0">总公司</span>
                  <span v-else="scope.row.coMark === 1">子公司</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="status"
                label="状态"
                width="58">
                <template slot-scope="scope">
                  <span v-if="scope.row.status === 0">无效</span>
                  <span v-else="scope.row.status === 1">有效</span>
                </template>
              </el-table-column>
            </el-table>
          </el-col>

        </el-col>
      </el-row>



      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>关联城市列表</h3>
        </el-col>
        <el-col :span="24" class="margin-left20">
          <el-col >
<!--            <el-table-->
<!--              ref="multipleTable"-->
<!--              :data="deptInfo.cityList"-->
<!--              class="table—style" border-->
<!--              size="mini">-->
<!--              <el-table-column-->
<!--                prop="city"-->
<!--                label="city"-->
<!--                width="50" >-->
<!--                <template slot-scope="scope">{{ scope.row.date }}</template>-->
<!--              </el-table-column>-->

<!--            </el-table>-->
            <span class="border" v-for="city in deptInfo.cityList" key="city">{{city}}</span>
          </el-col>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>其他信息</h3>
        </el-col>
        <el-col :span="24">
          <el-row>
            <el-col :span="11">
              <el-form-item label="新建时间：">
                <el-input v-model="deptInfo.dept.createTime" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="新建人：">
                <el-input v-model="deptInfo.createEmpName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="修改时间：">
                <el-input v-model="deptInfo.dept.modifyTime" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="修改人：">
                <el-input v-model="deptInfo.modifyEmpName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="状态：">
                <el-input v-model="deptInfo.dept.status" readonly>
                </el-input>
              </el-form-item>
            </el-col>

          </el-row>
          <el-row>
            <el-col :span="22">
              <el-form-item label="备注：">
                <el-input type="textarea" v-model="deptInfo.dept.remark" readonly autosize resize="none"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>






    </el-form>

  </div>

</template>

<script>
    export default {
      name: "dept_info_detail",
      props:["deptInfoDetail"],
      data(){
        return{
          deptInfo: this.deptInfoDetail,
        }

      },
      created() {
        console.log(this.deptInfo)
      },
      watch:{
        deptInfoDetail: function() {
          this.deptInfo = this.deptInfoDetail;
        }
      },
      methods:{

      }

    }
</script>

<style scoped>
.border{
  border: #EBEEF5 solid 1px ;
  padding: 8px 15px 8px 15px;
}
</style>
