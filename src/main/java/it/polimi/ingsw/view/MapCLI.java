package it.polimi.ingsw.view;


import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Square;

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

    private GameBoard gameBoard;
    private Square[][] arena;

    public void printMap(){

        String color = BLACK;
        String spawnPoint = "*";
        String ammo = "A";
        String enemy = color + "X";
        String player = color + "O";

        //walls and door
        String door = SPACE;
        String wallVertical = "|";
        String wallStandardSouth = "_";
        String wallStandardNorth= "¯";
        String []wallNorth = new String[NUM_SQUARES];
        String []wallSouth = new String[NUM_SQUARES];
        String []wallEast = new String[NUM_SQUARES];
        String []wallWest = new String[NUM_SQUARES];


        //first square
        String spacePlayer1[] = new String[MAX_NUM_PLAYER];
        String space = SPACE;
        String spaceA11 = null;
        String spaceA12 = null;



        //second square
        String spacePlayer2[] = new String[MAX_NUM_PLAYER];
        String spaceB11 = null;
        String spaceB12 = null;


        //third square
        String spacePlayer3[] = new String[MAX_NUM_PLAYER];
        String spaceC11 = null;
        String spaceC12 = null;


        //fourth square
        String spacePlayer4[] = new String[MAX_NUM_PLAYER];
        String spaceD11 = null;
        String spaceD12 = null;



        //fifth square
        String spacePlayer5[] = new String[MAX_NUM_PLAYER];
        String spaceE11 = null;
        String spaceE12 = null;



        //sixth square
        String spacePlayer6[] = new String[MAX_NUM_PLAYER];
        String spaceF11 = null;
        String spaceF12 = null;


        //seventh square
        String spacePlayer7[] = new String[MAX_NUM_PLAYER];
        String spaceG11 = null;
        String spaceG12 = null;


        //eighth square
        String spacePlayer8[] = new String[MAX_NUM_PLAYER];
        String spaceH11 = null;
        String spaceH12 = null;


        //ninth square
        String spacePlayer9[] = new String[MAX_NUM_PLAYER];
        String spaceI11 = null;
        String spaceI12 = null;


        //tenth square
        String spacePlayer10[] = new String[MAX_NUM_PLAYER];
        String spaceL11 = null;
        String spaceL12 = null;


        //eleventh square
        String spacePlayer11[] = new String[MAX_NUM_PLAYER];
        String spaceM11 = null;
        String spaceM12 = null;


        //twelfth square
        String spacePlayer12[] = new String[MAX_NUM_PLAYER];
        String spaceN11 = null;
        String spaceN12 = null;

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

               colorSquare[i][j] = String.valueOf(arena[i][j].getColor());

               if(arena[i][j].getColor().equals(TokenColor.NONE)){
                   colorSquare[i][j] = BLACK;
               }
           }
       }

       //create doors
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

               k++;
           }
       }


        //first row
        System.out.print(colorSquare[0][0] + " _ " + wallNorth[0] + SPACE  + wallNorth[0] + " _" + SPACE + colorSquare[0][1] + "  _ " + wallNorth[1] + SPACE  + wallNorth[1] + " _" + SPACE + colorSquare[0][2] + "  _ " + wallNorth[2] + SPACE + wallNorth[2] + " _"   + SPACE + colorSquare[0][3] + "  _ " + wallNorth[3] + SPACE  + wallNorth[3] + " _\n"  + RESET);
        System.out.print(colorSquare[0][0] + "|" + spacePlayer1[0] + SPACE + spacePlayer1[1] + SPACE + spacePlayer1[2] + SPACE + spacePlayer1[3] + colorSquare[0][0] + "|" + SPACE + colorSquare[0][1] + "|" + spacePlayer2[0] + SPACE + spacePlayer2[1] + SPACE + spacePlayer2[2] + SPACE + spacePlayer2[3] + colorSquare[0][1] + "|"+ SPACE + colorSquare[0][2] + "|" + spacePlayer3[0] + SPACE + spacePlayer3[1] + SPACE + spacePlayer3[2] + SPACE + spacePlayer3[3] + SPACE + colorSquare[0][2] + "|"+ SPACE  + colorSquare[0][3] + "|" + spacePlayer4[0] + SPACE + spacePlayer4[1] + SPACE + spacePlayer4[2] + SPACE + spacePlayer4[3] + colorSquare[0][3] + "|\n" + RESET);
        System.out.print(colorSquare[0][0] + wallWest[0] + spacePlayer1[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[0][0] + wallEast[0] + SPACE + colorSquare[0][1] + wallWest[1] + spacePlayer2[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[0][1] + wallEast[1] + SPACE + colorSquare[0][2] + wallWest[2] + spacePlayer3[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[0][2] + wallEast[2] + SPACE  + colorSquare[0][3] + wallWest[3] + spacePlayer4[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[0][3] + wallEast[3] + "\n" + RESET);
        System.out.print(colorSquare[0][0] + "|" + CYAN + space + SPACE + space + SPACE + spaceA11+ SPACE + spaceA12 + colorSquare[0][0] + "|" + SPACE + colorSquare[0][1] + "|" + CYAN +space + SPACE + space + SPACE + spaceB11 + SPACE + spaceB12 + colorSquare[0][1] + "|" + SPACE + colorSquare[0][2] + "|" + CYAN +space + SPACE + space + SPACE + spaceC11 + SPACE + spaceC12 + colorSquare[0][2] + "|"+ SPACE  + colorSquare[0][3] + "|" + CYAN +space + SPACE + space + SPACE + spaceD11 + SPACE + spaceD12 + colorSquare[0][3] + "|\n" + RESET);
        System.out.print(colorSquare[0][0] + " ¯ " + wallSouth[0] + SPACE  + wallSouth[0] + " ¯" + SPACE + colorSquare[0][1] + "  ¯ " + wallSouth[1] + SPACE + wallSouth[1] + " ¯" + SPACE + colorSquare[0][2] + "  ¯ " + wallSouth[2] + SPACE + wallSouth[2] + " ¯" + SPACE + colorSquare[0][3] + "  ¯ " + wallSouth[3] + SPACE  + wallSouth[3] + " ¯\n" + RESET);

        //second row
        System.out.print(colorSquare[1][0] + " _ " + wallNorth[4] + SPACE  + wallNorth[4] + " _" + SPACE + colorSquare[1][1] + "  _ " + wallNorth[5] + " " + wallNorth[5] + " _" + SPACE + colorSquare[1][2] + "  _ " + wallNorth[6] + SPACE  + wallNorth[6] + " _"   + SPACE + colorSquare[1][3] + "  _ " + wallNorth[7] + " " + wallNorth[7] + " _\n"  + RESET);
        System.out.print(colorSquare[1][0] + "|" + spacePlayer5[0] + SPACE + spacePlayer5[1] + SPACE + spacePlayer5[2] + SPACE + spacePlayer5[3] + colorSquare[1][0] + "|" + SPACE + colorSquare[1][1] + "|" + spacePlayer6[0] + SPACE + spacePlayer6[1] + SPACE + spacePlayer6[2] + SPACE + spacePlayer6[3] + colorSquare[1][1] +"|"+ SPACE + colorSquare[1][2] + "|" + spacePlayer7[0] + SPACE + spacePlayer7[1] + SPACE + spacePlayer7[2] + SPACE + spacePlayer7[3] + colorSquare[1][2] +"|"+ SPACE  + colorSquare[1][3] + "|" + spacePlayer8[0] + SPACE + spacePlayer8[1] + SPACE + spacePlayer8[2] + SPACE + spacePlayer8[3] + colorSquare[1][3] + "|\n" + RESET);
        System.out.print(colorSquare[1][0] + wallWest[4] + spacePlayer5[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[1][0] + wallEast[4] + SPACE + colorSquare[1][1] + wallWest[5] + spacePlayer6[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[1][1] + wallEast[5] + SPACE + colorSquare[1][2] + wallWest[6] + spacePlayer7[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[1][2] + wallEast[6] + SPACE  + colorSquare[1][3] + wallWest[7] + spacePlayer8[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[1][3] +  wallEast[7] + "\n" + RESET);
        System.out.print(colorSquare[1][0] + "|" + CYAN + space + SPACE + space + SPACE + spaceE11 + SPACE + spaceE12 + colorSquare[1][0] + "|" + SPACE + colorSquare[1][1] + "|" + CYAN +space + SPACE + space + SPACE + spaceF11 + SPACE + spaceF12 + colorSquare[1][1] + "|" + SPACE + colorSquare[1][2] + "|" + CYAN +space + SPACE + space + SPACE + spaceG11 + SPACE + spaceG12 + colorSquare[1][3] + "|"+ SPACE  + colorSquare[1][3] + "|" + CYAN +space + SPACE + space + SPACE + spaceH11 + SPACE + spaceH12 + colorSquare[1][3] + "|\n" + RESET);
        System.out.print(colorSquare[1][0] + " ¯ " + wallSouth[4] + SPACE  + wallSouth[4] + " ¯" + SPACE + colorSquare[1][1] + "  ¯ " + wallSouth[5] + SPACE + wallSouth[5] + " ¯" + SPACE + colorSquare[1][2] + "  ¯ " + wallSouth[6] + SPACE + wallSouth[6] + " ¯" + SPACE + colorSquare[1][3] + "  ¯ " + wallSouth[7] + " " + wallSouth[7] + " ¯\n" + RESET);

        //third row
        System.out.print(colorSquare[2][0] + " _ " + wallNorth[8] + SPACE  + wallNorth[8] + " _" + SPACE + colorSquare[2][1] + "  _ " + wallNorth[9] + SPACE + wallNorth[9] + " _" + SPACE + colorSquare[2][2] + "  _ " + wallNorth[10] + SPACE + wallNorth[10] + " _"   + SPACE + colorSquare[2][3] + "  _ " + wallNorth[11] + SPACE  + wallNorth[11] + " _\n"  + RESET);
        System.out.print(colorSquare[2][0] + "|" + spacePlayer9[0] + SPACE + spacePlayer9[1] + SPACE + spacePlayer9[2] + SPACE + spacePlayer9[3] + colorSquare[2][0] + "|" + SPACE + colorSquare[2][1] + "|" + spacePlayer10[0] + SPACE + spacePlayer10[1] + SPACE + spacePlayer10[2] + SPACE + spacePlayer10[3] + colorSquare[2][1] + "|"+ SPACE + colorSquare[2][2] + "|" + spacePlayer11[0] + SPACE + spacePlayer11[1] + SPACE + spacePlayer11[2] + SPACE + spacePlayer11[3] + SPACE + colorSquare[2][2] + "|"+ SPACE  + colorSquare[2][3] + "|" + spacePlayer12[0] + SPACE + spacePlayer12[1] + SPACE + spacePlayer12[2] + SPACE + spacePlayer12[3] + colorSquare[2][3] + "|\n" + RESET);
        System.out.print(colorSquare[2][0] + wallWest[8] + spacePlayer9[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[2][0] + wallEast[8] + SPACE + colorSquare[2][1] + wallWest[9] + spacePlayer10[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[2][1] + wallEast[9] + SPACE + colorSquare[2][2] + wallWest[10] + spacePlayer11[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[2][2] + wallEast[10] + SPACE  + colorSquare[2][3] + wallWest[11] + spacePlayer12[4] + SPACE + space + SPACE + space + SPACE + space + colorSquare[2][3] + wallEast[11] + "\n" + RESET);
        System.out.print(colorSquare[2][0] + "|" + CYAN + space + SPACE + space + SPACE + spaceI11 + SPACE + spaceI12 + colorSquare[2][0] + "|" + SPACE + colorSquare[2][1] + "|" + CYAN +space + SPACE + space + SPACE + spaceL11 + SPACE + spaceL12 + colorSquare[2][1] + "|" + SPACE + colorSquare[2][2] + "|" + CYAN +space + SPACE + space + SPACE + spaceM11 + SPACE + spaceM12 + colorSquare[2][2] + "|"+ SPACE  + colorSquare[2][3] + "|" + CYAN +space + SPACE + space + SPACE + spaceN11 + SPACE + spaceN12 + colorSquare[2][3] + "|\n" + RESET);
        System.out.print(colorSquare[2][0] + " ¯ " + wallSouth[8] + SPACE  + wallSouth[8] + " ¯" + SPACE + colorSquare[2][1] + "  ¯ " + wallSouth[9] + SPACE  + wallSouth[9] + " ¯" + SPACE + colorSquare[2][2] + "  ¯ " + wallSouth[10] + " " + wallSouth[10] + " ¯" + SPACE + colorSquare[2][3] + "  ¯ " + wallSouth[11] + SPACE  + wallSouth[11] + " ¯\n" + RESET);

    }

}
