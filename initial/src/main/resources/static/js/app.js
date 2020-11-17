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
    $("#greetings").html("");
}

let lastData = false;
const connect = (data) => {
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        // Listening and waiting for messages from server
        stompClient.subscribe('/topic/simulation', function (data) {
            showGreeting(JSON.parse(data.body).content);
        });
        login(data);

        // === RUN CANVAS ===
        runPreloader();
        drawCanvas();
    });
};

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");

    // === CLEAR CANVAS ===
    clearCanvas();
};

const login = (data) => {
    // === COMPLETE DATA FROM FORM ===
    if(!data) {
        let interventionProbablity = [];

        lastData = JSON.stringify({
            'patrolsCount': $('#countOfPatrols').val() != "" ? $('#countOfPatrols').val(): null,
            'patrolsPerDistrict': [
                $('#countOfPatrolsPerSafety').val() != "" ? $('#countOfPatrolsPerSafety').val(): null,
                $('#countOfPatrolsPerMedium').val() != "" ? $('#countOfPatrolsPerMedium').val(): null,
                $('#countOfPatrolsPerDangerous').val() != "" ? $('#countOfPatrolsPerDangerous').val(): null
            ],
            'ambulancesCount': $('#countOfAmbulances').val() != "" ? $('#countOfAmbulances').val(): null,
            'patrolRadius': $('#patrolRadius').val() != "" ? $('#patrolRadius').val(): null,
            'interventionProbablity': [
                $('#interventionProbablityForSafety').val() != "" ? $('#interventionProbablityForSafety').val() : null,
                $('#interventionProbablityForMedium').val() != "" ? $('#interventionProbablityForMedium').val() : null,
                $('#interventionProbablityForDangerous').val() != "" ? $('#interventionProbablityForDangerous').val(): null
            ],
            'nightInterventionProbablity': [
                $('#interventionProbablityForSafetyNight').val() != "" ? $('#interventionProbablityForSafetyNight').val(): null,
                $('#interventionProbablityForMediumNight').val() != "" ? $('#interventionProbablityForMediumNight').val(): null,
                $('#interventionProbablityForDangerousNight').val() != "" ? $('#interventionProbablityForDangerousNight').val(): null
            ],
            'interventionDuration': [
                $('#minIntervationDuration').val() != "" ? $('#minIntervationDuration').val(): null,
                $('#maxIntervationDuration').val() != "" ? $('#maxIntervationDuration').val(): null
            ],
            'shootingProbablity': [
                $('#shootingProbablityForSafety').val() != "" ? $('#shootingProbablityForSafety').val(): null,
                $('#shootingProbablityForMedium').val() != "" ? $('#shootingProbablityForMedium').val(): null,
                $('#shootingProbablityForDangerous').val() != "" ? $('#shootingProbablityForDangerous').val(): null
            ],
            'interventionToShootingProbablity': [
                $('#interventionToShootingProbablityForSafety').val() != "" ? $('#interventionToShootingProbablityForSafety').val(): null,
                $('#interventionToShootingProbablityForMedium').val() != "" ? $('#interventionToShootingProbablityForMedium').val(): null,
                $('#interventionToShootingProbablityForDangerous').val() != "" ? $('#interventionToShootingProbablityForDangerous').val(): null
            ],
            'shootingDuration': [
                $('#minShootingDuration').val() != "" ? $('#minShootingDuration').val(): null,
                $('#maxShootingDuration').val() != "" ? $('#maxShootingDuration').val(): null
            ],
            'accuratePolicemanShootProbablity':
                $('#accurateShootingProbablityForPoliceman').val() != "" ? $('#accurateShootingProbablityForPoliceman').val(): null,
            'accuratePolicemanShootProbablityNight':
                $('#accurateShootingProbablityForPolicemanNight').val() != "" ? $('#accurateShootingProbablityForPolicemanNight').val(): null,
            'accurateAggressorShootProbablity':
                $('#accurateShootingProbablityForAggressor').val() != "" ? $('#accurateShootingProbablityForAggressor').val(): null,
            'accurateAggressorShootProbablityNight':
                $('#accurateShootingProbablityForAggressorNight').val() != "" ?  $('#accurateShootingProbablityForAggressorNight').val(): null,
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

const showGreeting = (message) => {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
};

$(() => {
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
