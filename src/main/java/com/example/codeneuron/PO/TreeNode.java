package com.example.codeneuron.PO;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;


/**
 * 构造文件树所用节点
 */

public class TreeNode {
    private String title; //最后一部分名字：类名or方法名
    private String key; //全名
    private Slot scopedSlots;
    private List<TreeNode> children;

    public TreeNode(String title,String key){
        this.title=title;
        this.key=key;
        this.scopedSlots = new Slot();
        children=new ArrayList<TreeNode>();
    };
    class Slot{
        String title = "title";
    };
    public void setTitle(String name) {
        this.title = name;
    }

    public String getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void add(TreeNode treeNode){
        children.add(treeNode);
    }

    public int getChildrenSize(){
        return children.size();
    }
}
