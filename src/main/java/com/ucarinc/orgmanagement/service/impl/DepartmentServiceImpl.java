package com.ucarinc.orgmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucarinc.orgmanagement.dao.*;
import com.ucarinc.orgmanagement.entity.*;
import com.ucarinc.orgmanagement.entity.form.DeptSearchForm;
import com.ucarinc.orgmanagement.entity.vo.*;
import com.ucarinc.orgmanagement.service.ICitySerivce;
import com.ucarinc.orgmanagement.service.IDepartmentService;
import com.ucarinc.orgmanagement.utils.ComUtil;
import com.ucarinc.orgmanagement.utils.PageUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description: 部门管理服务接口实现
 * @author daijianzhao
 * @version 1.0
 * @date 2019-08-03 10:48
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    /**
     * 部门信息表操作接口
     */
    @Autowired
    private DepartmentMapper departmentMapper;
    /**
     * 部门业务线关联表操作接口
     */
    @Autowired
    private DeptBussinessLineMapper deptBussinessLineMapper;

    /**
     * 部门公司关联表操作接口
     */
    @Autowired
    private DeptCompanyMapper deptCompanyMapper;
    @Autowired
    private BussinessLineMapper bussinessLineMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private ICitySerivce citySerivce;

    @Override
    @Transactional
    public void addDepartment(Department dept, List<DeptBussinessLine> deptBussinessLineList) {
        departmentMapper.insertDepartment(dept);
        for (DeptBussinessLine deptBussinessLine : deptBussinessLineList) {
            deptBussinessLineMapper.insertDeptBussinessLine(deptBussinessLine);
        }
    }

    @Override
    @Transactional
    public void updateDepartment(Department dept, List<DeptBussinessLine> deptBussinessLineList) {
        departmentMapper.updateDepartment(dept);
        List<DeptBussinessLine> list=deptBussinessLineMapper.getDeptBussinessLineListByDeptId(dept.getDeptId());

        for (DeptBussinessLine line : deptBussinessLineList) {
            if(!list.contains(line)){
                deptBussinessLineMapper.insertDeptBussinessLine(line);
            }
        }
        for (DeptBussinessLine bussinessLine : list) {
            if(!deptBussinessLineList.contains(bussinessLine)){
                deptBussinessLineMapper.removeDeptBussinessLine(bussinessLine);
            }
        }
    }

    @Override
    @Transactional
    public void deleteDepartment(String deptId){
            departmentMapper.deleteDepartment(deptId);
            deptBussinessLineMapper.deleteDeptBussinessList(deptId);
            deptCompanyMapper.deleteDeptCompany(deptId);
    }

    @Override
    public void updateSuperDept(String deptId, String superDeptId) {
        departmentMapper.updateSuperDept(deptId, superDeptId);
    }

    @Override
    public List<Department> getDepartmentList() {
        return departmentMapper.getDepartmentList();
    }

    @Override
    public String[] getDeptBussinessLine(String deptId) {
        List<DeptBussinessLine> list = deptBussinessLineMapper.getDeptBussinessLineListByDeptId(deptId);
        return lineToString(list);
    }

    @Override
    public void addDepartment(Department dept, List<Integer> integerList, int length) {
        Date time=new Date();
        Long empId=ShiroUtil.getActiveEmpId();
        dept.setCreateEmp(empId);
        dept.setCreateTime(time);
        dept.setModifyEmp(empId);
        dept.setModifyTime(time);
        List<DeptBussinessLine> bussinessLines = this.toDeptBussinessLine(integerList, time, dept);
        this.addDepartment(dept,bussinessLines);
    }

    @Override
    public void updateDepartment(Department dept, List<Integer> integerList, int length) {
        Date time=new Date();
        dept.setModifyTime(time);
        dept.setModifyEmp(ShiroUtil.getActiveEmpId());
        List<DeptBussinessLine> bussinessLines = this.toDeptBussinessLine(integerList, time, dept);
        this.updateDepartment(dept,bussinessLines);
    }

    @Override
    public Department getDepartmentById(String deptId) {
        return departmentMapper.getDepartmentById(deptId);
    }

    @Override
    public String getSuperDeptName(String superDeptId) {
        return departmentMapper.getSuperDeptName(superDeptId);
    }

    @Override
    public OptionalTreeResult getOptionalDeptTree(String deptId) {
        // deptList是从数据库中获得的部门数据集合
        List<Department> deptList = departmentMapper.getDepartmentList();
        Department department= departmentMapper.getDepartmentById(deptId);
        List<OptionalTreeResult> list = new ArrayList<>();
        OptionalTreeResult optionalTreeResult=null;
        // 用来记录根节点的下标
        int flag = 1;
        int currentDept=0;
        for(int i = 0;i < deptList.size();i++){
            optionalTreeResult= new OptionalTreeResult();
            optionalTreeResult.setId(deptList.get(i).getDeptId());
            optionalTreeResult.setLabel(deptList.get(i).getDeptName());
            if(deptList.get(i).getIsDelete()==1){
                optionalTreeResult.setDisabled(true);
            }
            List<Integer> spotList = departmentMapper.getSpotBySuperDeptId(deptList.get(i).getSuperDeptId());
            for (Integer integer : spotList) {
                if(integer!=null && department.getOfficeSpot()!=null && integer==(int)department.getOfficeSpot()){
                    optionalTreeResult.setDisabled(true);
                }
            }
            list.add(optionalTreeResult);
            if(deptList.get(i).getDeptId().equals(deptId)){
                currentDept=i;
            }
            if("".equals(deptList.get(i).getSuperDeptId())||null==deptList.get(i).getSuperDeptId()){
                flag = i;
            }

        }

        for(int i = 0;i < deptList.size();i++){
            for(int j = i+1;j < deptList.size();j++){
                if(deptList.get(j).getSuperDeptId().equals(deptList.get(i).getDeptId())){
                    list.get(i).add(list.get(j));
                }
            }
        }

        List<String> currentTreeList=new ArrayList<>();
        this.getCurrentTree(currentTreeList,list.get(currentDept));

        List<Integer> group = deptBussinessLineMapper.getBussinessLineGroop(currentTreeList);

        for (OptionalTreeResult result : list) {
            if(!result.getDisabled()){
                List<String> strings = new ArrayList<>();
                strings.add(result.getId());
                List<Integer> lineGroop = deptBussinessLineMapper.getBussinessLineGroop(strings);
                if(!lineGroop.containsAll(group)){
                    result.setDisabled(true);
                }
            }
        }
        return list.get(flag);
    }

    @Override
    public void updateDeptCompany(String deptId, List<Company> companyList) {
        //旧关联
        List<DeptCompany> oldDeptCompanyList= deptCompanyMapper.getDeptCompanyList(deptId);
        //新关联
        List<DeptCompany> deptCompanyList=this.toDeptCompany(deptId,companyList);
        for (DeptCompany oldCompany : oldDeptCompanyList) {//新里无旧则去旧
            if(!deptCompanyList.contains(oldCompany)){
                deptCompanyMapper.removeDeptCompany(oldCompany.getDeptId(),oldCompany.getCoCreditCode());
            }
        }
        for (DeptCompany newCompany : deptCompanyList) {
            if(!oldDeptCompanyList.contains(newCompany)){//旧里无新则添新
                deptCompanyMapper.insertDeptCompany(newCompany);
            }
        }
    }

    @Override
    public TreeResult  selectDepartment(Boolean showDelete){
        // deptList是从数据库中获得的部门数据集合
        List<Department> deptList = departmentMapper.selectDepartment();

        List<TreeResult> list = new ArrayList<>();
        // 用来记录根节点的下标
        int flag = 1;
        for(int i = 0;i < deptList.size();i++){
            TreeResult t = new TreeResult();
            t.setId(deptList.get(i).getDeptId());
            t.setLabel(deptList.get(i).getDeptName());
            t.setParentId(deptList.get(i).getSuperDeptId());
            if (showDelete) {
                if (deptList.get(i).getIsDelete() == 1) {
                    t.setDisabled(true);
                }
            }
            list.add(t);
            if("".equals(deptList.get(i).getSuperDeptId())||null==deptList.get(i).getSuperDeptId()){
                flag = i;
            }
        }
        for(int i = 0;i < deptList.size();i++){
            for(int j = i+1;j < deptList.size();j++){
                if(deptList.get(j).getSuperDeptId().equals(deptList.get(i).getDeptId())){
                    list.get(i).add(list.get(j));
                }
            }
        }
        return list.get(flag);
    }

    @Override
    public PageResult getDeptSearchList(Integer pageNum, Integer pageSize, DeptSearchForm deptSearchForm) {
        List<BussinessLine> bussinessLineList=bussinessLineMapper.getLineList();
        List<DeptInfoResult> deptInfoResults=new ArrayList<>();
        Page page = PageHelper.startPage(pageNum, pageSize);
        departmentMapper.getDeptSearchList(deptSearchForm);

        for(Object object : page){
            Department dept= (Department) object;
            if(null!=dept){
                deptInfoResults.add(deptToDeptInfo(dept,bussinessLineList));
            }
        }
        PageResult pageResult = PageUtil.getPageResult(page);
        pageResult.setPageData(deptInfoResults);
        return pageResult;
    }

    @Override
    public String getCityName(String interCode, String isCity) {
        String cityName=new String();
        if("1".equals(isCity)){
            cityName=departmentMapper.getCityNameById(interCode);
        }else if ("2".equals(isCity)){
            cityName=departmentMapper.getCountyNameById(interCode);
        }
        return cityName;
    }

    @Override
    public List<DeptExportInfoResult> getDeptExportInfoList(DeptSearchForm deptSearchForm) {
        List<BussinessLine> bussinessLineList=bussinessLineMapper.getLineList();
        List<DeptExportInfoResult> list=new ArrayList<>();
        List<Department> departmentList = departmentMapper.getDeptSearchList(deptSearchForm);
        DeptExportInfoResult deptExportInfoResult=null;
        for (Department department : departmentList) {
            deptExportInfoResult=new DeptExportInfoResult(department);
            if(department.getIsCity()==1){
                deptExportInfoResult.setCityName(departmentMapper.getCityNameById(department.getCityInterCode()));
            }else if(department.getIsCity()==2){
                deptExportInfoResult.setCityName(departmentMapper.getCountyNameById(department.getCountyInterCode()));
            }
            deptExportInfoResult.setSuperDeptName(departmentMapper.getSuperDeptName(department.getSuperDeptId()));
            List<DeptBussinessLine> deptBussinessLineList=deptBussinessLineMapper.getDeptBussinessLineListByDeptId(department.getDeptId());
            deptExportInfoResult.setBussinessLineList(this.getLogogramLine(bussinessLineList,deptBussinessLineList,false));
            List<Company> companyList=companyMapper.selectByDeptId(department.getDeptId());
            deptExportInfoResult.setCompanyList(this.getLogogramCompany(companyList,false));
            list.add(deptExportInfoResult);
        }
        return list;
    }

    @Override
    public int getDeptCountById(String deptId) {
        return departmentMapper.getDeptCountById(deptId);
    }

    @Override
    public int getSpotCount(String superDeptId, String officeSpot) {
        return departmentMapper.getSpotCount(superDeptId, officeSpot);
    }

    @Override
    public String[] getSubBussinessLine(String deptId) {
        List<Department> deptList = departmentMapper.getDepartmentList();

        List<OptionalTreeResult> list = new ArrayList<>();
        OptionalTreeResult optionalTreeResult=null;
        // 用来记录根节点的下标
        int currentDept=0;
        for(int i = 0;i < deptList.size();i++){
            optionalTreeResult= new OptionalTreeResult();
            optionalTreeResult.setId(deptList.get(i).getDeptId());
            list.add(optionalTreeResult);
            if(deptList.get(i).getDeptId().equals(deptId)){
                currentDept=i;
            }
        }

        for(int i = 0;i < deptList.size();i++){
            for(int j = i+1;j < deptList.size();j++){
                if(deptList.get(j).getSuperDeptId().equals(deptList.get(i).getDeptId())){
                    list.get(i).add(list.get(j));
                }
            }
        }

        List<String> currentTreeList=new ArrayList<>();
        this.getCurrentSub(currentTreeList,list.get(currentDept));
        if(currentTreeList.size()==0){
            return null;
        }
        List<Integer> group = deptBussinessLineMapper.getBussinessLineGroop(currentTreeList);

        String [] strs=new String[group.size()];
        for (int i=0;i<group.size();i++) {
           strs[i]=group.get(i).toString();
        }
        return strs;
    }

    private DeptInfoResult deptToDeptInfo(Department dept,List<BussinessLine> bussinessLineList){
        DeptInfoResult deptInfo = new DeptInfoResult();
        deptInfo.setDept(dept);
        if(dept.getIsCity()==1){
            deptInfo.setCityName(departmentMapper.getCityNameById(dept.getCityInterCode()));
        }else if(dept.getIsCity()==2){
            deptInfo.setCityName(departmentMapper.getCountyNameById(dept.getCountyInterCode()));
        }
        deptInfo.setSuperDeptName(departmentMapper.getSuperDeptName(dept.getSuperDeptId()));
        List<DeptBussinessLine> deptBussinessLineList=deptBussinessLineMapper.getDeptBussinessLineListByDeptId(dept.getDeptId());
        deptInfo.setLogogramBussinessLine(this.getLogogramLine(bussinessLineList,deptBussinessLineList,true));
        deptInfo.setWholeBussinessLine(this.getLogogramLine(bussinessLineList,deptBussinessLineList,false));
        deptInfo.setBussinessLineArr(this.lineToString(deptBussinessLineList));
        if(null != dept.getCreateEmp()){
            deptInfo.setCreateEmpName(ComUtil.getEmpStr(dept.getCreateEmp()));
        }
        if(null != dept.getModifyEmp()){
            deptInfo.setModifyEmpName(ComUtil.getEmpStr(dept.getModifyEmp()));
        }
        List<Company> companyList=companyMapper.selectByDeptId(dept.getDeptId());
        deptInfo.setLogogramCompany(this.getLogogramCompany(companyList,true));
        deptInfo.setWholeCompany(this.getLogogramCompany(companyList,false));
        deptInfo.setCompanyList(companyList);
        deptInfo.setCityList(this.getCityNameList(dept.getDeptId()));
        return deptInfo;
    }

    private String getLogogramLine(List<BussinessLine> line1, List<DeptBussinessLine> line2,boolean b){
        String logogramName="";
        int flag=0;
        for (BussinessLine bussinessLine : line1) {
            for (DeptBussinessLine line : line2) {
                if(line.getBussinessLineId()==bussinessLine.getBussinessLineId()){
                    if(b){
                        if(flag==2){
                            logogramName+="……";
                            return logogramName;
                        }else{
                            logogramName+=bussinessLine.getBussinessLineName()+";";
                            flag++;
                        }
                    }else{
                        logogramName+=bussinessLine.getBussinessLineName()+";";
                    }
                }
            }
        }
        return logogramName;
    }
    private String[] lineToString(List<DeptBussinessLine> list){
        String[] bussinessLine=new String[list.size()];
        for (int i=0;i<list.size();i++) {
            bussinessLine[i]=list.get(i).getBussinessLineId().toString();
        }
        return bussinessLine;
    }

    private String getLogogramCompany(List<Company> companyList, boolean b){
        String logogramName="";
        int flag=0;
        for (Company company : companyList) {
            if(b){
                if(flag==2){
                    logogramName+="……";
                    return logogramName;
                }else{
                    flag++;
                }
            }
            logogramName+= company.getCoName()+";";
        }
        return logogramName;
    }

    private List<String> getCityNameList(String deptId){
        DeptTree tree=this.getDeptTree(deptId);
        List<String> list=new ArrayList<>();
        this.iteratorTree(list,tree);
        return list;
    }
    private DeptTree getDeptTree(String deptId){
        List<Department> deptList = departmentMapper.getDepartmentList();
        List<DeptTree> deptTreeList=this.deptToDeptTree(deptList);
        int currentDept = 0;
        for(int i = 0;i < deptTreeList.size();i++){
            if(deptTreeList.get(i).getDept().getDeptId().equals(deptId)){
                currentDept=i;
            }
            for(int j =0;j < deptTreeList.size();j++){
                if(deptTreeList.get(j).getDept().getSuperDeptId().equals(deptTreeList.get(i).getDept().getDeptId())){
                    deptTreeList.get(i).getChildren().add(deptTreeList.get(j));
                }
            }
        }
        return deptTreeList.get(currentDept);
    }

    private List<DeptTree> deptToDeptTree(List<Department> list){
        List<DeptTree> deptTreeList=new ArrayList<>();
        DeptTree tree=null;
        for (Department department : list) {
            tree=new DeptTree();
            tree.setDept(department);
            deptTreeList.add(tree);
        }
        return deptTreeList;
    }

    private void iteratorTree(List<String> list, DeptTree tree){
        if(tree.getDept().getIsCity()==1){
            list.add(departmentMapper.getCityNameById(tree.getDept().getCityInterCode()));
        }else if(tree.getDept().getIsCity()==2){
            list.add(departmentMapper.getCountyNameById(tree.getDept().getCountyInterCode()));
        }
        for (DeptTree child : tree.getChildren()) {
            iteratorTree(list,child);
        }
    }

    private List<DeptBussinessLine> toDeptBussinessLine(List<Integer> integerList, Date time, Department dept){
        List<DeptBussinessLine> bussinessLines=new ArrayList<>();
        DeptBussinessLine deptBussinessLine=null;
        for (Integer integer : integerList) {
            deptBussinessLine=new DeptBussinessLine();
            deptBussinessLine.setDeptId(dept.getDeptId());
            deptBussinessLine.setBussinessLineId(Byte.parseByte(integer.toString()));
            deptBussinessLine.setCreateEmp(dept.getModifyEmp());
            deptBussinessLine.setCreateTime(time);
            deptBussinessLine.setModifyEmp(dept.getModifyEmp());
            deptBussinessLine.setModifyTime(time);
            bussinessLines.add(deptBussinessLine);
        }
        return bussinessLines;
    }

    private void getCurrentSub(List<String> list, OptionalTreeResult otr){
        for (OptionalTreeResult child : otr.getChildren()) {
            this.getCurrentTree(list,child);
        }
    }

    private void getCurrentTree(List<String> list, OptionalTreeResult otr){
        otr.setDisabled(true);
        list.add(otr.getId());
        for (OptionalTreeResult child : otr.getChildren()) {
            this.getCurrentTree(list,child);
        }
    }

    private List<DeptCompany> toDeptCompany(String deptId, List<Company> companyList){
        //新关联
        List<DeptCompany> deptCompanyList=new ArrayList<>();
        DeptCompany deptCompany=null;
        Date time = new Date();
        for (Company company : companyList) {
            deptCompany = new DeptCompany();
            deptCompany.setDeptId(deptId);
            deptCompany.setCoCreditCode(company.getCoCreditCode());
            deptCompany.setCoName(company.getCoName());
            deptCompany.setCreateEmp(10001000L);
            deptCompany.setCreateTime(time);
            deptCompany.setModifyEmp(10001000L);
            deptCompany.setModifyTime(time);
            deptCompanyList.add(deptCompany);
        }
        return deptCompanyList;
    }
}
