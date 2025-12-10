import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


// import java.io.*;
// import java.util.*;

//manage of inputs, outputs and using scanner class to set and get methods in playing class 
class ManageClientData {

    
    ManageClientData(){}
    ManageClientData(Clients client){}

    private File f = new File("Clients.txt");

    //convert line to obj
    private static Clients lineToClient(String line) {
        String split [] = line.split(", ");
        return new Clients(split[0], split[1], split[2], split[3], Double.parseDouble(split[4]));
    }

    public boolean checkAccNum(String accNum){
           

        try (Scanner sc = new Scanner(f)) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (line.startsWith(accNum + ", ")) 
                    return true;
            }


        } catch (FileNotFoundException e) {
            System.out.println("-----------FileNotFoundException Catching---------");
        }
        return false;
    }


    public void addNewClient(String accNum, String name, String phone, String pinCode, double accBalance) {
        //try with resources
        try (PrintWriter pw = new PrintWriter(new FileWriter(f, true))) {
            
           //Adding 
            if (f.length() > 0) pw.println();
            pw.println(accNum + ", " + name + ", " + phone + ", " + pinCode + ", " + accBalance);
        
        
        } catch (IOException e) {
            System.out.println("\"-------IOException Catching------\"");
        }
    }

    public void listOfClients() {
        try (Scanner sc = new Scanner(f)) {
            int index = 1;
            System.out.println("------------------------------");
            while (sc.hasNextLine()) {
                System.out.println("Client " + index + ":");
                System.out.println(sc.nextLine());
                System.out.println("------------------------------\n");

                index++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("-------FileNotFoundException Catching------");
        }
    }

    public void searchingByAccNum(String accNum) {

        try  {


            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.startsWith(accNum + ", ")) {
                    System.out.println(line);
                    sc.close();     
                    return;
                }
            }
            System.out.println("Client Not Found!");
            sc.close();




        } catch (FileNotFoundException e) {
            System.out.println("-------FileNotFoundException Catching------");
        }

        
    }

    //Update 
    public void updateClientData(String newAccNum, String name, String phone, String pinCode) {

        ArrayList<String> lines = new ArrayList<>();
        //String arr [] = new String[5]; => i can't do it by array becuase arr will be have on DT and we have more Dt as AccBalance or AccNum (String ,Double)


        //Step  1
        //save client will update in ArrayList
        try (Scanner sc = new Scanner(f)) {

            String line = sc.nextLine();
            Clients c = lineToClient(line);

            c.setAccNum(newAccNum);
            c.setName(name);
            c.setPhone(phone);
            c.setPinCode(pinCode);


            lines.add(c.getAccNum() + ", " + c.getName() + ", " + c.getphone() + ", " + c.getpinCode() + ", " + c.getaccBalance());  
            
            
        }catch  (FileNotFoundException ffe){
            System.out.println("---------IOException Catching--------");

        }

     
        //Step 2 
        //write Client was updated in file
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {



            for (String l : lines)  pw.print(l);




        } catch (IOException e) {
            System.out.println("---------IOException Catching--------");
        }


        System.out.println("Client Updated Successfully!");
    }
    public void updateClientData(String newAccNum) {

        ArrayList<String> lines = new ArrayList<>();
        //String arr [ ] = new String[5]; => i can't do it by array becuase arr will be have on DT and we have more Dt as AccBalance or AccNum (String ,Double)


        //Step  1
        //save client will update in ArrayList
        try (Scanner sc = new Scanner(f)) {

            String line = sc.nextLine();
            Clients c = lineToClient(line);

            c.setAccNum(newAccNum);


            lines.add(c.getAccNum() + ", " + c.getName() + ", " + c.getphone() + ", " + c.getpinCode() + ", " + c.getaccBalance());  
            
            
        }catch  (FileNotFoundException ffe){
            System.out.println("---------IOException Catching--------");

        }

     
        //Step 2 
        //write Client was updaed in file
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {



            for (String l : lines)  pw.print(l);




        } catch (IOException e) {
            System.out.println("---------IOException Catching--------");
        }


        System.out.println("Client Updated Successfully!");
    }    
   
    //Delete
    public void deleteClient(String accNum) {

        //we are following way, will add all client in ArrayList ( without client start by accnum we need removed ) , after that we write all client it found arrayList we write it in file 
        ArrayList<String> lines = new ArrayList<>();


        boolean falg = false;

        //Step  1

        // save old client in arrayList (Without client we need remove)
        try {
            Scanner sc = new Scanner(f);    

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.startsWith(accNum + ", ")) {
                    falg = true;
                    continue; 
                }
                lines.add(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("--------FileNotFoundException Catching-------");
            return;
        }

        if (falg != true) {
            System.out.println("Account not found!");
            return;
        }




        //Step  2
        //add clients in arrayList to file 
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            for (String l : lines)
                pw.println(l);
        } catch (IOException e) {
            System.out.println("----------IOException Caching--------");
        }

        System.out.println("Client Deleted Successfully!");
    }


    //transActions (Deposit/Withdrow)

    public void updateBalance(double amount, String accNum, char depositOrWithdrow ){
    
        ArrayList  <String> arrL = new ArrayList<>(); 
    
        try(Scanner sc = new Scanner(f) ){
        boolean flag = false;
        while (sc.hasNextLine()) {


            String line = sc.nextLine() ;

            if (line.startsWith(accNum + ", ")){
                flag = true;
                Clients c = lineToClient(line);
            
                //Withdrow
                if (depositOrWithdrow == '-'){

                    //realy balance - withDrow
                    if (amount <= c.getaccBalance() ) {
                        c.setAccBalance(c.getaccBalance()- amount);
                        arrL.add(c.getAccNum()+ ", " + c.getName() + ", " + c.getphone()+ ", " + c.getpinCode()+ ", " + c.getaccBalance());   

                    }   
                    else 
                        System.out.println("Cash not enough.");
                }


                //Deposit
                else{

                    //realy balance + Deposit
                    if(amount > 0){
                        c.setAccBalance(c.getaccBalance() + amount);
                        arrL.add(c.getAccNum() + ", " + c.getName() + ", " + c.getphone()+ ", " + c.getpinCode()+ ", " +c.getaccBalance());
                    }
                    else System.out.println("Transaction failed.");
                
                }

            }

            else{

                arrL.add(line);
                
            }
        }
           
        if(flag == false) ; System.out.println("Account is not found.");


    }catch(FileNotFoundException ffe){
        System.out.println("-----------FileNotFoundException Catching----------");
    }




    try(FileWriter fw = new FileWriter(f)){

        for (String l : arrL ){
            fw.write(l); 
        }



    }catch(IOException io){
        System.out.println("---------IOException Catching------");
    }
    







}









}

//special about client data onlyyyyyyyyyyy
class Clients {

    private String accNum ,  name , phone , pinCode ;
    private double accBalance ;
    



    //Constructors

    public Clients(){this.accBalance = 0.0;}
    public Clients (String accNum,String name , String phone , String pinCode, double accBalance ){
        this.accBalance = accBalance;
        this.accNum = accNum ;
        this.name = name ;
        this.phone = phone ;
        this.pinCode = pinCode ;
    }


    //Setter & Getter

    public void setAccNum(String accNum){
        this.accNum = accNum ;

    } 
    public void setName(String name){
        this.name = name ;

    }    
    public void setPhone(String phone){
        this.phone =phone ;

    }    
    public void setPinCode(String pinCode){
        this.pinCode = pinCode ;

    }    
    public void setAccBalance(double accBalance){
        this.accBalance = accBalance ;

    }
    public String getAccNum(){
        return accNum ;
    }  
    public String getName(){
        return name ;
    } 
    public String getphone(){
        return phone ;
    }    
    public String getpinCode(){
        return pinCode ;
    }    
    public double getaccBalance(){
        return accBalance ;
    }
    @Override
    public String toString(){
        return "Account Number: " + accNum + "\nName: "+name + "\nPin Code: "+ pinCode + "\nPhone: " + phone + "\nAccount Balance: "+ accBalance; 
    }
//this method will be return all line in file as obj, it's same of method LineToClient in Manage Class
    static Clients returnLineAsClientObj(String Line){
        String accNum = "" ,  name ="" ,  phone = "" ,  pinCode =""; double accBalance = 0.0 ;
        Scanner scn = new Scanner(Line);
        scn.useDelimiter(", ");

        while (scn.hasNext()) {
            accNum = scn.next();
            name = scn.next();
            phone = scn.next();
            pinCode = scn.next();
            accBalance = scn.nextDouble();
            
        }
        scn.close();
        return new Clients(accNum , name , phone , pinCode , accBalance);

    }
    






}


class Start {

    Scanner in = new Scanner(System.in);

    public static void clearScreen() {
        try {
    if (System.getProperty("os.name").contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } else {
        new ProcessBuilder("clear").inheritIO().start().waitFor();
    }
} catch (Exception e) {
    e.printStackTrace();
}
    }





    void start(){
    while (true){
        short choice  ;
        System.out.println("==============================================================\n-------------------Main Menu------------------\n==============================================================\n1_Add New Client\n2_Display All Client\n3_Search by Account Number\n4_Updating\n5_Delete\n6_Withdrow\n7_Deposit\n8_Logout\n-----------------------------------------------------------\nPlease Enter Your Choice:");
        choice = in.nextShort();in.nextLine();
        switch (choice ){

        case 1 ->{
            String accNum ,  name , phone , pinCode ; double accBalance;
            System.out.println("Enter your Account Number: ");
            accNum = in.nextLine()  ;
            System.out.println("Enter your Name: ");
            name = in.nextLine() ;            
            System.out.println("Enter your phone: ");
            phone = in.nextLine() ;            
            System.out.println("Enter your Pin Code: ");
            pinCode = in.nextLine() ;
            System.out.println("Enter a Balance: ");
            accBalance = in.nextDouble();in.nextLine();
            ManageClientData mcd = new ManageClientData() ;
            mcd.addNewClient(accNum, name, phone, pinCode, accBalance);
            //or  new ManageClientData().addNewClient(accNum, name, phone, pinCode, accBalance);

            System.out.println("Cleint Added Successfly, Please press Enter to back.");
            in.nextLine(); clearScreen();
        
        }

        case 2 -> {
            new ManageClientData().listOfClients();
            System.out.println("Please press Enter to back.");
            in.nextLine(); clearScreen();

        }

        case 3 -> {
            String accNum ;
            System.out.println("Enter Your Account Number you want to search for: ");
            accNum = in.nextLine();
            new ManageClientData().searchingByAccNum(accNum);
            System.out.println("Please press Enter to back.");
            in.nextLine(); clearScreen();

        }

        case 4 -> {
            String oldAccNum,  newAccNum,  name ,  phone ,  pinCode;
            //هعمل ميثود تشيك على الرقم القديم 

            System.out.println("Enter your Account Number to checking: ");
            oldAccNum = in.nextLine() ;
            if (new ManageClientData().checkAccNum(oldAccNum)){
                System.out.println("Enter your New Account Number: ");
                newAccNum = in.nextLine() ;
                

                //فيه مشكله الحلات مش منطقيه ( لازم اعمل فنكشن اخري ترجعلي قيم محدده)

                System.out.println("Do you want to change the rest of the data? (Y/N): ");
                if(in.nextLine().trim().toUpperCase().equals("Y")){

                    System.out.println("Enter a new name: ");
                    name  = in.nextLine();
                    System.out.println("Enter a new phone: ");
                    phone = in.nextLine();
                    System.out.println("Enter a new Pin Code: ");
                    pinCode = in.nextLine();
                    new ManageClientData().updateClientData(newAccNum, name, phone, pinCode);
                    return ;

                }
                else new ManageClientData().updateClientData(newAccNum);
                
                    
                }
                System.out.println("Account is not Found.");

            System.out.println("Please press Enter to back.");
            in.nextLine(); clearScreen();


            

            }
        
    
        
            
        
        case 5 -> {
            String accNum ;
            System.out.println("Enter an Account Number: ");
            accNum = in.nextLine();
            if (new ManageClientData().checkAccNum(accNum)){
                System.out.println("Are you sure about delete the Account? (Y/N): ");
                if(in.nextLine().trim().toUpperCase().equals("Y")){
                    new ManageClientData().deleteClient(accNum);
                }           
            }
            System.out.println("Please press Enter to back.");
            in.nextLine(); clearScreen();    
        }


        case 6 -> {
            String accNum ; double amount ;
            System.out.println("Enter an Account Number: ");
            accNum = in.nextLine();

            if (new ManageClientData().checkAccNum(accNum)){
                System.out.println("Enter amount of withdrow: ");
                amount = in.nextDouble();in.nextLine();
                System.out.println("Are you sure about Withdrow amount " + amount + " ? (Y/N): ");
                if(in.nextLine().trim().toUpperCase().equals("Y")){
                    new ManageClientData().updateBalance(amount, accNum, '-');
                    System.out.println("Transaction Successully");
                }
                else continue;           
            }
            System.out.println("Please press Enter back.");
            in.nextLine(); clearScreen(); 
        }

        case 7 -> {
            String accNum ; double amount ;
            System.out.println("Enter an Account Number: ");
            accNum = in.nextLine();

            if (new ManageClientData().checkAccNum(accNum)){
                System.out.println("Enter amount of Deposit: ");
                amount = in.nextDouble();in.nextLine();
                System.out.println("Are you sure about Deposit amount " + amount + " ? (Y/N): ");
                if(in.nextLine().trim().toUpperCase().equals("Y")){
                    new ManageClientData().updateBalance(amount, accNum, '+');
                    System.out.println("Transaction Successully");

                }
                else continue;           
            } 

            System.out.println("Please press Enter to back.");
            in.nextLine(); clearScreen();

        }


    
        case 8 -> {
            return;
        }

        
        default -> {
            System.out.println("Please Enter correct Choice, Press Enter.");
            in.nextLine();
            clearScreen();
            continue;

        }









            
    
    
    }



        }
    }
}




    









public class Bank {

    static public void main (String a[]){


        new Start().start();




    }
    
}
