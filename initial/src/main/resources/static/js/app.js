// STOMP OPERATIONS
let stompClient = null;

const setConnected = (connected) => {
    $("#initialData").css("display", connected ? "none" : "block");
    $("#afterConnection").css("display", connected ? "block" : "none" );
    $("#monitorPreview").css("display", connected ? "block" : "none" );

    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#notifications").html("");
}

let lastData = false;
const connect = (data) => {
    runPreloader();
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        // Listening and waiting for messages from server
        stompClient.subscribe('/topic/simulation', function (data) {

            const msgData = JSON.parse(data.body);

            if (!(msgData.currentMap != null && msgData.currentMap != undefined)) {
                // LOG ACCIDENTS AND EVENTS
                console.log('PORTION OF DATA', msgData);
                showNotification(msgData.content);
            }
            else {
                // INIT SIMULATION DATA (MAP, etc.)
                console.log('START DATA', msgData);
                initiateMonitor(msgData.currentMap.mapAsString).then(updateMonitor()).then($("#loader-wrapper").remove());
            }
        });
        login(data);

        // === RUN CANVAS ===
    });
};

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");

    // === CLEAR CANVAS ===
    deleteMonitor();
};

const login = (data) => {
    // === COMPLETE DATA FROM FORM ===
    if (!data) {
        lastData = JSON.stringify({
            'patrolsCount': $('#countOfPatrols').val() != "" ? parseInt($('#countOfPatrols').val()) : -1,
            'patrolsPerDistrict': [
                $('#countOfPatrolsPerSafety').val() != "" ? parseInt($('#countOfPatrolsPerSafety').val()) : -1,
                $('#countOfPatrolsPerMedium').val() != "" ? parseInt($('#countOfPatrolsPerMedium').val()) : -1,
                $('#countOfPatrolsPerDangerous').val() != "" ? parseInt($('#countOfPatrolsPerDangerous').val()) : -1
            ],
            'patrolRadius': $('#patrolRadius').val() != "" ? parseInt($('#patrolRadius').val()) : -1,
            'ambulancesCount': $('#countOfAmbulances').val() != "" ? parseInt($('#countOfAmbulances').val()) : -1,
            'interventionProbablity': [
                $('#interventionProbablityForSafety').val() != "" ? parseInt($('#interventionProbablityForSafety').val()) : -1,
                $('#interventionProbablityForMedium').val() != "" ? parseInt($('#interventionProbablityForMedium').val()) : -1,
                $('#interventionProbablityForDangerous').val() != "" ? parseInt($('#interventionProbablityForDangerous').val()) : -1
            ],
            'nightInterventionProbablity': [
                $('#interventionProbablityForSafetyNight').val() != "" ? parseInt($('#interventionProbablityForSafetyNight').val()) : -1,
                $('#interventionProbablityForMediumNight').val() != "" ? parseInt($('#interventionProbablityForMediumNight').val()) : -1,
                $('#interventionProbablityForDangerousNight').val() != "" ? parseInt($('#interventionProbablityForDangerousNight').val()) : -1
            ],
            'interventionDuration': [
                $('#minIntervationDuration').val() != "" ? parseInt($('#minIntervationDuration').val()) : -1,
                $('#maxIntervationDuration').val() != "" ? parseInt($('#maxIntervationDuration').val()) : -1
            ],
            'shootingProbablity': [
                $('#shootingProbablityForSafety').val() != "" ? parseInt($('#shootingProbablityForSafety').val()) : -1,
                $('#shootingProbablityForMedium').val() != "" ? parseInt($('#shootingProbablityForMedium').val()) : -1,
                $('#shootingProbablityForDangerous').val() != "" ? parseInt($('#shootingProbablityForDangerous').val()) : -1
            ],
            'interventionToShootingProbablity': [
                $('#interventionToShootingProbablityForSafety').val() != "" ? parseInt($('#interventionToShootingProbablityForSafety').val()) : -1,
                $('#interventionToShootingProbablityForMedium').val() != "" ? parseInt($('#interventionToShootingProbablityForMedium').val()): -1,
                $('#interventionToShootingProbablityForDangerous').val() != "" ? parseInt($('#interventionToShootingProbablityForDangerous').val()) : -1
            ],
            'shootingDuration': [
                $('#minShootingDuration').val() != "" ? parseInt($('#minShootingDuration').val()) : -1,
                $('#maxShootingDuration').val() != "" ? parseInt($('#maxShootingDuration').val()) : -1
            ],
            'accuratePolicemanShootProbablity':
                $('#accurateShootingProbablityForPoliceman').val() != "" ? parseInt($('#accurateShootingProbablityForPoliceman').val()) : -1,
            'accuratePolicemanShootProbablityNight':
                $('#accurateShootingProbablityForPolicemanNight').val() != "" ? parseInt($('#accurateShootingProbablityForPolicemanNight').val()) : -1,
            'accurateAggressorShootProbablity':
                $('#accurateShootingProbablityForAggressor').val() != "" ? parseInt($('#accurateShootingProbablityForAggressor').val()) : -1,
            'accurateAggressorShootProbablityNight':
                $('#accurateShootingProbablityForAggressorNight').val() != "" ?  parseInt($('#accurateShootingProbablityForAggressorNight').val()) : -1,
            'approachProbablity': [
                $('#failReachingDestinationProbablityForSafety').val() != "" ? parseInt($('#failReachingDestinationProbablityForSafety').val()) : -1,
                $('#failReachingDestinationProbablityForMedium').val() != "" ? parseInt($('#failReachingDestinationProbablityForMedium').val()): -1,
                $('#failReachingDestinationProbablityForDangerous').val() != "" ? parseInt($('#failReachingDestinationProbablityForDangerous').val()) : -1
            ],
            'isDayAndNightSystem': $('#dayAndNight').is(':checked'),
            'isRandomMap': $('#randMap').is(':checked')
        });
    }
    stompClient.send('/app/login', {}, lastData);
    console.log("Last data", lastData);
};

const sendName = () => {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
};

const showNotification = (message) => {
    $("#notifications").append("<tr><td>" + message + "</td></tr>");
    console.log('MESSAGE', message);
};

$(() => {
    $(".param-toggler").change((event) => {
        const INPUT_TURN_ON_ID = 'turnOnParams';
        const INPUT_TURN_OFF_ID = 'turnOffParams';
        if (event.currentTarget.id) {
            $('#' + INPUT_TURN_OFF_ID).parent().toggleClass("active");
            $('#' + INPUT_TURN_ON_ID).parent().toggleClass("active");
            $('#paramsWrapper').toggle();
        }
    });

    $("#dayAndNight").change(() => {
        console.log('Day and Night param');
        $('.dependsOnNightParam').toggle();
    });
    $("form").on('submit', (e) => {
        e.preventDefault();
    });
    $( "#connect" ).click(() => {
        connect(lastData);
    });
    $( "#disconnect" ).click(() => {
        disconnect();
    });
    $( "#reconnect" ).click(() => {
        disconnect();
        connect(lastData);
        runPreloader();
    });
    $( "#send" ).click(() => {
        sendName();
    });
});
