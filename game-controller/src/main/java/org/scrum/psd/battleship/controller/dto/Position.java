package org.scrum.psd.battleship.controller.dto;

public class Position {
    private Letter column;
    private int row;
    private boolean isDestroyed;


    public Position() {
        super();
    }

    public Position(Letter column, int row) {
        this();

        this.column = column;
        this.row = row;
        this.isDestroyed=false;
    }

    public Letter getColumn() {
        return column;
    }

    public void setColumn(Letter column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override public boolean equals(Object o) {
        if(o instanceof Position) {
            Position position = (Position) o;

            if(position == null) {
                return false;
            }

            return position.column.equals(column) && position.row == row;
        }

        return false;
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}
