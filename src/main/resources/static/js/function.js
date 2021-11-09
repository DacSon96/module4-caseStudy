let currentUser = JSON.parse(localStorage.getItem('currentUser'));
let page = 1;
let postPerPage = 4;
let content = "";
let currentUserName = currentUser.username;
let currentUserId = currentUser.id;
var userSettings = document.querySelector(".user-settings");
var darkBtn = document.getElementById("dark-button");
var LoadMoreBackground = document.querySelector(".btn-LoadMore");

function UserSettingToggle() {
    userSettings.classList.toggle("user-setting-showup-toggle");
}

// darkBtn.onclick = function(){
//     darkBtn.classList.toggle("dark-mode-on");
// }

function darkModeON() {
    darkBtn.classList.toggle("dark-mode-on");
    document.body.classList.toggle("dark-theme");
};

function LoadMoreToggle() {
    page++;
    getPostByPage();
};

function LoadMoreToggleProfile() {
    page++;
    getMyPostByPage();
};
$(document).ready(function () {
    let name = document.getElementsByClassName("currentUserName");
    for (let i = 0; i < name.length; i++) {
        name[i].textContent = currentUserName;
    }
    getUserDetail();
})

function getUserDetail() {
    document.getElementById("whatOnYourMind").placeholder = "What on your mind, " + currentUserName + "?";
    getUserAvatar();
}

function getUserAvatar() {
    let avatarElement = document.getElementsByClassName("currentUserAvatar");
    for (let i = 0; i < avatarElement.length; i++) {
        avatarElement[i].src = currentUser.avatar;
    }
}

function getPostByPage() {
    $.ajax({
        url: "/posts",
        method: "GET",
        success: function (data) {
            showPost(data);
        },
    });
}

function getMyPostByPage() {
    $.ajax({
        url: "/posts/" + currentUserId,
        method: "GET",
        success: function (data) {
            showPost(data);
        }
    });
}

function getPost(post) {
    return `<div class="status-field-container write-post-container">
                <div class="user-profile-box">
                    <div class="user-profile">
                        <img src="${post.user.avatar}" alt="">
                        <div>
                            <p>${post.user.username}</p>
                            <small>August 13 1999, 09.18 pm</small>
                        </div>
                    </div>
                    <div>
                        <div class="dropdown">
                        <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink"                               data-bs-toggle="dropdown" aria-expanded="false">
                                ...
                        </a>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
    <li><a class="dropdown-item"  onclick="getEditForm(${post.id})" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop" >Edit</a></li>
    <li><a class="dropdown-item" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop1"
     onclick="showFormDelete(${post.id})">Delete</a></li>
  </ul>
</div>
                    </div>
                </div>
                <div class="status-field">
                    <p>${post.content}</a> </p>
                    <img src="${post.img}" alt="">
                </div>
                <div class="post-reaction">
                    <div class="activity-icons">
                        <div><img src="/images/like-blue.png" alt="">120</div>
                        <div><img src="/images/comments.png" alt="">52</div>
                        <div><img src="/images/share.png" alt="">35</div>
                    </div>
                    <div class="post-profile-picture">
                        <img src="/images/profile-pic.png " alt=""> <i class=" fas fa-caret-down"></i>
                    </div>
                </div>
            </div>`
}

function showFormDelete(data){
    let content = ` <button type="button" id="btn-delete-post" class="btn btn-primary" data-bs-dismiss="modal" onclick="getDeletePost(${data})">Delete Post</button>`
    $('#btn-delete-post').html(content);
    event.preventDefault();
}

function getDeletePost(id){
    $.ajax({
        url:`/posts/${id}`,
        type:"DELETE",
        success : getMyPostByPage
    })
    event.preventDefault();
}

function getEditForm(data) {
    $.ajax({
        url:`/posts/edit/${data}`,
        type:"GET",
        success : showFormEdit
    })
}

function showFormEdit(data){
    $('#idPost').val(data.id);
    $('#postContent').val(data.content);
    $('#postShowImg').attr("src",data.img)
}

function saveEditPost(){
    let form = $('#editPost')[0];
    let data = new FormData(form);
    data.append('user', currentUser.id);
    let id = $('#idPost').val();
    // goi ajax
    $.ajax({
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        type: "PUT",
        //tên API
        url: `/posts/${id}`,

        //xử lý khi thành công
        success: function () {
            getMyPostByPage()
        }
    });
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}


function showPost(data) {
    content = "";
    if (data.length <= (page * postPerPage)) {
        for (let i = 0; i < data.length; i++) {
            content += getPost(data[i]);
        }
    } else {
        for (let i = 0; i < page * postPerPage; i++) {
            content += getPost(data[i]);
        }
    }
    document.getElementById('postField').innerHTML = content;
}
