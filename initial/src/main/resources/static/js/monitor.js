const drawCanvas = () => {
    let monitorParent = document.getElementById("monitorPreview");

    const CHARACTER_WALL = "#";
    const CHARACTER_HOSPITAL = "H";

    let backgroundColor="#c0c0c0";
    let wallColor = "#222";
    let patrolColor = "#374F6B";

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

    //SETTING UP MAP CANVAS
    let canvas = document.createElement("canvas");
    canvas.id = "monitor";
    canvas.height = monitorParent.clientHeight;
    canvas.width = monitorParent.clientWidth;
    canvas.style.zIndex = "1";
    canvas.style.position = "absolute";
    monitorParent.appendChild(canvas);
    let context = canvas.getContext("2d");

    //SETTING UP DATA CANVAS ON TOP OF MAP CANVAS
    let dataCanvas = document.createElement("canvas");
    dataCanvas.id="dataMonitor";
    dataCanvas.height = monitorParent.clientHeight;
    dataCanvas.width = monitorParent.clientWidth;
    dataCanvas.style.zIndex = "2";
    dataCanvas.style.position = "absolute";
    monitorParent.appendChild(dataCanvas);
    let dataContext = dataCanvas.getContext("2d");

    //TRIGGER ON DATA CANVAS CLICK
    dataCanvas.addEventListener("mousemove", function(e) {
        let cRect = canvas.getBoundingClientRect();        // Gets CSS pos, and width/height
        let mouseX = parseInt(Math.round(e.clientX - cRect.left)/boxSize);  // Subtract the 'left' of the canvas
        let mouseY = parseInt(Math.round(e.clientY - cRect.top)/boxSize);   // from the X/Y positions to make
        dataContext.clearRect(0, 0, 200, 33);  // (0,0) the top left of the canvas
        dataContext.fillStyle="#FFF";
        dataContext.font = "21px Roboto";
        dataContext.fillText("X: "+mouseX+", Y: "+mouseY, 10, 20);
    });

    //MONITOR LOGIC
    initiateWalls();
    drawPatrol(37,22,"test");
    drawPatrol(12,10,"test");
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

    function clearData(){
        dataContext.clearRect(0, 0, dataCanvas.width, dataCanvas.height);
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

    function drawPatrol(x,y,type){
        //TODO: FINISH ALL STATES OF PATROL
        let outlineSize = parseInt(boxSize/4.5);
        dataContext.fillStyle = patrolColor;
        dataContext.fillRect(boxSize*x,boxSize*y,boxSize,boxSize);
        dataContext.clearRect(boxSize*x, boxSize*y, boxSize, outlineSize);
        dataContext.clearRect(boxSize*x, boxSize*y+boxSize, boxSize, -outlineSize);
        dataContext.clearRect(boxSize*x, boxSize*y, outlineSize, boxSize);
        dataContext.clearRect(boxSize*x+boxSize, boxSize*y, -outlineSize, boxSize);
    }
}

const clearCanvas = () => {
    // TODO:
    // LUIGI THE CITY NEEDS YOU
}
