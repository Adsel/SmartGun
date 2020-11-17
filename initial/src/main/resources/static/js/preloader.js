const runPreloader = () => {
    $("#loader-wrapper").css("display", "block");

    // TODO:
    // CHECK WHEN CANVAS IS LOADED THEN RUN CODE BELOW
    // LUIGI THE CITY NEEDS YOU

    // CODE BELOW HIDES PRELOADER
        setTimeout(() => {
            $("#loader-wrapper").remove();
        }, 3000);

}
