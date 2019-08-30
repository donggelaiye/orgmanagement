package com.ucarinc.orgmanagement.utils;

import com.ucarinc.orgmanagement.entity.vo.TreeResult;

import java.util.*;

/**
 * description:树工具
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-05 17:04
 */
public class TreeUtil {

    /**
     * description:构造树
     *
     * @param nodes TreeResult List
     * @return java.util.List<T>
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 8:53
     */
    public static <T extends TreeResult> List<T> buildTree(List<T> nodes) {
        if (nodes == null) {
            return null;
        }
        List<T> treeList = new ArrayList<>();
        for (T childNode : nodes) {
            //默认该节点为子节点
            String parentId = childNode.getParentId();
            //自身id
            String selfId = childNode.getId();
            //是否为根节点
            if (parentId == null) {
                //无父节点,该节点为根节点
                treeList.add(childNode);
                continue;
            }
            //有父节点,遍历取父节点
            for (T parentNode : nodes) {
                //取父节点id
                String fatherId = parentNode.getId();
                //找到其父节点,
                if (fatherId != null && fatherId.equals(parentId)) {
                    parentNode.add(childNode);
                    break;
                }
            }
        }
        return treeList;
    }

    /**
     * description:获取树中子-父节点映射关系map
     *
     * @param tree 树
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 8:53
     */
    public static <T extends TreeResult> Map<String, String> getChildParentIdIndexMap(List<T> tree) {
        Map<String, String> indexMap = new HashMap<>();
        recursionIterTreeGetChildParentIndexMap(tree, indexMap);
        return indexMap;
    }

    /**
     * description:递归遍历树列表获取子-父节点Id映射关系
     *
     * @param tree 树
     * @return void
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 8:56
     */
    public static <T extends TreeResult> void recursionIterTreeGetChildParentIndexMap(List<T> tree, Map<String, String> indexMap) {
        for (T treeNode : tree) {
            //自身id,父节点id存入map
            indexMap.put(treeNode.getId(), treeNode.getParentId());
            //有孩子节点
            if (treeNode.hasChildren()) {
                //递归
                recursionIterTreeGetChildParentIndexMap(treeNode.getChildren(), indexMap);
            }
        }
    }

    /**
     * description:递归获取指定Id节点在部门树中id索引,由底至顶
     *
     * @param childParentIndexMap 树索引map
     * @param targetNodeId        目标结点Id
     * @param tempIndexList       递归结果List,暂存
     * @return java.util.List<java.lang.String>
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 10:20
     */
    public static List<String> getTreeNodeIndexList(Map<String, String> childParentIndexMap, String targetNodeId, List<String> tempIndexList) {
        tempIndexList.add(targetNodeId);
        if (!childParentIndexMap.get(targetNodeId).isEmpty()) {
            getTreeNodeIndexList(childParentIndexMap, childParentIndexMap.get(targetNodeId), tempIndexList);
        }
        return tempIndexList;
    }

    /**
     * description:依照目标节点索引从树中获取指定节点
     *
     * @param tree      树
     * @param indexList 目标节点索引
     * @return T
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 11:26
     */
    public static <T extends TreeResult> T getTargetNodeByIndex(List<T> tree, List<String> indexList) {
        //获取迭代器
        Iterator<String> iterator = indexList.iterator();
        return (T) recursionGetTargetNodeByNodeId(tree, iterator);
    }

    /**
     * description:依照节点迭代器,递归从树中获取指定节点
     *
     * @param treeNodeList 同级节点列表
     * @param iterator     节点Id迭代器
     * @return T
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 11:26
     */
    public static <T extends TreeResult> TreeResult recursionGetTargetNodeByNodeId(List<T> treeNodeList, Iterator<String> iterator) {
        //取下一个
        String targetNode = iterator.hasNext() ? iterator.next() : null;
        for (T treeNode : treeNodeList) {
            //找到目标节点
            if (treeNode.getId().equals(targetNode)) {
                //是否有下一级
                if (iterator.hasNext()) {
                    //向下递归
                    TreeResult resultNode = recursionGetTargetNodeByNodeId(treeNode.getChildren(), iterator);
                    return resultNode;
                } else {
                    //已遍历到底,返回
                    return treeNode;
                }
            }
        }
        return null;
    }

    /**
     * description:依照节点向下递归遍历,获取节点IdList,包含自身
     *
     * @param treeNode   目标节点
     * @param treeIdList 结果IdList
     * @return java.util.List<java.lang.String>
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 13:06
     */
    public static <T extends TreeResult> List<String> recursionDownGetNodeIdListByTreeNode(T treeNode, List<String> treeIdList) {
        treeIdList.add(treeNode.getId());
        if (treeNode.hasChildren()){
            for (TreeResult child : treeNode.getChildren()) {
                recursionDownGetNodeIdListByTreeNode(child,treeIdList);
            }
        }
        return treeIdList;
    }



}

