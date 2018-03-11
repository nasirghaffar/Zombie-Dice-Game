/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaassignment3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JavaAssignment3 {
        
    boolean gameOver;

    int noOfPlayers;

    List<Player> players = new ArrayList<>();

    Player currentPlayer;
    int turnIdx;


    
    int brainCount;
    boolean gamestatus;
    
    Cup c;
    int shotsCount;
    // Lets Assume that 1 = green, 2 = yellow, 3= red
    int colour;
    // 1= brain; 2= footprint; 3 = shot
    int noOfRunners;
    int noOfShots;
    ZombieDiceBoard z=new ZombieDiceBoard();
    
     public  static void main(String[] args) {
    
         JavaAssignment3 js=new JavaAssignment3();
       js.z.initiliaz();
       js.z.getGameDisplay();
       
    
     
        
}
    
    public class ZombieDiceBoard {
    

    Roll newroll, lastroll;


   ZombieDiceBoard() {
        turnIdx = 0;
    }

   void initiliaz(){
      gameOver=false;

   }

    void getGameDisplay() {
        System.out.println("*****************************************");
        System.out.println("*      WELCOME TO ZOMBIE DICE GAME      *");
        System.out.println("*****************************************");

        Scanner reader = new Scanner(System.in);
        System.out.println("Enter the Number of players:");
        //Incase user Enters Wrong input
        
        noOfPlayers = reader.nextInt();
       
       
        for (int i = 1; i <= noOfPlayers; i++) {
            Scanner namereader = new Scanner(System.in);
            System.out.println("Enter player " + i + " name:");
            String name = namereader.next();
            Player p = new Player(name);
            players.add(p);
        }

        currentPlayer = players.get(turnIdx);
        displayScore();

        Scanner inputreader = new Scanner(System.in);
        String userinput;
        while (!gameOver) {
            currentPlayer.runnerCount = 0;
            System.out.println("************************************************");
            System.out.println("Turn for  Player : " + currentPlayer.name);
            System.out.println("\nPress P to pick the dice.");
            userinput = inputreader.next();
            if (userinput.equalsIgnoreCase("P")) {
                boolean dead = false;
                boolean allrunner = false;
                newroll = currentPlayer.selectDicetoRoll();
                System.out.println("\nYour dice for this roll are: ");
                displaySelectedDice();
                while (!userinput.equalsIgnoreCase("R")) {
                    System.out.println("\nPress R to roll the dice. ");
                    userinput = inputreader.next();
                    if (userinput.equalsIgnoreCase("R")) {
                        currentPlayer.rollDice(newroll);
                        displayDiceValues();
                        currentPlayer.updateBoard();

                        dead = checkShotsLimitCrossed();
                      

                        gameOver = checkGameOver();
                       

                        allrunner = checkRunnerCount();
                       
                        displayScore();
                          if (dead) {
                            changeTurn();
                        }
                           if (allrunner) {
                            changeTurn();
                        }
                            if (gameOver) {
                            System.out.println("GAME OVER");
                            System.out.println(currentPlayer.name + " is the winner.");
                        }
                        Scanner continueoption = new Scanner(System.in);
                        if (!dead) {
                            while (true) {
                                System.out.println("\nDo you want to continue? Y / N");
                                String continueTurn = continueoption.next();
                                if (continueTurn.equalsIgnoreCase("N")) {
                                    changeTurn();
                                    break;
                                }
                                if (continueTurn.equalsIgnoreCase("Y")) {
                                    break;
                                }
                                
                            }
                        }
                    }
                }
            }
        }

    }

    void displayScore() {
        System.out.println("\n\n---------------------------------S C O R E B O A R D------------------------\n");
        System.out.println("| Players  \t|\tTotal Brains Eaten \t|\tShotguns Taken");
        for (int i = 0; i < noOfPlayers; i++) {
            System.out.println("| "+players.get(i).name + "\t\t|\t\t" + players.get(i).brainCount + "\t\t|\t\t" + players.get(i).shotsCount);

        }
        System.out.println("\n");
        System.out.println("\n\n---------------------------------------END----------------------------------\n");

    }

    private void displaySelectedDice() {
        for (int i = 0; i < 3; i++) {
            System.out.println(currentPlayer.newroll.dc.get(i).getColour());
        }
        System.out.print("\n");
    }

    private void displayDiceValues() {

        for (int i = 0; i < 3; i++) {
            System.out.println(currentPlayer.newroll.dc.get(i).getColour() + "\t" + currentPlayer.newroll.dc.get(i).getDiceOutput());
        }
        System.out.println("\n");

    }

    private boolean checkShotsLimitCrossed() {
        if (currentPlayer.shotsCount >= 3) {
            System.out.println("\nYou were shot 3 times. You are dead. Next player's turn.");
            currentPlayer.brainCount = 0;
            return true;
        }
        return false;
    }

    private void changeTurn() {

        currentPlayer.shotsCount = 0;

        turnIdx = (turnIdx + 1) % noOfPlayers;
        currentPlayer = players.get(turnIdx);
    }

    private boolean checkRunnerCount() {
        if (currentPlayer.runnerCount >= 3) {
            System.out.println("All your targets ran away. Next player's turn.");
            return true;
        }
        return false;
    }

    private boolean checkGameOver() {
        if (currentPlayer.brainCount >= 13) {
            return true;
        }
        return false;
    }

}
    public class Roll {
    
    List<Die> dc = new ArrayList<>();
    
    int noOfRunners;
    int noOfShots;
    
    
    Roll()
    {
        noOfRunners =0;
        noOfShots =0;

    }
    
}


    public class Die {

    
    
    final int[]  greenDie = {1,1,1,2,2,3};
    final int[]  yellowDie = {1,1,2,2,3,3};
    final int[]  redDie = {1,2,2,3,3,3};
        int value=0; 
        boolean inCup;


    
    public Die(int c) {
        inCup =true;
        colour = c;
    }

    Die() {

    }

    
    String getColour()
    {
        switch(colour)
        {
            case 1:
                return "Green";
            case 2:
                return "Yellow";
            case 3:
                return "Red";
            default:
                return "";
        }
        
    }
    
    String getDiceOutput()
    {
        switch(value)
        {
            case 1:
                return "Braaaaaaaains";
            case 2:
                return "FootPrints";
            case 3:
                return "Shot";
            default:
                return "";
        }
        
    }
    

    int rollDie() {
        switch (colour) {
            case 1:
               //value = greenDie[1 + (int) (new Random().nextInt()*6) -1];
                  value = greenDie[1 + (int) (Math.random() * 6) -1];

                break;
            case 2:
                value = yellowDie[1 + (int) (Math.random() * 6) -1];
                break;
            case 3:
                value = redDie[1 + (int) (Math.random() * 6) -1];
                break;
            default:
                break;
        }
        return value;
    }
    boolean isBrain() {
            if (value == 1) {
            return true;
        }
        return false;
        
    }
    boolean isRunner() {
        if (value == 2) {
            return true;
        }
        return false;
    }
    

    boolean isShot() {
        if (value == 3) {
            return true;
        }
        return false;
    }

}
    
    public class Player {
    
       int brainCount;
    boolean gamestatus;
    String name;
    Roll newroll, oldroll;
    
    Cup c;
    int shotsCount;
    int runnerCount;
    Roll rollDice(Roll r) {
        for (int i = 0; i < 3; i++) {
            r.dc.get(i).value = r.dc.get(i).rollDie();
        }
        return r;
    }

    Roll selectDicetoRoll() {
        
        newroll = new Roll();

        if (oldroll != null) {
            for (int i = 0; i < 3; i++) {
                if (oldroll.dc.get(i).isRunner()) {
                    newroll.dc.add(oldroll.dc.get(i));
                }

            }
        }

        for (int i = 0; i < 3; i++) {
            if (newroll.dc.size() < 3) {
                newroll.dc.add(c.pickDieFromCup());
            }

        }

        return newroll;

    }
    

    void updateBoard()
    {
        for(int i=0;i<3;i++)
        {
            if (newroll.dc.get(i).isBrain())
                brainCount++;
            else if (newroll.dc.get(i).isShot())
                shotsCount++;
            else 
                runnerCount++;
                
            
        }
        oldroll = newroll;
    }
    
    
    
    Player(String nm)
    {
        name = nm;
        c = new Cup();
        oldroll = null;
        newroll = new Roll();
    }
    

    
   
    
}

        

     public class Cup {

    List<Die> dice = new ArrayList<>();



    public Cup() {

        for (int i = 0; i < 6; i++) {
            Die d = new Die(1);
            dice.add(d);
        }

        for (int i = 0; i < 4; i++) {
            Die d = new Die(2);
            dice.add(d);
        }

        for (int i = 0; i < 3; i++) {
            Die d = new Die(3);
            dice.add(d);
        }

       
    }

    Die pickDieFromCup() {

        Die d = new Die();
        boolean atleastOneincup = false;
        for(int i =0; i<13;i++)
        {
            
            if(dice.get(i).inCup)
                atleastOneincup = true;
            
        }
        
        if(!atleastOneincup)
        {
            for(int i = 0; i<=13;i++)
            {
                if (dice.get(i).value==1)
                    dice.get(i).inCup= false;
            }
        }
        while (true) {
            int index = 1 + (int) (Math.random() * 13) -1;
            d = dice.get(index);
            
            if ((d.inCup)) {
                dice.get(index).inCup = false;
            }
            break;
        }
        return d; 
    }

    

}
     
     
}
   
        
    

    

