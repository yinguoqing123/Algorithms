//2i 的写法是错的  应该写成2*i 第二次犯这个错了 
import java.util.Random;
public class Sikaoti {
		public static void main(String[] args)
		{
			int[] arr={1,9,3,8,10,0,48,69,27,30};
			System.out.println(select1(arr,0,9,3));
			System.out.println(select1(arr,0,9,10));
			
		}
		private static int select1(int[] arr,int low,int high,int i)
		{
			int[] arr1=new int[2*i+1];
			if(i>=(high-low+1)/2)
				return select(arr,low,high,i);
			else
			{
				if((high-low+1)<=2*i)
				{	
					int[] arr2=new int[high-low+1];
					System.arraycopy(arr,low,arr2,0,high-low+1);
					return select(arr2,0,high-low,i);
				}
				else
				{
					int q=(high-low+1)/2;
					for(int x=0;x<q;x++)
					{
						if(arr[low+x]<arr[low+q+x])
							exchange(arr,low+x,low+q+x);
					}
					//先递归 再调用partition  重点是基准情形
					int key=select1(arr,low+q,high,i);
					int m=partition1(arr,low+q,high,key);
					int j=m-low-q+1;
					for(int p=0;p<j;p++)
					{
						arr1[p]=arr[low+p];
						arr1[p+i]=arr[low+p+q];
					}
					if((high-low+1)%2==1)
						arr1[2*i]=arr[high];
					if((high-low+1)%2==0)
						return select(arr1,0,2*i-1,i);
					else
						return select(arr1,0,2*i,i);
				}
			}
		}
		private static int partition(int[] arr,int low,int high)
		{
			Random ran=new Random();
			//尽量简单化表达式 用n表示所取的随机值
			int n=ran.nextInt(high-low+1)+low;
			int key=arr[n];
			exchange(arr,n,high);
			int i=low-1;
			for(int j=low;j<high;j++)
			{
				if(arr[j]<key)
				{
					i++;
					exchange(arr,i,j);
				}
			}
			exchange(arr,i+1,high);
			return i+1;
		}
		private static int partition1(int[] arr,int low,int high,int key)
		{
			int len=high-low+1;
			int i=low-1;
			int p=low;
			while(arr[p]!=key)
				p++;
			exchange(arr,p,high);
			for(int j=low;j<high;j++)
			{
				if(arr[j]<key)
				{
					i++;
					exchange(arr,i,j);
					exchange(arr,i-len,j-len);
				}
			}
			exchange(arr,i+1,high);
			exchange(arr,i+1-len,high-len);
			return i+1;
		}
		private static int select(int[] arr,int low,int high,int i)
		{
			if(low==high)
				return arr[low];
			int q=partition(arr,low,high);
			int k=q-low+1;
			if(i==k)
				return arr[q];
			if(i<k)
				return select(arr,low,q-1,i);
			if(i>k&&q+1<=high)
				return select(arr,q+1,high,i-k);
			if(i>k&&q==high)
				return arr[high];
			return 0;
		}
		private static void  exchange(int[] arr,int a,int b)
		{
			int temp;
			temp=arr[a];
			arr[a]=arr[b];
			arr[b]=temp;
		}
}

