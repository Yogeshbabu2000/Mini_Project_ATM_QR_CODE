//Imports
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


//Class that contains main and to repl
public class Main_Innovative {
    // Main class
    public static void main(String[] args) throws WriterException,IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED_BACKGROUND = "\u001B[41m";
        final String ANSI_YELLOW="\u001B[33m";
        String FilePath = "./";
        boolean b = true;
        // Menu for register,login and to exit
        while (b) {
            new ProcessBuilder("cmd", "/c", "cls").start().waitFor();
            System.out.println(ANSI_RED_BACKGROUND+"\n-----WELCOME TO ATM-----"+ANSI_RESET);
            System.out.println("1. Register New User");
            System.out.println("2. Login for existing user");
            System.out.println("3. Exit Program");
            System.out.print("Enter Choice : ");
            // int choice = sc.nextInt();
            int choice = 3;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
            }
            // System.out.println();
            brea: {
                // Register with name,username, password, phone and check if account already
                // exist
                if (choice == 1) {

                    System.out.println("Enter Name : ");
                    sc.nextLine();
                    String name = sc.nextLine();

                    System.out.println("Enter Username : ");
                    String username = sc.nextLine();

                    File file = new File(FilePath + username + ".txt");
                    File f = new File(FilePath + username + ".jpg");

                    if (file.exists()) {
                        System.out.println("Username Already Taken !");
                        System.out.println("\nPress enter to continue");
                        try {
                            System.in.read();
                        } catch (Exception e) {
                        }
                        break brea;
                    }

                    // System.out.println("Enter Password : ");
                    // String password=sc.nextLine();
                    Console console = System.console();
                    if (console == null) {
                        System.out.println("Couldn't get Console instance");
                        System.exit(0);
                    }
                    char[] passwordArray = console
                            .readPassword("Enter your secret password: (It won't show due to security reasons) \n");
                    String password = new String(passwordArray);
                    // System.out.println(password);
                    System.out.println("Password Length : " + password.length() + ".");

                    System.out.println("Enter Phone Number : ");
                    String number = sc.nextLine();

                    System.out.println("Enter Account Number : ");
                    try{
                    String cardNumber = sc.nextLine();
                    } catch (Exception){
                        
                    }

                    User_Info reg = new User_Info(name, username, password, number,cardNumber, FilePath);
                    reg.message();

                    System.out.println("\nPress enter to continue");
                    try {
                        System.in.read();
                    } catch (Exception e) {
                    }

                }
                // Login with username and password
                else if (choice == 2) {
                    System.out.println();
                    System.out.println("Enter Username : ");
                    sc.nextLine();
                    String username = sc.nextLine();

                    Console console = System.console();
                    if (console == null) {
                        System.out.println("Couldn't get Console instance");
                        System.exit(0);
                    }
                    char[] passwordArray = console
                            .readPassword("Enter your secret password: (It won't show due to security reasons) \n");
                    String password = new String(passwordArray);

                    boolean bool = true;

                    File file = new File(FilePath + username + ".txt");
                    //File f=new File(FilePath + username + ".jpg");
                    
                    // If login successful
                    if (file.exists()) {
                        try {
                            // Take file data in variables
                            Scanner dataReader = new Scanner(file);
                            String money = dataReader.nextLine();
                            int login_money = Integer.parseInt(money);
                            String login_name = dataReader.nextLine();
                            String login_username = dataReader.nextLine();
                            String login_password = dataReader.nextLine();
                            String number = dataReader.nextLine();
                            if (username.equals(login_username) && password.equals(login_password)) {

                                // -----------------------------------------------------------------

                                boolean bo = true;
                                // Menu for logged in customers
                                while (bo) {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println("Welcome " + login_name + " !");
                                    System.out.println("\n**** OPERATIONS ****");
                                    System.out.println("1. Deposit Money");
                                    System.out.println("2. Withdraw Money");
                                    System.out.println("3. View Details/Balance");
                                    System.out.println("4. Logout ");
                                    System.out.print("Enter Choice : ");
                                    int choice2 = sc.nextInt();
                                    System.out.println();

                                    // Deposit Method
                                    if (choice2 == 1) {
                                        System.out.println("Enter Amount to Deposit : (Limit : 50,000)");
                                        int amount = sc.nextInt();
                                        // Validation
                                        if (amount < 0 || amount > 50000) {
                                            System.out.println("Enter correct amount !");
                                            System.out.println("\nPress enter to continue");
                                            try {
                                                System.in.read();
                                            } catch (Exception e) {
                                            }
                                        } else {
                                            try {
                                                // Open file, Replace original amount with updated with time also
                                                FileWriter f0 = new FileWriter(FilePath + username + ".txt", true);
                                                
                                                String old_money = Integer.toString(login_money);
                                                login_money += amount;
                                                int temp = amount;
                                                String to_be_deposited = Integer.toString(login_money);
                                                modifyFile(FilePath + username
                                                        + ".txt", old_money, to_be_deposited);
                                                /*modifyFile(FilePath + username + ".txt", "TRANSACTION:0",
                                                        "TRANSACTION:1");*/
                                                //Creating qr code for deposite operation without physical ATM card
                                                       /* createQR(data, path, "UTF-8", hashMap, 200, 200);
                                                        System.out.println("QR Code Generated!!! ");
                                                        
                                                        Image image = readImageFile(path);
                                                        
                                                        displayImage(image);
                                                        System.out.println("Scan the Qr Code with in a 10 seconds");
                                                        Thread.sleep(10000);*/

                                                System.out.println("Rs. " + temp + " Deposited !");

                                                System.out.println("\nPress enter to continue");
                                                try {
                                                    System.in.read();
                                                } catch (Exception e) {
                                                }

                                                f0.close();
                                            } catch (IOException e) {
                                                System.out.println("User Data not found !");
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    // Withdraw method
                                    else if (choice2 == 2) {

         String data = username+" "+number;

		// The path where the image will get saved and display on ATM
		String path = "C:\\Users\\Admin\\Desktop\\YOGESH\\yogesh.jpg";

		Map<EncodeHintType, ErrorCorrectionLevel> hashMap= new HashMap<EncodeHintType,ErrorCorrectionLevel>();

		hashMap.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);

	                                    System.out.println("Enter Amount to Withdraw : (Limit : 0 to " + login_money + ")");
                                        int amount_withdraw = sc.nextInt();
                                        // Validation
                                        if (amount_withdraw < 0 || amount_withdraw > login_money) {
                                            System.out.println("Enter correct amount !");
                                            System.out.println("\nPress enter to continue");
                                            try {
                                                System.in.read();
                                            } catch (Exception e) {
                                            }
                                        } else {
                                            try {
                                                // Open file, Replace original amount with updated with time also
                                                FileWriter f0 = new FileWriter(FilePath + username + ".txt", true);
                                                String old_money = Integer.toString(login_money);
                                                login_money -= amount_withdraw;
                                                int temp1 = amount_withdraw;                                                
                                                String to_be_withdrawed = Integer.toString(login_money);
                                                modifyFile(FilePath + username + ".txt", old_money, to_be_withdrawed);
        createQR(data, path, "UTF-8", hashMap, 200, 200);
		System.out.println("QR Code Generated!!! ");
		
		Image image = readImageFile(path);
		
		displayImage(image);
		System.out.println("Scan the Qr Code with in a 10 seconds");
		Thread.sleep(10000);
        
        
                                                System.out.println("Rs. " + temp1 + "Successfully Withdrawed !");

                                                System.out.println("\nPress enter to continue");
                                                try {
                                                    System.in.read();
                                                } catch (Exception e) {
                                                }

                                                f0.close();
                                            } catch (IOException e) {
                                                System.out.println("User Data not found !");
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    // Shwo all details from variables that we stored file contents
                                    else if (choice2 == 3) {
                                        System.out.println("\nDetails :");
                                        System.out.println("1. Name      : " + login_name);
                                        System.out.println("2. Username  : " + login_username);
                                        System.out.printf("3. Password  : ");
                                        for (int i = 0; i < login_password.length(); i++) {
                                            System.out.printf("*");
                                        }
                                        System.out.println();
                                        System.out.println("4. Phone No. : " + number);
                                        System.out.println("5. Balance   : " + login_money + " Rs.");

                                        System.out.println("\nPress enter to continue");
                                        try {
                                            System.in.read();
                                        } catch (Exception e) {
                                        }
                                    }
                                   else {
                                        break brea;
                                    }
                                }
                                sc.close();

                                // -------------------------------------------------------------------

                                bool = false;
                                break brea;
                            }

                            dataReader.close();
                        } catch (FileNotFoundException e) {
                            System.out.println("File not found !");
                            e.printStackTrace();
                            System.out.println("\nPress enter to continue");
                            try {
                                System.in.read();
                            } catch (Exception E) {
                            }
                        }
                    } else {
                        System.out.println("User not registered!");
                        System.out.println("\nPress enter to continue");
                        try {
                            System.in.read();
                        } catch (Exception e) {
                        }
                    }

                    if (bool) {
                        System.out.println("Username or Password Incorrect !\nPlease Try Again");
                        System.out.println("\nPress enter to continue");
                        try {
                            System.in.read();
                        } catch (Exception e) {
                        }
                        break brea;
                    }
                } else if (choice == 3) {
                    System.out.println(ANSI_YELLOW+"\n***** Thank you Visit Again *****"+ANSI_RESET );
                    System.out.println("\nPress enter to continue");
                    System.out.println("\n");
                    System.exit(0);
                    try {
                        System.in.read();
                    } catch (Exception e) {
                    }
                    sc.close();
                    b = false;
                } else {
                    System.out.println("Enter correct number input !");
                    System.out.println("\nPress enter to continue");
                    try {
                        System.in.read();
                    } catch (Exception e) {
                    }
                }
            }
            System.out.println();
            
        }
    }

    // Replacing string in file function (static)
    static void modifyFile(String filePath, String oldString, String newString) {
        // File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = null;
        // FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            // Reading all the lines of input text file into oldContent
            String line = reader.readLine();
            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }
            // Replacing oldString with newString in the oldContent
            String newContent = oldContent.replaceFirst(oldString, newString);

            // Rewriting the input text file with newContent
            new FileWriter(filePath, false).close();
            FileWriter writer = new FileWriter(filePath);
            writer.write(newContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Closing the resources
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Function to create the QR code
	public static void createQR(String data, String path,String charset, Map hashMap,int height, int width)throws WriterException, IOException
	{

		BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset),BarcodeFormat.QR_CODE, width, height);

		MatrixToImageWriter.writeToFile(matrix,path.substring(path.lastIndexOf('.') + 1),new File(path));
	}
	// Reading the Image from file
	public static Image readImageFile(String fileName) {

		// Defining the Image Object
		Image image = null;
		
		if(fileName != null && !"".equals(fileName)) {
			
			// Source Image from File Location
			File imageFile = new File(fileName);
			
			try {
				// Reading the image from file using ImageIO
				image = ImageIO.read(imageFile);
				
			} catch (IOException e) {
				
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
		} else {
			
			System.out.println("Please provide the valid path or file name");
		}
		
		return image;
	}
	
	// Displaying the image to window
	public static void displayImage(Image image) {
		
		// Creating the window to display the image
		JFrame window = new JFrame();
		
		if(image != null) {
			window.setTitle("Image Viewer");
			window.setSize(700, 500);
			window.setVisible(true);
			
			// Creating the image icon on JLabel
			JLabel label = new JLabel(new ImageIcon(image));
			label.setVisible(true);
			window.add(label);
		}
    }

	// Function to read the QR file
	public static String readQR(String path, String charset,Map hashMap)throws FileNotFoundException, IOException,NotFoundException
	{
		BinaryBitmap binaryBitmap= new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));
	
		Result result= new MultiFormatReader().decode(binaryBitmap);
	
		return result.getText();
	}    
}
