<template>
<div>
  <el-container>
    <el-header height="200">
        <el-col :offset="1" :span="22">
          <el-row>
            <el-col :span="21" class="address-input">
              <span>所在城市(区县)：<span style="font-size: 15px;"><strong>{{this.addressObj.city}}</strong>
              </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所在地址：</span>
                <el-autocomplete
                  popper-class="my-autocomplete"
                  v-model="addressObj.address"
                  placeholder="请输入内容"
                  :fetch-suggestions="returnResult"
                  @focus="inputFocus('true')" @blur="inputFocus('false')"
                  @select="handleSelect"
                  @input="inputKeyword"
                  @change="changeAddress"
                  size="mini">
                  <template slot-scope="scope">
                    <div class="name">{{ scope.item.name }}</div>
                    <span class="addr">{{ scope.item.district }}</span>
                  </template>
                </el-autocomplete>

            </el-col>
            <el-col :span="2">
              <el-button @click="confirm" size="small">确认</el-button>
            </el-col>
          </el-row>
        </el-col>
    </el-header>
    <el-main>
      <el-col :offset="1" :span="22">
        <div id="container" class="map" tabindex="0"></div>
      </el-col>
    </el-main>
    <el-footer>
      <el-form ref="lonlat" :rules="rules" status-icon :model="addressObj" label-width="120px" size="mini">
      <el-col :offset="1" :span="22">
        <el-row>
          <el-col :span="11">
            <el-form-item label="经度：" prop="lon">
            <el-input placeholder="请输入内容" v-model="addressObj.lon" style="width:100%" size="small" :disabled="isSelect">
            </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="纬度：" prop="lat">
            <el-input placeholder="请输入内容" v-model="addressObj.lat" style="width:100%" size="small" :disabled="isSelect">
            </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-col>
      </el-form>
    </el-footer>
  </el-container>
</div>
</template>

<script>
    import AMap from 'AMap';
    import AMapUI from 'AMapUI'
    const searchDetail ='https://restapi.amap.com/v3/geocode/geo?key=7c76f067e9f475e7fd2ee20284a9f985&s=rsv3&city=35&address=';

    export default {
      name: "map_dialog",
      props: ["inputVal"],
      data(){
        return {
          isSelect:false,
          addressObj:{
            address:'',
            lon:'',
            lat:'',
            city:''
          },
          url:'',
          inputIsFocus:false,
          isFirst:false,
          isDoubleSubmit:false,
          result:[],
          center:['116.407526','39.90403'],
          rules:{
            lon:[
              {
              pattern: /^[-]?(((\d|[1-9]\d|1[0-7]\d|0{1,3})\.\d{0,6})|(\d|[1-9]\d|1[0-7]\d|0{1,3})|180\.0{0,6}|180)$/,
              message: '经度范围-180度到180度',
              trigger: 'blur'
            }],
            lat:[
              {
              pattern:  /^[-]?([0-8]?\d{1}\.\d{0,6}|90\.0{0,6}|[0-8]?\d{1}|90)$/,
              message: '纬度范围-90度到180度',
              trigger: 'blur'
            }]
          }
        }
      },
      methods: {
        returnResult(str,cb){
            if(this.result===null || this.result===[]){
              cb([]);
            }else{
              cb(this.result);
            }
        },
        inputKeyword(data){
          this.isSelect=false;
          if(typeof data ==='undefined'){
            this.isDoubleSubmit=true;
          }else{
            if(data.length===0){
              this.addressObj.lon="";
              this.addressObj.lat="";
            }
            this.isDoubleSubmit=false;
          }
          if(!this.isDoubleSubmit){
            this.placeSearch(data,this.setResult);
          }
        },
        inputFocus(data){
          this.inputIsFocus=data;
          if(data){
            this.placeSearch(this.addressObj.address,this.setResult);
          }
        },
        placeSearch(keywords,setResult) {
            AMap.plugin(['AMap.Autocomplete'], () => {
              // 实例化Autocomplete
              let autoOptions = {
                input:'tipInput'
              };
              // 开始搜索
              let autocomplete = new AMap.Autocomplete(autoOptions);
              autocomplete.search(keywords, function(status,result) {
                // 搜索成功时，result即是对应的匹配数据
                if(typeof setResult === 'function'){
                  if(status==="complete"){
                    setResult(result.tips);
                  }else{
                    setResult([]);
                  }
                }
              });
            });
        },
        loadMap(setAddress,msgbox,center){
            AMapUI.loadUI(['misc/PositionPicker'], function(PositionPicker) {
              let map = new AMap.Map('container', {
                zoom: 16,
                scrollWheel: true,
                center:center
              });
              let positionPicker = new PositionPicker({
                mode: 'dragMap',
                map: map,
              });
                positionPicker.on('success',function(positionResult) {
                    if(typeof setAddress ==='function'){
                        setAddress(positionResult);
                    }
                });
                positionPicker.on('fail', function() {
                    if(typeof msgbox ==='function'){
                      msgbox();
                    }
                });
              positionPicker.start();
            });
        },
        setResult:function(data){
          this.result=data;
        },
        setAddress: function(data){
          if(this.isFirst){
            // this.addressObj.address='';
            this.isFirst=false;
          }else{
            this.addressObj.address=data.address;
            this.addressObj.lon=data.position.lng;
            this.addressObj.lat=data.position.lat;
            this.center=[this.addressObj.lon,this.addressObj.lat];
          }
          this.isSelect=true;
        },
        msgbox:function(){
          this.$msgbox({
            title:'提示',
            showCancelButton: false,
            confirmButtonText: '确定',
            type:'error',
            message:'该地点不可选择！'
          })
        },
        handleSelect(item) {
            this.addressObj.address=item.district+item.name;
            this.url=searchDetail+this.addressObj.address;
            this.getLocation(this.url);
            this.isSelect=true;
        },

        getLocation(url){
          this.$http.get(url)
            .then(response=>{
            if(response.data.info==='OK'){
              let locat=response.data.geocodes[0].location;
              let arr=locat.split(",");
              this.addressObj.lon=arr[0];
              this.addressObj.lat=arr[1];
              this.addressObj.address=response.data.geocodes[0].formatted_address;
              this.center=[this.addressObj.lon,this.addressObj.lat];
              this.isFirst=false;
              this.loadMap(this.setAddress,this.msgbox,this.center);
            }
          })
        },

        confirm(){
            if(this.addressObj.address.indexOf(this.addressObj.city)>-1 || this.addressObj.address.length===0){
              this.$emit('checkInfo',this.addressObj);
            }else {
              this.$msgbox({
                title:'提示',
                showCancelButton: false,
                confirmButtonText: '确定',
                type:'warning',
                message:'该地址不在所在城市，请重新输入！'
              }).then(() => {
              }).catch(() => {});
            }
          }
      },
      created:function(){
        this.addressObj.city=this.inputVal.city;
        this.addressObj.address = this.inputVal.address;
        this.addressObj.lon = this.inputVal.lon;
        this.addressObj.lat = this.inputVal.lat;
        if (this.inputVal.lon !=="" && this.inputVal.lat !== ""){
          this.center=[this.addressObj.lon,this.addressObj.lat];
        }
        this.isFirst=true;
        this.loadMap(this.setAddress,this.msgbox,this.center);
      },
    }
</script>

<style scoped>
  .map{
    width: 100%;
    height: 275px;
  }
  .name {
    text-overflow: ellipsis;
    overflow: hidden;
  }
  .addr {margin-top: 0px;
    font-size: 12px;
    color: #b4b4b4;
  }
.addr {
    color: #ddd;
  }


</style>
