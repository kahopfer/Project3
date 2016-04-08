/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umsl;

import java.io.*;
import java.util.*;
import java.text.*;

/**
 *
 * @author Chau, Kyle
 */
public abstract class Account implements Serializable
{
    protected double balance;
    protected String acctName;
    protected int firstDate;
    protected int secondDate;
    protected boolean usePrevDate = false;
    protected Calendar cal1 = new GregorianCalendar();
    protected Calendar cal2 = new GregorianCalendar();
    protected boolean dateflag = false;
    protected double rate;
    protected double accumInterest;
    protected String dispDate;
    
    public Account(double begin_balance, String aName)
    {
        balance = begin_balance;
        acctName = aName;
    }
    
    public void acctMenu()
    {
        Scanner sc = new Scanner(System.in);
        char input;

        for(;;)
        {
            System.out.println("Account Name: " + acctName.toUpperCase());
            System.out.println("--------------------");
            System.out.println("a. Deposit");
            System.out.println("b. Withdraw");
            System.out.println("c. Check Balance");
            System.out.println("d. Back to Main Menu");
            System.out.println("--------------------");
            System.out.print("Please choose one: ");
 
            String str = sc.next();
            input = str.charAt(0);

            if((input == 'a' || input == 'A') && str.length() == 1)
            {
                System.out.println();
                if(dateflag)
                {
                    getDate2();                   
                    if(!usePrevDate)
                    {
                        calcInterest();
                    }              
                    deposit();
                    usePrevDate = false;                  
                }
                else
                {
                    getDate1();
                    deposit();
                }
            }
            else if((input == 'b' || input == 'B') && str.length() == 1)
            {
                
                System.out.println();
                if(dateflag)
                {
                    getDate2();
                    if(!usePrevDate)
                    {
                        calcInterest();                        
                    }
                    withdraw();                   
                    usePrevDate = false;               
                }
                else
                {
                    getDate1();
                    withdraw();
                }
            }
            else if((input == 'c' || input == 'C') && str.length() == 1)
            {
                System.out.println();
                if(dateflag)
                {
                    getDate2();
                    if(!usePrevDate)
                    {
                        calcInterest();
                    }
                    if(accumInterest > 0)
                    {
                        System.out.println("Accumulated earned interest: " + formatCurrency(accumInterest));                       
                    }      
                    checkBalance();
                    usePrevDate = false;                   
                }
                else
                {
                    getDate1();
                    checkBalance();
                }
            }
            else if((input == 'd' || input == 'D') && str.length() == 1)
            {
                break;
            }
            else if(str.length() > 1)
            {
                System.out.println();
                System.out.println("Please enter only one character at a time.");
                System.out.println();
            }
            else
            {
                System.out.println();
                System.out.println("Invalid input.");
                System.out.println();
            }
        }
    }
    
    protected void deposit()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the amount you would like to deposit: $");
        double amount = sc.nextDouble();
        balance += amount;
        System.out.println();
        System.out.println(formatCurrency(amount) + " was deposited into your account.");
        System.out.println();
    }
    
    protected void withdraw()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the amount you would like to withdraw: $");
        double amount = sc.nextDouble();
        if(amount > balance)
        {
            System.out.println();
            System.out.println("Insufficient funds.");
        }
        else
        {
            balance -= amount;
            System.out.println();
            System.out.println(formatCurrency(amount) + " was withdrawn from your account.");
        }     
        System.out.println();
    }
  
    protected void checkBalance()
    {
        System.out.println("Current Balance: " + formatCurrency(balance));
        System.out.println();
    }
    
    protected void getDate1()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter today's date (mm/dd/yyyy): ");
        String input = sc.next();
        dispDate = input;
        System.out.println();

        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");        
            ParsePosition pos = new ParsePosition(0);
            Date myDate = formatter.parse(input, pos);
            cal1.setTime(myDate);
            firstDate = cal1.get(Calendar.DAY_OF_YEAR);
            dateflag = true;
        }
        catch(Exception e)
        {
            System.out.println("Invalid input.");
            System.out.println();
            getDate1();
        }       
    }
    
    protected void getDate2()
    {   
        boolean invalidInput = false;
        boolean pastDate = false;
         
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter today's date [mm/dd/yyyy] or 'p' to use previous date (" + dispDate + "): ");        
        String input = sc.next();
        System.out.println();
        
        if(input.charAt(0) == 'p' && input.length() == 1)
        {
            usePrevDate = true;
        }
        else
        {
            try
            {
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");        
                ParsePosition pos = new ParsePosition(0);
                Date myDate = formatter.parse(input, pos);
                cal2.setTime(myDate);
                secondDate = cal2.get(Calendar.DAY_OF_YEAR);

                if(firstDate > secondDate)
                {
                    System.out.println("You must enter a future date.");
                    System.out.println();
                    pastDate = true;
                    getDate2();
                }
            }
            catch(Exception e)
           {
                System.out.println("Invalid input");
                System.out.println();
                invalidInput = true;
                getDate2();
            }
            if(input.charAt(0) != 'p' && invalidInput == false && pastDate == false)
            {
                dispDate = input;
            }                   
        }
    }
   
    protected abstract void calcInterest();
    
    protected String formatCurrency(double dollars)
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(dollars);
        return moneyString;
    }

    public String getAcctName()
    {
        return acctName;
    }
}
