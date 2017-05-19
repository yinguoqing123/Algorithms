
public class InsertSort{
	public static void main(String[] args)
	{
		int[] A=new int[] {7,6,5,4,3,2,1};
		for(int j=1;j<A.length;j=j+1)
		{
			int key=A[j];
			int i=j-1;
			//将key值大小定位
			while(i>-1&&A[i]>key)
			{
				A[i+1]=A[i];
				i-=1;
			}
			A[i+1]=	key;
		}
		for(int a=0;a<A.length;a++)
		{
			System.out.println(A[a]);
		}
	}
}