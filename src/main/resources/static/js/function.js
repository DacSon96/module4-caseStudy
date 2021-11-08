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
function getUserAvatar(){
    let avatarElement= document.getElementsByClassName("currentUserAvatar");
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
                        <select class="form-select" aria-label="Default select example">
                            <option selected>select</option>
                            <option value="1">Edit</option>
                            <option value="2">Delete</option>
                        </select>
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
