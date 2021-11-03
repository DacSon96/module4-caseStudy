function login() {
    let username = $('#login-username').val();
    let password = $('#login-password').val();

    let loginUser = {
        username: username,
        password: password
    };

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        data: JSON.stringify(loginUser),
        url: `http://localhost:8080/login`,
        type: 'POST',
        success: function (result) {
            localStorage.setItem('currentUser', JSON.stringify(result));
            window.location.href = "index.html"
        }
    });
}

$(document).ready(function (){
    $("#login-form").validate({
        rules:{
            loginUsername: "required",
            loginPassword: "required"
        },
        submitHandler: function(form) {
            login();
            return false; // required to block normal submit since you used ajax
        }
    });
});

function register() {
    let username = $('#register-username').val();
    let password = $('#register-password').val();
    let rePassword = $('#register-re-password').val();
    let email = $('#register-email').val();

    let loginUser = {
        username: username,
        password: password,
        rePassword: rePassword,
        email: email
    };

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        data: JSON.stringify(loginUser),
        url: `http://localhost:8080/register`,
        type: 'POST',
        success: function (result) {
            localStorage.setItem('currentUser', JSON.stringify(result));
            window.location.href = "index.html"
        }
    });
}

$(document).ready(function (){
    $("#registration-form").validate({
        rules:{
            registerUsername: "required",
            registerPassword: "required",
            registerRePassword: "required",
            registerEmail: {
                required: true,
                email: true
            }
        },
        submitHandler: function(form) {
            register();
            return false; // required to block normal submit since you used ajax
        }
    });
});
