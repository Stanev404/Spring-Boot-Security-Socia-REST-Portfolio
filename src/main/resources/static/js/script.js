// function setHalfVolume() {
//     var myAudio = document.getElementById("backgroundMusic");
//     myAudio.volume = 0.15; //Changed this to 0.5 or 50% volume since the function is called Set Half Volume ;)
// }


$(document).ready(function () {

// Music logic - continously playing and half volume
    // setHalfVolume();
    //
    // function setCookie(c_name,value,exdays)
    // {
    //     var exdate=new Date();
    //     exdate.setDate(exdate.getDate() + exdays);
    //     var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
    //     document.cookie=c_name + "=" + c_value;
    // }
    //
    // function getCookie(c_name)
    // {
    //     var i,x,y,ARRcookies=document.cookie.split(";");
    //     for (i=0;i<ARRcookies.length;i++)
    //     {
    //         x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
    //         y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
    //         x=x.replace(/^\s+|\s+$/g,"");
    //         if (x==c_name)
    //         {
    //             return unescape(y);
    //         }
    //     }
    // }
    //
    // var song = document.getElementsByTagName('audio')[0];
    // var played = false;
    // var tillPlayed = getCookie('timePlayed');
    // function update()
    // {
    //     if(!played){
    //         if(tillPlayed){
    //             song.currentTime = tillPlayed;
    //             song.play();
    //             played = true;
    //         }
    //         else {
    //             song.play();
    //             played = true;
    //         }
    //     }
    //
    //     else {
    //         setCookie('timePlayed', song.currentTime);
    //     }
    // }
    // setInterval(update,0);

// Scroll-Top
    var offset = 800;
    var duration = 500;
    $(window).scroll(function () {
        if ($(this).scrollTop() > offset) {
            $('.to-top').fadeIn(duration);
        } else {
            $('.to-top').fadeOut(duration);
        }
    });
    $('#scr').click(function () {

        $('html,body').animate({
            scrollTop: 0
        }, duration);
    });
    // fixes the facebook #_=_ append to url
    if (window.location.hash == '#_=_'){
        history.replaceState
            ? history.replaceState(null, null, window.location.href.split('#')[0])
            : window.location.hash = '';
    }
});
