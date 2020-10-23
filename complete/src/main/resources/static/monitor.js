let monitorParent = document.getElementById("monitorPreview");

const CHARACTER_WALL = "#";

let wallColor = "#222";

let map = loadMapFromServer();
let mapY=map.length;
let mapX=map[0].length;

//SETTING UP SIZE OF SINGLE ELEMENT IN MONITOR
let boxSize;
if(mapX>mapY){
    boxSize = parseInt( monitorParent.clientWidth / mapX);
}else{
    boxSize = parseInt(monitorParent.clientHeight / mapY);
}
//ADJUSTING SIZE OF MONITOR SCREEN
monitorParent.style.height = mapY * boxSize + "px";
monitorParent.style.width = mapX * boxSize + "px";

//SETTING UP CANVAS
let canvas = document.createElement("canvas");
canvas.id = "monitor";
canvas.height = monitorParent.clientHeight;
canvas.width = monitorParent.clientWidth;
monitorParent.appendChild(canvas);
let context = canvas.getContext("2d");


//MONITOR LOGIC
    initiateWalls();
//END OF MONITOR LOGIC

function loadMapFromServer(){
    let arrayMap = [];
    let len=0;
    let loadedMap =
        "##############################\n" +
        "#........#######.............#\n" +
        "####.###..#####..####.######.#\n" +
        "####.####.####..#####......#.#\n" +
        "##.............######.####.#.#\n" +
        "####.########.###.....####.#.#\n" +
        "####.########.###.###.####.#.#\n" +
        "#............................#\n" +
        "####.##############.###.####.#\n" +
        "####.##############.###.####.#\n" +
        "##............#.....###.####.#\n" +
        "##.####.###########.......##.#\n" +
        "##.####.####........#####....#\n" +
        "##...##......######.......####\n" +
        "##############################";

    //TODO: POBRANIE MAPY Z SERWERA

    loadedMap.split('\n').forEach(function(line) {
        arrayMap[len++]=line;
    });
    return arrayMap;
}

function drawWall(x, y){
    context.fillStyle = wallColor;
    context.fillRect(boxSize*x,boxSize*y,boxSize,boxSize);
}
function initiateWalls(){
    let currX=0;
    let currY=0;
    map.forEach(function(line){
        console.log(line.split(""));
        line.split("").forEach(function(character){
            // console.log("Character:" + character + " CurrX:" + currX + " CurrY:"+ currY);
           switch(character){
               case CHARACTER_WALL:
                   drawWall(currX,currY);
                   break;
           }
           currX++;
           if(currX>(mapX-1)){
               currY++;
               currX = 0;
           }
        });
    });
}





