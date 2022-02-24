package org.scrum.psd.battleship.ascii;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.*;

public class Main {
    private static List<Ship> myFleet;
    private static List<Ship> enemyFleet;
    private static ColoredPrinter console;

    private static List<Position> positions1=new ArrayList<Position>();
    private static List<Position> positions2=new ArrayList<Position>();
    private static List<Position> positions3=new ArrayList<Position>();
    private static List<Position> positions4=new ArrayList<Position>();
    private static List<Position> positions5=new ArrayList<Position>();


    public static void main(String[] args) {
        console = new ColoredPrinter.Builder(1, false).background(Ansi.BColor.BLACK).foreground(Ansi.FColor.WHITE).build();

        console.setForegroundColor(Ansi.FColor.MAGENTA);
        console.println("                                     |__");
        console.println("                                     |\\/");
        console.println("                                     ---");
        console.println("                                     / | [");
        console.println("                              !      | |||");
        console.println("                            _/|     _/|-++'");
        console.println("                        +  +--|    |--|--|_ |-");
        console.println("                     { /|__|  |/\\__|  |--- |||__/");
        console.println("                    +---------------___[}-_===_.'____                 /\\");
        console.println("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _");
        console.println(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7");
        console.println("|                        Welcome to Battleship                         BB-61/");
        console.println(" \\_________________________________________________________________________|");
        console.println("");
        console.setForegroundColor(Ansi.FColor.WHITE);

        InitializeGame();

        StartGame();
    }

    private static void StartGame() {
        Scanner scanner = new Scanner(System.in);
        boolean endGame=false;

        console.print("\033[2J\033[;H");
        console.println("                  __");
        console.println("                 /  \\");
        console.println("           .-.  |    |");
        console.println("   *    _.-'  \\  \\__/");
        console.println("    \\.-'       \\");
        console.println("   /          _/");
        console.println("  |      _  /\" \"");
        console.println("  |     /_\'");
        console.println("   \\    \\_/");
        console.println("    \" \"\" \"\" \"\" \"");

        do {
            console.setBackgroundColor(Ansi.BColor.WHITE);
            console.setForegroundColor(Ansi.FColor.BLACK);
            console.println("");
            console.println("Player, it's your turn");
            console.println("Enter coordinates for your shot :");

            String input = scanner.next();
            if(!GameController.isInputValid(input)){
                console.println("Wrong coordinates, again!");
                continue;
            }

            Position position = parsePosition(input);
//            boolean isHit = GameController.checkIsHit(enemyFleet, position);

            Optional<Ship> shottedShip = GameController.getShottedShip(enemyFleet, position);
            if (shottedShip.isPresent()) {
                beep();
                console.setForegroundColor(Ansi.FColor.RED);
                console.println("Nice hit !");
                console.println("                \\         .  ./");
                console.println("              \\      .:\" \";'.:..\" \"   /");
                console.println("                  (M^^.^~~:.'\" \").");
                console.println("            -   (/  .    . . \\ \\)  -");
                console.println("               ((| :. ~ ^  :. .|))");
                console.println("            -   (\\- |  \\ /  |  /)  -");
                console.println("                 -\\  \\     /  /-");
                console.println("                   \\  \\   /  /");

                if(shottedShip.get().isDestroyed()){
                    console.println(String.format("Ship %s is destroyed", shottedShip.get().getName()));
                    System.out.println();
                    System.out.println();
                    for(Ship ship: GameController.getReadyToAttackShip(enemyFleet)){
                        console.println(String.format("Ship %s still wanna kill you", ship.getName()));
                    }
                }


                if(GameController.checkYouWin(enemyFleet)){
                    console.println("YOU   ARE   THE   WINNER!");
                    endGame=true;
                }

            }
            else{
                console.setForegroundColor(Ansi.FColor.BLUE);
                console.println("You Miss!!");
            }
            System.out.println();
            System.out.println();


            position = getRandomPosition();
            boolean isHit = GameController.checkIsHit(myFleet, position);

            console.println("");
            console.setBackgroundColor(Ansi.BColor.YELLOW);

            if(!endGame) {
                if (isHit) {
                    beep();
                    console.setForegroundColor(Ansi.FColor.RED);
                    console.print(String.format("Computer shoot in %s%s and ", position.getColumn(), position.getRow()));
                    console.println("Computer Nice Hit !");
                    console.println("                \\         .  ./");
                    console.println("              \\      .:\" \";'.:..\" \"   /");
                    console.println("                  (M^^.^~~:.'\" \").");
                    console.println("            -   (/  .    . . \\ \\)  -");
                    console.println("               ((| :. ~ ^  :. .|))");
                    console.println("            -   (\\- |  \\ /  |  /)  -");
                    console.println("                 -\\  \\     /  /-");
                    console.println("                   \\  \\   /  /");

                    if (GameController.checkYouWin(myFleet)) {
                        console.println("You lost!");
                        endGame = true;
                    }

                } else {
                    console.setForegroundColor(Ansi.FColor.BLUE);
                    console.print(String.format("Computer shoot in %s%s and ", position.getColumn(), position.getRow()));
                    console.println("Computer Miss!!");
                    System.out.println();
                    System.out.println();

                }
            }

            if(endGame){
                endGame=false;

                console.setBackgroundColor(Ansi.BColor.WHITE);
                console.setForegroundColor(Ansi.FColor.BLACK);
                console.println("press R - If you want to restart game!");
                console.println("press X - If you want to end game!");
                String signal = scanner.next();

                if("R".equals(signal.toUpperCase().substring(0,1))){
                    InitializeGame();
                }
                if("X".equals(signal.toUpperCase().substring(0,1))){
                    System.exit(0);
                }


            }

        } while (true);
    }

    private static void beep() {
        console.print("\007");
    }

    protected static Position parsePosition(String input) {
        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));
        return new Position(letter, number);
    }

    private static Position getRandomPosition() {
        int rows = 8;
        int lines = 8;
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(lines)];
        int number = random.nextInt(rows);
        Position position = new Position(letter, number);
        return position;
    }

    private static void InitializeGame() {
        InitializeMyFleet();

        InitializeEnemyFleet();
    }

    private static void InitializeMyFleet() {
        Scanner scanner = new Scanner(System.in);
        myFleet = GameController.initializeShips();

        console.println("Please position your fleet (Game board has size from A to H and 1 to 8) :");

        for (Ship ship : myFleet) {
            console.println("");
            console.println(String.format("Please enter the positions for the %s (size: %s)", ship.getName(), ship.getSize()));
            for (int i = 1; i <= ship.getSize(); i++) {
                console.println(String.format("Enter position %s of %s (i.e A3):", i, ship.getSize()));

                String positionInput = scanner.next();
                if(!GameController.isShipPositionValid(myFleet,ship,positionInput)) {
                    console.println(String.format("Entered position is incorrect"));
                    //console.println(String.format("If you want to reset ship configuration press R if you want to continue press C"));

                    i--;
                }
                else {
                    ship.addPosition(positionInput);
                }

            }
        }
    }

    static void InitializeEnemyFleet() {
        enemyFleet = GameController.initializeShips();
        generateRandomPositionLists();
        List<Position> positions = getRandomPositionList();

        enemyFleet.get(0).getPositions().add(positions.get(0));
        enemyFleet.get(0).getPositions().add(positions.get(1));
        enemyFleet.get(0).getPositions().add(positions.get(2));
        enemyFleet.get(0).getPositions().add(positions.get(3));
        enemyFleet.get(0).getPositions().add(positions.get(4));

        enemyFleet.get(1).getPositions().add(positions.get(5));
        enemyFleet.get(1).getPositions().add(positions.get(6));
        enemyFleet.get(1).getPositions().add(positions.get(7));
        enemyFleet.get(1).getPositions().add(positions.get(8));

        enemyFleet.get(2).getPositions().add(positions.get(9));
        enemyFleet.get(2).getPositions().add(positions.get(10));
        enemyFleet.get(2).getPositions().add(positions.get(11));

        enemyFleet.get(3).getPositions().add(positions.get(12));
        enemyFleet.get(3).getPositions().add(positions.get(13));
        enemyFleet.get(3).getPositions().add(positions.get(14));

        enemyFleet.get(4).getPositions().add(positions.get(15));
        enemyFleet.get(4).getPositions().add(positions.get(16));
    }

    private static List<Position> getRandomPositionList() {
        Random random = new Random();
        int i = random.nextInt(6);
        switch (i) {
            case 2:
                return positions2;
            case 3:
                return positions3;
            case 4:
                return positions4;
            case 5:
                return positions5;
            default:
                return positions1;
        }
    }

    private static void generateRandomPositionLists() {
        positions1.add(new Position(Letter.B, 1));
        positions1.add(new Position(Letter.B, 2));
        positions1.add(new Position(Letter.B, 3));
        positions1.add(new Position(Letter.B, 4));
        positions1.add(new Position(Letter.B, 5));
        positions1.add(new Position(Letter.E, 1));
        positions1.add(new Position(Letter.E, 2));
        positions1.add(new Position(Letter.E, 3));
        positions1.add(new Position(Letter.E, 4));
        positions1.add(new Position(Letter.A, 6));
        positions1.add(new Position(Letter.B, 6));
        positions1.add(new Position(Letter.C, 6));
        positions1.add(new Position(Letter.F, 1));
        positions1.add(new Position(Letter.G, 1));
        positions1.add(new Position(Letter.H, 1));

        positions1.add(new Position(Letter.C, 7));
        positions1.add(new Position(Letter.C, 8));

        positions2.add(new Position(Letter.B, 4));
        positions2.add(new Position(Letter.B, 5));
        positions2.add(new Position(Letter.B, 6));
        positions2.add(new Position(Letter.B, 7));
        positions2.add(new Position(Letter.B, 8));
        positions2.add(new Position(Letter.E, 6));
        positions2.add(new Position(Letter.E, 7));
        positions2.add(new Position(Letter.E, 8));
        positions2.add(new Position(Letter.E, 9));
        positions2.add(new Position(Letter.A, 3));
        positions2.add(new Position(Letter.B, 3));
        positions2.add(new Position(Letter.C, 3));
        positions2.add(new Position(Letter.F, 8));
        positions2.add(new Position(Letter.G, 8));
        positions2.add(new Position(Letter.H, 8));
        positions2.add(new Position(Letter.C, 5));
        positions2.add(new Position(Letter.C, 6));

        positions3.add(new Position(Letter.B, 4));
        positions3.add(new Position(Letter.B, 5));
        positions3.add(new Position(Letter.B, 6));
        positions3.add(new Position(Letter.B, 7));
        positions3.add(new Position(Letter.B, 8));
        positions3.add(new Position(Letter.E, 6));
        positions3.add(new Position(Letter.E, 7));
        positions3.add(new Position(Letter.E, 8));
        positions3.add(new Position(Letter.E, 9));
        positions3.add(new Position(Letter.A, 3));
        positions3.add(new Position(Letter.B, 3));
        positions3.add(new Position(Letter.C, 3));
        positions3.add(new Position(Letter.F, 8));
        positions3.add(new Position(Letter.G, 8));
        positions3.add(new Position(Letter.H, 8));
        positions3.add(new Position(Letter.C, 5));
        positions3.add(new Position(Letter.C, 6));

        positions4.add(new Position(Letter.B, 4));
        positions4.add(new Position(Letter.B, 5));
        positions4.add(new Position(Letter.B, 6));
        positions4.add(new Position(Letter.B, 7));
        positions4.add(new Position(Letter.B, 8));
        positions4.add(new Position(Letter.E, 6));
        positions4.add(new Position(Letter.E, 7));
        positions4.add(new Position(Letter.E, 8));
        positions4.add(new Position(Letter.E, 9));
        positions4.add(new Position(Letter.A, 3));
        positions4.add(new Position(Letter.B, 3));
        positions4.add(new Position(Letter.C, 3));
        positions4.add(new Position(Letter.F, 8));
        positions4.add(new Position(Letter.G, 8));
        positions4.add(new Position(Letter.H, 8));
        positions4.add(new Position(Letter.C, 5));
        positions4.add(new Position(Letter.C, 6));

        positions5.add(new Position(Letter.B, 4));
        positions5.add(new Position(Letter.B, 5));
        positions5.add(new Position(Letter.B, 6));
        positions5.add(new Position(Letter.B, 7));
        positions5.add(new Position(Letter.B, 8));
        positions5.add(new Position(Letter.E, 6));
        positions5.add(new Position(Letter.E, 7));
        positions5.add(new Position(Letter.E, 8));
        positions5.add(new Position(Letter.E, 9));
        positions5.add(new Position(Letter.A, 3));
        positions5.add(new Position(Letter.B, 3));
        positions5.add(new Position(Letter.C, 3));
        positions5.add(new Position(Letter.F, 8));
        positions5.add(new Position(Letter.G, 8));
        positions5.add(new Position(Letter.H, 8));
        positions5.add(new Position(Letter.C, 5));
        positions5.add(new Position(Letter.C, 6));
    }

}
