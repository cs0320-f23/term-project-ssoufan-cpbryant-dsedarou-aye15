function toggleSubmit() {
    var submitButton = document.getElementById("submitButton");
    
    // Toggle the "pressed" class
    submitButton.classList.toggle("pressed");

    // Remove the "pressed" class after a short delay (you can adjust the delay)
    setTimeout(function() {
        submitButton.classList.remove("pressed");
    }, 100); // 300 milliseconds
}