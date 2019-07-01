package P1;

import java.io.*;
import java.util.Scanner;

public class MagicSquare {
	static boolean ismagicSquare(String fileName,int rows)//1.判断是否为矩阵
	{
		//判断不是Magic Square，行列不相等或存在空格
		String[] myword;
		String myline;
		File file = new File(fileName);
		Reader in1;
		boolean result = true;
		try {
			in1 = new FileReader(file);
			BufferedReader  bfr = new BufferedReader(in1);
			while((myline = bfr.readLine()) != null)
			{
				myword = myline.split("\t");
				if(rows != myword.length)
				{
					result = false;
				}
			}
			bfr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	static boolean isLegalMagicSquare(String fileName){
		File file = new File(fileName);
		int i = 0, j = 0, flag = 1, sum = 0, temp,n;
//		boolean result = true;
		try {
			int br[][] = new int[1000][1000];
			String myline = null;
			String[] myword;
			Reader in1 = new FileReader(file);
			BufferedReader  bfr = new BufferedReader(in1);
			//将读取的数据分割成行,从文件中读入数据存储在二维数组中bfr[][]
			while((myline = bfr.readLine()) != null)
			{
				myword = myline.split(".");
				if(myword.length>1)
				{
					System.out.print("矩阵中存在浮点数，");
					return false;
				}
				myword = myline.split("-");
				if(myword.length>1)
				{
					System.out.print("矩阵中存在负数，");
	                return false;
				}
				myword = myline.split("\t");
				for(String word: myword)
				{
					try{//数字之间并非使用 \t 分割
						br[i][j] = Integer.valueOf(word).intValue();
						}catch(NumberFormatException e){
							System.out.print("数据存在非法符号,");
							return false;
						}
					j++;
				}
                j = 0;
				i++;
			}
			bfr.close();
            n = i;
            if(!ismagicSquare(fileName, n))//判断是否是magicsquare
            {
            	System.out.print("不符合 Magic Square的定义，");
            	return false;
            }
    		for(i=0;i<n;i++) // 求第一行的和sum
    		{ 
    			sum += br[0][i]; 
    		} 
    		//有一个不等于sum,不成立
    		//计算每行
    		for (i = 1; i < n; i++) {
    			temp = 0;
    			for (j = 0; j < n; j++) 
    			{
    				temp += br[i][j];
    			}
    			if (sum != temp) {
    				flag = 0;
    			}
    		} 
    		//计算每列
    		temp = 0;
    		for (i = 0; i < n; i++) {
    			temp = 0;
    			for (j = 0; j < n; j++) 
    			{
    				temp += br[j][i];
    			}
    			if (sum != temp) {
    				flag = 0;
    			}
    		} 
    		// 正对角线 
    		temp = 0; 
    		for(i=0;i<n;i++) 
    		{ 
    			temp += br[i][i]; 
    		}
    		if (sum != temp) {
    			flag = 0;
    		} 
    		// 反对角线
    		temp = 0;
    		for (i = 0; i < n; i++) {
    			temp += br[i][n - 1 - i];
    		}
    		if (sum != temp) {
    			flag = 0;
    		}
        } catch (IOException e) {
            e.printStackTrace();
        }
		if (flag == 1) {
			return true;
		} 
		else
			return false;
	}
	public static boolean generateMagicSquare(int n) {
		if(n <=0 || n % 2 == 0)
		{
			System.out.println("输入错误，请输入正奇数");
			return false;
		}
		int magic[][] = new int[n][n];//创建一个n*n的矩阵
		int row = 0, col = n / 2, i, j, square = n * n;
		//square为矩阵元素总数量
		for (i = 1; i <= square; i++) 
		{//首先将1放在最上一行的中间。其后沿着自左下至右上的这条对角线按自然顺序放置
			magic[row][col] = i;
			if (i % n == 0)//当要放的位置上已经填好了整数，或上一个整数放在右上角时，则当前要摆放的整数将放在紧挨上述位置的下方
				
				row++;
			else {
				if (row == 0)//在到达顶行时，下一个整数要放在底行，所放位置就是把底行当做顶行上边一行时该数应该放置的位置
					row = n - 1;
				else
					row--;
				if (col == (n - 1))//当到达最右边的一列时，下一个整数要放在最左边的一列上，
					col = 0;
				else
					col++;
			}
		}
		//将产生的 magic square 写入文件\src\P1\txt\6.txt
		try {
			File file = new File("src\\P1\\txt\\6.txt");
			FileWriter writer = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(writer);
			try
			{
				for(i=0;i<n;i++)
				{
					for(j=0;j<n;j++)
					{
						out.write(magic[i][j]+"\t");
					}
					out.write("\r\n");
				}
				out.flush();
			}catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	public static void main(String[] args)  {
		boolean result[] = new boolean[5],result_1,result6;
		int i,n=0;
		String[] fileName = {"src/P1/txt/1.txt",
				"src/P1/txt/2.txt",
				"src/P1/txt/3.txt",
				"src/P1/txt/4.txt",
				"src/P1/txt/5.txt"};
		for(i=0;i<5;i++)
		{
			result[i] = isLegalMagicSquare(fileName[i]);
			if(result[i])
			{
				System.out.println((i+1)+".txt is a magicsquare.");
			}
			else {
				System.out.println((i+1)+".txt is not a magicsquare.");
			}
		}
		while(n != 11)
		{	
			Scanner input = new Scanner(System.in);
			System.out.println("请输入6.txt的参数：");
			try {
				n = input.nextInt();			//如果输入的不为整数，会抛出一个InputMismatchException异常
			}catch(Exception e) {				
				System.out.println("您输入的不是整数，请继续输入一个整数！");
				input.next();						//继续输入
				}
			result_1 = generateMagicSquare(n);
			if(result_1)
			{
				result6 = isLegalMagicSquare("src/P1/txt/6.txt");
				if(result6)
				{
					System.out.println("6.txt is a magicsquare.");
				}
				else {
					System.out.println("6.txt is not a magicsquare.");
				}
			}
		}
	}
}
