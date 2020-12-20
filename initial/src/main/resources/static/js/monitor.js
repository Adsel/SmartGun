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
const CHARACTER_WALL = "#";
const CHARACTER_HOSPITAL = "H";
const CHARACTER_ROAD = ".";

let context;
let dataContext;

let serverData;
let dataPresentingType = "all";


async function initiateMonitor(serverMap) {
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

        serverMap.map.split('\n').forEach(function (line) {
            arrayMap[len++] = line;
        });
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

    document.getElementById("monitorDataPreview").style.display = "block";
    document.getElementById("centered-group").style.display = "block";

    document.getElementById("onClickInfo").addEventListener("click", function () {
        dataPresentingType = "allInfo";
        console.log(dataPresentingType);
    });
    document.getElementById("allInfo").addEventListener("click", function () {
        dataPresentingType = "onClick";
        console.log(dataPresentingType);
    });
}

function deleteMonitor() {
    let monitorParent = document.getElementById("monitorPreview");
    monitorParent.innerHTML = '';
    monitorParent.style.height = 1000 + "px";
    monitorParent.style.width = 1000 + "px";
    console.log("boxsize:" + boxSize);
    document.getElementById("monitorDataPreview").style.display = "none";
    document.getElementById("centered-group").style.display = "none";
    document.getElementById("monitorDataPreview").innerHTML = "";
}

async function updateMonitor() {
    clearData();
    serverData = getServerData();
    drawData();
    /*$('#monitorDataPreview').innerHTML="";
    let ta = document.createElement("table");
    ta.id = "monitorDataTable";
    $('#monitorDataPreview').appendChild(ta);*/


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
            case "patrolling": {
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

    //TRIGGER ON DATA CANVAS CLICK
    document.getElementById("dataMonitor").addEventListener("mousemove", function (e) {
        let cRect = document.getElementById("monitor").getBoundingClientRect();        // Gets CSS pos, and width/height
        let mouseX = parseInt(Math.round(e.clientX - cRect.left) / boxSize);  // Subtract the 'left' of the canvas
        let mouseY = parseInt(Math.round(e.clientY - cRect.top) / boxSize);   // from the X/Y positions to make
        dataContext.clearRect(0, 0, 200, 33);  // (0,0) the top left of the canvas
        dataContext.fillStyle = "#FFF";
        dataContext.font = "16px Roboto";
        dataContext.fillText("X: " + mouseX + ", Y: " + mouseY, 10, 20);

    });
    let data = [
            {
                'id':0,
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
