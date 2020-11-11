let map;
let boxSize;
let backgroundColor="#c0c0c0";
let wallColor = "#222";
const PATROL_PATROLLING = "#374F6B";
const PATROL_INTERVENTION = "#2137BD";
const PATROL_SHOOTING = "#cba214";
const PATROL_WOUNDED = "#b50202";
let patrol
const CHARACTER_WALL = "#";
const CHARACTER_HOSPITAL = "H";

let context;
let dataContext;


async function initiateMonitor(){
    let monitorParent = document.getElementById("monitorPreview");
    map = loadMapFromServer();
    let mapY=map.length;
    let mapX=map[0].length;

    //SETTING UP SIZE OF SINGLE ELEMENT IN MONITOR
    if(mapX>mapY){
        boxSize = parseInt( monitorParent.clientWidth / mapX);
    }else{
        boxSize = parseInt(monitorParent.clientHeight / mapY);
    }
    //ADJUSTING SIZE OF MONITOR SCREEN
    monitorParent.style.height = mapY * boxSize + "px";
    monitorParent.style.width = mapX * boxSize + "px";

    //SETTING UP MAP CANVAS
    let canvas = document.createElement("canvas");
    canvas.id = "monitor";
    canvas.height = monitorParent.clientHeight;
    canvas.width = monitorParent.clientWidth;
    canvas.style.zIndex = "1";
    canvas.style.position = "absolute";
    monitorParent.appendChild(canvas);
    context = canvas.getContext("2d");

    //SETTING UP DATA CANVAS ON TOP OF MAP CANVAS
    let dataCanvas = document.createElement("canvas");
    dataCanvas.id="dataMonitor";
    dataCanvas.height = monitorParent.clientHeight;
    dataCanvas.width = monitorParent.clientWidth;
    dataCanvas.style.zIndex = "2";
    dataCanvas.style.position = "absolute";
    monitorParent.appendChild(dataCanvas);
    dataContext = dataCanvas.getContext("2d");

    initiateWalls();
    //END OF MONITOR LOGIC
    function loadMapFromServer(){
        let arrayMap = [];
        let len=0;
        // let loadedMap =
        //     "##############################\n" +
        //     "#........#######.............#\n" +
        //     "####.###..#####..####.######.#\n" +
        //     "####.####.##H#..#####......#.#\n" +
        //     "##.............######.####.#.#\n" +
        //     "####.########.###.....####.#.#\n" +
        //     "####.########.###.###.####.#.#\n" +
        //     "#............................#\n" +
        //     "####.##############.###.####.#\n" +
        //     "####.##############.###.####.#\n" +
        //     "##............#.....###.####.#\n" +
        //     "##.####.###########.......##.#\n" +
        //     "##.####.####........#####....#\n" +
        //     "##...##......######.......####\n" +
        //     "##############################";
        let loadedMap = "############################################################\n" +
            "#........#####.........................#####...............#\n" +
            "####.###..####.######.######.#####.###.#####.######.######.#\n" +
            "####.####.#H##.######......#.#####.###.#####.######......#.#\n" +
            "##.............######.####.#.###.............######.####.#.#\n" +
            "####.########.###.....####.#.#####.########.###.....####.#.#\n" +
            "####.########.###.###.####.#.#####.########.###.###.####.#.#\n" +
            "#............................##............................#\n" +
            "####.##############.###.####.#####.##############.###.####.#\n" +
            "####.##############.###.####.#####.##############.###.####.#\n" +
            "##............#.....###.####.###............#.....###.####.#\n" +
            "##.####.###########.......##.###.####.###########.......##.#\n" +
            "##.####.####........#####......#.####.####........#####....#\n" +
            "##...##......######.......####.#...##......###.##.......####\n" +
            "#########################.####.###############.#############\n" +
            "#########################.####.###############.#############\n" +
            "#........#######.............#.........#######.............#\n" +
            "####.###..#####..####.######.#####.###..#####..####.######.#\n" +
            "####.####.####..#####......#.#####.####.#H##..#####......#.#\n" +
            "##.............######.####.#.###.............######.####.#.#\n" +
            "####.########.###.....####.#.#####.########.###.....####.#.#\n" +
            "####.########.###.###.####.#.#####.########.###.###.####.#.#\n" +
            "#............................##............................#\n" +
            "####.##############.###.####.#####.##############.###.####.#\n" +
            "####.##############.###.####.#####.##############.###.####.#\n" +
            "##............#.....###.####................#.....###.####.#\n" +
            "##.####.###########.......##.###.####.###########.......##.#\n" +
            "##.####.####........#####....###.####.####........#####....#\n" +
            "##...##......######.......######...##......######.......####\n" +
            "############################################################";

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
        context.fillStyle = backgroundColor;
        context.fillRect(0,0,monitorParent.clientWidth,monitorParent.clientHeight);
        let currX=0;
        let currY=0;
        map.forEach(function(line){
            line.split("").forEach(function(character){
                // console.log("Character:" + character + " CurrX:" + currX + " CurrY:"+ currY);
               switch(character){
                   case CHARACTER_WALL:
                       drawWall(currX,currY);
                       break;
                   case CHARACTER_HOSPITAL:
                       drawHospital(currX,currY);
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


    function drawHospital(x,y){
        let drawSize = parseInt(boxSize/2.5);
        let outlineSize = parseInt(boxSize/6);
        context.fillStyle = "#FF0000";
        context.fillRect(boxSize*x,boxSize*y,boxSize,boxSize);
        context.fillStyle = "#FFF";
        context.fillRect(boxSize*x, boxSize*y, drawSize, drawSize);
        context.fillRect((boxSize*x)+boxSize, boxSize*y, -drawSize, drawSize);
        context.fillRect((boxSize*x)+boxSize, (boxSize*y)+boxSize, -drawSize, -drawSize);
        context.fillRect((boxSize*x), (boxSize*y)+boxSize, drawSize, -drawSize);
        context.fillRect(boxSize*x, boxSize*y, boxSize, outlineSize);
        context.fillRect(boxSize*x, boxSize*y+boxSize, boxSize, -outlineSize);
        context.fillRect(boxSize*x, boxSize*y, outlineSize, boxSize);
        context.fillRect(boxSize*x+boxSize, boxSize*y, -outlineSize, boxSize);
    }

}

function deleteMonitor(){
    let monitorParent = document.getElementById("monitorPreview");
    monitorParent.innerHTML = '';
    monitorParent.style.height = 1000 + "px";
    monitorParent.style.width = 1000 + "px";
    console.log("boxsize:" + boxSize);
}

async function updateMonitor(){
    clearData();
    let serverData = getServerData();
    drawData();
    function drawPatrol(x,y,type){
        //TODO: FINISH ALL STATES OF PATROL
        let outlineSize = parseInt(boxSize/4.5);

        switch(type){
            case "patrolling":{
                dataContext.fillStyle = PATROL_PATROLLING;
                break;
            }
            case "intervention":{
                dataContext.fillStyle = PATROL_INTERVENTION;
                break;
            }
            case "shooting":{
                dataContext.fillStyle = PATROL_SHOOTING;
                break;
            }
            case "wounded":{
                dataContext.fillStyle = PATROL_WOUNDED;
                break;
            }
        }
        dataContext.fillRect(boxSize*x,boxSize*y,boxSize,boxSize);
        dataContext.clearRect(boxSize*x, boxSize*y, boxSize, outlineSize);
        dataContext.clearRect(boxSize*x, boxSize*y+boxSize, boxSize, -outlineSize);
        dataContext.clearRect(boxSize*x, boxSize*y, outlineSize, boxSize);
        dataContext.clearRect(boxSize*x+boxSize, boxSize*y, -outlineSize, boxSize);
    }
    function clearData(){
        dataContext.clearRect(0, 0, document.getElementById("dataMonitor").clientWidth, document.getElementById("dataMonitor").clientHeight);
    }
    function getServerData(){
        //SOME SAMPLE DATA
        let data = {
            "0":{
                "x": 28,
                "y": 7,
                "type": "patrol",
                "status": "patrolling",
                "heading-to": {
                    "x": 15,
                    "y": 22
                },
                "info": "Some additional info to display"
            },
            "1":{
                "x": 47,
                "y": 25,
                "type": "patrol",
                "status": "intervention",
                "heading-to": {
                    "x": 15,
                    "y": 22
                },
                "info": "Some additional info to display"
            },
            "2":{
                "x": 21,
                "y": 20,
                "type": "patrol",
                "status": "shooting",
                "heading-to": {
                    "x": 15,
                    "y": 22
                },
                "info": "Some additional info to display"
            },
            "3":{
                "x": 58,
                "y": 7,
                "type": "patrol",
                "status": "wounded",
                "heading-to": {
                    "x": 15,
                    "y": 22
                },
                "info": "Some additional info to display"
            },

        }
        return data;
    }

    function drawData(){
        for(let obj in serverData){
            console.log(serverData[obj]);
            drawPatrol(serverData[obj]["x"],serverData[obj]["y"],serverData[obj]["status"]);
        }

    }
    //TRIGGER ON DATA CANVAS CLICK
    document.getElementById("dataMonitor").addEventListener("click", function(e) {
        let cRect = document.getElementById("monitor").getBoundingClientRect();        // Gets CSS pos, and width/height
        let mouseX = parseInt(Math.round(e.clientX - cRect.left)/boxSize);  // Subtract the 'left' of the canvas
        let mouseY = parseInt(Math.round(e.clientY - cRect.top)/boxSize);   // from the X/Y positions to make
        dataContext.clearRect(0, 0, 200, 33);  // (0,0) the top left of the canvas
        dataContext.fillStyle="#FFF";
        dataContext.font = "21px Roboto";
        dataContext.fillText("X: "+mouseX+", Y: "+mouseY, 10, 20);
    });
}
