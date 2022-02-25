package org.scrum.psd.battleship.ascii;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

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

        //console.setForegroundColor(Ansi.FColor.MAGENTA);
        console.println("                                     |__                                           ");
        console.println("                                     |\\/                                           ");
        console.println("                                     ---                                           ");
        console.println("                                     / | [                                         ");
        console.println("                              !      | |||                                         ");
        console.println("                            _/|     _/|-++'                                        ");
        console.println("                        +  +--|    |--|--|_ |-                                     ");
        console.println("                     { /|__|  |/\\__|  |--- |||__/                                  ");
        console.println("                    +---------------___[}-_===_.'____                 /\\           ");
        console.println("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _       ");
        console.println(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7     ");
        console.println("|                        Welcome to Battleship                         BB-61/      ");
        console.println(" \\_________________________________________________________________________|       ");
        console.println("");
      //  console.setForegroundColor(Ansi.FColor.WHITE);


        InitializeGame();

        StartGame();
    }

    private static void StartGame() {
        Scanner scanner = new Scanner(System.in);
        boolean endGame=false;

        console.println("                  __                           ");
        console.println("                 /  \\                         ");
        console.println("           .-.  |    |                         ");
        console.println("   *    _.-'  \\  \\__/                        ");
        console.println("    \\.-'       \\                             ");
        console.println("   /          _/                               ");
        console.println("  |      _  /\" \"                             ");
        console.println("  |     /_\'                                   ");
        console.println("   \\    \\_/                                  ");
        console.println("    \" \"\" \"\" \"\" \"                       ");

        do {

            if(!endGame) {
                //console.setBackgroundColor(Ansi.BColor.WHITE);
                console.setForegroundColor(Ansi.FColor.GREEN);

                console.println("--------------------------------------------------------------");
                console.println("Player, it's your turn");
                console.println("Enter coordinates for your shot :");

                String input = scanner.next();
                if (!GameController.isInputValid(input)) {
                    console.println("Wrong coordinates, again!");
                    continue;
                }

                Position position = parsePosition(input);
//            boolean isHit = GameController.checkIsHit(enemyFleet, position);

                Optional<Ship> shottedShip = GameController.getShottedShip(enemyFleet, position);
                if (shottedShip.isPresent()) {
                    beep();
                    console.setForegroundColor(Ansi.FColor.RED);
                    console.println("Nice hit !                                     ");
                    console.println("                \\         .  ./               ");
                    console.println("              \\      .:\" \";'.:..\" \"   /   ");
                    console.println("                  (M^^.^~~:.'\" \").           ");
                    console.println("            -   (/  .    . . \\ \\)  -         ");
                    console.println("               ((| :. ~ ^  :. .|))             ");
                    console.println("            -   (\\- |  \\ /  |  /)  -         ");
                    console.println("                 -\\  \\     /  /-             ");
                    console.println("                   \\  \\   /  /               ");


                    if (shottedShip.get().isDestroyed()) {
                        console.println(String.format("Enemy ship %s is destroyed", shottedShip.get().getName()));
                        console.println(String.format("%s", shottedShip.get().getPositions().toString()));

                        System.out.println();
                        System.out.println();
                        for (Ship ship : GameController.getReadyToAttackShip(enemyFleet)) {
                            console.println(String.format("Ship %s still wanna kill you", ship.getName()));
                        }
                        try {
                            Thread.currentThread().sleep(1000);
                        }catch (Exception e){

                        }
                    }


                    if (GameController.checkYouWin(enemyFleet)) {
                        endGame = true;
                        GameController.clearConsole();
                        int i = 0;
                        while (i < 10) {
                            try {
                                console.setForegroundColor(Ansi.FColor.RED);
                                effectWOW( 160, 60, console);
                                Thread.currentThread().sleep(1000);
                                GameController.clearConsole();
                                console.setForegroundColor(Ansi.FColor.YELLOW);
                                effectWOW( 160, 60, console);
                                Thread.currentThread().sleep(1000);
                                GameController.clearConsole();
                            } catch(InterruptedException e){}
                            i++;
                        }
                    }

                } else {
                    console.setForegroundColor(Ansi.FColor.BLUE);
                    console.println("YOU MISS");
                }

                if (!endGame) {
                    System.out.println();
                    System.out.println();


                    position = getRandomPosition();
                    shottedShip = GameController.getShottedShip(myFleet, position);

                    console.println("");
                    console.setForegroundColor(Ansi.FColor.YELLOW);
                    console.println("--------------------------------------------------------------");
                    console.println("Computer turn");

                    if (shottedShip.isPresent()) {
                        beep();
                        console.println(String.format("Computer shoot in %s%s", position.getColumn(), position.getRow()));
                        console.setForegroundColor(Ansi.FColor.RED);
                        console.println("Computer hits your ship !");
                        console.println("                \\         .  ./");
                        console.println("              \\      .:\" \";'.:..\" \"   /");
                        console.println("                  (M^^.^~~:.'\" \").");
                        console.println("            -   (/  .    . . \\ \\)  -");
                        console.println("               ((| :. ~ ^  :. .|))");
                        console.println("            -   (\\- |  \\ /  |  /)  -");
                        console.println("                 -\\  \\     /  /-");
                        console.println("                   \\  \\   /  /");

                        if (shottedShip.get().isDestroyed()) {
                            console.println(String.format("Player, your ship %s is destroyed", shottedShip.get().getName()));
                        }

                        if (GameController.checkYouWin(myFleet)) {
                            console.println("Computer destroys all of Your fleet.");
                            console.println("You lost!");
                            endGame = true;
                        }

                    } else {
                        console.println(String.format("Computer shoot in %s%s", position.getColumn(), position.getRow()));
                        console.setForegroundColor(Ansi.FColor.BLUE);
                        console.println("Computer Miss!!");
                        System.out.println();
                        System.out.println();

                    }
                }
            }

            if(endGame){
                GameController.clearConsole();
                console.setForegroundColor(Ansi.FColor.WHITE);
                console.println("press R - If you want to restart game!");
                console.println("press X - If you want to end game!");
                String signal = scanner.next();

                if("R".equals(signal.toUpperCase().substring(0,1))){
                    endGame=false;
                    InitializeGame();
                }
                else if("X".equals(signal.toUpperCase().substring(0,1))){
                    System.exit(0);
                }
                else {
                    System.out.print("\033[H\033[2J");
                    console.println("The Game is Over!!");
                }
            }

        } while (true);
    }

    private static void effectWOW(int width, int height, ColoredPrinter console) {

        BufferedImage bufferedImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.drawString("YOU ARE THE WINNER", 12, 24);

        for (int y = 0; y < height; y++) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int x = 0; x < width; x++) {
                stringBuilder.append(bufferedImage.getRGB(x, y) == -16777216 ? " " : "*");
            }

            if (stringBuilder.toString().trim().isEmpty()) {
                continue;
            }

            console.println(stringBuilder);
        }
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
        console.setForegroundColor(Ansi.FColor.WHITE);
        console.println("                                                                                   ");
        console.println("                                                                                   ");
        console.println("Please position your fleet (Game board has size from A to H and 1 to 8) :          ");

        for (Ship ship : myFleet) {
            console.println("");
            console.println("                                                                                   ");
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
        positions2.add(new Position(Letter.E, 5));
        positions2.add(new Position(Letter.E, 6));
        positions2.add(new Position(Letter.E, 7));
        positions2.add(new Position(Letter.E, 8));
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
        positions3.add(new Position(Letter.E, 5));
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
        positions4.add(new Position(Letter.E, 5));
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
        positions5.add(new Position(Letter.E, 5));
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
