$(document).ready(function () {
    $("#login-form").validate({
        rules: {
            loginUsername: {
                required: true,
                minlength: 4,
                maxlength: 20
            },
            loginPassword: {
                required: true,
                minlength: 4,
                maxlength: 20
            },
        },
        submitHandler: function (form) {
            login();
            return false;
        }
    });
});

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
    }).fail(function (data) {
        alert("Try again champ!");
    });
}

$(document).ready(function () {
    $("#registration-form").validate({
        rules: {
            registerUsername: {
                required: true,
                minlength: 4,
                maxlength: 20
            },
            registerPassword: {
                required: true,
                minlength: 4,
                maxlength: 20
            },
            registerRePassword: {
                required: true,
                minlength: 4,
                maxlength: 20,
                equalTo: '#register-password'
            },
            registerEmail: {
                required: true,
                email: true
            },
        },
        submitHandler: function (form) {
            register();
            return false;
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



