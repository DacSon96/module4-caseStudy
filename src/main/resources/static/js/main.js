let currentUser = JSON.parse(localStorage.getItem('currentUser'));


$(document).ready(function () {
    getUserById();
    showEditInfo();
});

function createPost() {
    let form = $('#post')[0];
    let data = new FormData(form);
    data.append('user', currentUser.id);
    // goi ajax
    $.ajax({
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        type: "POST",
        //tên API
        url: "/posts",

        //xử lý khi thành công
        success: function () {
            $('#content').val("");
            $('#image').val("");
        }
    });
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}

function getUserById() {
    $.ajax({
        headers: {
            'Authorization': 'Bearer ' + currentUser.accessToken
        },
        url: `http://localhost:8080/users/${currentUser.id}`,
        type: 'GET',
        success: function (user) {
            let username = `<h3 class="my-username" style="font-family: 'Poppins', sans-serif;font-size: 1.17em;font-weight: bold;margin-bottom: 0px;">${user.username}</h3>`;
            let cover = `<img src="${user.cover}" alt="coverImage" class="coverImage" style="height: 350px">`
            let avatar = `<img src="${user.avatar}" alt="avatarImage" class="dashboard-img">`
            let intro = `<h4>intro</h4><p>${user.intro}</p><hr>`
            let information = `<a href="#"><i class="fas fa-briefcase"></i>
                              <p>${user.work}</p>
                          </a>
                          <a href="#"><i class="fas fa-home"></i>
                              <p>${user.address}</p>
                          </a>`
            $('.my-username').html(username);
            $('.my-cover').html(cover);
            $('.my-avatar').html(avatar);
            $('.intro-bio').html(intro);
            $('.background-details').html(information);
        }
    })
};


function showEditInfo() {
    $.ajax({
        headers: {
            'Authorization': 'Bearer ' + currentUser.accessToken
        },
        url: `http://localhost:8080/users/${currentUser.id}`,
        type: 'GET',
        success: function (user) {
            let intro = `<label class="form-label">Intro</label>
                         <input class="form-control" type="text" name="intro" id="edit-intro" value="${user.intro}">`
            let work = `<label class="form-label">Work</label>
                         <input class="form-control" type="text" name="work" id="edit-work" value="${user.work}">`
            let address = `<label class="form-label">Address</label>
                         <input class="form-control" type="text" name="address" id="edit-address" value="${user.address}">`
            let avatar = `<div class="row">
                            <label class="form-label">Avatar</label>
                            <div class="col">
                                <input class="form-control" type="file" name="avatar">
                            </div>
                            <div class="col">
                                <input class="form-control" value="${user.avatar}" readonly>
                            </div>
                          </div>`
            let cover = `<div class="row">
                            <label class="form-label">Cover</label>
                            <div class="col">
                                <input class="form-control" type="file" name="cover">
                            </div>
                            <div class="col">
                                <input class="form-control" value="${user.cover}" readonly>
                            </div>
                         </div>`

            $('#intro').html(intro);
            $('#work').html(work);
            $('#address').html(address);
            $('#avatar').html(avatar);
            $('#cover').html(cover);
        }
    })
};


function editUserInfo() {
    let form = new FormData($('#edit-user-info')[0]);
    $.ajax({
        headers: {
            'Authorization': 'Bearer ' + currentUser.accessToken
        },
        url: `http://localhost:8080/users/${currentUser.id}`,
        type: 'PUT',
        data: form,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function () {
            showToast();
            location.href = "/profile";
        }
    })
};


function showToast() {
    let toastLiveExample = document.getElementById('editInfoToast');
    let toast = new bootstrap.Toast(toastLiveExample);
    toast.show();
};