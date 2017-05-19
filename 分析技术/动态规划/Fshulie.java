public class Fshulie
{
	public static void main(String[] args)
	{
		System.out.println(fn(6));
		System.out.println(fn1(7));
	}
	private static int fn(int n)
	{
		int[] arr=new int[n];
		arr[0]=1;
		arr[1]=2;
		int fn;
		for(int i=2;i<n;i++)
		{
			fn=arr[i-1]+arr[i-2];
			arr[i]=fn;
		}
		return arr[n-1];
	}
	private static int fn1(int n)
	{
		int[] arr=new int[2];
		arr[0]=1;
		arr[1]=2;
		int fn=0;
		int temp;
		for(int i=2;i<n;i++)
		{
			fn=arr[0]+arr[1];
			temp=arr[1];
			arr[1]=fn;
			arr[0]=temp;
		}
		return fn;
	}
}