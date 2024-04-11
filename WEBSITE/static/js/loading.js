let currentImageIndex = 1;

function changeImage() {
    const image = document.querySelector('.image-container img');
    image.src = `static/Images/farmer${currentImageIndex}.PNG`;
    console.log(image.src)
    currentImageIndex++;
    if (currentImageIndex > 3) {
        clearInterval(intervalId); // Stop the interval after all images are shown
        setTimeout(function () {
            window.location.href = '/still'; // Redirect to 'index.html' after a delay
        }, 2000); // Add a delay of 2 seconds after showing all images
    }
}

// Initially set the first image
changeImage();

// Change image every 2 seconds
const intervalId = setInterval(changeImage, 2000);
