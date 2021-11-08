let currentUser = JSON.parse(localStorage.getItem('currentUser'));

$(document).ready(function (){
    getUserById();
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

function getUserById(){
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
function getStory() {
    let storyContent = "";
    $.ajax({
        headers: {
            'Authorization': 'Bearer ' + currentUser.accessToken
        },
        url: "/users",
        method: "GET",
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                storyContent += getStoryList(data[i]);
            }
            document.getElementById('story-gallery').innerHTML = storyContent;
        }
    });
}

function getStoryList(user) {
    return `<div class="story story2">
                    <img src="${user.avatar}" alt="">
                    <p>${user.username}</p>
             </div>`
}

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
            location.href = "/profile";
            showToast();
        }
    })
};


function showToast() {
    let toastLiveExample = document.getElementById('editInfoToast');
    let toast = new bootstrap.Toast(toastLiveExample);
    toast.show();
};