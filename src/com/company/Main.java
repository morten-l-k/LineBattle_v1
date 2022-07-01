package com.company;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
  private int playerLocation;
  private int enemyLocation;
  private int playerSoldiers;
  private int enemySoldiers;
  private int playerFirepower;
  private int enemyFirepower;
  private boolean isBombPlaced;
  private boolean isBombExploded;
  private boolean runLoop = true;
  private String[] board = new String[21]; //Lokation 10 er midten
  UI ui = new UI();
  Scanner sc = new Scanner(System.in);
  Random random = new Random();

  public Main() {
    this.playerLocation = 20;
    this.enemyLocation = 0;
    this.playerSoldiers = 25;
    this.enemySoldiers = 25;
    this.playerFirepower = 2500;
    this.enemyFirepower = 2500;
    this.isBombPlaced = false;
    this.isBombExploded = false;
    this.board[0] = "Enemy";
    this.board[20] = "Player";
    for (int i = 0; i <= board.length - 1; i++) {
      if (board[i] == null) {
        board[i] = "0";
      }
    }
  }

  public int randNumber1_6() {
    return random.nextInt(1, 7);
  }

  public int randNumberForwardMove() {
    int number = random.nextInt(1, 7);
    int returnNumber = 0;
    if (number == 1 || number == 3 || number == 5) {
      returnNumber = 1;
    } else returnNumber = 2;
    return returnNumber;
  }

  public void movePlayerForward(int randForwardNumber) {
    int newLocation = getPlayerLocation() - randForwardNumber;
    if (newLocation < 0) {
      setPlayerLocation(0);
    } else setPlayerLocation(newLocation);
  }

  public int randNumberBackwardMove() {
    int number = random.nextInt(1, 7);
    int returnNumber = 0;
    if (number == 1 || number == 2) {
      returnNumber = 1;
    } else if (number == 3 || number == 4) {
      returnNumber = 2;
    } else returnNumber = 3;
    return returnNumber;
  }

  public void movePlayerBackward(int randomBackwardNumber) {
    int newLocation = getPlayerLocation() + randomBackwardNumber;
    if (newLocation > 20) {
      setPlayerLocation(20);
    } else setPlayerLocation(newLocation);
  }

  public int getPlayerOrEnemyLocation() {
    int location = 99;
    for (int i = 0; i <= board.length - 1; i++) {
      if (board[i].equals("Player") || board[i].equals("Enemy")) {
        location = i;
      }
    }
    return location;
  }

  public void moveEnemyBackward(int randomBackwardNumber) {
    int newLocation = getEnemyLocation() - randomBackwardNumber;
    if (newLocation < 0) {
      setEnemyLocation(0);
    } else setEnemyLocation(newLocation);
  }

  public void moveEnemyForward(int randNumberForwardMove) {
    int newLocation = getEnemyLocation() + randNumberForwardMove;
    if (newLocation > 20) {
      setEnemyLocation(20);
    } else setEnemyLocation(newLocation);
  }

  public void subtractEnemySoldiers(int randomNumber) {
    enemySoldiers -= randomNumber;
  }

  public void subtractPlayerSoldiers(int randomNumberEnemy) {
    playerSoldiers -= randomNumberEnemy;
  }

  public int subtractPlayerFirepower() {
    int rand = randNumber1_6();
    int firepower = 100 * rand;
    playerFirepower -= firepower;
    return rand;
  }

  public int subtractEnemyFirepower() {
    int rand = randNumber1_6();
    int firepower = 100 * rand;
    enemyFirepower -= firepower;
    return rand;
  }

  public void increasePlayerFirepower() {
    if (playerFirepower <= 2250) {
      this.playerFirepower += 250;
    } else this.playerFirepower = 2500;
  }

  public void increaseEnemyFirepower() {
    if (enemyFirepower <= 2250) {
      this.enemyFirepower += 250;
    } else this.enemyFirepower = 2500;
  }

  public boolean isEnemyClose() {
    return playerLocation - enemyLocation < 3 && playerLocation - enemyLocation > -4;
  }

  public void firstMove() {
      int numberPlayer = 21 - randNumber1_6();
      setPlayerLocation(numberPlayer);
      int numberEnemy = randNumber1_6();
      setEnemyLocation(numberEnemy);
    }

  public void setPlayerLocation(int playerLocation) {
    this.playerLocation = playerLocation;
  }

  public void setEnemyLocation(int enemyLocation) {
    this.enemyLocation = enemyLocation;
  }

  public int getPlayerLocation() {
    return playerLocation;
  }

  public int getEnemyLocation() {
    return enemyLocation;
  }

  public String[] getBoard() {
    for (int i = 0; i < board.length; i++) {
      board[i] = "0";
    }
    for (int i = 0; i < board.length; i++) {
      if (this.playerLocation == this.enemyLocation) {
        board[playerLocation] = "Enemy & Player";
      }
      if (this.playerLocation == i) {
        board[i] = "Player";
      }
      if (this.enemyLocation == i) {
        board[i] = "Enemy";
      }
    }
    return board;
  }

  //In current version, only player can place and detonate bomb
  public void setBombPlaced(boolean bombPlaced) {
    isBombPlaced = bombPlaced;
  }

  public void setBombDetonated(boolean bombDetonated) {
    isBombExploded = bombDetonated;
  }

  public void checkEnemeySoldiersStatus() {
    if (enemySoldiers <= 0) {
      ui.playerWon();
      runLoop = false;
    }
  }

  public void checkPlayerSoldiersStatus() {
    if (enemySoldiers > 0) {
      if (playerSoldiers <= 0) {
        ui.enemyWon();
        runLoop = false;
      }
    }
  }

  public void detonateBomb() {
    if (isBombPlaced) {
      setBombDetonated(true);
      if (playerLocation == 0) {
        playerSoldiers = 0;
      } else ui.playerWonBombVictory();
      runLoop = false;
    } else ui.invalidDetonateBomb();
  }

  public void placeBomb() {
    if (playerLocation == 0) {
      setBombPlaced(true);
      ui.bombPlaced();
    } else ui.invalidBombPlaced();
  }

  public void attackMove() {
    int randomNumberPlayer = subtractPlayerFirepower();
    subtractEnemySoldiers(randomNumberPlayer);
    int randomNumberEnemy = subtractEnemyFirepower();
    subtractPlayerSoldiers(randomNumberEnemy);
  }

  public void backwardMove() {
    movePlayerBackward(randNumberBackwardMove());
    increasePlayerFirepower();
    moveEnemyBackward(randNumberBackwardMove());
    increaseEnemyFirepower();
    if (isEnemyClose()) {
      ui.enemyIsClose(playerLocation, enemyLocation);
    }
  }

  public void forwardMove() {
    movePlayerForward(randNumberForwardMove());
    moveEnemyForward(randNumberForwardMove());
    if (isEnemyClose()) {
      ui.enemyIsClose(playerLocation, enemyLocation);
    }
  }

  public void run() {
    ui.firstMove();
    System.out.println(Arrays.toString(board));
    firstMove();
    while (runLoop) {
      ui.menu();
      ui.statusBar(playerFirepower, playerSoldiers, enemyFirepower, enemySoldiers);
      System.out.println(Arrays.toString(getBoard()));
      ui.choice();
      String choice = sc.nextLine();
      switch (choice) {
        case "1" -> {
          forwardMove();
        }
        case "2" -> {
          backwardMove();
        }
        case "3" -> {
          attackMove();
        }
        case "4" -> {
          placeBomb();
        }
        case "5" -> {
          detonateBomb();
        }
        case "6" -> {
          System.out.println(Arrays.toString(board));
          getBoard();
        }
        case "99" -> {
          runLoop = false;
        }
        default -> ui.invalidInput();
      }
      checkEnemeySoldiersStatus();
      checkPlayerSoldiersStatus();
    }
  }

  public void runProgram() {
    run();
    boolean checkInput = true;
    while (checkInput) {
      ui.playAgain();
      String input = sc.nextLine();
      switch (input) {
        case "1" -> {
          runLoop = true;
          Main main = new Main();
          main.run();
        }
        case "2" -> {
          checkInput = false;
          ui.ExitingProgram();
        }
      }
    }
  }

  public static void main(String[] args) {
    Main main = new Main();
    main.runProgram();
  }
}
