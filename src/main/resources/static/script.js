// Set today's date as max for DOB on page load
document.addEventListener("DOMContentLoaded", function () {
    const today = new Date().toISOString().split("T")[0];
    document.getElementById("dob").setAttribute("max", today);
  });
  
  // Image Preview
  document.getElementById("photoUpload").addEventListener("change", function () {
    const file = this.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
        document.getElementById("photoPreview").setAttribute("src", e.target.result);
      };
      reader.readAsDataURL(file);
    }
  });
  
  // Form Validation
  document.getElementById("admissionForm").addEventListener("submit", function (e) {
    e.preventDefault();
    let form = this;
    let isValid = form.checkValidity();
  
    // Gender validation
	// Gender validation
	const genderSelected = document.querySelector('input[name="gender"]:checked');
	const genderError = document.getElementById("genderError");

	if (!genderSelected) {
	  genderError.style.display = "block";
	  isValid = false;
	} else {
	  genderError.style.display = "none";
	}
	
	// Hide gender error when a radio button is selected
	document.querySelectorAll('input[name="gender"]').forEach((radioButton) => {
	  radioButton.addEventListener("change", function () {
	    const genderError = document.getElementById("genderError");
	    genderError.style.display = "none";
	  });
  
	
    // DOB Validation
    const dobInput = document.getElementById("dob");
    const dobValue = new Date(dobInput.value);
    const today = new Date();
    const age = today.getFullYear() - dobValue.getFullYear();
    const monthDiff = today.getMonth() - dobValue.getMonth();
    const dayDiff = today.getDate() - dobValue.getDate();
    const hundredYearsAgo = new Date();
    hundredYearsAgo.setFullYear(hundredYearsAgo.getFullYear() - 100);
  
    const tooYoung = age < 17 || (age === 17 && (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)));
    const tooOld = dobValue < hundredYearsAgo;
    const futureDate = dobValue > today;
  
    if (!dobInput.value || tooYoung || tooOld || futureDate) {
      isValid = false;
      dobInput.classList.add("is-invalid");
      const msg = !dobInput.value
        ? "Please select your date of birth."
        : tooYoung
        ? "You must be at least 17 years old."
        : tooOld
        ? "Please enter a birth date within the last 100 years."
        : "Date of birth cannot be in the future.";
      dobInput.nextElementSibling.innerText = msg;
    } else {
      dobInput.classList.remove("is-invalid");
    }
  
    // Final check
    if (!isValid) {
      e.stopPropagation();
    } else {
      alert("Form submitted successfully!");
      form.reset();
      document.getElementById("photoPreview").src = "ukn.jpg"; // Reset image
    }
  
    form.classList.add("was-validated");
	
	
  });