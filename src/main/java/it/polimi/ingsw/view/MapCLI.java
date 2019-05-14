package it.polimi.ingsw.view;


import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.enums.BoardType;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Square;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;

import java.util.List;

public class MapCLI {

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String GREY = "\033[0;37m";   // WHITE
    public static final String SPACE = " ";

    private static final int ROWS = 3;
    private static final int COLUMNS = 4;
    private static final int NUM_SQUARES=12;
    private static final int MAX_NUM_PLAYER=5;

    private Square[][] arena = new Square[ROWS][COLUMNS];
    private GameBoard gameBoard;
    public GameBoard getGameBoard(){
        return gameBoard;
    }


    public MapCLI(GameBoard gameBoard){

        this.gameBoard = gameBoard;
    }


    public void printMap(){

        String color = BLACK;
        String spawnPoint = "*";
        String ammo = "A";
        String player;
        List<Player> genericPlayer;

        //walls and door
        String door = SPACE;
        String wallVertical = "|";
        String wallStandardSouth = "¯";
        String wallStandardNorth= "_";
        String []wallNorth = new String[NUM_SQUARES];
        String []wallSouth = new String[NUM_SQUARES];
        String []wallEast = new String[NUM_SQUARES];
        String []wallWest = new String[NUM_SQUARES];
        String spaceSpawnPoint[] = new String[NUM_SQUARES];
        String space = SPACE;
        String[] spaceAmmoPoint = new String[20];

        //first square
        String spacePlayer1[] = new String[MAX_NUM_PLAYER];


        //second square
        String spacePlayer2[] = new String[MAX_NUM_PLAYER];


        //third square
        String spacePlayer3[] = new String[MAX_NUM_PLAYER];


        //fourth square
        String spacePlayer4[] = new String[MAX_NUM_PLAYER];


        //fifth square
        String spacePlayer5[] = new String[MAX_NUM_PLAYER];


        //sixth square
        String spacePlayer6[] = new String[MAX_NUM_PLAYER];


        //seventh square
        String spacePlayer7[] = new String[MAX_NUM_PLAYER];


        //eighth square
        String spacePlayer8[] = new String[MAX_NUM_PLAYER];


        //ninth square
        String spacePlayer9[] = new String[MAX_NUM_PLAYER];

        //tenth square
        String spacePlayer10[] = new String[MAX_NUM_PLAYER];


        //eleventh square
        String spacePlayer11[] = new String[MAX_NUM_PLAYER];

        //twelfth square
        String spacePlayer12[] = new String[MAX_NUM_PLAYER];

        //initialize players on squares
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

        //initialize arena, walls and colorSquare
        arena = gameBoard.getArena();
        String [][]colorSquare = new String[ROWS][COLUMNS];

        for(int i = 0; i < NUM_SQUARES; i++){

            wallNorth[i] = wallStandardNorth;
            wallSouth[i] = wallStandardSouth;
            wallEast[i] = wallVertical;
            wallWest[i] = wallVertical;
        }

        //give color to squares
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

       //create doors, spawn and ammoPoints
       int k = 0;
       for(int i = 0; i < ROWS; i++){
           for (int j = 0; j < COLUMNS; j++){

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

                   spaceSpawnPoint[k] = spawnPoint;
                   spaceAmmoPoint[k] = SPACE;

               }

               if(!arena[i][j].isSpawn()) {

                   spaceSpawnPoint[k] = SPACE;
                   spaceAmmoPoint[k] = ammo;
               }

               if(colorSquare[i][j] == BLACK){

                   spaceAmmoPoint[k] = SPACE;
               }

               k++;
           }
       }

        //put players on the CLI
        for(int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLUMNS; j++){

                genericPlayer = arena[i][j].getPlayers();

                if(!arena[0][0].getPlayers().isEmpty()){
                    for(int index = 0; index < genericPlayer.size(); index ++){

                        color = Converter.fromTokenColorToCLIColor(genericPlayer.get(index).getColor());
                        player = color + "X";

                        spacePlayer1[index] = player;
                    }
                }

                if(!arena[0][1].getPlayers().isEmpty()){
                    for(int index = 0; index < genericPlayer.size(); index ++){

                        color = Converter.fromTokenColorToCLIColor(genericPlayer.get(index).getColor());
                        player = color + "X";

                        spacePlayer2[index] = player;
                    }
                }

                if(!arena[0][2].getPlayers().isEmpty()){
                    for(int index = 0; index < genericPlayer.size(); index ++){

                        color = Converter.fromTokenColorToCLIColor(genericPlayer.get(index).getColor());
                        player = color + "X";

                        spacePlayer3[index] = player;
                    }
                }

                if(!arena[0][3].getPlayers().isEmpty()){
                    for(int index = 0; index < genericPlayer.size(); index ++){

                        color = Converter.fromTokenColorToCLIColor(genericPlayer.get(index).getColor());
                        player = color + "X";

                        spacePlayer4[index] = player;
                    }
                }

                if(!arena[1][0].getPlayers().isEmpty()){
                    for(int index = 0; index < genericPlayer.size(); index ++){

                        color = Converter.fromTokenColorToCLIColor(genericPlayer.get(index).getColor());
                        player = color + "X";

                        spacePlayer5[index] = player;
                    }
                }

                if(!arena[1][1].getPlayers().isEmpty()){
                    for(int index = 0; index < genericPlayer.size(); index ++){

                        color = Converter.fromTokenColorToCLIColor(genericPlayer.get(index).getColor());
                        player = color + "X";

                        spacePlayer6[index] = player;
                    }
                }

                if(!arena[1][2].getPlayers().isEmpty()){
                    for(int index = 0; index < genericPlayer.size(); index ++){

                        color = Converter.fromTokenColorToCLIColor(genericPlayer.get(index).getColor());
                        player = color + "X";

                        spacePlayer7[index] = player;
                    }
                }

                if(!arena[1][3].getPlayers().isEmpty()){
                    for(int index = 0; index < genericPlayer.size(); index ++){

                        color = Converter.fromTokenColorToCLIColor(genericPlayer.get(index).getColor());
                        player = color + "X";

                        spacePlayer8[index] = player;
                    }
                }

                if(!arena[2][0].getPlayers().isEmpty()){
                    for(int index = 0; index < genericPlayer.size(); index ++){

                        color = Converter.fromTokenColorToCLIColor(genericPlayer.get(index).getColor());
                        player = color + "X";

                        spacePlayer9[index] = player;
                    }
                }

                if(!arena[2][1].getPlayers().isEmpty()){
                    for(int index = 0; index < genericPlayer.size(); index ++){

                        color = Converter.fromTokenColorToCLIColor(genericPlayer.get(index).getColor());
                        player = color + "X";

                        spacePlayer10[index] = player;
                    }
                }


                if(!arena[2][2].getPlayers().isEmpty()){
                    for(int index = 0; index < genericPlayer.size(); index ++){

                        color = Converter.fromTokenColorToCLIColor(genericPlayer.get(index).getColor());
                        player = color + "X";

                        spacePlayer11[index] = player;
                    }
                }


                if(!arena[2][3].getPlayers().isEmpty()){
                    for(int index = 0; index < genericPlayer.size(); index ++){

                        color = Converter.fromTokenColorToCLIColor(genericPlayer.get(index).getColor());
                        player = color + "X";

                        spacePlayer12[index] = player;
                    }
                }
            }

        }


        //first row
        Printer.print(colorSquare[0][0] + " ___" + wallNorth[0] + "___" + SPACE + colorSquare[0][1] + "  ___" + wallNorth[1] + "___" + SPACE + colorSquare[0][2] + "  ___" + wallNorth[2] + "___"   + SPACE + colorSquare[0][3] + "  ___" + wallNorth[3] + "___\n"  + RESET);
        Printer.print(colorSquare[0][0] + "|" + spacePlayer1[0] + SPACE + spacePlayer1[1] + SPACE + spacePlayer1[2] + SPACE + spacePlayer1[3] + colorSquare[0][0] + "|" + SPACE + colorSquare[0][1] + "|" + spacePlayer2[0] + SPACE + spacePlayer2[1] + SPACE + spacePlayer2[2] + SPACE + spacePlayer2[3] + colorSquare[0][1] + "|"+ SPACE + colorSquare[0][2] + "|" + spacePlayer3[0] + SPACE + spacePlayer3[1] + SPACE + spacePlayer3[2] + SPACE + spacePlayer3[3] + colorSquare[0][2] + "|"+ SPACE  + colorSquare[0][3] + "|" + spacePlayer4[0] + SPACE + spacePlayer4[1] + SPACE + spacePlayer4[2] + SPACE + spacePlayer4[3] + colorSquare[0][3] + "|\n" + RESET);
        Printer.print(colorSquare[0][0] + wallWest[0] + spacePlayer1[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[0][0] + wallEast[0] + SPACE + colorSquare[0][1] + wallWest[1] + spacePlayer2[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[0][1] + wallEast[1] + SPACE + colorSquare[0][2] + wallWest[2] + spacePlayer3[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[0][2] + wallEast[2] + SPACE  + colorSquare[0][3] + wallWest[3] + spacePlayer4[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[0][3] + wallEast[3] + "\n" + RESET);
        Printer.print(colorSquare[0][0] + "|" + CYAN + space + SPACE + space + SPACE + spaceAmmoPoint[0] + SPACE + spaceSpawnPoint[0] + colorSquare[0][0] + "|" + SPACE + colorSquare[0][1] + "|" + CYAN +space + SPACE + space + SPACE + spaceAmmoPoint[1] + SPACE + spaceSpawnPoint[1] + colorSquare[0][1] + "|" + SPACE + colorSquare[0][2] + "|" + CYAN +space + SPACE + space + SPACE + spaceAmmoPoint[2] + SPACE + spaceSpawnPoint[2] + colorSquare[0][2] + "|"+ SPACE  + colorSquare[0][3] + "|" + CYAN +space + SPACE + space + SPACE + spaceAmmoPoint[3] + SPACE + spaceSpawnPoint[3] + colorSquare[0][3] + "|\n" + RESET);
        Printer.print(colorSquare[0][0] + " ¯¯" + wallSouth[0] + wallSouth[0] + wallSouth[0] + "¯¯" + SPACE + colorSquare[0][1] + "  ¯¯" + wallSouth[1] + wallSouth[1] + wallSouth[1] + "¯¯" + SPACE + colorSquare[0][2] + "  ¯¯" + wallSouth[2] + wallSouth[2] + wallSouth[2] + "¯¯" + SPACE + colorSquare[0][3] + "  ¯¯" + wallSouth[3] + wallSouth[3]  + wallSouth[3] + "¯¯\n" + RESET);

        //second row
        Printer.print(colorSquare[1][0] + " __" + wallNorth[4] + wallNorth[4]  + wallNorth[4] + "__" + SPACE + colorSquare[1][1] + "  __" + wallNorth[5] + wallNorth[5] + wallNorth[5] + "__" + SPACE + colorSquare[1][2] + "  __" + wallNorth[6] + wallNorth[6]  + wallNorth[6] + "__"   + SPACE + colorSquare[1][3] + "  __" + wallNorth[7] + wallNorth[7] + wallNorth[7] + "__\n"  + RESET);
        Printer.print(colorSquare[1][0] + "|" + spacePlayer5[0] + SPACE + spacePlayer5[1] + SPACE + spacePlayer5[2] + SPACE + spacePlayer5[3] + colorSquare[1][0] + "|" + SPACE + colorSquare[1][1] + "|" + spacePlayer6[0] + SPACE + spacePlayer6[1] + SPACE + spacePlayer6[2] + SPACE + spacePlayer6[3] + colorSquare[1][1] +"|"+ SPACE + colorSquare[1][2] + "|" + spacePlayer7[0] + SPACE + spacePlayer7[1] + SPACE + spacePlayer7[2] + SPACE + spacePlayer7[3] + colorSquare[1][2] +"|"+ SPACE  + colorSquare[1][3] + "|" + spacePlayer8[0] + SPACE + spacePlayer8[1] + SPACE + spacePlayer8[2] + SPACE + spacePlayer8[3] + colorSquare[1][3] + "|\n" + RESET);
        Printer.print(colorSquare[1][0] + wallWest[4] + spacePlayer5[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[1][0] + wallEast[4] + SPACE + colorSquare[1][1] + wallWest[5] + spacePlayer6[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[1][1] + wallEast[5] + SPACE + colorSquare[1][2] + wallWest[6] + spacePlayer7[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[1][2] + wallEast[6] + SPACE  + colorSquare[1][3] + wallWest[7] + spacePlayer8[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[1][3] +  wallEast[7] + "\n" + RESET);
        Printer.print(colorSquare[1][0] + "|" + CYAN + space + SPACE + space + SPACE + spaceAmmoPoint[4] + SPACE + spaceSpawnPoint[4] + colorSquare[1][0] + "|" + SPACE + colorSquare[1][1] + "|" + CYAN +space + SPACE + space + SPACE + spaceAmmoPoint[5] + SPACE + spaceSpawnPoint[5] + colorSquare[1][1] + "|" + SPACE + colorSquare[1][2] + "|" + CYAN +space + SPACE + space + SPACE + spaceAmmoPoint[6] + SPACE + spaceSpawnPoint[6] + colorSquare[1][2] + "|"+ SPACE  + colorSquare[1][3] + "|" + CYAN +space + SPACE + space + SPACE + spaceAmmoPoint[7] + SPACE + spaceSpawnPoint[7] + colorSquare[1][3] + "|\n" + RESET);
        Printer.print(colorSquare[1][0] + " ¯¯" + wallSouth[4] + wallSouth[4]  + wallSouth[4] + "¯¯" + SPACE + colorSquare[1][1] + "  ¯¯" + wallSouth[5] + wallSouth[5] + wallSouth[5] + "¯¯" + SPACE + colorSquare[1][2] + "  ¯¯" + wallSouth[6] + wallSouth[6] + wallSouth[6] + "¯¯" + SPACE + colorSquare[1][3] + "  ¯¯" + wallSouth[7] + wallSouth[7] + wallSouth[7] + "¯¯\n" + RESET);

        //third row
        Printer.print(colorSquare[2][0] + " __" + wallNorth[8] + wallNorth[8]  + wallNorth[8] + "__" + SPACE + colorSquare[2][1] + "  __" + wallNorth[9] + wallNorth[9] + wallNorth[9] + "__" + SPACE + colorSquare[2][2] + "  __" + wallNorth[10] + wallNorth[10] + wallNorth[10] + "__"   + SPACE + colorSquare[2][3] + "  __" + wallNorth[11] + wallNorth[11]  + wallNorth[11] + "__\n"  + RESET);
        Printer.print(colorSquare[2][0] + "|" + spacePlayer9[0] + SPACE + spacePlayer9[1] + SPACE + spacePlayer9[2] + SPACE + spacePlayer9[3] + colorSquare[2][0] + "|" + SPACE + colorSquare[2][1] + "|" + spacePlayer10[0] + SPACE + spacePlayer10[1] + SPACE + spacePlayer10[2] + SPACE + spacePlayer10[3] + colorSquare[2][1] + "|"+ SPACE + colorSquare[2][2] + "|" + spacePlayer11[0] + SPACE + spacePlayer11[1] + SPACE + spacePlayer11[2] + SPACE + spacePlayer11[3] + colorSquare[2][2] + "|"+ SPACE  + colorSquare[2][3] + "|" + spacePlayer12[0] + SPACE + spacePlayer12[1] + SPACE + spacePlayer12[2] + SPACE + spacePlayer12[3] + colorSquare[2][3] + "|\n" + RESET);
        Printer.print(colorSquare[2][0] + wallWest[8] + spacePlayer9[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[2][0] + wallEast[8] + SPACE + colorSquare[2][1] + wallWest[9] + spacePlayer10[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[2][1] + wallEast[9] + SPACE + colorSquare[2][2] + wallWest[10] + spacePlayer11[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[2][2] + wallEast[10] + SPACE  + colorSquare[2][3] + wallWest[11] + spacePlayer12[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[2][3] + wallEast[11] + "\n" + RESET);
        Printer.print(colorSquare[2][0] + "|" + CYAN + space + SPACE + space + SPACE + spaceAmmoPoint[8] + SPACE + spaceSpawnPoint[8] + colorSquare[2][0] + "|" + SPACE + colorSquare[2][1] + "|" + CYAN +space + SPACE + space + SPACE + spaceAmmoPoint[9] + SPACE + spaceSpawnPoint[9] + colorSquare[2][1] + "|" + SPACE + colorSquare[2][2] + "|" + CYAN +space + SPACE + space + SPACE + spaceAmmoPoint[10] + SPACE + spaceSpawnPoint[10] + colorSquare[2][2] + "|"+ SPACE  + colorSquare[2][3] + "|" + CYAN +space + SPACE + space + SPACE + spaceAmmoPoint[11] + SPACE + spaceSpawnPoint[11] + colorSquare[2][3] + "|\n" + RESET);
        Printer.print(colorSquare[2][0] + " ¯¯" + wallSouth[8] + wallSouth[8]  + wallSouth[8] + "¯¯" + SPACE + colorSquare[2][1] + "  ¯¯" + wallSouth[9] + wallSouth[9]  + wallSouth[9] + "¯¯" + SPACE + colorSquare[2][2] + "  ¯¯" + wallSouth[10] + wallSouth[10] + wallSouth[10] + "¯¯" + SPACE + colorSquare[2][3] + "  ¯¯" + wallSouth[11] + wallSouth[11]  + wallSouth[11] + "¯¯\n" + RESET);

    }
}
