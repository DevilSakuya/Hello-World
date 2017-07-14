
public class javaDemo{

	public static void main(String args[]){
		System.out.println("共输入参数"+args.length+"个");
		for(int i=0;i<args.length;i++){
			System.out.println("第"+i+"个参数为："+args[i]);
		}
		System.out.println("hello word");
		System.out.println("doSomeing");
		System.out.println("just do it");
		try{
			Thread.currentThread().join();
		} catch(Exception e){
			System.out.println("error");
		}
	}
}
