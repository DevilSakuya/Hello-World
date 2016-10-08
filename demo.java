package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class demo {
	
	static boolean logopen = false;
	
	/**
	 * ������
	 * @param arg
	 */
	public static void main(String[] arg){
		dosomething();
	}
	
	/**
	 * �������
	 */
	public static void dosomething(){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String t=null;
		String r = null;
		String x = null;
		try {
			t = in.readLine();
		} catch (IOException e) {
			log("�����������...");
		}
		
		try {
			r = in.readLine();
		} catch (IOException e) {
			log("�����������...");
		}
		try {
			x = in.readLine();
		} catch (IOException e) {
			log("�����������...");
		}
		//���Խ����ַ���
		linkList list = getLink(t);
		linkList listr = getLink(r);
		linkList listx = getLink(x);
		System.out.println("����ʽ��"+list);
		System.out.println("����ʽ��"+listr);
		System.out.println("����ʽ��"+listx);
		linkList[] o = {
				list,
				listr,
				listx
		};
		linkList re = mergeList(o);
		System.out.println("���:"+re.toString());
	}
	
	/**
	 * �ϲ������ʽ
	 * @param linkListArr
	 * @return
	 */
	public static linkList mergeList(linkList[] linkListArr){
		//�ϲ��������
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
							
							log("ɨ��ڵ�:"+j+" ---- "+temp.bean.x+(temp.bean.hasN ? ("X^"+temp.bean.n):"  ����"));
							
							if(temp.bean.hasN == nhasN&&temp.bean.n == cn&&temp != currHead){
								if(nhasN){
									log("�ҵ�δ֪������ȵĽڵ�...��׼��ɾ��");
								} else {
									log("����...");
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
	 * ת���ַ���Ϊ����
	 * @param str 
	 * 		��Ҫת�����ַ��� ��5X^3-6X^7+3X^9-8X+9X^4��
	 * @return
	 */
	public static linkList getLink(String str){

		log("��ʼ������ʽ...");
		List<String> tArr = null;
		try{
			//������Ĺ�ʽ����ȡ
			tArr = getArr(str);
		} catch(Exception e){
			log("��ʽ��������...:"+e.getMessage());
			return null;
		}
		
		//�����ַ���ת��������ڵ�����
		linkList list=new linkList();
		for(int k=0;k<tArr.size();k++){

			String temp = tArr.get(k);
			
			if(temp.contains("X")){//���ַ�������δ֪��X,
				int x=0;
				int n=0;
				boolean hasN=true;
				
				if(temp.contains("^")) {//ָ�����ڻ����1  ��5X^3��
					String[] xArr = temp.split("[X][//^]");
					
					log("length��"+xArr.length);
					log(xArr.length+"\\"+xArr[0]+"\\"+xArr[1]);
					
					if(xArr[0].equals(""))
						x=1;
					else 
						x=Integer.parseInt(xArr[0]);
					
					n=Integer.parseInt(xArr[1]);
					
					list.add(new node(new bean(x, hasN, n)));
				} else {//δָ��ָ����ָ������1 ��5X��
					String[] xArr = temp.split("[X]");
					
					log("length��"+xArr.length);
					log(xArr.length+"\\"+xArr[0]);
					
					if(xArr[0].equals(""))
						x=1;
					else 
						x=Integer.parseInt(xArr[0]);
					
					n=1;
					
					list.add(new node(new bean(x, hasN, n)));
				}
			} else { //���ִ����ڳ���
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
	 * ��ϵ���з�
	 * @param str ��Ҫ�зֵ��ַ��� 
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
	 * ��ϵ���з�
	 * @param str  ��Ҫ�зֵ��ַ���
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
	 * ��������
	 * �򵥵�����ʵ��
	 * @author ayumiwind
	 *
	 */
	public static class linkList{
		//����ͷ
		public node head = null;
		/**
		 * �����������һ���ڵ�
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
		 * ɾ��һ���ڵ�
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
			
			log(isok ? "�ɹ�ɾ��һ���ڵ�...":"��������û���ҵ��ýڵ�...");
		}
		
		/**
		 * ���������Ĺ�ʽ
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
	 * ����ڵ�
	 * @author ayumiwind
	 *
	 */
	public static class node{
		//����ڵ�������
		public bean bean;
		//����ڵ�ָ����
		public node next;
		
		public node(bean bean){
			this.bean = bean;
			this.next = null;
		}
	}
	
	/**
	 * ����ڵ�����
	 * @author ayumiwind
	 *
	 */
	public static class bean{
		//ϵ��
		public int x;
		//�Ƿ����δ֪��
		public boolean hasN;
		//δ֪��ָ���������
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
