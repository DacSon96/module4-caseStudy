// LOGIN VALIDATION
$(document).ready(function () {
    $('#login-form').validate({
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


// LOGIN
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
            location.href = 'homePage.html'
        }
    }).fail(function (data) {
        $('#login-error-box').html(
            `<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
                <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                </symbol>
            </svg>
            <div class="alert alert-danger d-flex align-items-center justify-content-center">
                <svg class="flex-shrink-0 me-2" width="16" height="16" aria-label="Danger:">
                <use xlink:href="#exclamation-triangle-fill"/>
                </svg>
                <div style="font-weight: bold">Invalid username or password.</div>
            </div>`
        )
    });
};


// REGISTER VALIDATION
$(document).ready(function () {
    $('#registration-form').validate({
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


// REGISTER INPUT CLEAR
function clear() {
    username = $('#register-username').val('');
    password = $('#register-password').val('');
    rePassword = $('#register-re-password').val('');
    email = $('#register-email').val('');
};

$('#btn-close').click(function () {
    clear();
});


// REGISTER
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
            window.location.href = 'homePage.html';

        }
    }).fail(function (data) {
        $('#register-error-box').html(
            `<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
                <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                </symbol>
            </svg>
            <div class="alert alert-danger d-flex align-items-center justify-content-center">
                <svg class="flex-shrink-0 me-2" width="16" height="16" aria-label="Danger:">
                <use xlink:href="#exclamation-triangle-fill"/>
                </svg>
                <div style="font-weight: bold">Username or Email has already been registered.</div>
            </div>`
        )
    });
};


// SHOW & HIDE PASSWORD
const togglePassword = document.querySelector('#togglePassword1');
let password = document.querySelector('#login-password');
togglePassword.addEventListener('click', function (e) {
    // toggle the type attribute
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);
    // toggle the eye / eye slash icon
    this.classList.toggle('bi-eye');
});

const togglePassword2 = document.querySelector('#togglePassword2');
const password2 = document.querySelector('#register-password');
togglePassword2.addEventListener('click', function (e) {
    // toggle the type attribute
    const type = password2.getAttribute('type') === 'password' ? 'text' : 'password';
    password2.setAttribute('type', type);
    // toggle the eye / eye slash icon
    this.classList.toggle('bi-eye');
});

const togglePassword3 = document.querySelector('#togglePassword3');
const password3 = document.querySelector('#register-re-password');
togglePassword3.addEventListener('click', function (e) {
    // toggle the type attribute
    const type = password3.getAttribute('type') === 'password' ? 'text' : 'password';
    password3.setAttribute('type', type);
    // toggle the eye / eye slash icon
    this.classList.toggle('bi-eye');
});
