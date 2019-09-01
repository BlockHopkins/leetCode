package com.qc.mypractice;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 水壶问题：
 * 给你一个装满水的8升满壶 和 两个分别是5升，3升的空壶，想个优雅的方法，
 * 使得其中一个水壶恰好装4升水，每一步的操作只能是倒空或装满
 */
public class WaterBucket {
	private static int[] volume = new int[] {8,5,3};//每个桶的容积
	private static int target = 4;//目标体积

	private static LinkedList<Node> queue = new LinkedList<Node>();//处理队列
	
	public static void main(String[] args) {
		//测试用例1
		setBucketParams(new int[]{8,5,3}, 4);//三个桶容积为8,5,3，目标是取得4升水
		Node initStatus = new Node(null, new int[] {8,0,0});//初始状态是只有第一个桶装满水8升
		
		//测试用例2
//		setBucketParams(new int[]{10,7,3}, 5);//三个桶容积为10,7,3，目标是取得5升水
//		Node initStatus = new Node(null, new int[] {10,0,0});//初始状态是只有第一个桶装满水10升
		
		Node result = bfsToGetResult(initStatus);
		printRouter(result);
	}
	
	/**
	 * 一点也不优雅地使用广度优先搜索求解 
	 */
	private static Node bfsToGetResult(Node node){
		if(node == null) {
			return null;
		}
		if(node.status.length != volume.length) {
			throw new RuntimeException("error: the length of init status does not equal to the length of volume!");
		}
		queue.add(node);
		int index = 0;
		Node tempNode = null; 
		//广度优先遍历
		while(index<queue.size()) {
			tempNode = queue.get(index);
			//将第i个桶的水倒到其余的某一个桶j里，各得到一个新的状态
			for(int i=0; i<tempNode.status.length; i++) {
				for(int j=0; j<tempNode.status.length; j++) {
					if(i==j //不倒给自己
						|| volume[j] == tempNode.status[j]) {//目标容器已满
						continue;
					}
					Node newNode = new Node(tempNode, Arrays.copyOf(tempNode.status, tempNode.status.length));
					if(newNode.status[i] > volume[j]-newNode.status[j]) { 
						//装满：如果当前容器的水体积比目标容器剩余容积大，则结果原容器有剩余，目标容器满
						newNode.status[i] = newNode.status[i] - (volume[j]-newNode.status[j]);//原容器有剩余
						newNode.status[j] = volume[j];//目标容器满
					}else {
						//倒空：如果目标容器可以装下当前容器的所有水，则结果当前容器的水体积都加到目标容器里面去，当前容器水清空
						newNode.status[j] = newNode.status[j]+newNode.status[i];
						newNode.status[i] = 0;
					}
					
					if(newNode.status[i]==target || newNode.status[j]==target) {
						//如果结果已经得到目标水的体积，这返回
						return newNode;
					}else if(!queue.contains(newNode)) {
						queue.add(newNode);//如果队列中没出现过这种状态，这添加到队列中待下一轮处理
					}
				}
			}
			index++;
		}
		return null;
	}
	
	/**
	 * 初始化
	 * @param volume0 每个桶的容积，数据
	 * @param target0 目标要得到的水的体积
	 */
	private static void setBucketParams(int[] volume0, int target0) {
		volume = volume0;
		target = target0;
	}
	
	/**
	 * 打印节点经历的路径
	 */
	private static void printRouter(Node node) {
		if(node==null) {
			return;
		}
		if(node.preNode!=null) {
			printRouter(node.preNode);
		}
		System.out.println(Arrays.toString(node.status));
	}
}

/**
 * 记录每一个状态及前一状态
 */
class Node{
	Node preNode = null;//上一状态
	int[] status = null;//当前状态：每个桶装水的体积
	
	public Node(Node preNode, int[] status) {
		this.preNode = preNode;
		this.status = status;
	}

	/**
	 * 重写equals方法用于List中判重
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Node) {
			Node target = (Node)obj;
			if(this.status==null && target.status==null) {
				return true;
			}else if(this.status!=null && target.status!=null) {
				if(this.status.length==target.status.length) {
					for(int i=0;i<this.status.length;i++) {
						if(this.status[i]!=target.status[i]) {
							return false;
						}
					}
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
}
