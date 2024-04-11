document.addEventListener("DOMContentLoaded", function() {
    const animatedImage = document.getElementById("animatedImage");

    animatedImage.addEventListener("animationend", function() {
        // Animation is complete, navigate to the next page
        window.location.href = "/test"; // Change "next-page.html" to the actual URL of your next page
    });
});
