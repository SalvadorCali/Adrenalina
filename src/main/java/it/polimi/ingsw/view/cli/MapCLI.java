package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Square;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;

/**
 * This class prints the game board for the CLI.
 */
public class MapCLI {
    private static final String RESET = "\033[0m";
    private static final String SPACE = " ";
    private static final String NEW_LINE = "\n";

    //colors
    private static final String BLACK = "\033[0;30m";
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String BLUE = "\033[0;34m";
    private static final String PURPLE = "\033[0;35m";
    private static final String CYAN = "\033[0;36m";
    private static final String GREY = "\033[0;37m";

    //letters
    private static final String PLAYER = "X";
    private static final String SPAWN_POINT = "*";
    private static final String AMMO_POINT = "A";
    private static final String VERTICAL = "|";
    private static final String HORIZONTAL = "-";

    //size
    private static final int ROWS = 3;
    private static final int COLUMNS = 4;
    private static final int NUM_SQUARES=12;
    private static final int MAX_NUM_PLAYER=5;

    /**
     * The matrix of square of the board.
     */
    private Square[][] arena = new Square[ROWS][COLUMNS];
    /**
     * The board that will be printed.
     */
    private GameBoard gameBoard;
    /**
     * An array that contains the northern walls.
     */
    private String []wallNorth = new String[NUM_SQUARES];
    /**
     * An array that contains the southern walls.
     */
    private String []wallSouth = new String[NUM_SQUARES];
    /**
     * An array that contains the eastern walls.
     */
    private String []wallEast = new String[NUM_SQUARES];
    /**
     * An array that contains the western walls.
     */
    private String []wallWest = new String[NUM_SQUARES];
    /**
     * An array that contains * or spaces.
     */
    private String[] spaceSpawnPoint = new String[NUM_SQUARES];
    /**
     * An array that contains A or spaces.
     */
    private String[] spaceAmmoPoint = new String[NUM_SQUARES];
    /**
     * An array that contains the color of each square.
     */
    private String [][]colorSquare = new String[ROWS][COLUMNS];
    /**
     * An array of players for the first square.
     */
    private String[] spacePlayer1 = new String[MAX_NUM_PLAYER];
    /**
     * An array of players for the second square.
     */
    private String[] spacePlayer2 = new String[MAX_NUM_PLAYER];
    /**
     * An array of players for the third square.
     */
    private String[] spacePlayer3 = new String[MAX_NUM_PLAYER];
    /**
     * An array of players for the fourth square.
     */
    private String[] spacePlayer4 = new String[MAX_NUM_PLAYER];
    /**
     * An array of players for the fifth square.
     */
    private String[] spacePlayer5 = new String[MAX_NUM_PLAYER];
    /**
     * An array of players for the sixth square.
     */
    private String[] spacePlayer6 = new String[MAX_NUM_PLAYER];
    /**
     * An array of players for the seventh square.
     */
    private String[] spacePlayer7 = new String[MAX_NUM_PLAYER];
    /**
     * An array of players for the eighth square.
     */
    private String[] spacePlayer8 = new String[MAX_NUM_PLAYER];
    /**
     * An array of players for the ninth square.
     */
    private String[] spacePlayer9 = new String[MAX_NUM_PLAYER];
    /**
     * An array of players for the tenth square.
     */
    private String[] spacePlayer10 = new String[MAX_NUM_PLAYER];
    /**
     * An array of players for the eleventh square.
     */
    private String[] spacePlayer11 = new String[MAX_NUM_PLAYER];
    /**
     * An array of players for the twelfth square.
     */
    private String[] spacePlayer12 = new String[MAX_NUM_PLAYER];

    /**
     * Class constructor.
     * @param gameBoard the game board.
     */
    public MapCLI(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    /**
     * Getter for the game board.
     * @return the game board.
     */
    public GameBoard getGameBoard(){
        return gameBoard;
    }

    /**
     * Setter for the game board.
     * @param gameBoard the game board that will be set.
     */
    void setGameBoard(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    /**
     * Main method of this class. Prints each square with its color and an "A" or an "*" inside it. "A" represents an ammo point and "*" represents a spawn point.
     * Players are represented by an "X" of their color.
     */
    public void printMap(){
        //initialize players on squares
        spacePlayerSetup();
        //initialize arena, walls and colorSquare
        arena = gameBoard.getArena();
        for(int i = 0; i < NUM_SQUARES; i++){
            wallNorth[i] = HORIZONTAL;
            //String wallStandardSouth = "¯";
            //String wallStandardNorth= "_";
            wallSouth[i] = HORIZONTAL;
            String wallVertical = VERTICAL;
            wallEast[i] = wallVertical;
            wallWest[i] = wallVertical;
        }
        //give color to squares
        colorSquareSetup();
        //create doors, spawn and ammoPoints
        boardSetup();
        //put players on the CLI
        setPlayers();
        print();
    }

    /**
     * Initializes players on squares.
     */
    private void spacePlayerSetup(){
        for(int i = 0; i < MAX_NUM_PLAYER; i++){
            spacePlayer1[i] = SPACE;
            spacePlayer2[i] = SPACE;
            spacePlayer3[i] = SPACE;
            spacePlayer4[i] = SPACE;
            spacePlayer5[i] = SPACE;
            spacePlayer6[i] = SPACE;
            spacePlayer7[i] = SPACE;
            spacePlayer8[i] = SPACE;
            spacePlayer9[i] = SPACE;
            spacePlayer10[i] = SPACE;
            spacePlayer11[i] = SPACE;
            spacePlayer12[i] = SPACE;
        }
    }

    /**
     * Gives the right color to the squares.
     */
    private void colorSquareSetup(){
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLUMNS; j++){
                if(arena[i][j].getColor().equals(TokenColor.NONE)){
                    colorSquare[i][j] = BLACK;
                }
                if(arena[i][j].getColor().equals(TokenColor.BLUE)){
                    colorSquare[i][j] = BLUE;
                }
                if(arena[i][j].getColor().equals(TokenColor.RED)){
                    colorSquare[i][j] = RED;
                }
                if(arena[i][j].getColor().equals(TokenColor.GREY)){
                    colorSquare[i][j] = GREY;
                }
                if(arena[i][j].getColor().equals(TokenColor.GREEN)){
                    colorSquare[i][j] = GREEN;
                }
                if(arena[i][j].getColor().equals(TokenColor.PURPLE)){
                    colorSquare[i][j] = PURPLE;
                }
                if(arena[i][j].getColor().equals(TokenColor.YELLOW)){
                    colorSquare[i][j] = YELLOW;
                }
                if(arena[i][j].getColor().equals(TokenColor.SKULL)){
                    colorSquare[i][j] = CYAN;
                }
            }
        }
    }

    /**
     * Creates doors, spawn points and ammo points.
     */
    private void boardSetup(){
        int k = 0;
        for(int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLUMNS; j++){
                //walls and door
                String door = SPACE;
                if(arena[i][j].getNorth().equals(Cardinal.DOOR)){
                    wallNorth[k] = door;
                }
                if(arena[i][j].getEast().equals(Cardinal.DOOR)){
                    wallEast[k] = door;
                }
                if(arena[i][j].getWest().equals(Cardinal.DOOR)){
                    wallWest[k] = door;
                }
                if(arena[i][j].getSouth().equals(Cardinal.DOOR)){
                    wallSouth[k] = door;
                }
                if(arena[i][j].getEast().equals(Cardinal.ROOM))
                    wallEast[k] = door;
                if(arena[i][j].getWest().equals(Cardinal.ROOM))
                    wallWest[k] = door;
                if(arena[i][j].getSouth().equals(Cardinal.ROOM))
                    wallSouth[k] = door;
                if(arena[i][j].getNorth().equals(Cardinal.ROOM))
                    wallNorth[k] = door;
                if(arena[i][j].isSpawn()){
                    spaceSpawnPoint[k] = SPAWN_POINT;
                    spaceAmmoPoint[k] = SPACE;
                }
                if(!arena[i][j].isSpawn()) {
                    spaceSpawnPoint[k] = SPACE;
                    spaceAmmoPoint[k] = AMMO_POINT;
                }
                if(colorSquare[i][j].equals(BLACK)){
                    spaceAmmoPoint[k] = SPACE;
                }
                k++;
            }
        }
    }

    /**
     * Puts the players on the squares.
     */
    private void setPlayers(){
        String color = BLACK;
        String player;
        if(!arena[0][0].getPlayers().isEmpty()){
            for(int index = 0; index < arena[0][0].getPlayers().size(); index ++){
                color = Converter.fromTokenColorToCLIColor(arena[0][0].getPlayers().get(index).getColor());
                player = color + PLAYER;
                spacePlayer1[index] = player;
            }
        }
        if(!arena[0][1].getPlayers().isEmpty()){
            for(int index = 0; index < arena[0][1].getPlayers().size(); index ++){
                color = Converter.fromTokenColorToCLIColor(arena[0][1].getPlayers().get(index).getColor());
                player = color + PLAYER;
                spacePlayer2[index] = player;
            }
        }
        if(!arena[0][2].getPlayers().isEmpty()){
            for(int index = 0; index < arena[0][2].getPlayers().size(); index ++){
                color = Converter.fromTokenColorToCLIColor(arena[0][2].getPlayers().get(index).getColor());
                player = color + PLAYER;
                spacePlayer3[index] = player;
            }
        }
        if(!arena[0][3].getPlayers().isEmpty()){
            for(int index = 0; index < arena[0][3].getPlayers().size(); index ++){
                color = Converter.fromTokenColorToCLIColor(arena[0][3].getPlayers().get(index).getColor());
                player = color + PLAYER;
                spacePlayer4[index] = player;
            }
        }
        if(!arena[1][0].getPlayers().isEmpty()){
            for(int index = 0; index < arena[1][0].getPlayers().size(); index ++){
                color = Converter.fromTokenColorToCLIColor(arena[1][0].getPlayers().get(index).getColor());
                player = color + PLAYER;
                spacePlayer5[index] = player;
            }
        }
        if(!arena[1][1].getPlayers().isEmpty()){
            for(int index = 0; index < arena[1][1].getPlayers().size(); index ++){
                color = Converter.fromTokenColorToCLIColor(arena[1][1].getPlayers().get(index).getColor());
                player = color + PLAYER;
                spacePlayer6[index] = player;
            }
        }
        if(!arena[1][2].getPlayers().isEmpty()){
            for(int index = 0; index < arena[1][2].getPlayers().size(); index ++){
                color = Converter.fromTokenColorToCLIColor(arena[1][2].getPlayers().get(index).getColor());
                player = color + PLAYER;
                spacePlayer7[index] = player;
            }
        }
        if(!arena[1][3].getPlayers().isEmpty()){
            for(int index = 0; index < arena[1][3].getPlayers().size(); index ++){
                color = Converter.fromTokenColorToCLIColor(arena[1][3].getPlayers().get(index).getColor());
                player = color + PLAYER;
                spacePlayer8[index] = player;
            }
        }
        if(!arena[2][0].getPlayers().isEmpty()){
            for(int index = 0; index < arena[2][0].getPlayers().size(); index ++){
                color = Converter.fromTokenColorToCLIColor(arena[2][0].getPlayers().get(index).getColor());
                player = color + PLAYER;
                spacePlayer9[index] = player;
            }
        }
        if(!arena[2][1].getPlayers().isEmpty()){
            for(int index = 0; index < arena[2][1].getPlayers().size(); index ++){
                color = Converter.fromTokenColorToCLIColor(arena[2][1].getPlayers().get(index).getColor());
                player = color + PLAYER;
                spacePlayer10[index] = player;
            }
        }
        if(!arena[2][2].getPlayers().isEmpty()){
            for(int index = 0; index < arena[2][2].getPlayers().size(); index ++){
                color = Converter.fromTokenColorToCLIColor(arena[2][2].getPlayers().get(index).getColor());
                player = color + PLAYER;
                spacePlayer11[index] = player;
            }
        }
        if(!arena[2][3].getPlayers().isEmpty()){
            for(int index = 0; index < arena[2][3].getPlayers().size(); index ++){
                color = Converter.fromTokenColorToCLIColor(arena[2][3].getPlayers().get(index).getColor());
                player = color + PLAYER;
                spacePlayer12[index] = player;
            }
        }
    }

    /**
     * Prints the game board.
     */
    private void print(){
        /*
        //first row
        Printer.print(colorSquare[0][0] + " ___" + wallNorth[0] + "___" + SPACE + colorSquare[0][1] + "  ___" + wallNorth[1] + "___" + SPACE + colorSquare[0][2] + "  ___" + wallNorth[2] + "___"   + SPACE + colorSquare[0][3] + "  ___" + wallNorth[3] + "___\n"  + RESET);
        Printer.print(colorSquare[0][0] + "|" + spacePlayer1[0] + SPACE + spacePlayer1[1] + SPACE + spacePlayer1[2] + SPACE + spacePlayer1[3] + colorSquare[0][0] + "|" + SPACE + colorSquare[0][1] + "|" + spacePlayer2[0] + SPACE + spacePlayer2[1] + SPACE + spacePlayer2[2] + SPACE + spacePlayer2[3] + colorSquare[0][1] + "|"+ SPACE + colorSquare[0][2] + "|" + spacePlayer3[0] + SPACE + spacePlayer3[1] + SPACE + spacePlayer3[2] + SPACE + spacePlayer3[3] + colorSquare[0][2] + "|"+ SPACE  + colorSquare[0][3] + "|" + spacePlayer4[0] + SPACE + spacePlayer4[1] + SPACE + spacePlayer4[2] + SPACE + spacePlayer4[3] + colorSquare[0][3] + "|\n" + RESET);
        Printer.print(colorSquare[0][0] + wallWest[0] + spacePlayer1[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[0][0] + wallEast[0] + SPACE + colorSquare[0][1] + wallWest[1] + spacePlayer2[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[0][1] + wallEast[1] + SPACE + colorSquare[0][2] + wallWest[2] + spacePlayer3[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[0][2] + wallEast[2] + SPACE  + colorSquare[0][3] + wallWest[3] + spacePlayer4[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[0][3] + wallEast[3] + "\n" + RESET);
        Printer.print(colorSquare[0][0] + "|" + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[0] + SPACE + spaceSpawnPoint[0] + colorSquare[0][0] + "|" + SPACE + colorSquare[0][1] + "|" + CYAN +spaces + SPACE + spaces + SPACE + spaceAmmoPoint[1] + SPACE + spaceSpawnPoint[1] + colorSquare[0][1] + "|" + SPACE + colorSquare[0][2] + "|" + CYAN +spaces + SPACE + spaces + SPACE + spaceAmmoPoint[2] + SPACE + spaceSpawnPoint[2] + colorSquare[0][2] + "|"+ SPACE  + colorSquare[0][3] + "|" + CYAN +spaces + SPACE + spaces + SPACE + spaceAmmoPoint[3] + SPACE + spaceSpawnPoint[3] + colorSquare[0][3] + "|\n" + RESET);
        Printer.print(colorSquare[0][0] + " ¯¯" + wallSouth[0] + wallSouth[0] + wallSouth[0] + "¯¯" + SPACE + colorSquare[0][1] + "  ¯¯" + wallSouth[1] + wallSouth[1] + wallSouth[1] + "¯¯" + SPACE + colorSquare[0][2] + "  ¯¯" + wallSouth[2] + wallSouth[2] + wallSouth[2] + "¯¯" + SPACE + colorSquare[0][3] + "  ¯¯" + wallSouth[3] + wallSouth[3]  + wallSouth[3] + "¯¯\n" + RESET);

        //second row
        Printer.print(colorSquare[1][0] + " __" + wallNorth[4] + wallNorth[4]  + wallNorth[4] + "__" + SPACE + colorSquare[1][1] + "  __" + wallNorth[5] + wallNorth[5] + wallNorth[5] + "__" + SPACE + colorSquare[1][2] + "  __" + wallNorth[6] + wallNorth[6]  + wallNorth[6] + "__"   + SPACE + colorSquare[1][3] + "  __" + wallNorth[7] + wallNorth[7] + wallNorth[7] + "__\n"  + RESET);
        Printer.print(colorSquare[1][0] + "|" + spacePlayer5[0] + SPACE + spacePlayer5[1] + SPACE + spacePlayer5[2] + SPACE + spacePlayer5[3] + colorSquare[1][0] + "|" + SPACE + colorSquare[1][1] + "|" + spacePlayer6[0] + SPACE + spacePlayer6[1] + SPACE + spacePlayer6[2] + SPACE + spacePlayer6[3] + colorSquare[1][1] +"|"+ SPACE + colorSquare[1][2] + "|" + spacePlayer7[0] + SPACE + spacePlayer7[1] + SPACE + spacePlayer7[2] + SPACE + spacePlayer7[3] + colorSquare[1][2] +"|"+ SPACE  + colorSquare[1][3] + "|" + spacePlayer8[0] + SPACE + spacePlayer8[1] + SPACE + spacePlayer8[2] + SPACE + spacePlayer8[3] + colorSquare[1][3] + "|\n" + RESET);
        Printer.print(colorSquare[1][0] + wallWest[4] + spacePlayer5[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[1][0] + wallEast[4] + SPACE + colorSquare[1][1] + wallWest[5] + spacePlayer6[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[1][1] + wallEast[5] + SPACE + colorSquare[1][2] + wallWest[6] + spacePlayer7[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[1][2] + wallEast[6] + SPACE  + colorSquare[1][3] + wallWest[7] + spacePlayer8[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[1][3] +  wallEast[7] + "\n" + RESET);
        Printer.print(colorSquare[1][0] + "|" + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[4] + SPACE + spaceSpawnPoint[4] + colorSquare[1][0] + "|" + SPACE + colorSquare[1][1] + "|" + CYAN +spaces + SPACE + spaces + SPACE + spaceAmmoPoint[5] + SPACE + spaceSpawnPoint[5] + colorSquare[1][1] + "|" + SPACE + colorSquare[1][2] + "|" + CYAN +spaces + SPACE + spaces + SPACE + spaceAmmoPoint[6] + SPACE + spaceSpawnPoint[6] + colorSquare[1][2] + "|"+ SPACE  + colorSquare[1][3] + "|" + CYAN +spaces + SPACE + spaces + SPACE + spaceAmmoPoint[7] + SPACE + spaceSpawnPoint[7] + colorSquare[1][3] + "|\n" + RESET);
        Printer.print(colorSquare[1][0] + " ¯¯" + wallSouth[4] + wallSouth[4]  + wallSouth[4] + "¯¯" + SPACE + colorSquare[1][1] + "  ¯¯" + wallSouth[5] + wallSouth[5] + wallSouth[5] + "¯¯" + SPACE + colorSquare[1][2] + "  ¯¯" + wallSouth[6] + wallSouth[6] + wallSouth[6] + "¯¯" + SPACE + colorSquare[1][3] + "  ¯¯" + wallSouth[7] + wallSouth[7] + wallSouth[7] + "¯¯\n" + RESET);

        //third row
        Printer.print(colorSquare[2][0] + " __" + wallNorth[8] + wallNorth[8]  + wallNorth[8] + "__" + SPACE + colorSquare[2][1] + "  __" + wallNorth[9] + wallNorth[9] + wallNorth[9] + "__" + SPACE + colorSquare[2][2] + "  __" + wallNorth[10] + wallNorth[10] + wallNorth[10] + "__"   + SPACE + colorSquare[2][3] + "  __" + wallNorth[11] + wallNorth[11]  + wallNorth[11] + "__\n"  + RESET);
        Printer.print(colorSquare[2][0] + "|" + spacePlayer9[0] + SPACE + spacePlayer9[1] + SPACE + spacePlayer9[2] + SPACE + spacePlayer9[3] + colorSquare[2][0] + "|" + SPACE + colorSquare[2][1] + "|" + spacePlayer10[0] + SPACE + spacePlayer10[1] + SPACE + spacePlayer10[2] + SPACE + spacePlayer10[3] + colorSquare[2][1] + "|"+ SPACE + colorSquare[2][2] + "|" + spacePlayer11[0] + SPACE + spacePlayer11[1] + SPACE + spacePlayer11[2] + SPACE + spacePlayer11[3] + colorSquare[2][2] + "|"+ SPACE  + colorSquare[2][3] + "|" + spacePlayer12[0] + SPACE + spacePlayer12[1] + SPACE + spacePlayer12[2] + SPACE + spacePlayer12[3] + colorSquare[2][3] + "|\n" + RESET);
        Printer.print(colorSquare[2][0] + wallWest[8] + spacePlayer9[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[2][0] + wallEast[8] + SPACE + colorSquare[2][1] + wallWest[9] + spacePlayer10[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[2][1] + wallEast[9] + SPACE + colorSquare[2][2] + wallWest[10] + spacePlayer11[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[2][2] + wallEast[10] + SPACE  + colorSquare[2][3] + wallWest[11] + spacePlayer12[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[2][3] + wallEast[11] + "\n" + RESET);
        Printer.print(colorSquare[2][0] + "|" + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[8] + SPACE + spaceSpawnPoint[8] + colorSquare[2][0] + "|" + SPACE + colorSquare[2][1] + "|" + CYAN +spaces + SPACE + spaces + SPACE + spaceAmmoPoint[9] + SPACE + spaceSpawnPoint[9] + colorSquare[2][1] + "|" + SPACE + colorSquare[2][2] + "|" + CYAN +spaces + SPACE + spaces + SPACE + spaceAmmoPoint[10] + SPACE + spaceSpawnPoint[10] + colorSquare[2][2] + "|"+ SPACE  + colorSquare[2][3] + "|" + CYAN +spaces + SPACE + spaces + SPACE + spaceAmmoPoint[11] + SPACE + spaceSpawnPoint[11] + colorSquare[2][3] + "|\n" + RESET);
        Printer.print(colorSquare[2][0] + " ¯¯" + wallSouth[8] + wallSouth[8]  + wallSouth[8] + "¯¯" + SPACE + colorSquare[2][1] + "  ¯¯" + wallSouth[9] + wallSouth[9]  + wallSouth[9] + "¯¯" + SPACE + colorSquare[2][2] + "  ¯¯" + wallSouth[10] + wallSouth[10] + wallSouth[10] + "¯¯" + SPACE + colorSquare[2][3] + "  ¯¯" + wallSouth[11] + wallSouth[11]  + wallSouth[11] + "¯¯\n" + RESET);
         */
        //first row
        String spaces = SPACE;
        Printer.print(colorSquare[0][0] + SPACE + HORIZONTAL + HORIZONTAL + HORIZONTAL + wallNorth[0] + HORIZONTAL + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[0][1] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + HORIZONTAL + wallNorth[1] + HORIZONTAL + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[0][2] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + HORIZONTAL + wallNorth[2] + HORIZONTAL + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[0][3] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + HORIZONTAL + wallNorth[3] + HORIZONTAL + HORIZONTAL + HORIZONTAL + NEW_LINE + RESET);
        Printer.print(colorSquare[0][0] + VERTICAL + spacePlayer1[0] + SPACE + spacePlayer1[1] + SPACE + spacePlayer1[2] + SPACE + spacePlayer1[3] + colorSquare[0][0] + VERTICAL + SPACE + colorSquare[0][1] + VERTICAL + spacePlayer2[0] + SPACE + spacePlayer2[1] + SPACE + spacePlayer2[2] + SPACE + spacePlayer2[3] + colorSquare[0][1] + VERTICAL + SPACE + colorSquare[0][2] + VERTICAL + spacePlayer3[0] + SPACE + spacePlayer3[1] + SPACE + spacePlayer3[2] + SPACE + spacePlayer3[3] + colorSquare[0][2] + VERTICAL + SPACE  + colorSquare[0][3] + VERTICAL + spacePlayer4[0] + SPACE + spacePlayer4[1] + SPACE + spacePlayer4[2] + SPACE + spacePlayer4[3] + colorSquare[0][3] + VERTICAL + NEW_LINE + RESET);
        Printer.print(colorSquare[0][0] + wallWest[0] + spacePlayer1[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[0][0] + wallEast[0] + SPACE + colorSquare[0][1] + wallWest[1] + spacePlayer2[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[0][1] + wallEast[1] + SPACE + colorSquare[0][2] + wallWest[2] + spacePlayer3[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[0][2] + wallEast[2] + SPACE  + colorSquare[0][3] + wallWest[3] + spacePlayer4[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[0][3] + wallEast[3] + NEW_LINE + RESET);
        Printer.print(colorSquare[0][0] + VERTICAL + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[0] + SPACE + spaceSpawnPoint[0] + colorSquare[0][0] + VERTICAL + SPACE + colorSquare[0][1] + VERTICAL + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[1] + SPACE + spaceSpawnPoint[1] + colorSquare[0][1] + VERTICAL + SPACE + colorSquare[0][2] + VERTICAL + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[2] + SPACE + spaceSpawnPoint[2] + colorSquare[0][2] + VERTICAL + SPACE  + colorSquare[0][3] + VERTICAL + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[3] + SPACE + spaceSpawnPoint[3] + colorSquare[0][3] + VERTICAL + NEW_LINE + RESET);
        Printer.print(colorSquare[0][0] + SPACE + HORIZONTAL + HORIZONTAL + wallSouth[0] + wallSouth[0] + wallSouth[0] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[0][1] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallSouth[1] + wallSouth[1] + wallSouth[1] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[0][2] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallSouth[2] + wallSouth[2] + wallSouth[2] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[0][3] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallSouth[3] + wallSouth[3]  + wallSouth[3] + HORIZONTAL + HORIZONTAL + NEW_LINE + RESET);

        //second row
        Printer.print(colorSquare[1][0] + SPACE + HORIZONTAL + HORIZONTAL + wallNorth[4] + wallNorth[4]  + wallNorth[4] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[1][1] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallNorth[5] + wallNorth[5] + wallNorth[5] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[1][2] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallNorth[6] + wallNorth[6]  + wallNorth[6] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[1][3] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallNorth[7] + wallNorth[7] + wallNorth[7] + HORIZONTAL + HORIZONTAL + NEW_LINE + RESET);
        Printer.print(colorSquare[1][0] + VERTICAL + spacePlayer5[0] + SPACE + spacePlayer5[1] + SPACE + spacePlayer5[2] + SPACE + spacePlayer5[3] + colorSquare[1][0] + VERTICAL + SPACE + colorSquare[1][1] + VERTICAL + spacePlayer6[0] + SPACE + spacePlayer6[1] + SPACE + spacePlayer6[2] + SPACE + spacePlayer6[3] + colorSquare[1][1] + VERTICAL + SPACE + colorSquare[1][2] + VERTICAL + spacePlayer7[0] + SPACE + spacePlayer7[1] + SPACE + spacePlayer7[2] + SPACE + spacePlayer7[3] + colorSquare[1][2] + VERTICAL + SPACE  + colorSquare[1][3] + VERTICAL + spacePlayer8[0] + SPACE + spacePlayer8[1] + SPACE + spacePlayer8[2] + SPACE + spacePlayer8[3] + colorSquare[1][3] + VERTICAL + NEW_LINE + RESET);
        Printer.print(colorSquare[1][0] + wallWest[4] + spacePlayer5[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[1][0] + wallEast[4] + SPACE + colorSquare[1][1] + wallWest[5] + spacePlayer6[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[1][1] + wallEast[5] + SPACE + colorSquare[1][2] + wallWest[6] + spacePlayer7[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[1][2] + wallEast[6] + SPACE  + colorSquare[1][3] + wallWest[7] + spacePlayer8[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[1][3] +  wallEast[7] + NEW_LINE + RESET);
        Printer.print(colorSquare[1][0] + VERTICAL + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[4] + SPACE + spaceSpawnPoint[4] + colorSquare[1][0] + VERTICAL + SPACE + colorSquare[1][1] + VERTICAL + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[5] + SPACE + spaceSpawnPoint[5] + colorSquare[1][1] + VERTICAL + SPACE + colorSquare[1][2] + VERTICAL + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[6] + SPACE + spaceSpawnPoint[6] + colorSquare[1][2] + VERTICAL + SPACE  + colorSquare[1][3] + VERTICAL + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[7] + SPACE + spaceSpawnPoint[7] + colorSquare[1][3] + VERTICAL + NEW_LINE + RESET);
        Printer.print(colorSquare[1][0] + SPACE + HORIZONTAL + HORIZONTAL + wallSouth[4] + wallSouth[4]  + wallSouth[4] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[1][1] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallSouth[5] + wallSouth[5] + wallSouth[5] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[1][2] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallSouth[6] + wallSouth[6] + wallSouth[6] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[1][3] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallSouth[7] + wallSouth[7] + wallSouth[7] + HORIZONTAL + HORIZONTAL + NEW_LINE + RESET);

        //third row
        Printer.print(colorSquare[2][0] + SPACE + HORIZONTAL + HORIZONTAL + wallNorth[8] + wallNorth[8]  + wallNorth[8] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[2][1] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallNorth[9] + wallNorth[9] + wallNorth[9] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[2][2] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallNorth[10] + wallNorth[10] + wallNorth[10] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[2][3] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallNorth[11] + wallNorth[11]  + wallNorth[11] + HORIZONTAL + HORIZONTAL + NEW_LINE + RESET);
        Printer.print(colorSquare[2][0] + VERTICAL + spacePlayer9[0] + SPACE + spacePlayer9[1] + SPACE + spacePlayer9[2] + SPACE + spacePlayer9[3] + colorSquare[2][0] + VERTICAL + SPACE + colorSquare[2][1] + VERTICAL + spacePlayer10[0] + SPACE + spacePlayer10[1] + SPACE + spacePlayer10[2] + SPACE + spacePlayer10[3] + colorSquare[2][1] + VERTICAL + SPACE + colorSquare[2][2] + VERTICAL + spacePlayer11[0] + SPACE + spacePlayer11[1] + SPACE + spacePlayer11[2] + SPACE + spacePlayer11[3] + colorSquare[2][2] + VERTICAL + SPACE  + colorSquare[2][3] + VERTICAL + spacePlayer12[0] + SPACE + spacePlayer12[1] + SPACE + spacePlayer12[2] + SPACE + spacePlayer12[3] + colorSquare[2][3] + VERTICAL + NEW_LINE + RESET);
        Printer.print(colorSquare[2][0] + wallWest[8] + spacePlayer9[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[2][0] + wallEast[8] + SPACE + colorSquare[2][1] + wallWest[9] + spacePlayer10[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[2][1] + wallEast[9] + SPACE + colorSquare[2][2] + wallWest[10] + spacePlayer11[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[2][2] + wallEast[10] + SPACE  + colorSquare[2][3] + wallWest[11] + spacePlayer12[4] + SPACE + spaces + SPACE + spaces + SPACE + spaces + colorSquare[2][3] + wallEast[11] + NEW_LINE + RESET);
        Printer.print(colorSquare[2][0] + VERTICAL + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[8] + SPACE + spaceSpawnPoint[8] + colorSquare[2][0] + VERTICAL + SPACE + colorSquare[2][1] + VERTICAL + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[9] + SPACE + spaceSpawnPoint[9] + colorSquare[2][1] + VERTICAL + SPACE + colorSquare[2][2] + VERTICAL + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[10] + SPACE + spaceSpawnPoint[10] + colorSquare[2][2] + VERTICAL + SPACE  + colorSquare[2][3] + VERTICAL + CYAN + spaces + SPACE + spaces + SPACE + spaceAmmoPoint[11] + SPACE + spaceSpawnPoint[11] + colorSquare[2][3] + VERTICAL + NEW_LINE + RESET);
        Printer.print(colorSquare[2][0] + SPACE + HORIZONTAL + HORIZONTAL + wallSouth[8] + wallSouth[8]  + wallSouth[8] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[2][1] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallSouth[9] + wallSouth[9]  + wallSouth[9] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[2][2] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallSouth[10] + wallSouth[10] + wallSouth[10] + HORIZONTAL + HORIZONTAL + SPACE + colorSquare[2][3] + SPACE + SPACE + HORIZONTAL + HORIZONTAL + wallSouth[11] + wallSouth[11]  + wallSouth[11] + HORIZONTAL + HORIZONTAL + NEW_LINE + RESET);

    }
}
