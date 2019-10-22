// $('.button').on('click', function(e){
//     $('.display').text('Clicked');
//     setTimeout(function(){
//       $('.display').text('');
//     },300);
//   });
//
var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendLeft() {
    // stompClient.send("/app/hello", {}, 'abc');
    stompClient.send("/app/hello", {}, JSON.stringify('left'));
}

function sendRight() {
    stompClient.send("/app/hello", {}, JSON.stringify('right'));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "abc" + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#power" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#right" ).click(function() { sendRight(); });
    $( "#left" ).click(function() { sendLeft(); });
});


