function validateRecaptcha() {
    var response = grecaptcha.getResponse();
    if (response.length === 0) {
        alert("Please complete the reCAPTCHA");
        return false;
    }
    return true;
}
