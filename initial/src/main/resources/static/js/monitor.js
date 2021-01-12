let map;
let boxSize;
let backgroundColor = "#B6B6B6";
let wallColor = "#222";
const PATROL_PRIMARY = "#29ff00";
const PATROL_PATROLLING = "#374F6B";
const PATROL_INTERVENTION = "#2137BD";
const PATROL_SHOOTING = "#9b7d12";
const PATROL_WOUNDED = "#b50202";
const AMBULANCE_PRIMARY = "#fa1111";
const AMBULANCE_SECONDARY = "#EEEEEE";
const INCIDENT_PRIMARY = "rgba(191,33,55,0.4)";
const INCIDENT_INTERVENSION = "rgba(210,139,30,0.4)";
const INCIDENT_OUTLINE = "#bd2137BD";
const STATION_PRIMARY = "#2740e2";
const STATION_SECONDARY = "#f4fd0e";
const STATION_TERITARY = "#c3ca22";
const NIGHT_COLOR = "rgba(3,3,21,0.25)";

const CHARACTER_WALL = "#";
const CHARACTER_HOSPITAL = "H";
const CHARACTER_ROAD = ".";
const CHARACTER_STATION = "S";


let context;
let dataContext;

let serverData;

function preHide(){
    $("#mainDataContainer").children().hide();
    $("#mainDataContainer").hide();
}
function enableSimulationOptions(){
    $("#afterConnection").children().show();
    $("#afterConnection").show();
}
function disableSimulationOptions(){
    $("#afterConnection").children().hide();
    $("#afterConnection").hide();
}
async function initiateMonitor(serverMap) {
    $('#conversation-row').toggle();
    document.getElementById("mainDataContainer").setAttribute("display","inline");
    let cont = document.getElementById("mainDataContainer");
    cont.setAttribute("style", "width: 98vw");
    cont.setAttribute("style", "height: 66vw");
    $("#mainDataContainer").children().show();
    $("#mainDataContainer").show();
    let monitorParent = document.getElementById("monitorPreview");
    monitorParent.setAttribute("style", "width: 99.8vw");
    monitorParent.setAttribute("style", "height: 99.8vw");
    map = loadMapFromServer();
    let mapY = map.length;
    let mapX = map[0].length;

    //SETTING UP SIZE OF SINGLE ELEMENT IN MONITOR
    if (mapX > mapY) {
        boxSize = parseInt(monitorParent.clientWidth / mapX);
    } else {
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
    dataCanvas.id = "dataMonitor";
    dataCanvas.height = monitorParent.clientHeight;
    dataCanvas.width = monitorParent.clientWidth;
    dataCanvas.style.zIndex = "2";
    dataCanvas.style.position = "absolute";
    monitorParent.appendChild(dataCanvas);
    dataContext = dataCanvas.getContext("2d");

    initiateWalls();
    drawPoliceStation(10,10);
    //END OF MONITOR LOGIC
    function loadMapFromServer() {
        let arrayMap = [];
        let len = 0;
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
        /*let loadedMap = "##############################################################################\n" +
            "##...................................#########....###############.....#####.##\n" +
            "##.#####.######.#############.######...........##.###.......#####.#########.##\n" +
            "##.#####..###......####.......###################.#########.................##\n" +
            "##.######.###.####......#####.###################.##########.##.##########.###\n" +
            "##.##.###.###.#########.####.................................##.#########..###\n" +
            "##.##.###....................####.#####.################.######.#########.####\n" +
            "##.##.###.#######################.#####.#####.....######.######.##........####\n" +
            "##.##.###........######.................#########..................#.####.####\n" +
            "##.##.##########..#####.#########.#####...........##################.####.####\n" +
            "##.##.###########.#####.#########.#####.################...............##.####\n" +
            "##...........................####.#####.#.##.............#######.###.#.##.####\n" +
            "#####.#######.#########.#########.#####.#.##.###.###.###.#######..........####\n" +
            "#####.#....##.#########.#########.......#................#######.########.####\n" +
            "###...#.##.##.....................#####.#.######.###.###.#######.########.####\n" +
            "###.#.#.###############################.#...####.....###.####H##.########.####\n" +
            "###.#.#.###H###########################.################...............#####.#\n" +
            "###.#....................................................#############.#####.#\n" +
            "###.#############.#############.#######.######.#############...........##....#\n" +
            "###.###########.................#######.######.###.##.###.####.###.###.##.####\n" +
            "###.###########.#######################.....##.###.##.###.####.###.###.##.####\n" +
            "###.........................###########.###..#.###..........................##\n" +
            "###.##.##############.#####.............###.##.####.##.###.###.###.###.####.##\n" +
            "###..#.........##.....###.########.##.#.....##.####.##.###.###.###.###.####.##\n" +
            "####.#########.######.###.########.##.##.##.##.####.##.............###.####.##\n" +
            "####...............................##.##.##.##.###################.....####.##\n" +
            "####.######.#######.##############.##.##.#####..............##########.####.##\n" +
            "####.######.#######.##############.##.##.#####.####.#######.....#............#\n" +
            "####...............................##.##.#####.####.#######.#####.####.#####.#\n" +
            "####.#########.##########.########.##.##.#####.####.........#####.####.#####.#\n" +
            "####.#########.##########.########.##.##.#####.#######.####.#####.####.#####.#\n" +
            "###..............########..........##.##.#####...............................#\n" +
            "################..........########.##.##.#####.###############################\n" +
            "#....###########.########.########.##.##.#####.###############################\n" +
            "####..................................##.#####.###############################\n" +
            "##############.#########################.#####.###############################\n" +
            "###............#########################.#####.###############################\n" +
            "#####.#############......................#####.###############################\n" +
            "#####.#############.##########################.###############################\n" +
            "#.....#############.##########################..##############################\n" +
            "###################.###########################.##############################\n" +
            "#########...........###########################.##############################\n" +
            "#########.##.###.##.##################.....................................###\n" +
            "#########.##.#......##############.....########.######.###.########.###.##.###\n" +
            "#########.##...####.##############.###..........######.###.####.....###.##.###\n" +
            "#########.##.#......##############.###.#####.#########.###.####.##.####..#.###\n" +
            "#............######.##############.###.#####...............####.##.#####...###\n" +
            "#.###.#.##.#.....##.##############.###.###########.#######.........##....#.###\n" +
            "#.....#.##.#.###.##.##############.###.....................#####.####.####.###\n" +
            "#.###..................................######.##########H#######....#.####.###\n" +
            "#.###.##.##.####.##.###.#####################....###############.##.#.####.###\n" +
            "#.######.##......##.###.########################.###############.##.#.####.###\n" +
            "#....##.....####.##.###.########################...........................###\n" +
            "####.##.#####.......###.###########################.###########.####.#####.###\n" +
            "####....#####.##.##..............#################......................##.###\n" +
            "####.##..........##.############...........#######.######.#####.#######.##.###\n" +
            "####.##.#####.##.##.############.#########.#######.######.#####............###\n" +
            "####................############.#########.#######.#####........##############\n" +
            "###..##############........................#######....########################\n" +
            "##############################################################################";*/
        serverMap.split('\n').forEach(function (line) {
            arrayMap[len++] = line;
        });
        if(arrayMap[len-1] == '') arrayMap.pop();
        return arrayMap;
    }

    function drawWall(x, y) {
        context.fillStyle = wallColor;
        context.fillRect(boxSize * x, boxSize * y, boxSize, boxSize);
    }

    function initiateWalls() {
        context.fillStyle = backgroundColor;
        context.fillRect(0, 0, monitorParent.clientWidth, monitorParent.clientHeight);
        let currX = 0;
        let currY = 0;
        map.forEach(function (line) {
            line.split("").forEach(function (character) {
                // console.log("Character:" + character + " CurrX:" + currX + " CurrY:"+ currY);
                switch (character) {
                    case CHARACTER_WALL:
                        drawWall(currX, currY);
                        break;
                    case CHARACTER_HOSPITAL:
                        drawHospital(currX, currY);
                        break;
                    case CHARACTER_STATION:
                        drawPoliceStation(currX,currY);
                        break;
                }
                currX++;
                if (currX > (mapX - 1)) {
                    currY++;
                    currX = 0;
                }
            });
        });
    }


    function drawHospital(x, y) {
        let drawSize = (parseInt(boxSize / 2.5)) + 1;
        let outlineSize = parseInt(boxSize / 6);
        context.fillStyle = "#FF0000";
        context.fillRect(boxSize * x, boxSize * y, boxSize, boxSize);
        context.fillStyle = "#FFF";
        context.fillRect(boxSize * x, boxSize * y, drawSize, drawSize);
        context.fillRect((boxSize * x) + boxSize, boxSize * y, -drawSize, drawSize);
        context.fillRect((boxSize * x) + boxSize, (boxSize * y) + boxSize, -drawSize, -drawSize);
        context.fillRect((boxSize * x), (boxSize * y) + boxSize, drawSize, -drawSize);
        context.fillRect(boxSize * x, boxSize * y, boxSize, outlineSize);
        context.fillRect(boxSize * x, boxSize * y + boxSize, boxSize, -outlineSize);
        context.fillRect(boxSize * x, boxSize * y, outlineSize, boxSize);
        context.fillRect(boxSize * x + boxSize, boxSize * y, -outlineSize, boxSize);
    }

    function drawPoliceStation(x,y){
        context.fillStyle=STATION_PRIMARY;
        context.fillRect(boxSize * x, boxSize * y, boxSize, boxSize);
        context.fillStyle = STATION_SECONDARY;
        context.strokeStyle = STATION_SECONDARY;
        context.beginPath();
        context.arc(x*boxSize+(boxSize/2), y*boxSize+(boxSize/2), boxSize/3, 0, 2 * Math.PI);
        context.stroke();
        context.fill();
        context.fillStyle = STATION_TERITARY;
        context.strokeStyle = STATION_TERITARY;
        context.beginPath();
        context.arc(x*boxSize+(boxSize/2), y*boxSize+(boxSize/2), boxSize/4, 0, 2 * Math.PI);
        context.stroke();
        context.fill();
    }

    document.getElementById("mainDataContainer");
    cont.setAttribute("style", "width: 100%");
    cont.setAttribute("style", "height: " + (monitorParent.clientHeight + (window.innerHeight * 2 / 100)) + "px");
    console.log(monitorParent.clientHeight);
    $("#afterConnection").children().hide();
    $("#afterConnection").hide();
}

function deleteMonitor() {
    let monitorParent = document.getElementById("monitorPreview");
    monitorParent.innerHTML = '';
    monitorParent.setAttribute("style", "width: 100%");
    monitorParent.setAttribute("style", "height: 100%");
    $("#mainDataContainer").children().hide();
    $("#mainDataContainer").hide();

    console.log("boxsize:" + boxSize);
    document.getElementById("monitorDataPreview").style.display = "none";
    document.getElementById("centered-group").style.display = "none";
    document.getElementById("monitorDataPreview").innerHTML = "";
}

async function updateMonitor(dataFromServer) {
    clearData();
    serverData = getServerData();
    //drawData();
    /*$('#monitorDataPreview').innerHTML="";
    let ta = document.createElement("table");
    ta.id = "monitorDataTable";
    $('#monitorDataPreview').appendChild(ta);*/

    console.log(dataFromServer);

    //DRAW PATROLS
    dataFromServer.patrols.forEach(element => {
       drawPatrol(element.coordinates.x,element.coordinates.y,"patroling");
    });

    //DRAW INCIDENTS
    dataFromServer.incidents.forEach(element => {
        drawIncident(element.incidentLocalization.x,element.incidentLocalization.y,element.incidentType);
    });

    drawNight();


    function drawNight(){
        dataContext.fillStyle = NIGHT_COLOR;
        dataContext.fillRect(0, 0, boxSize*(map[0].length), boxSize*(map.length));
    }

    function drawAmbulance(x, y) {
        let drawSize = parseInt(boxSize / 2.2);
        let outlineSize = parseInt(boxSize / 9);
        let outlineSizeUnit = parseInt(boxSize / 5.5);
        let outlineSizeUnitOut = parseInt(boxSize / 6.5);
        dataContext.fillStyle = wallColor;
        dataContext.fillRect(boxSize * x, boxSize * y, boxSize, boxSize);
        dataContext.fillStyle = AMBULANCE_PRIMARY;
        dataContext.fillRect(boxSize * x + outlineSizeUnit, boxSize * y + outlineSizeUnit, boxSize - 2 * outlineSizeUnit, boxSize - 2 * outlineSizeUnit);
        dataContext.fillStyle = AMBULANCE_SECONDARY;
        dataContext.fillRect(boxSize * x + outlineSizeUnit, boxSize * y + outlineSizeUnit, drawSize - outlineSizeUnit, drawSize - outlineSizeUnit);
        dataContext.fillRect((boxSize * x) + boxSize - outlineSizeUnit, boxSize * y + outlineSizeUnit, -drawSize + outlineSizeUnit, drawSize - outlineSizeUnit);
        dataContext.fillRect((boxSize * x) + boxSize - outlineSizeUnit, (boxSize * y) + boxSize - outlineSizeUnit, -drawSize + outlineSizeUnit, -drawSize + outlineSizeUnit);
        dataContext.fillRect((boxSize * x) + outlineSizeUnit, (boxSize * y) + boxSize - outlineSizeUnit, drawSize - outlineSizeUnit, -drawSize + outlineSizeUnit);
        dataContext.fillRect(boxSize * x + outlineSizeUnit, boxSize * y + outlineSizeUnit, boxSize - 2 * outlineSizeUnit, outlineSize);
        dataContext.fillRect(boxSize * x + outlineSizeUnit, boxSize * y + boxSize - outlineSizeUnit, boxSize - 2 * outlineSizeUnit, -outlineSize);
        dataContext.fillRect(boxSize * x + outlineSizeUnit, boxSize * y + outlineSizeUnit, outlineSize, boxSize - 2 * outlineSizeUnit);
        dataContext.fillRect(boxSize * x + boxSize - outlineSizeUnit, boxSize * y + outlineSizeUnit, -outlineSize, boxSize - 2 * outlineSizeUnit);
        dataContext.clearRect(boxSize * x, boxSize * y, boxSize, outlineSizeUnitOut);
        dataContext.clearRect(boxSize * x, boxSize * y + boxSize, boxSize, -outlineSizeUnitOut);
        dataContext.clearRect(boxSize * x, boxSize * y, outlineSizeUnitOut, boxSize);
        dataContext.clearRect(boxSize * x + boxSize, boxSize * y, -outlineSizeUnitOut, boxSize);
    }

    function drawPatrol(x, y, type) {

        let outlineSize = parseInt(boxSize / 6);
        let outlineSizeUnit = parseInt(boxSize / 2.7);

        switch (type) {
            case "patroling": {
                dataContext.fillStyle = PATROL_PATROLLING;
                break;
            }
            case "intervention": {
                dataContext.fillStyle = PATROL_INTERVENTION;
                break;
            }
            case "shooting": {
                dataContext.fillStyle = PATROL_SHOOTING;
                break;
            }
            case "wounded": {
                dataContext.fillStyle = PATROL_WOUNDED;
                break;
            }
        }

        dataContext.fillRect((boxSize * x) + outlineSize, (boxSize * y) + outlineSize, boxSize - (2 * outlineSize), boxSize - (2 * outlineSize));

        dataContext.fillStyle = PATROL_PRIMARY;
        dataContext.fillRect((boxSize * x) + outlineSizeUnit, (boxSize * y) + outlineSizeUnit, boxSize - (2 * outlineSizeUnit), boxSize - (2 * outlineSizeUnit));
        dataContext.clearRect(boxSize * x, boxSize * y, boxSize, outlineSize);
        dataContext.clearRect(boxSize * x, boxSize * y + boxSize, boxSize, -outlineSize);
        dataContext.clearRect(boxSize * x, boxSize * y, outlineSize, boxSize);
        dataContext.clearRect(boxSize * x + boxSize, boxSize * y, -outlineSize, boxSize);
    }

    function drawIncident(x,y,type){
        if (type=="INTERVENTION_TURNING_INTO_SHOOTING"){
            dataContext.fillStyle = INCIDENT_PRIMARY;
        }else{
            dataContext.fillStyle = INCIDENT_INTERVENSION;
        }
        dataContext.strokeStyle=INCIDENT_OUTLINE;
        dataContext.beginPath();
        dataContext.arc(x*boxSize+(boxSize/2), y*boxSize+(boxSize/2), boxSize*0.8, 0, 2 * Math.PI);
        dataContext.stroke();
        dataContext.fill();
    }

    function clearData() {
        dataContext.clearRect(0, 0, document.getElementById("dataMonitor").clientWidth, document.getElementById("dataMonitor").clientHeight);
    }

    function getServerData() {
        //SOME SAMPLE DATA
        let data = {
            "ambulances": {
                "0": {
                    "x": 5,
                    "y": 7,
                    "status": "intervention",
                    "info": "Some additional info to display"
                },
                "1": {
                    "x": 46,
                    "y": 36,
                    "status": "intervention",
                    "info": "Some additional info to display"
                },
            },
            "patrols": {
                "0": {
                    "x": 21,
                    "y": 21,
                    "status": "patrolling",
                    "info": "Some additional info to display"
                },
                "1": {
                    "x": 62,
                    "y": 27,
                    "status": "intervention",
                    "info": "Some additional info to display"
                },
                "2": {
                    "x": 4,
                    "y": 48,
                    "status": "shooting",
                    "info": "Some additional info to display"
                },
                "3": {
                    "x": 29,
                    "y": 49,
                    "status": "wounded",
                    "info": "Some additional info to display"
                },
            },

        }
        return data;
    }

    function drawData() {
        console.log(serverData["patrols"]["0"]);
        for (let obj in serverData["patrols"]) {
            drawPatrol(serverData["patrols"][obj]["x"], serverData["patrols"][obj]["y"], serverData["patrols"][obj]["status"]);
        }
        for (let obj in serverData["ambulances"]) {
            drawAmbulance(serverData["ambulances"][obj]["x"], serverData["ambulances"][obj]["y"]);
        }

    }

    function changeSimulationTime(time){
        document.getElementById("simulationTime").innerText = time;
    }

    //TRIGGER ON DATA CANVAS CLICK
    document.getElementById("dataMonitor").addEventListener("click", function (e) {
        let cRect = document.getElementById("monitor").getBoundingClientRect();        // Gets CSS pos, and width/height
        let mouseX = parseInt(Math.round(e.clientX - cRect.left) / boxSize);  // Subtract the 'left' of the canvas
        let mouseY = parseInt(Math.round(e.clientY - cRect.top) / boxSize);   // from the X/Y positions to make
        //dataContext.clearRect(0, 0, 200, 33);  // (0,0) the top left of the canvas
        dataContext.fillStyle = "#FFF";
        dataContext.font = "16px Roboto";
        dataContext.fillText("X: " + mouseX + ", Y: " + mouseY, mouseX*boxSize, mouseY*boxSize);

    });
    let data = [
            {
                'id':21,
                'type':"ambulance",
                'x': 21,
                'y': 21,
                'status': "patrolling",
                "info": "Some additional info to display"
            },
            {
                'id':1,
                "type":"patrol",
                "x": 62,
                "y": 27,
                "status": "intervention",
                "info": "Some additional info to display"
            },
            {
                'id':2,
                "type":"patrol",
                "x": 4,
                "y": 48,
                "status": "shooting",
                "info": "Some additional info to display"
            },
            {
                'id':3,
                "type":"patrol",
                "x": 29,
                "y": 49,
                "status": "wounded",
                "info": "Some additional info to display"
            }

    ]

    $('#monitorDataTable').DataTable( {
        destroy: true,
        data: data,
        paging: false,
        searching: false,
        columns: [
            { data: 'id', name: "ID" },
            { data: 'type', name: "Type"},
            { data: 'x', name: "X" },
            { data: 'y', name: "Y" },
            { data: 'status', name: "Status" },
            { data: 'info', name: "Info" }
        ],
        //paging: false
    } );
    let columns= document.getElementsByTagName("th");
    columns[0].innerText="ID";
    columns[1].innerText="Type";
    columns[2].innerText="X";
    columns[3].innerText="Y";
    columns[4].innerText="Status";
    columns[5].innerText="Info";
}
