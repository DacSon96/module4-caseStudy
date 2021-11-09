
$(document).ready(function () {
    getUserById();
});

function resultValueSearch() {
    let valueSearch = $('#q').val();

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "get",
        //tên API
        url: `/users?q=${valueSearch}`,
        //xử lý khi thành công
        success: showResultSearch

    });
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();

}


function showResultSearch(data) {
    let content = "";
    for (let i = 0; i < data.length; i++) {
        content += `<div class="online-list">
            <div class="online">
                <img src="${data[i].avatar}" alt="">
            </div>
            <p>${data[i].username}</p>
        </div>`
    }
    document.getElementById('postField').innerHTML = content;
}


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
            getMyPostByPage()
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
        url: `/users/${currentUser.id}`,
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
}
