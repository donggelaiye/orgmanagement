package com.ucarinc.orgmanagement.test;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 该文件说明
 *
 * @author daijianzhao
 * @version 1.0
 * @date 2019-08-04 11:40
 */
public class TreeNodeTest {
    public static void main(String[] args) {
        TreeNode node1=new TreeNode("根节点",1,0);
        TreeNode node2=new TreeNode("二级节点，父节点1",2,1);
        TreeNode node3=new TreeNode("二级节点，父节点1",3,1);
        TreeNode node4=new TreeNode("二级节点，父节点1",4,1);
        TreeNode node5=new TreeNode("三级结点，父节点2",5,2);
        TreeNode node6=new TreeNode("三级结点，父节点2",6,2);
        TreeNode node7=new TreeNode("三级结点，父节点2",7,2);
        TreeNode node8=new TreeNode("三级结点，父节点3",8,3);
        TreeNode node9=new TreeNode("四级节点，父节点5",9,5);
        TreeNode node10=new TreeNode("四级节点，父节点5",10,5);
        TreeNode node11=new TreeNode("四级节点，父节点5",11,5);
        TreeNode node12=new TreeNode("五级节点，父节点9",12,9);
        TreeNode node13=new TreeNode("五级节点，父节点11",13,11);

        List<TreeNode> tree=new ArrayList<>();
        tree.add(node8);
        tree.add(node13);
        tree.add(node3);
        tree.add(node4);
        tree.add(node5);
        tree.add(node6);
        tree.add(node10);
        tree.add(node1);
        tree.add(node9);
        tree.add(node7);
        tree.add(node11);
        tree.add(node12);
        tree.add(node2);

        for(int i=0;i<tree.size();i++){
            for(int j=0;j<tree.size();j++){
                if(tree.get(j).getPid()==tree.get(i).getId()){
                    tree.get(i).add(tree.get(j));
                }
            }
        }

        System.out.println(tree.get(7));

    }
}
