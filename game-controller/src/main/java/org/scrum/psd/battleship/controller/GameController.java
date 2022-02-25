package org.scrum.psd.battleship.controller;

import org.scrum.psd.battleship.controller.dto.Color;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.*;

public class GameController {
    public static boolean checkIsHit(Collection<Ship> ships, Position shot) {
        if (ships == null) {
            throw new IllegalArgumentException("ships is null");
        }

        if (shot == null) {
            throw new IllegalArgumentException("shot is null");
        }

        for (Ship ship : ships) {
            for (Position position : ship.getPositions()) {
                if (position.equals(shot)) {
                    position.setDestroyed(true);
                    return true;
                }
            }
        }

        return false;
    }

    public static Optional<Ship> getShottedShip(Collection<Ship> ships, Position shot) {
        if (ships == null) {
            throw new IllegalArgumentException("ships is null");
        }

        if (shot == null) {
            throw new IllegalArgumentException("shot is null");
        }

        for (Ship ship : ships) {
            for (Position position : ship.getPositions()) {
                if (position.equals(shot)) {
                    position.setDestroyed(true);
                    return Optional.of(ship);
                }
            }
        }
        return Optional.empty();
    }

    public static List<Ship> getReadyToAttackShip(Collection<Ship> ships){
        List<Ship> readyToAttack = new ArrayList<>();

        for (Ship ship : ships) {
            if(!ship.isDestroyed()){
                readyToAttack.add(ship);
            }
        }
        return readyToAttack;
    }

    public static List<Ship> initializeShips() {
        return Arrays.asList(
                new Ship("Aircraft Carrier", 5, Color.CADET_BLUE),
                new Ship("Battleship", 4, Color.RED),
                new Ship("Submarine", 3, Color.CHARTREUSE),
                new Ship("Destroyer", 3, Color.YELLOW),
                new Ship("Patrol Boat", 2, Color.ORANGE));
    }

    public static boolean isShipValid(Ship ship) {
        return ship.getPositions().size() == ship.getSize();
    }

    public static boolean isInputValid(String newPositionInput){

        int newRow;

        try {
            Letter.valueOf(newPositionInput.toUpperCase().substring(0, 1));
            newRow = Integer.parseInt(newPositionInput.substring(1));
        }catch (IllegalArgumentException e){
            return false;
        }

        if(newRow>8){
            return false;
        }

        return true;

    }

    public static boolean isShipPositionValid(List<Ship> myFleet, Ship ship, String newPositionInput) {

        if(!isInputValid(newPositionInput)){
            return false;
        }

        Letter newLetter =  Letter.valueOf(newPositionInput.toUpperCase().substring(0, 1));
        int newRow= Integer.parseInt(newPositionInput.substring(1));

        for (Ship myFleetShip : myFleet) {
            if(myFleetShip.isUsedPosition(newPositionInput)){
                return false;
            }
        }

        if(ship.getPositions().size()==0){
            return true;
        }

        if(ship.getPositions().size()==1){

            int sum = Math.abs(ship.getPositions().get(0).getRow() - newRow)  + Math.abs(ship.getPositions().get(0).getColumn().ordinal() - newLetter.ordinal());

            if(sum==1){
                return true;
            }
            return false;
        }


        if(ship.getPositions().size()>1){
            if( ship.getPositions().get(0).getColumn().equals(ship.getPositions().get(1).getColumn()) && !ship.getPositions().get(0).getColumn().equals(newLetter)){
                return false;
            }
            if( ship.getPositions().get(0).getRow() == ship.getPositions().get(1).getRow() && !(ship.getPositions().get(0).getRow()==newRow)){
                return false;
            }
        }

        for(Position position : ship.getPositions()){
            if( Math.abs(position.getRow() - newRow) == 1) {
                return true;
            }
            if( Math.abs(position.getColumn().ordinal() - newLetter.ordinal()) == 1 ){
                return true;
            }
        }

        return false;
    }

    public static Position getRandomPosition(int size) {
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(size)];
        int number = random.nextInt(size);
        Position position = new Position(letter, number);
        return position;
    }

    public static boolean checkYouWin(List<Ship> fleet) {

        boolean tmp = true;
        for(Ship ship: fleet){
            tmp = tmp && ship.isDestroyed();
        }
        return tmp;
    }
}
