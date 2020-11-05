let stompClient = null;

const setConnected = (connected) => {
    $("#beforeConnection").css("display", connected ? "none" : "block");
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

const connect = () => {
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        // Listening and waiting for messages from server
        stompClient.subscribe('/topic/simulation', function (data) {
            showGreeting(JSON.parse(data.body).content);
        });
        login();

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

const login = () => {
    stompClient.send('/app/login', {});
};

const sendName = () => {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
};

const showGreeting = (message) => {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
};

$(() => {
    $("form").on('submit', function(e) {
        e.preventDefault();
    });
    $( "#connect" ).click(() => {
        connect();
    });
    $( "#disconnect" ).click(() => {
        disconnect();
    });
    $( "#send" ).click(() => {
        sendName();
    });
});
