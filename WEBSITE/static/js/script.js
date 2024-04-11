// script.js
document.addEventListener("DOMContentLoaded", function () {
    const uploadImage = document.getElementById("uploadImage");
    const imageSelect = document.getElementById("imageSelect");
    const analysisResult = document.querySelector(".analysis-result");
    const previewImage = document.getElementById("previewImage");
    const downloadLink = document.getElementById('downloadLink');
    // Event listener for image upload
    
    uploadImage.addEventListener("change", function () {
        const selectedImage = uploadImage.files[0];
        // Check if an image is selected
        if (selectedImage) {
            // Check if an option has been selected from the dropdown
            if (imageSelect.value !== "default") {
                var yourValue = imageSelect.value;
                $.ajax({
                    type: 'POST',
                    url: '/process_value',
                    data: { value: yourValue },
                    success: function(response) {
                      console.log(response);
                      console.log('image selected')
                    },
                    error: function(error) {
                      console.error(error);
                    }
                  });
                console.log(imageSelect.value)
                // Display the selected image in the preview
                const reader = new FileReader();
                reader.onload = function (e) {
                    analysisResult.textContent = "Analysis";
                    document.getElementById("disease").innerHTML = 'Description';
                    localStorage["selectedImage"] = e;
                    previewImage.src = e.target.result;
                  
                };
                reader.addEventListener("load", () => {
                    localStorage.setItem("recentimage", reader.result);
                });
               
                reader.readAsDataURL(selectedImage);
                console.log(selectedImage.name)
                
                //selectedImage.save('C:/Users/Isha Desai/Downloads/demo1 (5)/demo1/static/uploads')
                // Simulate image analysis with a placeholder result.
                const analysis = "";
                analysisResult.textContent = analysis;
            } else {
                // Clear the file input, analysis result, and image preview if no option is selected
                uploadImage.value = "";
                analysisResult.textContent = "";
                previewImage.src = "";
                alert("Please select an option from the dropdown before uploading an image.");
            }
        } else {
            // Clear the analysis result and image preview if no image is selected
            analysisResult.textContent = "";
            previewImage.src = "";
        }
        
    });
   

    imageSelect.addEventListener("change", function () {
        const selectedOption = imageSelect.value;

        // Check if an option other than the default is selected
        if (selectedOption !== "default") {
            // Enable the image upload input
            uploadImage.removeAttribute("disabled");
        } else {
            // Disable the image upload input
            uploadImage.setAttribute("disabled", true);
            // Clear the image preview when the option is changed back to default
            previewImage.src = "";
        }
    });
    const fileInput = document.getElementById("uploadImage");
     // true even if empty

});
