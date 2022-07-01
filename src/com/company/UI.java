package com.company;

public class UI {
  public final String RED = "\u001B[31m";
  public final String BLUE = "\033[0;34m";
  public final String RESET = "\u001B[0m";

  public void menu() {
    System.out.println("\n" + """
        1 - Move forward
        2 - Move back
        3 - Attack opponent
        4 - Place bomb in enemy camp
        5 - Detonate bomb
        6 - See board
        99 - Exit program
        """);
  }

  public void choice() {
    System.out.println("Make your input: ");
  }

  public void firstMove() {
    System.out.println("Welcome to line-battle!");
    System.out.println("You are located at the right side of the board, the enemy on the left.");
    statusBar(2500,25,2500,25);
    System.out.println("The first step is taken");
  }

  public void enemyIsClose(int playerLocation, int enemyLocation) {
    int tileDistance = playerLocation - enemyLocation;
    System.out.println(RED + "Beware: The scout reports that the enemy is " + tileDistance + " tiles away from you!" + RESET);
  }

  public void statusBar(int playerFirepower, int playerSoldiers, int enemyFirepower, int enemySoldiers) {
    System.out.println(BLUE + "Your firepower is " + playerFirepower + " || " + "Your number of soldiers: " + playerSoldiers + RESET);
    System.out.println(RED + "Enemy firepower is " + enemyFirepower + " || " + "Enemy soldiers left: " + enemySoldiers + RESET);
  }

  public void invalidInput() {
    System.out.println("Invalid input. Make input again.");
  }

  public void invalidBombPlaced() {
    System.out.println("You need to be in enemy camp to place bomb");
  }

  public void bombPlaced() {
    System.out.println("You successfully placed the bomb. Now move back!");
  }

  public void playerWonBombVictory() {
    System.out.println("Kaappooww! The bomb exploded! You won!");
  }

  public void invalidDetonateBomb() {
    System.out.println("You need to place the bomb in enemy camp, before you can detonate it");
  }

  public void playerWon() {
    System.out.println("You have defeated the enemy's soldiers. Congratulations, you won!");
  }

  public void enemyWon() {
    System.out.println("The enemy has defeated all your soldiers. You lost!");
  }

  public void playAgain() {
    System.out.println("Do you want to play again?");
    System.out.println("\n" + """
        1 - Yes
        2 - No
        """);
  }

  public void ExitingProgram() {
    System.out.println("Exitting program");
  }
}