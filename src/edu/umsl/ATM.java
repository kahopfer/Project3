/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umsl;

import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

/**
 *
 * @author Chau, Kyle
 */
public class ATM
{
    ArrayList<Account> acctArray = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        ATM myAtm = new ATM();
        myAtm.readAccount();
        myAtm.atmMenu();
        myAtm.writeAccount();
    }
    
    public void readAccount()
    {
        try
        {
            FileInputStream fis = new FileInputStream("./accounts.out");
            ObjectInputStream ois = new ObjectInputStream(fis);
            acctArray = (ArrayList<Account>)ois.readObject();
            fis.close();
            if(acctArray.isEmpty())
            {
                createAccount();
            }
        }
        catch (Exception e)
        {         
            createAccount();
        }
    }
       
    public void atmMenu()
    {
        int input;
        
        do
        {
            System.out.println("        ATM MENU");
            System.out.println("------------------------");
            System.out.println("1. Create a new account");
            System.out.println("2. Remove an old account");
            System.out.println("3. Make a transaction");
            System.out.println("4. Exit");            
            System.out.println("------------------------");
            System.out.print("Please choose one: ");
            input = sc.nextInt();
            System.out.println();

            if(input == 1)
            {
                createAccount();
            }
            else if(input == 2)
            {
                removeAccount();
                System.out.println();
            }
            else if(input == 3)
            {
                makeTransaction();
                System.out.println();
            }
            else if(input == 4)
            {
                System.out.println("Goodbye!");
            }
            else
            {
                System.out.println("Invalid input");
                System.out.println();
            }       
        }while(input != 4);      
    }
    
    public void createAccount()
    {
        String aName;
        int aType;
        boolean accountAlreadyExists = false;

        System.out.print("Please enter a name for your new account: ");
        aName = sc.next();

        System.out.println();
        System.out.println("Which type of account would you like to add?");
        System.out.println("-------------------------------");
        System.out.println("1. Checking (10% interest rate)");
        System.out.println("2. Savings (90% interest rate)");
        System.out.println("-------------------------------");
        System.out.print("Please choose one: ");

        aType = sc.nextInt();
        System.out.println();

        if(aType == 1)
        {
            aName += " (Checking)";
        }
        else if(aType == 2)
        {
            aName += " (Savings)";
        }
        else
        {
            System.out.println("Invalid input.");
            System.out.println();
            return;
        }
          
        for(int i = 0; i < acctArray.size(); i++)
        {
            if(aName.equalsIgnoreCase(acctArray.get(i).getAcctName()))
            {
                accountAlreadyExists = true;
            }
        }
        
        if(accountAlreadyExists)
        {
            System.out.println();
            System.out.println("Sorry, there is already an account associated with that name.");
        }
        else
        {
            if(aName.endsWith("(Checking)"))
            {
                acctArray.add(new Checking(100.00, aName));
            }
            else if(aName.endsWith("(Savings)"))
            {
                acctArray.add(new Savings(100.00, aName));
            }
            System.out.println("Account " + aName.toUpperCase() + " was successfully created.");
        }
        
        System.out.println();

    }
    
    public void removeAccount()
    {
        int acctNumber;
        String accountName;
        
        if(!acctArray.isEmpty())
        {
            System.out.println("CURRENT ACCOUNTS");
            System.out.println("----------------");
            for(int i = 0; i < acctArray.size(); i++)
            {
                System.out.println("Account " + (i + 1) + ": " + acctArray.get(i).getAcctName().toUpperCase());
            }
            
            System.out.println("----------------");
            System.out.print("Please enter the number of the account you would like to remove: ");
            acctNumber = sc.nextInt();

            if(acctNumber <= acctArray.size() && acctNumber > 0)
            {
                for(int i = 0; i < acctArray.size(); i++)
                {
                    if(acctNumber == (i + 1))
                    {
                        accountName = acctArray.get(i).getAcctName().toUpperCase();
                        acctArray.remove(i);
                        System.out.println();
                        System.out.println("Account " + accountName + " was successfully removed.");
                    }
                }
            }
            else
            {
                System.out.println();
                System.out.println("There is no account associated with that number.");
            }
        }
        else
        {
            System.out.println("There are currently no accounts in the system.");
        }
    }
    
    public void makeTransaction()
    {       
        int input;
        
        if(!acctArray.isEmpty())
        {
            System.out.println("CURRENT ACCOUNTS");
            System.out.println("----------------");
            for(int i = 0; i < acctArray.size(); i++)
            {
                System.out.println("Account " + (i + 1) + ": " + acctArray.get(i).getAcctName().toUpperCase());
            }
            
            System.out.println("----------------");
            System.out.print("Please enter your account number: ");
            input = sc.nextInt();
            System.out.println();
            
            if(input <= acctArray.size() && input > 0)
            {
                for(int i = 0; i < acctArray.size(); i++)
                {
                    if(input == (i + 1))
                    {
                        acctArray.get(i).acctMenu();
                    }
                }
            }
            else
            {
                System.out.println("There is no account associated with that number.");
            }               
        }
        else
        {
            System.out.println("There are currently no accounts in the system.");
        }
    }
    
    public void writeAccount()
    {
        
//        XMLEncoder e = null;
//        try {
//            e = new XMLEncoder(
//                    new BufferedOutputStream(
//                            new FileOutputStream("Test.xml")));
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(ATM.class.getName()).log(Level.SEVERE, null, ex);
//        }
//       e.writeObject(acctArray);
//       e.close();
        try
        {
            FileOutputStream fos = new FileOutputStream("./accounts.out");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(acctArray);
            oos.flush();
            fos.close();
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}
