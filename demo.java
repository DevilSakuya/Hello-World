package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class demo {
	
	static boolean logopen = false;
	
	/**
	 * 主函数
	 * @param arg
	 */
	public static void main(String[] arg){
		dosomething();
	}
	
	/**
	 * 程序入口
	 */
	public static void dosomething(){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String t=null;
		String r = null;
		String x = null;
		try {
			t = in.readLine();
		} catch (IOException e) {
			log("输入输出错误...");
		}
		
		try {
			r = in.readLine();
		} catch (IOException e) {
			log("输入输出错误...");
		}
		try {
			x = in.readLine();
		} catch (IOException e) {
			log("输入输出错误...");
		}
		//尝试解析字符串
		linkList list = getLink(t);
		linkList listr = getLink(r);
		linkList listx = getLink(x);
		System.out.println("链表公式："+list);
		System.out.println("链表公式："+listr);
		System.out.println("链表公式："+listx);
		linkList[] o = {
				list,
				listr,
				listx
		};
		linkList re = mergeList(o);
		System.out.println("结果:"+re.toString());
	}
	
	/**
	 * 合并多个公式
	 * @param linkListArr
	 * @return
	 */
	public static linkList mergeList(linkList[] linkListArr){
		//合并结果链表
		linkList resultLink = new linkList();
		

		for(int i=0;i<linkListArr.length;i++){

			linkList curr = linkListArr[i];

			node currHead = curr.head;
			
			while(currHead != null){
				int cx = currHead.bean.x;
				int cn = currHead.bean.n;
				
				log(i+"/"+cx+"X^"+cn);
				boolean nhasN = currHead.bean.hasN;
				for(int j = 0;j<linkListArr.length;j++){
					node temp = linkListArr[j].head;
						
					if(temp != null){
						while(true){
							
							log("扫描节点:"+j+" ---- "+temp.bean.x+(temp.bean.hasN ? ("X^"+temp.bean.n):"  常数"));
							
							if(temp.bean.hasN == nhasN&&temp.bean.n == cn&&temp != currHead){
								if(nhasN){
									log("找到未知数幂相等的节点...。准备删除");
								} else {
									log("常数...");
								}
								cx = temp.bean.x+cx;
								linkListArr[j].delete(temp);
							}
	
							if(temp.next != null){
								temp = temp.next;
							} else {
								break;
							}
						}
					}
				}
				resultLink.add(new node(new bean(cx, nhasN, cn)));
				if(currHead.next != null){
					currHead = currHead.next;
				} else {
					break;
				}
			}

		}
		return resultLink;
	}
	
	/**
	 * 转换字符串为链表
	 * @param str 
	 * 		需要转换的字符串 《5X^3-6X^7+3X^9-8X+9X^4》
	 * @return
	 */
	public static linkList getLink(String str){

		log("开始解析公式...");
		List<String> tArr = null;
		try{
			//在输入的公式中提取
			tArr = getArr(str);
		} catch(Exception e){
			log("公式解析错误...:"+e.getMessage());
			return null;
		}
		
		//将子字符串转换成链表节点数据
		linkList list=new linkList();
		for(int k=0;k<tArr.size();k++){

			String temp = tArr.get(k);
			
			if(temp.contains("X")){//子字符串包含未知数X,
				int x=0;
				int n=0;
				boolean hasN=true;
				
				if(temp.contains("^")) {//指数大于或等于1  《5X^3》
					String[] xArr = temp.split("[X][//^]");
					
					log("length："+xArr.length);
					log(xArr.length+"\\"+xArr[0]+"\\"+xArr[1]);
					
					if(xArr[0].equals(""))
						x=1;
					else 
						x=Integer.parseInt(xArr[0]);
					
					n=Integer.parseInt(xArr[1]);
					
					list.add(new node(new bean(x, hasN, n)));
				} else {//未指定指数，指数等于1 《5X》
					String[] xArr = temp.split("[X]");
					
					log("length："+xArr.length);
					log(xArr.length+"\\"+xArr[0]);
					
					if(xArr[0].equals(""))
						x=1;
					else 
						x=Integer.parseInt(xArr[0]);
					
					n=1;
					
					list.add(new node(new bean(x, hasN, n)));
				}
			} else { //该字串属于常数
				int x=0;
				int n=0;
				boolean hasN=false;
				
				x = Integer.parseInt(temp);

				list.add(new node(new bean(x, hasN, n)));
			}
		}
		return list;
	}
	
	/**
	 * 正系数切分
	 * @param str 需要切分的字符串 
	 * @return
	 * @throws Exception
	 */
	public static List<String> getArr(String str) throws Exception{
		ArrayList<String> list = new ArrayList<>();
		String[] temp = str.split("[+]");
		if(temp!=null&&temp.length>0){
			for(int i=0;i<temp.length;i++){
				if(!temp[i].contains("-")){
					list.add("+"+temp[i]);
				} else {
					List<String> f = getFZrr("+"+temp[i]);
					for(int j=0;j<f.size();j++){
						list.add(f.get(j));
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 负系数切分
	 * @param str  需要切分的字符串
	 * @return
	 * @throws Exception
	 */
	public static List<String> getFZrr(String str) throws Exception{
		ArrayList<String> list = new ArrayList<>();
		String[] temp = str.split("[-]");
		if(temp!=null&&temp.length>0)
			for(int i=0;i<temp.length;i++){
				if(temp[i].contains("+")){
					list.add(temp[i]);
				} else{
					list.add("-"+temp[i]);
				}
			}
		return list;
	}
	
	/**
	 * 定义链表
	 * 简单的链表实现
	 * @author ayumiwind
	 *
	 */
	public static class linkList{
		//链表头
		public node head = null;
		/**
		 * 在链表中添加一个节点
		 * @param bean
		 */
		public void add(node node){
			if(head == null){
				this.head = node;
			} else {
				node curr = head;
				while(curr.next!=null){
					curr = curr.next;
				}
				curr.next = node;
			}
		}
		
		/**
		 * 删除一个节点
		 * @param node
		 */
		public void delete(node node){
			boolean isok = false;
			
			if(head == node){
				if(head.next ==null){
					head = null;
				} else {
					node temp = head.next;
					head = temp;
				}
				isok = true;
			} else if(head.next != null){
				node pre = head;
				node curr = head.next;
				while(true&&curr!=null){
					if(curr == node){
						pre.next = curr.next;
					}
					pre = curr;
					curr = curr.next;
				}
			}
			
			log(isok ? "成功删除一个节点...":"在链表中没有找到该节点...");
		}
		
		/**
		 * 输出该链表的公式
		 */
		@Override
		public String toString(){
			String result = "";
			node curr = head;
			if(head!=null){
				while(curr!=null){
					if(curr!=null&&curr.bean!=null) {
						result+=curr.bean.x>0 ? ("+"+curr.bean.x) : curr.bean.x;
						result+=curr.bean.hasN ? ("X^"+curr.bean.n) : "";
					}
					curr=curr.next;
				}

			}
			return result;
		}
		
	}
	
	/**
	 * 链表节点
	 * @author ayumiwind
	 *
	 */
	public static class node{
		//链表节点数据域
		public bean bean;
		//链表节点指针域
		public node next;
		
		public node(bean bean){
			this.bean = bean;
			this.next = null;
		}
	}
	
	/**
	 * 链表节点数据
	 * @author ayumiwind
	 *
	 */
	public static class bean{
		//系数
		public int x;
		//是否包含未知数
		public boolean hasN;
		//未知数指数，如果有
		public int n;
		
		public bean(int x,boolean hasN,int n){
			this.x=x;
			this.hasN=hasN;
			this.n=n;
		}
	}
	
	public static void log(String log){
		if(logopen)
			System.out.println(log);
	}
}
