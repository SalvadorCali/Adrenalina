# Prova Finale Ingegneria del Software 2019
## Gruppo AM03

- ###   10490117    Andrea Calici ([@SalvadorCali](https://github.com/SalvadorCali))<br>andrea.calici@mail.polimi.it
- ###   10497479    Pietro Bernasconi ([@pitt1095](https://github.com/pitt1095))<br>pietro.bernasconi@mail.polimi.it
- ###   10467384    Tommaso Comelli ([@lpmcbg](https://github.com/lpmcbg))<br>tommaso.comelli@mail.polimi.it

| Functionality | State |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Complete rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Socket | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| RMI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| GUI | [![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#) |
| CLI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Multiple games | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Persistence | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Domination or Towers modes | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Terminator | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |

<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->

## Rules

<p text align="justify">In a world of savage chaos, five combatants are locked inside a futuristic arena. Armed with sophisticated weaponry, their mission is to kill or be killed.<p text align="justify">

## Deliverables

* [*Initial UML*](https://github.com/SalvadorCali/am03-ing-sw-2019-Bernasconi-Calici-Comelli/blob/master/deliverables/Initial-UML.pdf)
* [*Final UML*](https://github.com/SalvadorCali/am03-ing-sw-2019-Bernasconi-Calici-Comelli/tree/master/deliverables)
* [*server.jar*](https://github.com/SalvadorCali/am03-ing-sw-2019-Bernasconi-Calici-Comelli/tree/master/deliverables)
* [*client.jar*](https://github.com/SalvadorCali/am03-ing-sw-2019-Bernasconi-Calici-Comelli/tree/master/deliverables)
* [*Communication*](https://github.com/SalvadorCali/am03-ing-sw-2019-Bernasconi-Calici-Comelli/blob/master/deliverables/Communication.pdf)

## Getting Started

### Running

* Open the terminal
* Launch the jar files using:
```
java -jar jar_name.jar
```
There are 2 jars:

-**server.jar**: runs the Server

-**client.jar**: runs the Client

### Command-Line Arguments

You can also run the Server with a list of command-line arguments that will set the main timers of the game.
```
java -jar server.jar START_TIME_MS BOARD_TIME_MS SPAWN_TIME_MS TURN_TIME_MS
```
* **START_TIME_MS**: time for the beginning of the game in milliseconds.
* **BOARD_TIME_MS**: time for the choice of the board in milliseconds.
* **SPAWN_TIME_MS**: time for the choice of the spawn in milliseconds.
* **TURN_TIME_MS**: time for each turn in milliseconds.

#### Rules for the timers:

* All these times are contained in the <i>Config</i> class and handled by their classes contained in the <i>controller.timer</i> package. 
* If you don't put 4 command-line arguments, all the timers remain with the default value. 
* If you choose for one or more of them an incorrect value, the default value will be used.

#### Default values:
```
START_TIME_MS = 10000;
BOARD_TIME_MS = 10000;
SPAWN_TIME_MS = 10000;
TURN_TIME_MS = 50000;
```

## Graphic Interface

When you run the <i>client.jar</i>, you can choose between CLI or GUI:
```
[CLIENT]Choose 'cli' or 'gui': *insert 'cli' or 'gui'*
```

### CLI

<p text align="justify">If you choose 'cli', two others strings will be printed, where you can choose the connection and the ip address.<p text align="justify">
  
```
[CLIENT]Choose 'rmi' or 'socket': *insert 'rmi' or 'socket'*
[CLIENT]Please, set an ip address: *insert ip address*
```
  
### GUI

<p text align="justify">If you choose 'gui' a screen will be open where you can put you data for the game.<p text align="justify">

## Functionalities

### Multiple Games

<p text align="justify">We implemented the <i>multiple game</i> advanced functionality. It is handled by the <i>ServerControllerManager</i> class, that creates a new <i>ServerController</i> for each game designated to handle its game.<p text align="justify">

#### Rules

* If there are no games, a new game will be created. Each new game will be created when the previous reaches the minimum number of players.
* The username is unique in the lobby, so you can't choose an username already used by another player, even if the other player is playing a different game.
* If you are disconnected and try to reconnect yourself, the <i>ServerControllerManager</i> will look for you username in the list of disconnected users and reconnects you to the correct game.


## Built With

* [GitHub](https://github.com/) - Web-based hosting service for version control using Git
* [Intellij IDEA](https://www.jetbrains.com/idea/) - Java integrated development environment (IDE)
* [Maven](https://maven.apache.org/) - Dependency Management
* [SonarQube](https://www.sonarqube.org/) - Used  to perform automatic reviews with static analysis of code to detect bugs, code smells, and security vulnerabilities
