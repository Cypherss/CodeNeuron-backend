package com.example.codeneuron.ServiceImpl.CalculateService.Static;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.Dao.Node_codeMapper;
import com.example.codeneuron.PO.Edge;
import com.example.codeneuron.PO.GraphNode;
import com.example.codeneuron.PO.Node;
import com.example.codeneuron.PO.Node_code;
import com.example.codeneuron.Service.CalculateService.Dynamic.DomainCal;
import com.example.codeneuron.Service.CalculateService.Dynamic.GraphCal;
import com.example.codeneuron.Service.CalculateService.Static.CodeAnalysis;
import com.example.codeneuron.Service.InitGraph.InitGraph;
import com.example.codeneuron.VO.ResponseVO;
import org.apache.bcel.classfile.ClassParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class CalPosition{
    static final int CANVAS_WIDTH = 1000;
    static final int CANVAS_HEIGHT = 1000;
    private List<Node> mNodeList;
    private List<Edge> mEdgeList;
    private Map<String, Double> mDxMap = new HashMap<>();
    private Map<String, Double> mDyMap = new HashMap<>();
    private Map<String, Node> mNodeMap = new HashMap<>();
    private double k;


    CalPosition(List<Node> nodeList, List<Edge> edgeList) {
        this.mNodeList = nodeList;
        this.mEdgeList = edgeList;
        if (nodeList != null && nodeList.size() != 0) {
            k = Math.sqrt(CANVAS_WIDTH * CANVAS_HEIGHT / (double) mNodeList.size());
        }
        if (nodeList != null) {
            for (int i = 0; i < nodeList.size(); i++) {
                Node node = nodeList.get(i);
                if (node != null) {
                    mNodeMap.put(node.getName(), node);
                }
            }
        }
    }

    void collide() {
        calculateRepulsive();
        calculateTraction();
        updateCoordinates();
    }

    /**
     * 计算两个Node的斥力产生的单位位移。
     */
    private void calculateRepulsive() {
        int ejectFactor = 10;// default: 10
        double distX, distY, dist;
        for (int v = 0; v < mNodeList.size(); v++) {
            mDxMap.put(mNodeList.get(v).getName(), 0.0);
            mDyMap.put(mNodeList.get(v).getName(), 0.0);
            for (int u = 0; u < mNodeList.size(); u++) {
                if (u != v) {
                    distX = mNodeList.get(v).getX() - mNodeList.get(u).getX();
                    distY = mNodeList.get(v).getY() - mNodeList.get(u).getY();
                    dist = Math.sqrt(distX * distX + distY * distY);
                    if (dist < 30) {
                        ejectFactor = 5;
                    }
                    if (dist > 0 && dist < 250) {
                        String id = mNodeList.get(v).getName();
                        mDxMap.put(id, mDxMap.get(id) + distX / dist * k * k / dist * ejectFactor);
                        mDyMap.put(id, mDyMap.get(id) + distY / dist * k * k / dist * ejectFactor);
                    }
                }
            }
        }
    }

    /**
     * 计算Edge的引力对两端Node产生的引力。
     */
    private void calculateTraction() {
        int condenseFactor = 3;
        Node startNode, endNode;
        for (int e = 0; e < mEdgeList.size(); e++) {
            String eStartID = mEdgeList.get(e).getCallerName();
            String eEndID = mEdgeList.get(e).getCalleeName();
            startNode = mNodeMap.get(eStartID);
            endNode = mNodeMap.get(eEndID);
            if (startNode == null) {
                System.out.println("Cannot find node id: " + eStartID + ", please check it out.");
                return;
            }
            if (endNode == null) {
                System.out.println("Cannot find node id: " + eEndID + ", please check it out.");
                return;
            }
            double distX, distY, dist;
            distX = startNode.getX() - endNode.getX();
            distY = startNode.getY() - endNode.getY();
            dist = Math.sqrt(distX * distX + distY * distY);
            mDxMap.put(eStartID, mDxMap.get(eStartID) - distX * dist / k * condenseFactor);
            mDyMap.put(eStartID, mDyMap.get(eStartID) - distY * dist / k * condenseFactor);
            mDxMap.put(eEndID, mDxMap.get(eEndID) + distX * dist / k * condenseFactor);
            mDyMap.put(eEndID, mDyMap.get(eEndID) + distY * dist / k * condenseFactor);
        }
    }

    /**
     * 更新坐标。
     */
    private void updateCoordinates() {
        int maxt = 4, maxty = 3; //Additional coefficients.
        for (int v = 0; v < mNodeList.size(); v++) {
            Node node = mNodeList.get(v);
            int dx = (int) Math.floor(mDxMap.get(node.getName()));
            int dy = (int) Math.floor(mDyMap.get(node.getName()));

            if (dx < -maxt) dx = -maxt;
            if (dx > maxt) dx = maxt;
            if (dy < -maxty) dy = -maxty;
            if (dy > maxty) dy = maxty;

            node.setXPosition((node.getX() + dx) >= CANVAS_WIDTH || (node.getX() + dx) <= 0 ? node.getX() - dx : node.getX() + dx);
            node.setYPosition((node.getY() + dy) >= CANVAS_HEIGHT || (node.getY() + dy <= 0) ? node.getY() - dy : node.getY() + dy);
        }
    }

    List<Node> getNodeList() {
        return mNodeList == null ? new ArrayList<Node>() : mNodeList;
    }
}
