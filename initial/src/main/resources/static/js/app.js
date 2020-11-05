let stompClient = null;

const setConnected = (connected) => {
    $("#connect").css("display", connected ? "none" : "block");
    $("#disconnect").css("display", connected ? "block" : "none" );
    $("#reconnect").css("display", connected ? "block" : "none" );

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
    });
};

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
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
