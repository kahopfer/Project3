/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umsl;

/**
 *
 * @author Kyle
 */
public class Checking extends Account
{
    public Checking(double begin_balance, String aName)
    {
        super(begin_balance, aName);
    }
    
    @Override
    protected void calcInterest()
    {
        int dateDiff = secondDate - firstDate;
        rate = .10/365;
        double rateTime = Math.pow(1+rate, dateDiff);
        balance *= rateTime;
        double amountInterest = balance - (balance/rateTime);
        accumInterest += amountInterest;
        firstDate = secondDate;
    }
}
