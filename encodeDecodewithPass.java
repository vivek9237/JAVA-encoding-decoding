//*******************************
//written by vivek kumar mohanty
//www.facebook.com/vivek.kumohanty
//vivek.ku.mohanty@gmail.com
//*******************************
package constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class AsciiEncoding {
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
        Console console = System.console();

		//System.out.println("Drag the File Here and Press Enter :");
		String address = args[0];//in.nextLine();
		String text = read(address);
		//System.out.println("Action(encode/decode):");
		String action= args[1];//in.nextLine();
		System.out.println("Enter Password : ");
//		String pass = in.nextLine();
		char[] passwordChars = console.readPassword();
        String pass = new String(passwordChars);
		if(action.equalsIgnoreCase("encode")){
        System.out.println("Confirm Password : ");
        char[] passwordChars2 = console.readPassword();
        String pass2 = new String(passwordChars2);
        if(!(pass.equals(pass2))){
        	System.out.println("Password Mismatch!!!");
        	return;
        }
		}
		
		if(action.equalsIgnoreCase("encode")){
		String outText = asciiEncode(text,pass);
		writeFile(address,outText);
		}
		if(action.equalsIgnoreCase("decode")){
		String sreejit = asciiDecode(text,pass);
		writeFile(address,sreejit);
		}
	}

	public static String asciiEncode(String text, String pass) throws UnsupportedEncodingException{
		//		pass = "vivek";
		String outText = "";
		byte[] bytes = text.getBytes("US-ASCII");
		String buff ="";
		for (int i = 0; i < bytes.length; i++) {
			String viv = toBinary(bytes[i]);
			for (;viv.length()<8;) {
				viv= "0"+viv;
			}
			if(viv.length()>8){
				System.out.println("error");
			}
			buff+=viv;
			if(i%10==0){
				System.out.println(buff);
				buff="";
			}
			//			System.out.println(viv);
			outText+=viv;
		}
		String outTextFinal=xorEncode(outText,pass);
		String v = outTextFinal.replaceAll("0", Character.toString ((char) 32));
		String a = v.replaceAll("1", Character.toString ((char) 9));
		return a;
	}
	public static String asciiDecode(String outText, String pass) throws UnsupportedEncodingException{
		String sreejit = "";
		String b = outText.replaceAll(Character.toString ((char) 9), "1");
		String c = b.replaceAll(Character.toString ((char) 32), "0");
		String outTextFinal=xorDecode(c,pass);
		System.out.println(outTextFinal);
		for (int i = 0; i+8 <= outTextFinal.length(); i=i+8) {
			String foo = (outTextFinal.substring(i,i+8));
			sreejit+= Character.toString ((char) toDec(foo));
		}
		return sreejit;
	}
	public static String read(String address){
		try (BufferedReader br = new BufferedReader(new FileReader(address)))
		{

			String sCurrentLine;
			String total="";
			while ((sCurrentLine = br.readLine()) != null) {
				total+=sCurrentLine+System.lineSeparator();
			}
			return total;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void writeFile(String address,String body) throws IOException{
		try {
			File file = new File(address);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(body);
			bw.close();
			fw.close();
			System.out.println("Written in file!"+address);
		} catch (IOException e) {
			System.out.println("Not Written in file!"+address);
		}
	}
	public static int toDec(String strBinaryNumber) {       
		return Integer.parseInt(strBinaryNumber,2);
	}
	public static String toBinary(int i) {       
		return Integer.toBinaryString(i);                                               
	}
	public static String xorEncode(String outText, String pass) throws UnsupportedEncodingException{
		String passBinary="";
		byte[] bytes1 = pass.getBytes("US-ASCII");
		for (int i = 0; i < bytes1.length; i++) {
			String viv = toBinary(bytes1[i]);
			for (;viv.length()<8;) {
				viv= "0"+viv;
			}
			passBinary+=viv;
		}
		System.out.println(passBinary+passBinary);
		int j=0;
		String finall="";
		for (int i = 0; i < outText.length(); i++) {
			j=j%passBinary.length();
			if(outText.substring(i, i+1).equals(passBinary.substring(j, j+1))){
				j++;
				finall+="1";
			}
			else{
				j++;
				finall+="0";
			}
		}
		return finall;
	}
	public static String xorDecode(String finall, String pass2) throws UnsupportedEncodingException{
		String passBinary2 = "";
		//		pass2="vivek";
		byte[] bytes2 = pass2.getBytes("US-ASCII");
		for (int i = 0; i < bytes2.length; i++) {
			String viv = toBinary(bytes2[i]);
			for (;viv.length()<8;) {
				viv= "0"+viv;
			}
			passBinary2+=viv;
		}
		//		System.out.println("pas2="+passBinary2);

		String finall2="";
		int j=0;
		for (int i = 0; i < finall.length(); i++) {
			j=j%passBinary2.length();
			//			System.out.println("j="+j);
			//			System.out.println("i="+i);
			if(finall.substring(i, i+1).equals("1")){
				finall2+=passBinary2.substring(j, j+1);
				j++;
			}
			else{
				if(passBinary2.substring(j, j+1).equals("1")){
					finall2+="0";
					j++;
				}
				else{
					finall2+="1";
					j++;
				}
			}
		}
		//		System.out.println("deco="+finall2);

		return finall2;
	}

}
